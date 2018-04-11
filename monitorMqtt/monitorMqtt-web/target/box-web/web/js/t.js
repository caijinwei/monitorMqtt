/**
 * Created by Administrator on 2017/7/19.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: 2,
            totalItems: $scope.count,
            pagesLength: 15,
            perPageOptions: [5, 10, 20, 50, 100],
            //rememberPerPage: 'perPageItems',
            onChange: function () {
                if (this.currentPage != 0) {
                    $scope.getList(this.currentPage, this.itemsPerPage);
                }
            }
        }

        $scope.paginationConf2 = {
            currentPage: 1,
            itemsPerPage: 10,
            totalItems: $scope.count,
            pagesLength: 15,
            perPageOptions: [5, 10, 20, 50, 100],
            //rememberPerPage: 'perPageItems',
            onChange: function () {
                if (this.currentPage != 0) {
                    $scope.getList2(this.currentPage, this.itemsPerPage);
                }
            }
        }
    }

    $scope.paginationConf = {
        totalItems: $scope.count,
    }

    $scope.getList = function (pageIndex, pageSize) {
        if (pageIndex == 0)
            pageIndex = 1;
        $scope.paginationConf.totalItems = 15;
        var list = new Array();
        ;
        for (var i = 0; i < pageSize; i++) {
            var item = {id: i, name: "11111-name-" + i + ",pageIndex=" + pageIndex};
            list.push(item);
        }
        $scope.pushlist = list;
    }

    $scope.paginationConf2 = {
        totalItems: $scope.count,
    }

    $scope.getList2 = function (pageIndex, pageSize) {
        if (pageIndex == 0)
            pageIndex = 1;
        $scope.paginationConf2.totalItems = 15;
        var list = new Array();
        ;
        for (var i = 0; i < pageSize; i++) {
            var item = {id: i, name: "22222-name-" + i + ",pageIndex=" + pageIndex};
            list.push(item);
        }
        $scope.pushlist2 = list;
    }

    /**
     * 提交接口请求
     */
    $scope.act_submit = function () {
        var params = $.parseJSON($("#act_params").val());
        T.common.ajax.request("WeconBox", $("#act_url").val(), params, function (data, code, msg) {
            if (code == 200) {
//                    console.log(data);
                $("#act_ret").val(JSON.stringify(data, null, '\t'));
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    $scope.ws_clear = function () {
        $("#wsLog").empty();
        $scope.ws_log("Clear :)");
    }
    $scope.ws_log = function (data) {
        $('#wsLog').prepend('<p>' + data + '</p>');
    }

    var ws;// websocket实例
    var lockReconnect = false;// 避免重复连接
    $scope.createWebSocket = function() {
        try {
            ws = new WebSocket(T.common.requestUrl['WeconBoxWs'] + 'wstest-websocket/websocket?' + T.common.websocket.getParams());
            $scope.ws_log('>>>run createWebSocket');
            $scope.initEventHandle();
        } catch (e) {
            $scope.reconnect();
        }
    }
    $scope.initEventHandle = function() {
        ws.onclose = function() {
            $scope.ws_log(">>>run initEventHandle ws.onclose");
            $scope.reconnect();
        };
        ws.onerror = function() {
            $scope.ws_log(">>>run initEventHandle ws.onerror");
            $scope.reconnect();
        };
        ws.onopen = function() {
            $scope.ws_log(">>>run initEventHandle ws.onopen");
            var params = $.parseJSON($("#wsMsg").val());
            ws.send(angular.toJson(params));

            // 心跳检测重置
            //heartCheck.reset().start();
        };
        ws.onmessage = function(evt) {
            $scope.ws_log(">>>run initEventHandle ws.onmessage");
            $scope.ws_log('[server message] ->' + evt.data);
            console.log(evt);

            // 如果获取到消息，心跳检测重置
            // 拿到任何消息都说明当前连接是正常的
            //heartCheck.reset().start();
        }
    }

    $scope.reconnect = function() {
        $scope.ws_log(">>>run reconnect");
        /*if (lockReconnect)
            return;
        lockReconnect = true;
        // 没连接上会一直重连，设置延迟避免请求过多
        setTimeout(function() {
            $scope.createWebSocket();
            lockReconnect = false;
        }, 2000);*/
    }

    // 心跳检测
    var heartCheck = {
        timeout : 60000,// 60秒
        timeoutObj : null,
        serverTimeoutObj : null,
        reset : function() {
            clearTimeout(this.timeoutObj);
            clearTimeout(this.serverTimeoutObj);
            return this;
        },
        start : function() {
            var self = this;
            this.timeoutObj = setTimeout(function() {
                // 这里发送一个心跳，后端收到后，返回一个心跳消息，
                // onmessage拿到返回的心跳就说明连接正常
                var params = {
                    markid : -1,
                };
                ws.send(angular.toJson(params));
                /*
                 * self.serverTimeoutObj = setTimeout(function ()
                 * {// 如果超过一定时间还没重置，说明后端主动断开了 ws.close();//
                 * 如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect //
                 * 会触发onclose导致重连两次 }, self.timeout)
                 */
            }, this.timeout)
        }
    }


    $scope.ws_connect2 = function () {
        if ("WebSocket" in window) {
            $scope.createWebSocket();
            /*ws = new WebSocket(T.common.requestUrl['WeconBoxWs'] + 'wstest-websocket/websocket?' + T.common.websocket.getParams());
            ws.onopen = function () {
                $scope.ws_log('>>>open');
            };
            ws.onmessage = function (evt) {
                $scope.ws_log('server message ->' + evt.data);
                console.log(evt);
            };
            ws.onclose = function (evt) {
                $scope.ws_log('>>>close' + " ");
                console.log(evt);
            };
            ws.onerror = function (evt) {
                $scope.ws_log('>>>error' + " " + evt.data);
                console.log(evt);
            };*/
        } else {
            alert("WebSocket isn't supported by your Browser!");
        }
    }
    $scope.ws_send2 = function () {
        ws.send($("#wsMsg").val());
        $scope.ws_log('client message ->' + $("#wsMsg").val());
    }
    $scope.ws_close2 = function () {
        ws.close();
    }
})