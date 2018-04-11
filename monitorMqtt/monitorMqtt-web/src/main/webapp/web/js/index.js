/**
 * Created by zengzhipeng on 2018/1/23.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        //T.common.user.checkAuth();
        $scope.type = 1;
    }



});

