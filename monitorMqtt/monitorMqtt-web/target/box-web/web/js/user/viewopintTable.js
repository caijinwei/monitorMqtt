/**
 * Created by caijinw on 2017/8/9.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        var viewid = T.common.util.getParameter("viewid");
        var type = T.common.util.getParameter("type");
        $scope.type = T.common.util.getParameter("type");
        $scope.conf = {
            currentPage: 1,
            itemsPerPage: 10,
            totalItems: $scope.count,
            pagesLength: 15,
            perPageOptions: [5, 10, 20, 50, 100],
            rememberPerPage: 'perPageItems',
            onChange: function () {
                if (this.currentPage != 0) {
                    $scope.getRestList(this.currentPage, this.itemsPerPage);
                }
                //全选按钮
                $scope.isChecked = 0;
                $scope.allCheck = 0;
            }
        }
        $scope.showDeviceList();
        $scope.getRestList($scope.conf.currentPage, $scope.conf.itemsPerPage);

    }
    $scope.conf = {
        totalItems: $scope.count
    }
    /*
    * 展示余下监控点
    * */
    $scope.getRestList = function (pageIndex, pageSize) {
        var type = T.common.util.getParameter("type");
        if (pageIndex == 0)
            pageIndex = 1;
        if(type!=2)
        {
            $scope.getHisRealrestList(pageIndex,pageSize);
        }else
        {
            $scope.getAlarmrestList(pageIndex,pageSize);
        }

    }
    $scope.getHisRealrestList=function(pageIndex, pageSize)
    {
        var view_id = T.common.util.getParameter("viewid");
        var type = T.common.util.getParameter("type");
        var device_id = $("#check_device_id").val();
        var params =
        {
            view_id: view_id,
            device_id: device_id,
            type: type,
            pageIndex: pageIndex,
            pageSize: pageSize
        }
        T.common.ajax.request("WeconBox", "Viewpoint/showRealHiss", params, function (data, code, msg) {
            if (code == 200) {
                $scope.conf.totalItems = data.page.totalRecord;
                $scope.resrPointDatas = data.page.list;
                $scope.$apply();
            }
            else {
                swal("code + "-" + msg","","error");
            }
        }, function () {
            console.log("ajax error");
        });
    }

    /*
    * 获取余下报警监控点
    * device_id=0 表示全部盒子的监控点
    * */
    $scope.getAlarmrestList=function(pageIndex, pageSize)
    {
        var view_id = T.common.util.getParameter("viewid");
        var device_id = $("#check_device_id").val();
        var params =
        {
            view_id: view_id,
            device_id: device_id,
            pageIndex: pageIndex,
            pageSize: pageSize
        }
        T.common.ajax.request("WeconBox", "Viewpoint/showAlarmrestpoint", params, function (data, code, msg) {
            if (code == 200) {
                $scope.conf.totalItems = data.page.totalRecord;
                $scope.resrPointDatas = data.page.list;
                $scope.$apply();
            }
            else {
                swal("code + "-" + msg","","error");
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 展示管理账户的盒子
     * */
    $scope.showDeviceList = function () {
        var params = {};
        T.common.ajax.request("WeconBox", "Viewpoint/getDeviceName", params, function (data, code, msg) {
            if (code == 200) {
                $scope.deviceList = data.list;
                $scope.$apply();
            }
            else {
                swal("code + "-" + msg","","error");

            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 全选功能实现
     * */
    //默认没有选中全选

    $scope.selectAll = function () {
        if ($scope.isChecked == 0) {
            $scope.isChecked = 1;
        } else {
            $scope.isChecked = 0;
        }
        if ($scope.isChecked == 1) {
            $scope.allCheck = 1;
        } else {
            $scope.allCheck = 0;
        }
    };
});
