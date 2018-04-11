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
						$scope.settingId = T.common.util
								.getParameter("settingid");
						$scope.machine_code = T.common.util
						.getParameter("machine_code");
						$scope.datatype = T.common.util
						.getParameter("data_type");
						if($scope.datatype==''){
							$scope.datatype=2;//报警配置
						}
						
						$scope.paginationConf = {
							currentPage : 1,
							itemsPerPage : 10,
							totalItems : $scope.count,
							pagesLength : 15,
							perPageOptions : [ 5, 10, 20, 50, 100 ],
							onChange : function() {
								if (this.currentPage != 0) {
									$scope.showAllData(this.currentPage,
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
						});

					}
					$scope.paginationConf = {
						totalItems : $scope.count,
					}
					$scope.search = function() {

						$scope.showAllData($scope.paginationConf.currentPage,
								$scope.paginationConf.itemsPerPage);

					}

					/*
					 * 展示盒子下的所有数据
					 */
					$scope.showAllData = function(pageNum, pageSize) {
						  if (pageNum == 0)
                        	  pageNum = 1;

						var params = {
							settingId:$scope.settingId,
							datatype:$scope.datatype,
							start_date : $("#startdateid").val(),
							end_date : $("#enddateid").val(),
							pageNum : pageNum,
							pageSize : pageSize
						}
						T.common.ajax
								.request(
										"WeconBox",
										"baseInfoAction/showAllData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.paginationConf.totalItems = data.allData.totalRecord;
												$scope.allDatas = data.allData.list;
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