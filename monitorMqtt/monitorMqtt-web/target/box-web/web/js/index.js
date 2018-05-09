/**
 * Created by zengzhipeng on 2018/1/23.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        T.common.user.checkAuth();

        T.common.ajax.request('WeconBox', "user/userinfo", new Object(),
            function (data, code, msg) {
                if (code == 200) {
                    $scope.username = data.username;
                    $scope.type = data.type;
                    $scope.accountId = data.accountId;
                    $scope.$apply();

                    //正常管理员
                    if (data.type == 1) {
                        $scope.showServerList();
                        //serverList
                        $("#ifmain").attr('src', 'web/html/overview.html');
                        //超管
                    } else if (data.type == 0) {

                        $('#hosttab li').click(function () {
                            $('#hosttab li').removeClass('active');
                            $('#hosttab li span').removeAttr('style');
                            $(this).addClass('active');
                            $(this).find('span').css('color', 'white');
                        });
                    }

                } else {
                    swal({
                        title: code + " " + msg,
                        icon: "error"
                    });
                }
            });


    }

    //获取该用户下的serverList
    $scope.showServerList = function(){
        var params = {
            account_id:$scope.accountId
        }
        T.common.ajax.request('WeconBox', "mqttConfig/accServerList", params,
            function (data, code, msg) {
                if (code == 200) {
                    $scope.serverList = data.mqttServerList;
                    $scope.$apply();


                } else {
                    swal({
                        title: code + " " + msg,
                        icon: "error"
                    });
                }
            });

    }

    $scope.logout = function () {
        T.common.user.checkAuth();
        T.common.ajax.request('WeconBox', "user/signout", new Object(),
            function (data, code, msg) {
                location = "web/html/user/login.html";
            });
    }

    //addMqttServer

    $scope.addMqttConfig = function() {

        var serverId = "0";
        var serverName = $("#serverNameInput").val();
        var username = $("#usernameInput").val();
        var password = $("#passwordInput").val();
        var isSsl = 1;
        var serverIP = $("#serverIPInput").val();
        var port = $("#portInput").val();
        var maxConn = $("#maxConnInput").val();

        if (serverId == "" || serverName == "" || password == "" || isSsl == "" || serverIP == "" || port == "" || maxConn == "") {
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
                isSsl: isSsl
            }

            T.common.ajax.request('WeconBox', "mqttConfig/updateMqttConfig", params,
                function (data, code, msg) {
                    if (code == 200) {
                        $scope.showServerList();
                        $scope.$apply();
                    } else {
                        swal({
                            title: code + " " + msg,
                            icon: "error"
                        });
                    }
                });
        }
    }

});

