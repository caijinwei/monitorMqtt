/**
 * Created by caijinw on 2017/8/8.
 */

var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    /*
     * --------------------------------------------------------基本信息------------------------------------------------------------------------------
     * */
    /*
     * 盒子基本信息展示
     * */
    $scope.onInit = function () {

        $('#loader-wrapper').css("display", "block");
        $scope.server_id = T.common.util.getParameter("server_id");
        $scope.showMqttConfig();

        $('#loader-wrapper').css("display", "none");

    }

    /*
     * 展示基本信息
     * */

    $scope.showMqttConfig = function () {
        var server_id = $scope.server_id + "";
        console.log(server_id);
        var params = {
            serverId: server_id
        }
        T.common.ajax.request("WeconBox", "mqttConfig/getMqttConfigById", params, function (data, code, msg) {
            if (code == 200) {
                $scope.mqttConfig = data.mqttConfig;

                $scope.$apply();
            } else {
                alert(code + " " + msg);
                $("#loadingModal")
                    .modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    }

    /*
     * 保存修改
     * */
    $scope.saveMqttConfig = function () {
        /*
         *  private long serverId;
         private String serverName ;
         private String username;
         private String password;
         private boolean isSsl;
         private String serverIP;
         private int port;
         private int maxConn;
         * */
        var serverId = $scope.server_id;
        var serverName = $("#serverName").val();
        var username = $("#username").val();
        var password = $("#password").val();
        var isSsl = 1;
        var serverIP = $("#serverIP").val();
        var port = $("#port").val();
        var maxConn = $("#maxConn").val();
        var websocketPort = $("#websocketPort").val();

        console.log(websocketPort+'------------');

        if (serverId == "" || serverName == "" || password == "" || isSsl == "" || serverIP == "" || port == "" || maxConn == "" ||websocketPort =="") {
            swal({title: "参数未填写！", icon: "error"});
        } else {
            var params =
            {
                serverId: serverId,
                serverName: serverName,
                username: username,
                password: password,
                serverIP: serverIP,
                port: port,
                maxConn: maxConn,
                websocketPort:websocketPort,
                isSsl:isSsl
            }
            T.common.ajax.request("WeconBox", "mqttConfig/updateMqttConfig", params, function (data, code, msg) {
                if (code == 200) {
                    $scope.showMqttConfig();
                    swal("操作成功！","","success");
                    $scope.$apply();
                } else {
                    alert(code + " " + msg);

                }
            }, function () {
                console.log("ajax error");
            });
        }
    }


    /*
     * 删除配置
     * */
    $scope.deleteBtn = function(){

        swal({
            title: "确定删除配置？",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then(function (isok) {
            if (isok) {
                $scope.deleteMqttConfig($scope.server_id);
            }
        });
    }

    $scope.deleteMqttConfig = function(mqttConfigId){

        var params ={
            serverId:mqttConfigId
        }
        T.common.ajax.request("WeconBox", "mqttConfig/delete", params, function (data, code, msg) {
            if (code == 200) {
                swal("删除成功");
                $scope.showServerNotification($scope.serverId);
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        }, function () {
            console.log("ajax error");
        });
    }

})



