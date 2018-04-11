/**
 * Created by lanph on 2017/9/25.
 */
var appModule = angular.module('weconweb', []);
appModule
		.controller(
				"listController",
				function($scope, $http, $compile) {
					$scope.onInit = function() {
						$scope.showBoxInfo();
					}

					/*
					 * 展示盒子的当前报警条目、当前报警盒子数、在线盒子数、总盒子数
					 */
					$scope.showBoxInfo = function() {
						var params = {
							};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/boxInfo",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.boxInfo = data.allBoxInfo;
												$scope.$apply();
											} else {
												alert(code + " " + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}
				});