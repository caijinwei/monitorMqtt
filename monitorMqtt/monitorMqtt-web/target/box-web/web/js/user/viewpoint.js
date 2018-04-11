/**
 * Created by zengzhipeng on 2017/8/9.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $scope.viewid = T.common.util.getParameter("viewid");
        $scope.type = T.common.util.getParameter("type");
        var viewid = T.common.util.getParameter("viewid");
        var type = T.common.util.getParameter("type");
        $scope.name = T.common.util.getParameter("name");
        if (type == 0) {
            $scope.title = "实时监控点权限列表";
        } else if (type == 1) {
            $scope.title = "历史监控点权限列表";
        }
        $scope.paginationConf = {
            currentPage: 1,
            itemsPerPage: 10,
            totalItems: $scope.count,
            pagesLength: 15,
            perPageOptions: [5, 10, 20, 50, 100],
            rememberPerPage: 'perPageItems',
            onChange: function () {
                if (this.currentPage != 0) {
                    $scope.showPointBut(type, viewid, this.currentPage, this.itemsPerPage);
                }
            }
        }
        /*
         * 初始化 展示视图账户实时历史监控点
         * */
        $scope.showAlreadPoint(type, viewid);
    }
    $scope.paginationConf = {
        totalItems: $scope.count
    }
    /*
     * 展示 已分配监控点 的获取数据
     * */
    $scope.showAlreadPoint = function (type, viewid) {
        $scope.showPointBut(type, viewid, $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }
    $scope.showPointBut = function (type, view_id, pageIndex, pageSize) {
        if (pageIndex == 0) {
            pageIndex = 1;
        }
        //disabled
        var params =
        {
            view_id: view_id,
            type: type,
            pageIndex: pageIndex,
            pageSize: pageSize
        };

        T.common.ajax.request("WeconBox", "Viewpoint/showReal", params, function (data, code, msg) {
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
        var rightOption = [];
        var chk_value = [];
        $("#myiframe").contents().find('input[name="cbid"]:checked').each(
            function () {
                chk_value.push($(this).val());
                var tem = "right_" + $(this).val();
                var right = $("#myiframe").contents().find("input[name=" + tem + "]:checked").val();
                if (right != ""&&right!=undefined) {
                    rightOption.push(right);
                }

            }
        );
        var ids = chk_value.join(",");
        var rights = rightOption.join(",");
        if (chk_value.length == 0) {
            alert("没有添加任何监控点");
            $("#showRestOpint").modal("hide");
            return;
        }
        $("#btn_setViewOpint").attr("disabled","true");
        $("#btn_cancelSetOption").attr("disabled","true");
        var params = {viewId: viewid.toString(), selectedId: ids, rights: rights};
        T.common.ajax.request("WeconBox", "Viewpoint/setViewPoint", params, function (data, code, msg) {
            if (code == 200) {
                $("#showRestOpint").modal("hide");
                $("#btn_setViewOpint").removeAttr("disabled");
                $("#btn_cancelSetOption").removeAttr("disabled");
                alert("添加成功");
                var type = T.common.util.getParameter("type").toString();
                $scope.showAlreadPoint(type.toString(), viewid.toString());
                //$scope.getRestList( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
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
     * */

    $scope.deleteOpintParam = function (pointId, roleType) {
        $scope.pointIdParam = pointId;
        $scope.roleTypeParam = roleType.toString();
    }
    $scope.deleteOption = function () {
        var viewid = T.common.util.getParameter("viewid");
        var params =
        {
            roleType: $scope.roleTypeParam,
            pointId: $scope.pointIdParam,
            viewId: viewid
        }
        $("#btn_deleteOption").attr("disabled","true");
        T.common.ajax.request("WeconBox", "Viewpoint/deletePoint", params, function (data, code, msg) {
            if (code == 200) {
                var type = T.common.util.getParameter("type");
                $("#delectOpint").modal("hide");
                $("#btn_deleteOption").removeAttr("disabled");
                alert("解除关联操作成功！");
                $scope.showAlreadPoint(type, viewid);
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 修改视图账号监控点权限类型
     * */

    $scope.updateViewPointRoleTypeParam = function (viewpointIdParam) {
        $scope.viewPointRoleTypeParam = viewpointIdParam;
    }
    $scope.updateViewPointRoleType = function () {
        var roleType = $('input[name="roleType"]:checked').val();
        var viewId = T.common.util.getParameter("viewid");
        var type = T.common.util.getParameter("type");
        if (viewId == null) {
            alert("登录失效，请从新登录");
        }
        var params =
        {
            viewId: viewId,
            pointId: $scope.viewPointRoleTypeParam,
            roleType: roleType
        }
        T.common.ajax.request("WeconBox", "Viewpoint/updateViewPointRoleType", params, function (data, code, msg) {
            if (code == 200) {
                alert("修改监控点权限成功！");
                $("#updateRoleSyle").modal("hide");
                $scope.showAlreadPoint(type, viewId);
            }
        });
    }
    /*
     * 展示剩余监控点设置iframe的url属性
     * */
    $scope.showRestList = function () {
        var path = "../user/viewpointTable.html?type=" + $scope.type + "&viewid=" + $scope.viewid;
        $("#myiframe").attr('src', path);
    }

});
