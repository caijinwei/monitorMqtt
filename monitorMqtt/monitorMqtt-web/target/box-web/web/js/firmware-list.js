/**
 * Created by zengzhipeng on 2017/9/14.
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
    $scope.getList = function (pageIndex, pageSize) {
        if (pageIndex == 0)
            pageIndex = 1;
        var params = {
            pageIndex: pageIndex,
            pageSize: pageSize
        };
        var fields = $('#search-div .form-control');
        for (var i = 0; i < fields.length; i++) {
            var f = $(fields[i]);
            params[f.attr('id')] = f.val();
        }
        T.common.ajax.request("WeconBox", "firmwareaction/getfirmwarelist", params, function (data, code, msg) {
            if (code == 200) {
                $scope.paginationConf.totalItems = data.page.totalRecord;
                $scope.pushlist = data.page.list;
                $scope.$apply();
            }
        }, function () {
        });
    }
    $scope.methods = {
        edit: function (model) {
            location.href = "firmware-info.html?id=" + model.firmware_id;
        },
        delete: function (model) {
            if (confirm("确认要删除此固件吗")) {
                var params = {
                    id: model.firmware_id
                }
                T.common.ajax.request("WeconBox", "firmwareaction/delfirmware", params, function (data, code, msg) {
                    if (code == 200) {
                        alert("删除成功");
                        $scope.getList($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
                    }
                }, function () {
                });
            }
        }
    }

    $scope.addNew = function () {
        location.href = "firmware-info.html";
    }
})