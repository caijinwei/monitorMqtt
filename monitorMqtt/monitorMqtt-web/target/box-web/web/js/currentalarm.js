var appModule = angular.module('weconweb', []);
appModule
		.controller(
				"infoController",
				function($scope, $http, $compile) {
					$scope.onInit = function() {
						$scope.paginationConf = {
							currentPage : 1,
							itemsPerPage : 10,
							totalItems : $scope.count,
							pagesLength : 15,
							perPageOptions : [ 5, 10, 20, 50, 100 ],
							rememberPerPage : 'perPageItems',
							onChange : function() {
								if (this.currentPage != 0) {
									$scope.alarm_submit(this.currentPage,
											this.itemsPerPage);
								}
							}
						}

					}

					$scope.paginationConf = {
						totalItems : $scope.count,
					}
					/**
					 * 提交接口请求
					 */
					$scope.alarm_submit = function(pageIndex, pageSize) {
						$("#loadingModal").modal("show");
						if (pageIndex == 0)
							pageIndex = 1;
						var params = {
							pageIndex : pageIndex,
							pageSize : pageSize
						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getNowAlarmData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.alarmDatas = data.alarmData.list;
												$scope.paginationConf.totalItems = data.alarmData.totalRecord;
												$scope.$apply();
												$("#loadingModal")
														.modal("hide");

											} else {
												$("#loadingModal")
														.modal("hide");
												alert(code + "-" + msg);
											}
										}, function() {
											$("#loadingModal").modal("hide");
											console.log("ajax error");
										});
					}

				})