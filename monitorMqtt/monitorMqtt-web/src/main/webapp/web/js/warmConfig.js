/**
 * Created by zengzhipeng on 2017/8/28.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $("#loadingModal").modal("show");

        $scope.serverId = T.common.util.getParameter("server_id");
        $scope.showServerNotification($scope.serverId);

        $("#loadingModal").modal("hide");
    }
    /*
     * 新增报警配置
     * */
    $scope.addNotificationBtn = function () {
        //$("#loadingModal").modal("show");
        $("#addConfig").modal("show");

        $("#notificationIdInput").val("");
        //$("#loadingModal").modal("hide");
    }

    $scope.addNotification = function () {
        var notificationName = $("#notificationName").val();
        var notificationNumber = $("#notificationNumber").val();
        var maxTime = $("#maxTime").val();
        var type = $("#notificationType").val();
        if (notificationName == "" || notificationNumber == "" || maxTime == "" || type == "") {
            swal({title: "参数未填写！", icon: "error"});
        }else {
            var params = {
                serverId: $scope.serverId,
                name: $("#notificationName").val(),
                number: $("#notificationNumber").val(),
                maxTime: $("#maxTime").val(),
                type: $("#notificationType").val(),
                notificationId:'0'
            }
            T.common.ajax.request("WeconBox", "notification/update", params, function (data, code, msg) {
                if (code == 200) {
                    console.log("------------------>>>>>>>>>>>>>>>>>>>>>>>>>--------")
                    $scope.showServerNotification($scope.serverId);
                    $("#addConfig").modal("hide");
                }
                else {
                    swal({title: msg, icon: "error"});
                }
            }, function () {
                console.log("ajax error");
            });
        }
    }

    /*
    * 删除报警配置
    * */
    $scope.deleteBtn = function(notificationId){
        swal({
            title: "确定删除配置？",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then(function (isok) {
            if (isok) {
                $scope.deleteNotification(notificationId);
            }
        });
    }

    $scope.deleteNotification = function(notificationId){

        var params ={
            notificationId:notificationId
        }
        T.common.ajax.request("WeconBox", "notification/delete", params, function (data, code, msg) {
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


    /*
     * 展示改该服务器下的报警配置
     * */
    $scope.showServerNotification = function (serverId) {

        var msg = "获取报警配置列表失败！";
        if (serverId === "" || serverId == undefined) {
            swal({
                title: msg,
                icon: "error"
            });
        } else {
            var params = {
                serverId: serverId

            }
            T.common.ajax.request("WeconBox", "notification/showAllNotification", params, function (data, code, msg) {
                if (code == 200) {
                    $scope.notificationList = data.list;
                    $scope.serverName = data.serverName;
                    $scope.$apply();
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
    }


    /*
     * 修改用户信息密码
     * */
    $scope.chgPwdSubmit = function () {
        var newPwdInput = $("#newPwdInput").val();
        if (newPwdInput == undefined || newPwdInput == "") {
            return;
        }
        newPwdInput = T.common.util.md5(newPwdInput);
        var params = {
            account_id: $scope.selectedAccountId,
            password: newPwdInput,
            state: ""
        }
        T.common.ajax.request("WeconBox", "user/chgInfo", params, function (data, code, msg) {
            if (code == 200) {
                $scope.getList($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
                $("#newWinModal").modal("hide");
                swal({
                    title: "设置成功",
                    icon: "success"
                });
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
});