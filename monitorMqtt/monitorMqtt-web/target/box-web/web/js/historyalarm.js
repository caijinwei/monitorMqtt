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
									$scope.hisalarm_submit(this.currentPage,
											this.itemsPerPage);
								}
							}
						}
						$('.form_datetime').datetimepicker({
							weekStart : 1,
							todayBtn : 1,
							autoclose : 1,
							todayHighlight : 1,
							startView : 2,
							forceParse : 0,
							showMeridian : 1,
							pickerPosition : "bottom-left"
						}).on('changeDate', function(ev) { // 当日期被改变时触发
							// alert("当前日期是：" + ev.date.valueOf());
						});

					}

					$scope.paginationConf = {
						totalItems : $scope.count,
					}
					/**
					 * 提交接口请求
					 */
					$scope.hisalarm_submit = function(pageIndex, pageSize) {
						$("#loadingModal").modal("show");
						if (pageIndex == 0)
							pageIndex = 1;
						var params = {
							alarm_cfg_id : $("#alarmcfgid").val(),
							name : $("#alarmcfgname").val(),
							start_date : $("#startdateid").val(),
							end_date : $("#enddateid").val(),
							pageIndex : pageIndex,
							pageSize : pageSize
						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getHisAlarmData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.alarmHisDatas = data.alarmHisData.list;
												$scope.paginationConf.totalItems = data.alarmHisData.totalRecord;
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