/**
 * Created by caijinw on 2017/8/17.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.viewid = T.common.util.getParameter("viewid");
        $scope.type = T.common.util.getParameter("type");
        var viewid = T.common.util.getParameter("viewid");
        var type = T.common.util.getParameter("type");
        $scope.name = T.common.util.getParameter("name");
        $scope.title = "报警监控点权限列表";
        $scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: 10,
            totalItems: $scope.count,
            pagesLength: 15,
            perPageOptions: [5, 10, 20, 50, 100],
            rememberPerPage: 'perPageItems',
            onChange: function () {
                if (this.currentPage != 0) {
                    $scope.showViewAlarmpoint(viewid, this.currentPage, this.itemsPerPage);
                }
            }
        }

        //展示报警页面列表
        $scope.showViewAlarmpoint(viewid, $scope.paginationConf.currentPage, $scope.paginationConf.pagesLength);
    }
    /*
     * 展示余下监控点   （有分页操作）
     * */
    $scope.paginationConf = {
        totalItems: $scope.count
    }

    /*
     * 提交选中的监控点
     * */
    $scope.setViewOpint = function () {
        var viewid = T.common.util.getParameter("viewid");
        var chk_value = [];
        $("#myiframe").contents().find('input[name="cbid"]:checked').each(
            function () {
                chk_value.push($(this).val());
            }
        );
        var ids = chk_value.join(",");
        if (chk_value.length == 0) {
            alert("没有添加任何监控点");
            $("#showRestOpint").modal("hide");
            return;
        }
        $("#btn_setViewOpint").attr("disabled", "true");
        $("#btn_cancelSetOption").attr("disabled", "true");
        var params = {viewId: viewid, selectedId: ids, cfg_type: "2"};
        T.common.ajax.request("WeconBox", "Viewpoint/setViewHisAlarmPoint", params, function (data, code, msg) {
            if (code == 200) {
                $("#showRestOpint").modal("hide");
                $("#btn_setViewOpint").removeAttr("disabled");
                $("#btn_cancelSetOption").removeAttr("disabled");
                alert("添加成功");
                $scope.showViewAlarmpoint(viewid, "1", $scope.paginationConf.pagesLength);
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    };

    /*
     * 操作视图账号实时历史监控点解除绑定
     * role_type  报警数据3
     * */

    $scope.deleteOpintParam = function (pointId, roleType) {
        $scope.pointIdParam = pointId;
        $scope.roleTypeParam = roleType;
        swal({
            title: " 确定解除关联！",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then(function (isok) {
            if (isok) {
                $scope.deleteOption(pointId, roleType);
            }
        });
    }
    $scope.deleteOption = function () {
        var viewid = T.common.util.getParameter("viewid");
        var params =
        {
            roleType: 3,
            pointId: $scope.pointIdParam,
            viewId: viewid
        }
        $("#btn_deleteOption").attr("disabled", "true");
        T.common.ajax.request("WeconBox", "Viewpoint/deletePoint", params, function (data, code, msg) {
            if (code == 200) {
                var type = T.common.util.getParameter("type");
                $("#delectOpint").modal("hide");
                $("#btn_deleteOption").removeAttr("disabled");
                alert("解除关联操作成功！");
                $scope.showViewAlarmpoint(viewid, $scope.paginationConf.currentPage, $scope.paginationConf.pagesLength);
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 展示剩余监控点设置iframe的url属性
     * */
    $scope.showRestList = function () {
        var path = "../user/viewpointalarmTable.html?type=" + $scope.type + "&viewid=" + $scope.viewid;
        $("#myiframe").attr('src', path);
    }
    /*
     * 视图账户报警监控点展示
     * @RequestParam("view_id") Integer viewId ,@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize)
     * */
    $scope.showViewAlarmpoint = function (viewId, pageIndex, pageSize) {
        if (pageIndex == 0) {
            pageIndex == "1";
        }
        var params =
        {
            view_id: viewId,
            pageIndex: pageIndex,
            pageSize: pageSize
        }
        T.common.ajax.request("WeconBox", "Viewpoint/showAlarm", params, function (data, code, msg) {
            if (code == 200) {
                $scope.realpointDatas = data.page.list;
                $scope.conf.totalItems = data.page.totalRecord;
                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
});
