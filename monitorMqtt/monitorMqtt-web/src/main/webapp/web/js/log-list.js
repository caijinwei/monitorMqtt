/**
 * Created by zengzhipeng on 2017/8/30.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        //$scope.getOpLogConfigModel();
        //$scope.paginationConf = {
        //    currentPage: 1,
        //    itemsPerPage: 10,
        //    totalItems: $scope.count,
        //    pagesLength: 15,
        //    perPageOptions: [5, 10, 20, 50, 100],
        //    onChange: function () {
        //        if (this.currentPage != 0) {
        //            $scope.getList(this.currentPage, this.itemsPerPage);
        //        }
        //    }
        //}
    }
    $scope.paginationConf = {
        totalItems: $scope.count,
    }
    $scope.getOpLogConfigModel = function () {
        var params = {};
        T.common.ajax.request("WeconBox", "oplogaction/getlogrel", params, function (data, code, msg) {
            $scope.opTypeOption = data.OpTypeOption;
            $scope.resTypeOption = data.ResTypeOption;
            $scope.$apply();
        }, function () {
        });
    }
    /**
     * 查询数据
     * @param pageIndex
     * @param pageSize
     * @param sortId
     * @param sort
     */
    $scope.getList = function (pageIndex, pageSize) {
        if (pageIndex == 0)
            pageIndex = 1;
        $("#loadingModal").modal("show");
        var params = {
            pageIndex: pageIndex,
            pageSize: pageSize,
            sort: $scope.sort
        };
        var fields = $('#search-div .form-control');
        for (var i = 0; i < fields.length; i++) {
            var f = $(fields[i]);
            params[f.attr('id')] = f.val();
        }
        T.common.ajax.request("WeconBox", "oplogaction/getloglist", params, function (data, code, msg) {
            if (code == 200) {
                $scope.paginationConf.totalItems = data.page.totalRecord;
                $scope.pushlist = data.page.list;
                $scope.$apply();
                $("#loadingModal").modal("hide");
            }
        }, function () {
        });
    }
    /**
     * 列表操作
     */
    $scope.methods = {
        viewMsg: function (model) {
            $("#newWinModal").modal('show');
            $("#viewDetailDiv").text(JSON.stringify(JSON.parse(model.message), null, "\t"));

        }
    }
    /**
     * 显示完整信息
     * @param {type} event
     * @returns {undefined}
     */
    $scope.previewDetail = function (event) {
        $("#divPreview").show();
        $("#divPreview").text($(event.target).text());
        var height = $("#divPreview").height();

        //离底部距离
        var toBottom = $(window).height() - $(event.target).height() - $(event.target).offset().top + $(window).scrollTop();
        if (toBottom < height) {
            $('#divPreview').css("left", $(event.target).offset().left);
            $('#divPreview').css("top", $(event.target).offset().top - height - 15);
        } else {
            $('#divPreview').css("left", $(event.target).offset().left);
            $('#divPreview').css("top", $(event.target).offset().top + 25);
        }
    }
    $scope.previewDetailOut = function (event) {
        $("#divPreview").text("");
        $("#divPreview").hide();
    }
    $scope.viewDetail = function (event) {
        $("#newWinModal").modal('show');
        $("#viewDetailDiv").text($(event.target).text());
    }
})