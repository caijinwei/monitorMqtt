/**
 * Created by zengzhipeng on 2017/8/3.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        T.common.user.checkAuth();

        //拖拽方法
        $("[data-toggle='tooltip']").tooltip();

        //            右键菜单初始化
        context.init({
            fadeSpeed: 100,
            filter: function ($obj) {
            },
            above: 'auto',
            preventDoubleContext: true,
            compress: false
        });

        T.common.ajax.request('WeconBox', "user/userinfo", new Object(),
            function (data, code, msg) {
                if (code == 200) {
                    $scope.username = data.username;
                    $scope.type = data.type;
                    $scope.$apply();
                    $("i[name='i_box']").tooltip();
                    if (data.type == 1) {
                        $scope.searchbox();
                    } else if (data.type == 2) {
                        $('#overviewtab li').click(function () {
                            $('#overviewtab li').removeClass('active');
                            $('#overviewtab li span').removeAttr('style');
                            $(this).addClass('active');
                            $(this).find('span').css('color', 'white');
                        });
                    } else {
                        $('#hosttab li').click(function () {
                            $('#hosttab li').removeClass('active');
                            $('#hosttab li span').removeAttr('style');
                            $(this).addClass('active');
                            $(this).find('span').css('color', 'white');
                        });
                    }

                } else {
                    alert(code + " " + msg);
                }
            });

        var ht = $(window).height();//获取浏览器窗口的整体高度；
        $("#ifmain").height(ht - 5);
    }
    //右键添加菜单
    function attachContext(selector, map) {
        context.attach(selector, [{
            text: "地图定位",
            href: 'web/html/box-map.html?selAlarm=0&map=' + map,
            target: 'content_frame',
            action: function () {
            }
        }, {
            text: "报警设备",
            href: 'web/html/box-map.html?selAlarm=1&map=' + map,
            target: 'content_frame',
            action: function () {
            }
        }
        ])
    };

    $scope.logout = function () {
        T.common.user.checkAuth();
        T.common.ajax.request('WeconBox', "user/signout", new Object(),
            function (data, code, msg) {
                location = "web/html/user/login.html";
            });
    }

    /*
     * 添加盒子 1）判定条件： 1。盒子序列号 密码 别名 是否存在 2.是否是管理员 3.改盒子是否已经绑定了盒子
     * 4.如果有选择盒子就添加到表acoount_dir_rel中 -如果没有就放在默认分组中
     *
     */
    $scope.boundBox = function () {
        if ($("#acc_dir_id").val() == "" || $("#machine_code").val() == ""
            || $("#dev_password").val() == "" || $("#dev_name").val() == "") {
            alert("必填参数没有填写完整");
            return false;
        }

        /*
         * 如果选择其他行业   去读输入框值
         * */
        var deviceUseCode = $("#deviceUse").val();
        var deviceUseName = "";
        if (deviceUseCode == 999) {
            deviceUseName = $("#otherDeviceUseName").val();
            if (deviceUseName == "" || deviceUseName == undefined) {
                alert("其他行业参数未填写！");
                return false;
            }
        } else {
            deviceUseName = $("#deviceUse").text();
        }

        var params = {
            deviceUseCode: deviceUseCode,
            deviceUseName: deviceUseName,
            acc_dir_id: $("#acc_dir_id").val(),
            machine_code: $("#machine_code").val(),
            name: $("#dev_name").val(),
            password: $("#dev_password").val()
        }
        T.common.ajax.request('WeconBox', "baseInfoAction/boundBox", params,
            function (data, code, msg) {
                if (code == 200) {
                    alert("PIBox绑定成功");
                    $("#addPIBox").modal("hide");
                    $scope.searchbox();
                    $scope.clearInput();
                } else {
                    alert(code + "-" + msg);
                }
            }, function () {
                console.log("ajax error");
            });
    }
    /**
     * 查询盒子列表
     */
    $scope.searchbox = function () {
        T.common.ajax.request("WeconBox", "baseInfoAction/getBoxGroup",
            new Object(), function (data, code, msg) {
                if (code == 200) {
                    $scope.allDatas = data.allData;
                    $scope.deviceDatas = data.allData.deviceList;
                    $scope.$apply();
                    $("i[name='i_box']").tooltip();

                    //绑定左侧盒子搜索的输入监听
                    $('#searchinput').bind('input propertychange', function searchPIBox() {
                        var keyWord = $('#searchinput').val();
                        if (keyWord == "") {
                            T.common.ajax.request("WeconBox", "baseInfoAction/getBoxGroup",
                                new Object(), function (data, code, msg) {
                                    if (code == 200) {
                                        $scope.allDatas = data.allData;
                                        $scope.deviceDatas = data.allData.deviceList;
                                        $scope.$apply();
                                        $("i[name='i_box']").tooltip();
                                    }
                                });
                            return;
                        }
                        var boxList = $scope.getBoxList();
                        var boxIds = $scope.getBoxIds();
                        var searchBoxIds = $scope.searchByRegExp(keyWord, boxList, boxIds);
                        $scope.showSearchItems(searchBoxIds);
                    });
                    $("#side_nav li :gt(1) a").each(
                        function () {
                            var id = $(this).prop("id");
                            var map = $(this).attr("map");
                            attachContext("#" + id, map);
                        });

                } else {

                    alert(code + "-" + msg);
                }
            }, function () {
                console.log("ajax error");
            });
    }

    $scope.sideclick = function (model) {
        $ul = $('#dir_ul_' + model.accountdirId);
        $span = $('#spanid_' + model.accountdirId);
        console.log($ul);
        if ($ul.css("display") == "none") {
            $ul.css("display", "block");
            $span.attr("class", "glyphicon glyphicon-folder-open");
        } else {
            $ul.css("display", "none");
            $span.attr("class", "glyphicon glyphicon-folder-close");
        }
    }
    $scope.selectbox = function (boxid) {
        console.log("dss");
        $('#side_nav li ul a').removeClass('active');
        $('#customid').removeClass('custom-style');
        $('#customspanid').attr("style", 'color:#828e9a;');
        $('#' + boxid).addClass('active');

    }
    $scope.custom = function () {

        $('#side_nav li ul a').removeClass('active');
        $('#side_nav li ul').css("display", "none");
        $('#side_nav li a span').attr("class",
            "glyphicon glyphicon-folder-close");
        $('#customid span').attr("class", 'glyphicon glyphicon-eye-open');
        $('#customid').addClass('custom-style');
        $('#customspanid').attr("style", 'color: white;');

    }

    /*
     * 绑定PIBox表单中展示几个分组
     */
    $scope.getRefList = function () {
        $("#otherDeviceUseName").hide();
        var params = {
            type: "0"
        }
        T.common.ajax.request("WeconBox", "userdiract/getuserdirs", params,
            function (data, code, msg) {
                if (code == 200) {
                    $scope.deviceUseOptions=data.deviceUseOptions;
                    $scope.refList = data.list;
                    $scope.$apply();
                } else {
                    alert(code + " " + msg);
                }
            }, function () {
                console.log("ajax error");
            });
    }

    $scope.clearInput = function () {
        $("#machine_code").val("");
        $("#dev_password").val("");
        $("#dev_name").val("");
    }
    /*
     * 拖拽用户分组（用户分组的修改） @RequestParam("target_acc_dir_id") Integer
     * targetAccDirId, @RequestParam("target_ref_id") Integer targetRefId,
     * @RequestParam("from_acc_dir_id") Integer fromAccDirId,
     * @RequestParam("from_ref_id") Integer fromRefId)
     */
    $scope.dragToUpdateDir = function (target_acc_dir_id, target_ref_id,
                                       from_acc_dir_id, from_ref_id) {

        var params = {
            target_acc_dir_id: target_acc_dir_id,
            target_ref_id: target_ref_id,
            from_acc_dir_id: from_acc_dir_id,
            from_ref_id: from_ref_id
        }
        console.log(params);
        T.common.ajax.request("WeconBox", "baseInfoAction/dragToUpdateDir",
            params, function (data, code, msg) {
                if (code == 200) {
                } else {
                    alert(code + " " + msg);
                }
            }, function () {
                console.log("ajax error");
            });

    }

    ///*
    // * 展示行业应用的option
    // * */
    //$scope.getDeviceUseOptions = function () {
    //    var parmas = {};
    //    T.common.ajax.request("WeconBox", "baseInfoAction/getDeviceUseOptions", parmas, function (data, code, msg) {
    //        if (code == 200) {
    //            $scope.deviceUseOptions = data.data;
    //        }
    //        else {
    //            alert(code + "-" + msg);
    //        }
    //    }, function () {
    //        console.log("ajax error");
    //    });
    //}

    /**
     * 搜索盒子
     */
    $scope.searchPIBox = function () {
        var keyWord = $('#searchinput').val();
        var boxList = $scope.getBoxList();
        var boxIds = $scope.getBoxIds();
        var searchBoxIds = $scope.searchByRegExp(keyWord, boxList, boxIds);
        $scope.showSearchItems(searchBoxIds);
    }
    /**
     * 获取盒子名称的值的集合
     * @returns {Array}
     */
    $scope.getBoxList = function () {
        var boxList = new Array();
        var i = 0;
        $('#side_nav').find('li ul a').each(function () {
            boxList[i++] = $(this).text();
        });
        return boxList;
    }
    /**
     * 获取所有盒子元素的ID
     * @returns {Array}
     */
    $scope.getBoxIds = function () {
        var boxIds = new Array();
        var i = 0;
        $('#side_nav').find('li ul a').each(function () {
            boxIds[i++] = $(this).attr('id');
        });
        return boxIds;
    }
    /**
     * 正则匹配
     * @param keyWord
     * @param list
     * @param boxIds
     * @returns {Array}
     */
    $scope.searchByRegExp = function (keyWord, list, boxIds) {
        if (!(list instanceof Array)) {
            return;
        }
        var len = list.length;
        var arr = [];
        var reg = new RegExp(keyWord);
        for (var i = 0; i < len; i++) {
            //如果字符串中不包含目标字符会返回-1
            if (list[i].match(reg)) {
                arr.push(boxIds[i]);
            }
        }
        return arr;
    }
    /**
     * 显示被搜索项
     * @param boxIds
     */
    $scope.showSearchItems = function (boxIds) {
        $('#side_nav').find('li a').each(function () {
            $(this).find('span').attr('class', 'glyphicon glyphicon-folder-close');
        })
        $('#side_nav').find('li ul').each(function () {
            $(this).css('display', 'none');
            $(this).children().css('display', 'none');
        });
        for (var i = 0; i < boxIds.length; i++) {
            $("#" + boxIds[i]).parent().css('display', 'block');
            $("#" + boxIds[i]).css('display', 'block');
            $("#" + boxIds[i]).parent().prev('a').find('span').attr('class', 'glyphicon glyphicon-folder-open');
        }
    }

});
var fromDirId;
// 允许拖拽
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("Text", ev.target.id);
    fromDirId = $(ev.target).parent().attr("sid");
}
function drop(ev) {

    ev.preventDefault();
    var data = ev.dataTransfer.getData("Text");
    // 先获取到ev.target的父节点，即分组a
    var parent = $(ev.target).parent();
    // 再获取到父节点的兄弟节点，即子分组ul
    var next = $(parent).next();
    // 再往子分组ul中追加拖放的元素
    next.append($(document.getElementById(data)));

    // 调用后台方法
    var appElement = document.querySelector('[ng-controller=infoController]');
    var $scope = angular.element(appElement).scope();


    $scope.dragToUpdateDir($('#' + data).parent().attr("sid"), $('#' + data)
        .attr("data_devid"), fromDirId, $('#' + data).attr("data_devid"));
}

/**
 * 重新加载分组
 */
function reloadBoxList() {
    var appElement = document.querySelector('[ng-controller=infoController]');
    var $scope = angular.element(appElement).scope();
    $scope.searchbox();
}

/*
 * 是否选择了其他行业;
 *   是  展示input框
 * */
var isOtherOption = function () {
    if ($("#deviceUse").val() == 999) {
        $("#otherDeviceUseName").show();
    } else {
        $("#otherDeviceUseName").hide();
    }
}

