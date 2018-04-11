/**
 * Created by zengzhipeng on 2017/9/16.
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
        $('#loader-wrapper').css("display", "block");
        T.common.ajax.request("WeconBox", "driveraction/getdriverlist", params, function (data, code, msg) {
            if (code == 200) {
                $scope.paginationConf.totalItems = data.page.totalRecord;
                $scope.pushlist = data.page.list;
                $scope.$apply();
                $('#loader-wrapper').css("display", "none");
            }
        }, function () {
            $('#loader-wrapper').css("display", "none");
        });
    }
    $scope.methods = {
        edit: function (model) {
            location.href = "driver-info.html?id=" + model.driver_id;
        },
        delete: function (model) {
            swal({
                title: "确认要删除此驱动吗?",
                icon: "warning",
                buttons: true,
                dangerMode: true,
                buttons: ["取消", "确定"],
            }).then(function (isok) {
                if (isok) {
                    var params = {
                        id: model.driver_id
                    }
                    T.common.ajax.request("WeconBox", "driveraction/deldriver", params, function (data, code, msg) {
                        if (code == 200) {
                            swal({
                                title: "删除成功",
                                icon: "success",
                            });
                            $scope.getList($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
                        }
                    }, function () {
                    });
                }
            });
        }
    }

    $scope.addNew = function () {
        location.href = "driver-info.html";
    }

    $scope.addNewBatch = function () {
        location.href = "driver-batch.html";
    }
})