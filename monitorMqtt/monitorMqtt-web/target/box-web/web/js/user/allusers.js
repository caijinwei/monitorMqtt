/**
 * Created by zengzhipeng on 2017/8/28.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: 10,
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
    }

    $scope.paginationConf = {
        totalItems: $scope.count,
    }
    /**
     * 查询数据
     * @param pageIndex
     * @param pageSize
     */
    $scope.getList = function (pageIndex, pageSize) {
        if (pageIndex == 0)
            pageIndex = 1;
        $("#loadingModal").modal("show");
        var params = {
            pageIndex: pageIndex,
            pageSize: pageSize
        };
        var fields = $('#search-div .form-control');
        for (var i = 0; i < fields.length; i++) {
            var f = $(fields[i]);
            params[f.attr('id')] = f.val();
        }
        if (params["account_id"] == "") {
            params["account_id"] = "-1";
        }
        T.common.ajax.request("WeconBox", "user/getallusers", params, function (data, code, msg) {
            if (code == 200) {
                $scope.paginationConf.totalItems = data.page.totalRecord;
                $scope.pushlist = data.page.list;
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
        $("#addViewAccount").modal('show');
        $("#username").val("");
        $("#password").val("");
    }
    /**
     * 新增视图帐号
     */
    $scope.addviewuser = function () {
        $("#loadingModal").modal("show");
        var params = {
            username: $("#username").val().trim(),
            password: T.common.util.md5($("#password").val().trim())
        };
        if ($('#state').prop("checked")) {
            params['state'] = "1";
        } else {
            params['state'] = "0";
        }
        T.common.ajax.request("WeconBox", "user/addviewuser", params, function (data, code, msg) {
            if (code == 200) {
                $scope.getList($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
            }
            else {
                alert(msg);
            }
            $("#loadingModal").modal("hide");
            $("#addViewAccount").modal("hide");
        }, function () {
            console.log("ajax error");
        });
    }

    $scope.methods = {
        updstate: function (model) {
            var confirm_msg = "确认要做此操作吗?";
            if (model.state == 1) {
                confirm_msg = "确认要禁用此用户吗?";
            } else {
                confirm_msg = "确认要启用此用户吗?";
            }
            if (confirm(confirm_msg)) {
                var params = {
                    user_id: model.account_id
                };
                if (model.state == 1) {
                    params["state"] = "0";
                } else if (model.state == 0) {
                    params["state"] = "1";
                }
                T.common.ajax.request("WeconBox", "user/chgviewuserstate", params, function (data, code, msg) {
                    if (code == 200) {
                        $scope.getList($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
                        $("#loadingModal").modal("hide");
                    }
                    else {
                        alert(msg);
                        $("#loadingModal").modal("hide");
                    }
                }, function () {
                    console.log("ajax error");
                });
            }
        },
        viewpoint0: function (model) {
            location = "viewpoint.html?type=0&viewid=" + model.account_id + "&name=" + model.username;
        },
        viewpoint1: function (model) {
            location = "viewpoint.html?type=1&viewid=" + model.account_id + "&name=" + model.username;
        },
        viewpointalarm: function (model) {
            location = "viewpointalarm.html?type=2&viewid=" + model.account_id + "&name=" + model.username;
        }
    }
    /*
     * 修改账户密码
     * */
    $scope.chgPwd = function (id, name) {
        $("#newWinModal").modal('show');
        $("#newPwdInput").val("");
        $scope.selectedAccountId = id;
        $scope.selecetedUsername = name;
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
                alert("设置成功");
            }
            else {
                alert(msg);
                $("#loadingModal").modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 修改用户启用状态
     * */
    $scope.chgState = function (id, name, state) {
        $("#chgAccountState").modal('show');
        $scope.selectedAccountId = id;
        $scope.selecetedUsername = name;
        $scope.selecetedState = state;
    }
    $scope.chgStateSubmit = function () {
        var state = $("#stateInput").val();
        if (state == undefined || state == "") {
            alert("修改状态失败");
            return;
        }
        var params = {
            account_id: $scope.selectedAccountId,
            state: state,
            password: ""
        }
        T.common.ajax.request("WeconBox", "user/chgInfo", params, function (data, code, msg) {
            if (code == 200) {
                $scope.getList($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
                $("#chgAccountState").modal("hide");
                alert("设置成功");
            }
            else {
                alert(msg);
                $("#chgAccountState").modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 点击管理员账户 展示该账户下的所有视图账户
     * */
    $scope.getAccountRelationByManagerAccId = function (manager_id, type) {
        if (type != '1') {
            return;
        }
        if (manager_id == $scope.manager_id) {
            var selecetedName = "manager_" + manager_id;
            var $selecedView= $("tr[name=" + selecetedName + "]");
            if ($selecedView.is(":hidden")) {
                $selecedView.show();
            } else {
                $selecedView.hide();
            }
            return;
        }
        $scope.manager_id = manager_id;
        var params = {
            manager_id: manager_id
        }
        T.common.ajax.request("WeconBox", "user/getViewIdsByManagerId", params, function (data, code, msg) {
            if (code == 200) {
                var viewAccountList = [];
                var accountRels = data.list;
                var allUser = data.allList;
                $.each(accountRels, function (name, value) {
                    //console.log(value.view_id);
                    $.each(allUser, function () {
                        if (this.account_id == value.view_id) {
                            viewAccountList.push(this);
                        }
                    });
                });
                $scope.manager_id = manager_id;
                $scope.viewAccountList = viewAccountList;
                $("#viewShow" + manager_id).show();
                $scope.$apply();
            }
            else {
                alert(msg);
                $("#chgAccountState").modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    };
});