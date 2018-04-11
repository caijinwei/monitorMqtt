/**
 * Created by zengzhipeng on 2017/8/8.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.getList();
    }

    $scope.getList = function () {
        $("#loadingModal").modal("show");
        var params = {
            type: "0"
        }
        T.common.ajax.request("WeconBox", "userdiract/getuserdirs", params, function (data, code, msg) {
            if (code == 200) {
                $scope.pushlist = data.list;
                $scope.$apply();
                $("#loadingModal").modal("hide");
            }
            else {
                alert(code + " " + msg);
                $("#loadingModal").modal("hide");
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
            if (confirm("确认要删除分组吗?")) {
                var params = {
                    id: model.id
                };
                T.common.ajax.request("WeconBox", "userdiract/deluserdir", params, function (data, code, msg) {
                    if (code == 200) {
                        $scope.getList();
                        $("#loadingModal").modal("hide");
                        $("#addGroup").modal("hide");
                    }
                    else {
                        alert(msg);
                        $("#loadingModal").modal("hide");
                        $("#addGroup").modal("hide");
                    }
                }, function () {
                    console.log("ajax error");
                });
            }
        }
    }

    /**
     * 提交保存
     */
    $scope.submitUserGroup = function () {
        $("#loadingModal").modal("show");
        var params = {
            name: $("#groupname").val().trim(),
            id: $("#id").val(),
            type: "0",
            device_id: "0"
        };
        if (params.name == "") {
            alert("分组名称不能为空");
            $("#loadingModal").modal("hide");
            return;
        }

        T.common.ajax.request("WeconBox", "userdiract/saveuserdir", params, function (data, code, msg) {
            if (code == 200) {
                $scope.getList();
                $("#loadingModal").modal("hide");
                $("#addGroup").modal("hide");
            }
            else {
                alert(msg);
                $("#loadingModal").modal("hide");
                $("#addGroup").modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    }
})