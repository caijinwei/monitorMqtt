var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.act_submit();
    }

    var t;

    /**
     * 提交接口请求
     */
    $scope.act_submit = function act_submit() {

        var params = {
            machine_code: $("#machine_code_search").val()
        };

        T.common.ajax.request("WeconBox", "testact/getActDate", params, function (data, code, msg) {
            if (code == 200) {
                if (data.piBoxActDateMode == null) {
                    alert("查不到此机器码数据");
                    $scope.actDatas = null;
                    $scope.act_time_data_list = null;
                    $scope.addr_list = null;
                    $scope.$apply();
                    return;
                }
                $scope.actDatas = data.piBoxActDateMode;
                $scope.act_time_data_list = data.piBoxActDateMode.act_time_data_list;
                $scope.addr_list = data.piBoxActDateMode.act_time_data_list.addr_list;
                $scope.$apply();
                t = setTimeout(act_submit, 3000);
            }
            else {

                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    $scope.mc_change = function () {
        clearTimeout(t);
    }

    $scope.putMess = function () {
        var params = {
            machine_code: $("#machine_code").val(),
            com: $("#com").val(),
            addr: $("#addr").val(),
            value: $("#value").val(),
            addr_id: $("#addr_id").val()

        };
        T.common.ajax.request("WeconBox", "testact/putMess", params, function (data, code, msg) {
            if (code == 200) {
                alert("消息发送成功");
            }
            else {

                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }


});