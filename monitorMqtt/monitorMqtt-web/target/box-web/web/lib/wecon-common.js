/**
 * author Sean
 */
(function () {
    var common =
    {
        /**
         * 全局配置
         */
        config: {
            // 请求地址
            requestUrl: {
                //Usercenter: "http://${apphost}/usercenter-web/api/",
                WeconBox: "http://localhost:8080/box-web/api/",
                //WeconBox: "http://192.168.29.186:8686/box-web/api/",
                //WeconBox: "http://49.4.4.75/box-web/api/",
                //WeconBoxWs: "ws://localhost:8080/box-web/api/",
                //WeconBoxWs: "ws://192.168.29.186:8686/box-web/api/",
                WeconBoxWs: "ws://localhost:8080/box-web/api/",
            },
            api: {
                getRequestUrl: function (urlName) {
                    var url = common.config.requestUrl[urlName];
                    if (url.indexOf("apphost") >= 0) {
                        url = url.replace("${apphost}", "localhost:8080");
                    }
                    return url;
                },
            },
        },
        /**
         * 全局用户
         */
        user: {
            api: {
                // 读取sid
                getSid: function () {
                    return localStorage.getItem("sid");
                },
                //存储sid
                setSid: function (sid) {
                    localStorage.setItem("sid", sid);
                },
                /**
                 * 验证session有效
                 */
                checkAuth: function () {
                    common.ajax._request('WeconBox', "user/check", new Object(), function (data, code, msg) {
                        if (code == 200) {
                            if (data.auth == "1") {
                                if (location.href.indexOf("/user/") > 0) {
                                    location = "../../../main.html";
                                    return;
                                }
                            } else {
                                if (location.href.indexOf("/user/") == -1) {
                                    location = "web/html/user/login.html";
                                    return;
                                }
                            }
                        }
                        else {
                            alert(code + " " + msg);
                        }
                    });
                },
                // 读取签名私钥
                getSignKey: function () {
                    return "5cee621329f24e5cbdc43daa959ce9a1";
                },
            },
        },
        /**
         * 网络请求模块
         */
        ajax: {
            ajaxobj: null,
            // 获取header
            _getHeader: function (params) {
                var header = new Object();
                // 添加默认参数
                header.cuid = "123456789";
                header.pid = "1";
                header.sv = "1.0";
                header.ts = Date.parse(new Date());
                header.mt = 255;

                var sid = common.user.api.getSid();
                if (sid != null && sid != "") {
                    header.sid = sid;
                }

                // 签名
                var keyset = new Array();
                if (params != null) {
                    for (var key in params) {
                        keyset.push(key);
                    }
                }
                for (var key in header) {
                    keyset.push(key);
                }
                keyset.sort();
                var sign = "";
                for (var i = 0; i < keyset.length; i++) {
                    var key = keyset[i];
                    if (params[key] != null && params[key] != '') {
                        sign += key + "=" + params[key] + "&";
                    }
                    if (header[key] != null && header[key] != '') {
                        sign += key + "=" + header[key] + "&";
                    }
                }
                sign += "key=" + common.user.api.getSignKey();

                header.sign = common.util.md5(sign);
                return header;
            },
            // ajax请求
            _request: function (urlName, action, params, successCallback, failCallback) {
                var header = this._getHeader(params);

                this.ajaxobj = $.ajax(
                    {
                        url: common.config.api.getRequestUrl(urlName) + action,
                        type: 'post',
                        headers: {
                            "common": JSON.stringify(header)
                        },
                        cache: 'false',
                        data: params,
                        success: function (jsonstr) {
                            try {
                                var jsonobj = JSON.parse(jsonstr);
                                var code = jsonobj.code;
                                var msg = jsonobj.msg
                                var data = jsonobj.result;
                                // 成功
                                if (code == 200) {
                                    if (successCallback) {
                                        successCallback(data, code, msg);
                                    }
                                }
                                // 参数异常
                                else if (code == 400) {
                                    //alert("400 " + msg);
                                    alert("参数配置异常");
                                    console.log("400 " + msg);
                                }
                                // session超时
                                else if (code == 403) {
                                    console.log("403 session过期,请重新登录");
                                    if (location.href.indexOf("main.html") > 0) {
                                        location = "web/html/user/login.html";
                                        return;
                                    } else {
                                        if (location.href.indexOf("box-web") > 0) {
                                            top.location = "/box-web/web/html/user/login.html";
                                        } else {
                                            top.location = "/web/html/user/login.html";
                                        }
                                        return;
                                    }
                                }
                                // 系统异常
                                else if (code == 503) {
                                    console.log("503 系统异常 ");
                                }
                                // 业务异常
                                else {
                                    if (successCallback) {
                                        successCallback(data, code, msg);
                                    }
                                }
                            }
                            catch (e) {
                                var info = "请求success, 回调错误:\n\n";
                                info += "message :" + e.message + "\n\n";
                                info += "lineNumber :" + e.lineNumber + "\n\n";
                                info += "name :" + e.name + "\n\n";
                                info += "number :" + e.number + "\n\n";
                                info += "description :" + e.description + "\n\n";
                                info += "fileName :" + e.fileName + "\n\n";
                                info += "stack :\n" + e.stack + "\n\n";
                                info += "json :\n" + jsonstr + "\n\n";
                                alert(info);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if (failCallback) {
                                failCallback(XMLHttpRequest, textStatus, errorThrown);
                            }
                            else {
                                var info = "";
                                info += "请求error, 网络请求错误:\n\n";
                                info += "url :" + common.config.api.getRequestUrl(serverIndex) + action + "\n\n";
                                info += "status :" + XMLHttpRequest.status + "\n\n";
                                info += "textStatus :" + textStatus + "\n\n";
                                alert(info);
                            }
                        },
                    });
            },
            _loadRes: function (urlName, action, callback) {
                $.ajax(
                    {
                        url: action,
                        type: 'get',
                        dataType: 'html',
                        cache: 'false',
                        success: function (jsonstr) {
                            if (callback != null) {
                                callback(jsonstr);
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            var info = "";
                            info += "网络请求错误:\n\n";
                            info += "url :" + common.config.api.getRequestUrl(serverIndex) + action + "\n\n";
                            info += "status :" + XMLHttpRequest.status + "\n\n";
                            info += "textStatus :" + textStatus + "\n\n";
                            alert(info);
                        },
                    });
            },
            api: {
                /**
                 * ajax请求
                 * urlName : 服务名
                 * action ： 接口
                 * params： 参数对象
                 * successCallback ：成功回调函数successCallback(data, code, msg);
                 * failCallback : 失败回调函数
                 */
                request: function (urlName, action, params, successCallback, failCallback) {
                    common.ajax._request(urlName, action, params, successCallback, failCallback);
                },
                loadRes: function (urlName, html, callback) {
                    common.ajax._loadRes(urlName, html, callback);
                },
                getHead: function (params) {
                    return common.ajax._getHeader(params);
                }
            },
        },
        /**
         * 工具类
         */
        util: {
            // md5加密
            md5: function (txt) {
                return $.md5(txt);
            },
            // 获取url参数
            getParameter: function (name) {
                // 如果链接没有参数，或者链接中不存在我们要获取的参数，直接返回空
                if (location.href.indexOf("?") == -1 || location.href.indexOf(name + '=') == -1) {
                    return null;
                }

                // 获取链接中参数部分
                var queryString = decodeURI(location.href.substring(location.href.indexOf("?") + 1));

                // 分离参数对 ?key=value&key2=value2
                var parameters = queryString.split("&");

                var pos, paraName, paraValue;
                for (var i = 0; i < parameters.length; i++) {
                    // 获取等号位置
                    pos = parameters[i].indexOf('=');
                    if (pos == -1) {
                        continue;
                    }

                    // 获取name 和 value
                    paraName = parameters[i].substring(0, pos);
                    paraValue = parameters[i].substring(pos + 1);

                    // 如果查询的name等于当前name，就返回当前值，同时，将链接中的+号还原成空格
                    if (paraName == name) {
                        return unescape(paraValue.replace(/\+/g, " "));
                    }
                }
                return null;
            },
        },
        /**
         * 时间工具
         */
        time: {
            /**
             * 格式化日期
             * eg: var datestr = T.common.time.format(new Date(), "yyyy-MM-dd HH:mm:ss");
             */
            format: function (date, format) {
                format = format.replace("yyyy", date.getFullYear());
                format = format.replace("MM", date.getMonth() + 1 > 9 ? date.getMonth() + 1 : "0" + (date.getMonth() + 1));
                format = format.replace("dd", date.getDate() > 9 ? date.getDate() : "0" + date.getDate());
                format = format.replace("HH", date.getHours() > 9 ? date.getHours() : "0" + date.getHours());
                format = format.replace("mm", date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes());
                format = format.replace("ss", date.getSeconds() > 9 ? date.getSeconds() : "0" + date.getSeconds());
                return format;
            },
            /**
             * 字符串转日期
             * eg: var date = T.common.time.parse("2016-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
             */
            parse: function (datestr, format) {
                datestr += "";
                var target = [0, 0, 0, 0, 0, 0];
                var sign = ["yyyy", "MM", "dd", "HH", "mm", "ss"];
                for (var i = 0; i < sign.length; i++) {
                    var idx = format.indexOf(sign[i]);
                    if (idx > -1) {
                        target[i] = parseInt(datestr.substr(idx, sign[i].length));
                    }
                }
                var date = new Date(target[0], target[1] - 1, target[2], target[3], target[4], target[5]);
                return date;
            },
            /**
             * 日期增加
             * T.common.time.add(new Date(), "M", 2);
             */
            add: function (date, field, num) {
                switch (field) {
                    case "y" :
                        date.setFullYear(date.getFullYear() + num);
                        break;
                    case "M" :
                        date.setMonth(date.getMonth() + num);
                        break;
                    case "d" :
                        date.setDate(date.getDate() + num);
                        break;
                    case "H" :
                        date.setHours(date.getHours() + num);
                        break;
                    case "m" :
                        date.setMinutes(date.getMinutes() + num);
                        break;
                    case "s" :
                        date.setSeconds(date.getSeconds() + num);
                        break;
                }
                return date;
            },
        },
        /**
         * websocket
         */
        websocket: {
            api: {
                getParams: function () {
                    var params = "sid=" + localStorage.getItem("sid") + "&ts=" + Date.parse(new Date());
                    var sign = params + "&key=" + common.user.api.getSignKey();
                    params += "&sign=" + common.util.md5(sign);
                    return params;
                }
            },
        },
    };

    var api = new Object();
    api.config = common.config.api;
    api.requestUrl = common.config.requestUrl;
    api.user = common.user.api;
    api.ajax = common.ajax.api;
    api.util = common.util;
    api.time = common.time;
    api.websocket = common.websocket.api;
    if (window.T == null) {
        window.T = new Object();
    }
    T.common = api;
})();

/**
 * 处理token跨域分享
 */
(function () {
    var token = T.common.util.getParameter("token");
    if (token != null && token != "") {
        localStorage.setItem("sid", token);
    }
});

/**
 * MD5加密
 */
(function ($) {
    var rotateLeft = function (lValue, iShiftBits) {
        return (lValue << iShiftBits) | (lValue >>> (32 - iShiftBits));
    }

    var addUnsigned = function (lX, lY) {
        var lX4, lY4, lX8, lY8, lResult;
        lX8 = (lX & 0x80000000);
        lY8 = (lY & 0x80000000);
        lX4 = (lX & 0x40000000);
        lY4 = (lY & 0x40000000);
        lResult = (lX & 0x3FFFFFFF) + (lY & 0x3FFFFFFF);
        if (lX4 & lY4)
            return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
        if (lX4 | lY4) {
            if (lResult & 0x40000000)
                return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
            else
                return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
        } else {
            return (lResult ^ lX8 ^ lY8);
        }
    }

    var F = function (x, y, z) {
        return (x & y) | ((~x) & z);
    }

    var G = function (x, y, z) {
        return (x & z) | (y & (~z));
    }

    var H = function (x, y, z) {
        return (x ^ y ^ z);
    }

    var I = function (x, y, z) {
        return (y ^ (x | (~z)));
    }

    var FF = function (a, b, c, d, x, s, ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(F(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    };

    var GG = function (a, b, c, d, x, s, ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(G(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    };

    var HH = function (a, b, c, d, x, s, ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(H(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    };

    var II = function (a, b, c, d, x, s, ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(I(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    };

    var convertToWordArray = function (string) {
        var lWordCount;
        var lMessageLength = string.length;
        var lNumberOfWordsTempOne = lMessageLength + 8;
        var lNumberOfWordsTempTwo = (lNumberOfWordsTempOne - (lNumberOfWordsTempOne % 64)) / 64;
        var lNumberOfWords = (lNumberOfWordsTempTwo + 1) * 16;
        var lWordArray = Array(lNumberOfWords - 1);
        var lBytePosition = 0;
        var lByteCount = 0;
        while (lByteCount < lMessageLength) {
            lWordCount = (lByteCount - (lByteCount % 4)) / 4;
            lBytePosition = (lByteCount % 4) * 8;
            lWordArray[lWordCount] = (lWordArray[lWordCount] | (string
                .charCodeAt(lByteCount) << lBytePosition));
            lByteCount++;
        }
        lWordCount = (lByteCount - (lByteCount % 4)) / 4;
        lBytePosition = (lByteCount % 4) * 8;
        lWordArray[lWordCount] = lWordArray[lWordCount]
            | (0x80 << lBytePosition);
        lWordArray[lNumberOfWords - 2] = lMessageLength << 3;
        lWordArray[lNumberOfWords - 1] = lMessageLength >>> 29;
        return lWordArray;
    };

    var wordToHex = function (lValue) {
        var WordToHexValue = "", WordToHexValueTemp = "", lByte, lCount;
        for (lCount = 0; lCount <= 3; lCount++) {
            lByte = (lValue >>> (lCount * 8)) & 255;
            WordToHexValueTemp = "0" + lByte.toString(16);
            WordToHexValue = WordToHexValue
                + WordToHexValueTemp.substr(WordToHexValueTemp.length - 2,
                    2);
        }
        return WordToHexValue;
    };

    var uTF8Encode = function (string) {
        string = string.replace(/\x0d\x0a/g, "\x0a");
        var output = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                output += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                output += String.fromCharCode((c >> 6) | 192);
                output += String.fromCharCode((c & 63) | 128);
            } else {
                output += String.fromCharCode((c >> 12) | 224);
                output += String.fromCharCode(((c >> 6) & 63) | 128);
                output += String.fromCharCode((c & 63) | 128);
            }
        }
        return output;
    };

    $.extend({
        md5: function (string) {
            var x = Array();
            var k, AA, BB, CC, DD, a, b, c, d;
            var S11 = 7, S12 = 12, S13 = 17, S14 = 22;
            var S21 = 5, S22 = 9, S23 = 14, S24 = 20;
            var S31 = 4, S32 = 11, S33 = 16, S34 = 23;
            var S41 = 6, S42 = 10, S43 = 15, S44 = 21;
            string = uTF8Encode(string);
            x = convertToWordArray(string);
            a = 0x67452301;
            b = 0xEFCDAB89;
            c = 0x98BADCFE;
            d = 0x10325476;
            for (k = 0; k < x.length; k += 16) {
                AA = a;
                BB = b;
                CC = c;
                DD = d;
                a = FF(a, b, c, d, x[k + 0], S11, 0xD76AA478);
                d = FF(d, a, b, c, x[k + 1], S12, 0xE8C7B756);
                c = FF(c, d, a, b, x[k + 2], S13, 0x242070DB);
                b = FF(b, c, d, a, x[k + 3], S14, 0xC1BDCEEE);
                a = FF(a, b, c, d, x[k + 4], S11, 0xF57C0FAF);
                d = FF(d, a, b, c, x[k + 5], S12, 0x4787C62A);
                c = FF(c, d, a, b, x[k + 6], S13, 0xA8304613);
                b = FF(b, c, d, a, x[k + 7], S14, 0xFD469501);
                a = FF(a, b, c, d, x[k + 8], S11, 0x698098D8);
                d = FF(d, a, b, c, x[k + 9], S12, 0x8B44F7AF);
                c = FF(c, d, a, b, x[k + 10], S13, 0xFFFF5BB1);
                b = FF(b, c, d, a, x[k + 11], S14, 0x895CD7BE);
                a = FF(a, b, c, d, x[k + 12], S11, 0x6B901122);
                d = FF(d, a, b, c, x[k + 13], S12, 0xFD987193);
                c = FF(c, d, a, b, x[k + 14], S13, 0xA679438E);
                b = FF(b, c, d, a, x[k + 15], S14, 0x49B40821);
                a = GG(a, b, c, d, x[k + 1], S21, 0xF61E2562);
                d = GG(d, a, b, c, x[k + 6], S22, 0xC040B340);
                c = GG(c, d, a, b, x[k + 11], S23, 0x265E5A51);
                b = GG(b, c, d, a, x[k + 0], S24, 0xE9B6C7AA);
                a = GG(a, b, c, d, x[k + 5], S21, 0xD62F105D);
                d = GG(d, a, b, c, x[k + 10], S22, 0x2441453);
                c = GG(c, d, a, b, x[k + 15], S23, 0xD8A1E681);
                b = GG(b, c, d, a, x[k + 4], S24, 0xE7D3FBC8);
                a = GG(a, b, c, d, x[k + 9], S21, 0x21E1CDE6);
                d = GG(d, a, b, c, x[k + 14], S22, 0xC33707D6);
                c = GG(c, d, a, b, x[k + 3], S23, 0xF4D50D87);
                b = GG(b, c, d, a, x[k + 8], S24, 0x455A14ED);
                a = GG(a, b, c, d, x[k + 13], S21, 0xA9E3E905);
                d = GG(d, a, b, c, x[k + 2], S22, 0xFCEFA3F8);
                c = GG(c, d, a, b, x[k + 7], S23, 0x676F02D9);
                b = GG(b, c, d, a, x[k + 12], S24, 0x8D2A4C8A);
                a = HH(a, b, c, d, x[k + 5], S31, 0xFFFA3942);
                d = HH(d, a, b, c, x[k + 8], S32, 0x8771F681);
                c = HH(c, d, a, b, x[k + 11], S33, 0x6D9D6122);
                b = HH(b, c, d, a, x[k + 14], S34, 0xFDE5380C);
                a = HH(a, b, c, d, x[k + 1], S31, 0xA4BEEA44);
                d = HH(d, a, b, c, x[k + 4], S32, 0x4BDECFA9);
                c = HH(c, d, a, b, x[k + 7], S33, 0xF6BB4B60);
                b = HH(b, c, d, a, x[k + 10], S34, 0xBEBFBC70);
                a = HH(a, b, c, d, x[k + 13], S31, 0x289B7EC6);
                d = HH(d, a, b, c, x[k + 0], S32, 0xEAA127FA);
                c = HH(c, d, a, b, x[k + 3], S33, 0xD4EF3085);
                b = HH(b, c, d, a, x[k + 6], S34, 0x4881D05);
                a = HH(a, b, c, d, x[k + 9], S31, 0xD9D4D039);
                d = HH(d, a, b, c, x[k + 12], S32, 0xE6DB99E5);
                c = HH(c, d, a, b, x[k + 15], S33, 0x1FA27CF8);
                b = HH(b, c, d, a, x[k + 2], S34, 0xC4AC5665);
                a = II(a, b, c, d, x[k + 0], S41, 0xF4292244);
                d = II(d, a, b, c, x[k + 7], S42, 0x432AFF97);
                c = II(c, d, a, b, x[k + 14], S43, 0xAB9423A7);
                b = II(b, c, d, a, x[k + 5], S44, 0xFC93A039);
                a = II(a, b, c, d, x[k + 12], S41, 0x655B59C3);
                d = II(d, a, b, c, x[k + 3], S42, 0x8F0CCC92);
                c = II(c, d, a, b, x[k + 10], S43, 0xFFEFF47D);
                b = II(b, c, d, a, x[k + 1], S44, 0x85845DD1);
                a = II(a, b, c, d, x[k + 8], S41, 0x6FA87E4F);
                d = II(d, a, b, c, x[k + 15], S42, 0xFE2CE6E0);
                c = II(c, d, a, b, x[k + 6], S43, 0xA3014314);
                b = II(b, c, d, a, x[k + 13], S44, 0x4E0811A1);
                a = II(a, b, c, d, x[k + 4], S41, 0xF7537E82);
                d = II(d, a, b, c, x[k + 11], S42, 0xBD3AF235);
                c = II(c, d, a, b, x[k + 2], S43, 0x2AD7D2BB);
                b = II(b, c, d, a, x[k + 9], S44, 0xEB86D391);
                a = addUnsigned(a, AA);
                b = addUnsigned(b, BB);
                c = addUnsigned(c, CC);
                d = addUnsigned(d, DD);
            }
            var tempValue = wordToHex(a) + wordToHex(b) + wordToHex(c)
                + wordToHex(d);
            return tempValue.toLowerCase();
        }
    });
})(jQuery);


/**
 * 增加命名空间功能
 *
 * 使用方法：registNamespace('aaa.bbb.ccc','aaa.bbb.ddd');
 */
var registNamespace = function (ns) {
    ///<summary>增加命名空间功能,使用方法：commonUtils.registNamespace('aaa.bbb.ccc','aaa.bbb.ddd');</summary>
    var o = {}, d;
    for (var i = 0; i < arguments.length; i++) {
        d = arguments[i].split(".");
        o = window[d[0]] = window[d[0]] || {};
        for (var k = 0; k < d.slice(1).length; k++) {
            o = o[d[k + 1]] = o[d[k + 1]] || {};
        }
    }
    return o;
};


/*
* 获取当前时间
* */
var getCurrentDate = function () {
    ///<summary>获取当前时间</summary>
    var currentdate = new Date();
    //.format("yyyy-MM-dd hh:mm:ss")
    //.format("yyyy-MM-dd hh:mm:ss")
    return currentdate;
}

