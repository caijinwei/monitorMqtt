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
                    $scope.showTableList(this.currentPage, this.itemsPerPage);
                }
            }
        }

    }
    $scope.conf = {
        totalItems: $scope.count
    }

    //表格展示
    $scope.showTableList = function(pageIndex,pageSize){
        if (pageIndex == 0)
            pageIndex = 1;
        var serverId = $scope.server_id;
        var params = {
            serverId: 1,
            pageIndex: pageIndex,
            pageSize: pageSize,
            start_date: '2017-05-08 00:00:00',
            end_date: '2018-05-08 00:00:00'
        }
        //console.log(params.start_date);
        //console.log(params.end_date);
        /*
         * @RequestParam("serverId") long serverId, @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
         @RequestParam("start_date") String start_date, @RequestParam("end_date") String end_date
         * */

        T.common.ajax.request("WeconBox", "hisData/getHisData", params,
            function (data, code, msg) {
                if (code == 200) {
                    $scope.data = data.data.list;
                    $scope.$apply();
                } else {
                    swal(code + " " + msg);
                    console.log("error失败");
                }
            }, function () {
                console.log("ajax error");
            });
    }


});
