/**
 * Created by lanph on 2018/01/24.
 */
var appModule = angular.module('weconweb', []);
appModule
		.controller(
				"listController",
				function($scope, $http, $compile) {
					$scope.onInit = function() {
						$scope.iframeLoad();
						$scope.deviceid = T.common.util
								.getParameter("device_id");
						$("#myiframe")
								.attr(
										'src',
										'datamonitor.html?device_id='
												+ $scope.deviceid);
					}

					$scope.iframeLoad = function() {
						document.getElementById("myiframe").height = 0;
						document.getElementById("myiframe").height = document
								.getElementById("myiframe").contentWindow.document.body.scrollHeight;
					}

				});