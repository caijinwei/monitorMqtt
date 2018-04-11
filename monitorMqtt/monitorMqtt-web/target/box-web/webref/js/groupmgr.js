/**
 * Created by zengzhipeng on 2017/8/8.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.getList();
    }

    $scope.getList = function () {
        var params = {
            type: "0"
        }
        T.common.ajax.request("WeconBox", "userdiract/getuserdirs", params, function (data, code, msg) {
            if (code == 200) {
                $scope.pushlist = data.list;
                $scope.$apply();
            }
            else {
                swal({
                    title: code + " " + msg,
                    icon: "warning"
                });
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /**
     * 打开新增窗口
     */
    $scope.showaddmodal = function () {
        $("#addGroup").modal('show');
        $scope.grouptitle = "添加分组";
        $scope.groupbtn = "添加";
        $("#groupname").val("");
        $("#id").val("0");
    }

    $scope.methods = {
        edit: function (model) {
            $("#addGroup").modal('show');
            $scope.grouptitle = "更新分组";
            $scope.groupbtn = "更新";
            $("#groupname").val(model.name);
            $("#id").val(model.id);
        },
        del: function (model) {
            swal({
                title: "您确定删除该分组吗?",
                icon: "warning",
                buttons: true,
                dangerMode: true,
                buttons: ["取消", "确定"],
            }).then(function(isok) {
                if (isok ) {
                    var params = {
                        id: model.id
                    };
                    T.common.ajax.request("WeconBox", "userdiract/deluserdir", params, function (data, code, msg) {
                        if (code == 200) {
                            $scope.getList();
                            $("#addGroup").modal("hide");
                        }
                        else {
                            swal({
                                title: msg,
                                icon: "error"
                            });
                            $("#addGroup").modal("hide");
                        }
                    }, function () {
                        console.log("ajax error");
                    });
                }
            });
        }
    }

    /**
     * 提交保存
     */
    $scope.submitUserGroup = function () {
        var params = {
            name: $("#groupname").val().trim(),
            id: $("#id").val(),
            type: "0",
            device_id: "0"
        };
        if (params.name == "") {
            swal({
                title: "分组名称不能为空",
                icon: "warning"
            });
            return;
        }

        T.common.ajax.request("WeconBox", "userdiract/saveuserdir", params, function (data, code, msg) {
            if (code == 200) {
                $scope.getList();
                $("#addGroup").modal("hide");
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
                $("#addGroup").modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    }
})