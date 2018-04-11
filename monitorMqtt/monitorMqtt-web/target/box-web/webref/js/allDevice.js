/**
 * Created by Administrator on 2017/8/29.
 */
/**
 * Created by caijinw on 2017/8/28.
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
                    $scope.showAllDeviceDir("", this.currentPage, this.itemsPerPage);
                }
            }
        }
    }
    $scope.paginationConf = {
        totalItems: $scope.count,
    }
    /*
     * 根据用户id查询
     * */
    $scope.search = function () {
        var accountId = $("#account_id").val();
        var bind_state = $("#bind_state").val();

        if (bind_state != "") {
            $scope.showAllDeviceDir(accountId, $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        }
    }
    /*
     * 展示所有deviceDir
     * */
    $scope.showAllDeviceDir = function (accountId, pageNum, pageSize) {
        var bind_state = $("#bind_state").val();
        var machine_code = $("#machine_code").val();
        var device_id=$("#device_id").val();
        var state = $("#state").val();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (machine_code == "") {
            machine_code = -1;
        }
        if(bind_state==""){
            bind_state=="-1";
        }
        if(device_id==""){
            device_id=-1;
        }
        if(accountId==""){
            accountId=-1;
        }
        var params =
        {
            machine_code: machine_code,
            bind_state: bind_state,
            state: state,
            accountId: accountId,
            device_id: device_id,
            pageNum: pageNum,
            pageSize: pageSize
        }
            console.log(params);
        T.common.ajax.request("WeconBox", "baseInfoAction/showAllDeviceDir", params, function (data, code, msg) {
            if (code == 200) {
                $scope.paginationConf.totalItems = data.page.totalRecord;
                $scope.pushlist = data.page.list;
                $scope.$apply();
            }
            else {
                alert(code + " " + msg);
                $("#loadingModal").modal("hide");
            }
        }, function () {
            console.log("ajax error");
        });
    }
});
