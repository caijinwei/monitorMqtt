/**
 * Created by Administrator on 2017/9/7.
 */
/**
 * Created by lanph on 2017/9/7.
 */
var appModule = angular.module('weconweb', []);
appModule
		.controller(
				"listController",
				function($scope, $http, $compile) {
					$scope.onInit = function() {
						$scope.machine_code = T.common.util
								.getParameter("machine_code");
						$scope.paginationConf = {
							currentPage : 1,
							itemsPerPage : 10,
							totalItems : $scope.count,
							pagesLength : 15,
							perPageOptions : [ 5, 10, 20, 50, 100 ],
							onChange : function() {
								if (this.currentPage != 0) {
									$scope.showAllSetting(this.currentPage,
											this.itemsPerPage);
								}
							}
						}

					}
					$scope.paginationConf = {
						totalItems : $scope.count,
					}
					$scope.search = function() {

						$scope.showAllSetting($scope.paginationConf.currentPage,
								$scope.paginationConf.itemsPerPage);

					}

					/*
					 * 展示盒子下的所有配置
					 */
					$scope.showAllSetting = function(pageNum, pageSize) {
                          if (pageNum == 0)
                        	  pageNum = 1;
						var params = {
							machine_code : $scope.machine_code,
							settingtype : $("#settingid").val(),
							bindstate : $("#bind_state").val(),
							pageNum : pageNum,
							pageSize : pageSize
						}
						T.common.ajax
								.request(
										"WeconBox",
										"baseInfoAction/showAllSetting",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.paginationConf.totalItems = data.allSettingData.totalRecord;
												$scope.settingData = data.allSettingData.list;
												$scope.$apply();
											} else {
												alert(code + " " + msg);
												$("#loadingModal")
														.modal("hide");
											}
										}, function() {
											console.log("ajax error");
										});
					}
				});