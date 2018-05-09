/**
 * Created by Administrator on 2017/7/19.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {

        /*
        * 测试action连接
        * */

            var params = {
            }
            //alert("这边有测试到么");

            T.common.ajax.request("WeconBox", "test/testConnect", params,
            function (data, code, msg) {
                if (code == 200) {
                    alert("测试成功");
                } else {
                    alert(code + " " + msg);
                    console.log("error失败");
                }
            }, function () {
                console.log("ajax error");
            });
        }


})