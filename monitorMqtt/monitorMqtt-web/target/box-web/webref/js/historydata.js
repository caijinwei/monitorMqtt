var appModule = angular.module('weconweb', []);
appModule
		.controller(
				"infoController",
				function($scope, $http, $compile, $filter) {
					$scope.onInit = function() {
						$scope.deviceid = T.common.util
								.getParameter("device_id");
						$scope.devicename = T.common.util
								.getParameter("device_name");
						// 获取用户权限
						T.common.ajax.request("WeconBox", "user/userinfo",
								new Object(), function(data, code, msg) {
									$scope.accounttype = data.type;
									$scope.$apply();
								});

						$scope.commointor_submit();

					/*
					 * $('.form_datetime').datetimepicker({ // language: 'fr',
					 * weekStart : 1, todayBtn : 1, autoclose : 1,
					 * todayHighlight : 1, startView : 2, forceParse : 0,
					 * showMeridian : 1, pickerPosition : "bottom-left" });
					 */

					}

					/**
					 * 提交接口请求
					 */
					$scope.commointor_submit = function() {
						// $("#loadingModal").modal("show");
// $('#loader-wrapper').css("display", "block");
						var params = {
							device_id : $scope.deviceid

						};
						T.common.ajax.request("WeconBox",
								"hisDataAction/getComMonitor", params,
								function(data, code, msg) {
									if (code == 200) {
										$scope.commonitors = data.monitors;
										$("#searchid").attr("disabled",
												$scope.commonitors == "");
										$scope.$apply();
										$scope.searchHisData(1, 5);
										$scope.paginationConf = {
											currentPage : 1,
											itemsPerPage : 5,
											totalItems : $scope.count,
											pagesLength : 15,
											perPageOptions : [ 5, 10, 20, 50,
													100 ],
											onChange : function() {
												if (this.currentPage != 0) {
													$scope.searchHisData(
															this.currentPage,
															this.itemsPerPage);
												}
											}
										}
										$scope.paginationConf_register = {
											currentPage : 1,
											itemsPerPage : 10,
											totalItems : $scope.count,
											pagesLength : 15,
											perPageOptions : [ 5, 10, 20, 50,
													100 ],
											rememberPerPage : 'perPageItems',
											onChange : function() {
												if (this.currentPage != 0) {
													$scope.showhisconf(
															this.currentPage,
															this.itemsPerPage);
												}
											}
										}

										// $("#loadingModal").modal("hide");
										$('#loader-wrapper').css("display", "none");

									} else {
										// $("#loadingModal").modal("hide");
										$('#loader-wrapper').css("display", "none");
									       swal({
							                    title: code + "-" + msg,
							                    icon: "error"
							                });
											
									}
								}, function() {
									// $("#loadingModal").modal("hide");
									$('#loader-wrapper').css("display", "none");

									console.log("ajax error");
								});
					}
					$scope.paginationConf = {
						totalItems : $scope.count,
					}
					$scope.paginationConf_register = {
						totalItems : $scope.count,
					}
					$scope.searchHisData = function(pageIndex, pageSize) {
						if (pageIndex == 0)
							pageIndex = 1;
						// $("#loadingModal").modal("show");
						$('#loader-wrapper').css("display", "block");
						var params = {
							real_his_cfg_id : $("#monitorid").val(),
							start_date : $("#startdateid").val(),
							end_date : $("#enddateid").val(),
							pageIndex : pageIndex,
							pageSize : pageSize

						};
						T.common.ajax
								.request(
										"WeconBox",
										"hisDataAction/getHisData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.paginationConf.totalItems = data.realHisCfgDataList.totalRecord;
												$scope.Hisdatas = data.realHisCfgDataList.list;
												var xval = new Array();
												var yval = new Array();
												angular
														.forEach(
																data.realHisCfgDataList.list,
																function(data,
																		index,
																		array) {

																	xval
																			.push($filter(
																					'date')
																					(
																							data.monitor_time,
																							"yyyy-MM-dd HH:mm:ss"));

																	yval
																			.push(parseFloat(data.value));

																});
												angular
														.forEach(
																$scope.commonitors,
																function(data,
																		index,
																		array) {
																	if ($(
																			"#monitorid")
																			.val() == data.id) {
																		$scope.monitorName = data.name;
																	}

																});

												var chart = new Highcharts.Chart(
														'original-graph-container',
														{
															title : {
																text : '平均数值',
																x : -20
															},
															subtitle : {
																text : '数据来源: we-con.com.cn',
																x : -20
															},
															xAxis : {
																categories : xval
															},
															yAxis : {
																title : {
																	text : '值'
																},
																plotLines : [ {
																	value : 0,
																	width : 1,
																	color : '#808080'
																} ]
															},
															tooltip : {
																valueSuffix : ''
															},
															legend : {
																layout : 'vertical',
																align : 'right',
																verticalAlign : 'middle',
																borderWidth : 0
															},
															series : [ {
																name : $scope.monitorName,
																data : yval

															} ]
														});

												$scope.$apply();
												$("i[name='his_data_state']")
														.tooltip();
// $("#loadingModal")
// .modal("hide");
												$('#loader-wrapper').css("display", "none");
											} else {
												$('#loader-wrapper').css("display", "none");

											       swal({
									                    title: code + "-" + msg,
									                    icon: "error"
									                });
													
// $("#loadingModal")
// .modal("hide");
											
											}
										}, function() {
											console.log("ajax error");
										});
					}
					// 原始数据界面，列表视图、曲线视图
					$scope.showListOrCurves = function(btnId) {
						var checkType = btnId; // 查看类型
						$('#btn-list,#btn-curves').attr('class',
								'btn btn-default');
						$('#list-view,#curves-view').css('display', 'none');
						switch (btnId) {
						case 'btn-list':
							$('#btn-list').attr('class', 'btn btn-primary');
							$('#list-view').css('display', 'block');
							break;
						case 'btn-curves':
							$('#btn-curves').attr('class', 'btn btn-primary');
							$('#curves-view').css('display', 'block');

							break;
						default:
						
						swal({
		                    title:"视图切换异常！" ,
		                    icon: "warning"
		                });
							break;
						}
					}
					/*
					 * 盒子下plc配置展示
					 */
					var mtype = 0;
					$scope.showAllPlcConf = function(dealtype) {
						$scope.getDataType();
						mtype = dealtype;
						var params = {
							device_id : $scope.deviceid
						};
						T.common.ajax
								.request(
										"WeconBox",
										"plcInfoAction/showAllPlcConf",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.infoDatas = data.infoDatas;
												$scope.$apply();

												if (dealtype == 0) {
													$scope.showtype = 0;
													$("#datatypeid")
															.val(
																	$scope.dataTypes[0].value);
													mid = -1;
													if (data.infoDatas != "") {
														$scope
																.condevice(data.infoDatas[0].plcId);
														$("#nameid ").val("");
														$("#addrid").val("");
														$("#child_addrid").val(
																"");
														$("#describeid")
																.val("");

													}
												} else {
													$scope.showtype = 1;
													$("#nameid ").val(
															minfo.name);
													$("#conid ").val(
															minfo.plc_id);
													$scope
															.condevice(minfo.plc_id);

													$("#datatypeid").val(
															minfo.data_id);
													$("#hiscycleid").val(
															minfo.his_cycle);
													$("#addrid").val(
															minfo.main_addr);
													$("#describeid").val(
															minfo.describe);

												}
												$scope.datatype();

											} else {

			                                     swal({
									                    title: code + "-" + msg,
									                    icon: "error"
									                });
											}
										}, function() {
											console.log("ajax error");
										});
					}
					/**
					 * 获取数据类型
					 */
					$scope.getDataType = function() {
						var params = {};
						T.common.ajax.request("WeconBox",
								"actDataAction/getDataType", params, function(
										data, code, msg) {
									if (code == 200) {
										$scope.dataTypes = data.DataTypeOption;
										$scope.$apply();
									} else {

	                                     swal({
							                    title: code + "-" + msg,
							                    icon: "error"
							                });
									}
								}, function() {
									console.log("ajax error");
								});

					}
					/**
					 * 连接设备点击响应
					 */
					$("#conid").change(function() {

						$scope.condevice($("#conid").val());

					});

					var plcId;
					$scope.condevice = function(clickplc) {
						plcId = clickplc;
						var params = {
							plc_id : clickplc
						};
						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/getAddrType",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.allAddrs = data.allAddr;

												if (data.allAddr != "") {

													$scope.addrvalues = data.allAddr[0].addrRid;
													if (data.allAddr[0].addrRid != null) {

														if (data.allAddr[0].addrRid[0].addrvalue == null) {
															$('#registeraddr')
																	.css(
																			'display',
																			'none');
															$(
																	'#child_registeraddr')
																	.css(
																			'display',
																			'none');

														} else {
															if (data.allAddr[0].addrRid[0].bitCount == null) {
																$(
																		'#child_registeraddr')
																		.css(
																				'display',
																				'none');

															} else {

																$(
																		'#child_registeraddr')
																		.css(
																				'display',
																				'block');
																$(
																		"#child_rangid")
																		.html(
																				data.allAddr[0].addrRid[0].bRange);
																$(
																		"#child_scaleid")
																		.html(
																				data.allAddr[0].addrRid[0].bJinzhi);

															}

															$('#registeraddr')
																	.css(
																			'display',
																			'block');
															$("#rangid")
																	.html(
																			data.allAddr[0].addrRid[0].range);
															$("#scaleid")
																	.html(
																			data.allAddr[0].addrRid[0].mJinzhi);
														}

													} else {

														$('#registeraddr').css(
																'display',
																'none');
														$('#child_registeraddr')
																.css('display',
																		'none');
													}
													$scope.$apply();
													if (mtype == 1) {
														if (minfo.child_addr != null) {
															$(
																	'#child_registeraddr')
																	.css(
																			'display',
																			'block');
															$("#child_addrid")
																	.val(
																			minfo.child_addr);
															$("#child_rangid")
																	.html(
																			minfo.child_limit);
															$("#child_scaleid")
																	.html(
																			minfo.child_binary);

														} else {
															$(
																	'#child_registeraddr')
																	.css(
																			'display',
																			'none');
														}

														$("#addrtypeid")
																.val(
																		minfo.addr_type);

														angular
																.forEach(
																		$scope.allAddrs,
																		function(
																				data,
																				index,
																				array) {
																			if ($(
																					"#addrtypeid")
																					.val() == data.addrkey) {
																				$scope.addrvalues = data.addrRid;
																				$scope
																						.$apply();

																			}

																		})

														$("#registerid").val(
																minfo.rid);

														if ($("#registerid")
																.val() == null) {

															$("#addrtypeid")
																	.val(
																			data.allAddr[0].addrkey);
															if ($scope.addrvalues != null) {
																$("#registerid")
																		.val(
																				$scope.addrvalues[0].addrvalue);

																$("#rangid")
																		.html(
																				$scope.addrvalues[0].range);
																$("#scaleid")
																		.html(
																				$scope.addrvalues[0].mJinzhi);

															}

														} else {

															$("#rangid")
																	.html(
																			minfo.main_limit);

															$("#scaleid")
																	.html(
																			minfo.main_binary);

														}
													}
													$scope.initbit();
												}

											} else {

			                                     swal({
									                    title: code + "-" + msg,
									                    icon: "error"
									                });
											}
										}, function() {
											console.log("ajax error");
										});

					}

					/**
					 * 地址判断后的操作
					 */
					$scope.initbit = function() {

						if ($("#addrtypeid").val() == 0) {// 如果是位地址隐藏
							$('#divdatatypeid').css('display', 'none');
							$('#datadigitid').css('display', 'none');
							$('#div_stringid').css('display', 'none');
							$('#div_unit').css('display', 'none');
							$("#dataid").val("");
							$("#decid").val("");
							$("#unitid").val("");
							$("#stringid").val("");
						} else {

							$('#divdatatypeid').css('display', 'block');
							$('#div_unit').css('display', 'block');
							if (mtype == 1) {
								$("#unitid").val(minfo.ext_unit);
							}
							if ($("#datatypeid").val() == 1000) {
								if (mtype == 1) {
									$("#stringid").val(minfo.num);
								}
								$('#div_stringid').css('display', 'block');
							} else {
								if (mtype == 1) {
									$("#dataid").val(minfo.num);
									$("#decid").val(minfo.dec);
								}
								$('#datadigitid').css('display', 'block');
							}
						}

					}
					// 地址类型点击
					$("#addrtypeid").change(function() {

						$scope.changeaddrtype($("#addrtypeid").val());
						$scope.initbit();
					});
					$scope.changeaddrtype = function(value) {
						angular
								.forEach(
										$scope.allAddrs,
										function(data, index, array) {
											if (value == data.addrkey) {
												$scope.addrvalues = data.addrRid;

												if (data.addrRid[0].addrvalue == null) {
													$('#registeraddr').css(
															'display', 'none');
													$('#child_registeraddr')
															.css('display',
																	'none');

												} else {
													if (data.addrRid[0].bitCount == null) {
														$('#child_registeraddr')
																.css('display',
																		'none');

													} else {

														$('#child_registeraddr')
																.css('display',
																		'block');
														$("#chlid_rangid")
																.html(
																		data.addrRid[0].bRange);
														$("#chlid_scaleid")
																.html(
																		data.addrRid[0].bJinzhi);
													}

													$('#registeraddr').css(
															'display', 'block');
													$("#rangid")
															.html(
																	data.addrRid[0].range);
													$("#scaleid")
															.html(
																	data.addrRid[0].mJinzhi);
												}

												$scope.$apply();
											}

										})

					}

					$("#registerid").change(function() {

						$scope.changeaddr($("#registerid").val());

					});
					// 寄存器地址点击
					$scope.changeaddr = function(value) {
						angular.forEach($scope.addrvalues, function(data,
								index, array) {
							if (value == data.addrvalue) {
								$("#rangid").html(data.range);
								$("#scaleid").html(data.mJinzhi);
								if ($("#addrtypeid").val() == 0) {// 如果是位地址隐藏
									if (data.bRange != null
											&& data.bJinzhi != null) {
										$('#child_registeraddr').css('display',
												'block');
										$("#child_rangid").html(data.bRange);
										$("#child_scaleid").html(data.bJinzhi);
									} else {

										$('#child_registeraddr').css('display',
												'none');
										$("#child_rangid").html("");
										$("#child_scaleid").html("");

									}

								}
							}
						})
					}
					/**
					 * 数据格式设置
					 */
					$("#datatypeid").change(function() {
						$scope.datatype();

					});
					$scope.datatype = function() {
						if ($("#datatypeid").val() == 100) {
							$("#dataid").attr("placeholder", "1~16");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为不可编辑
							$("#decid").attr("placeholder", "无小数");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 101) {
							$("#dataid").attr("placeholder", "1~6");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为不可编辑
							$("#decid").attr("placeholder", "无小数");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');
						} else if ($("#datatypeid").val() == 102) {
							$("#dataid").attr("placeholder", "1~4");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为不可编辑
							$("#decid").attr("placeholder", "无小数");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 103) {
							$("#dataid").attr("placeholder", "0~4");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~4");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 104) {
							$("#dataid").attr("placeholder", "0~5");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~5");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 105) {
							$("#dataid").attr("placeholder", "0~5");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~5");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');
						} else if ($("#datatypeid").val() == 200) {
							$("#dataid").attr("placeholder", "1~32");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为不可编辑
							$("#decid").attr("placeholder", "无小数");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 201) {
							$("#dataid").attr("placeholder", "1~11");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为不可编辑
							$("#decid").attr("placeholder", "无小数");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');
						} else if ($("#datatypeid").val() == 202) {
							$("#dataid").attr("placeholder", "1~8");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为不可编辑
							$("#decid").attr("placeholder", "无小数");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 203) {
							$("#dataid").attr("placeholder", "0~8");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~8");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 204) {
							$("#dataid").attr("placeholder", "0~10");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~10");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 205) {
							$("#dataid").attr("placeholder", "0~10");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~10");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 206) {
							$("#dataid").attr("placeholder", "0~7");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~7");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');
						} else if ($("#datatypeid").val() == 400) {
							$("#dataid").attr("placeholder", "暂时没用");
							$("#decid").attr("placeholder", "暂时没用");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 401) {
							$("#dataid").attr("placeholder", "暂时没用");
							$("#decid").attr("placeholder", "暂时没用");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 402) {
							$("#dataid").attr("placeholder", "暂时没用");
							$("#decid").attr("placeholder", "暂时没用");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 403) {
							$("#dataid").attr("placeholder", "暂时没用");
							$("#decid").attr("placeholder", "暂时没用");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');
						} else if ($("#datatypeid").val() == 404) {
							$("#dataid").attr("placeholder", "暂时没用");
							$("#decid").attr("placeholder", "暂时没用");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 405) {
							$("#dataid").attr("placeholder", "暂时没用");
							$("#decid").attr("placeholder", "暂时没用");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 406) {
							$("#dataid").attr("placeholder", "0~15");
							$("#dataid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("disabled", false); // 设置为可编辑
							$("#decid").attr("placeholder", "0~15");
							$("#stringid").val("");
							$('#datadigitid').css('display', 'block');
							$('#div_stringid').css('display', 'none');

						} else if ($("#datatypeid").val() == 1000) {
							$("#dataid").attr("placeholder", "无整数");
							$("#decid").attr("placeholder", "无小数");
							$("#dataid").attr("disabled", true); // 设置为可编辑
							$("#decid").attr("disabled", true); // 设置为可编辑
							$("#dataid").val("");
							$("#decid").val("");
							$('#datadigitid').css('display', 'none');
							$('#div_stringid').css('display', 'block');

						}

					}
					var mid = -1;
					var minfo = null;
					// 获取修改监控点的信息
					$scope.editmonitor = function(model) {
						minfo = model;
						mid = model.id;
						$scope.showAllPlcConf(1);

					}
					$scope.addmonitor = function() {

						mid = -1;
						$("#dataid").val("");
						$("#decid").val("");
						$scope.showAllPlcConf(0);

					}
					// 保存添加/修改监控点
					$scope.saveupmonitor = function() {
						var num;
						var dec;
						var digit_counts = [];
						var rangdata, child_rangdata;
						var rangs = [];
						var addrdata, child_addrdata;
						var addrs = [];
						var scaliedata, child_scaliedata;
						var scalies = [];
						if (plcId == undefined
								|| $("#addrtypeid").val() == undefined
								|| $("#registerid").val() == undefined) {
							swal({
			                    title:"检查是否配置好通讯口配置！" ,
			                    icon: "warning"
			                });
							return;
						}
						if ($("#nameid").val() == "") {
							swal({
			                    title:"参数未配置完整！" ,
			                    icon: "warning"
			                });
							return;
						}
						if ($('#registeraddr').css('display') == 'block') {
							if ($("#addrid").val() == "") {
								swal({
				                    title:"参数未配置完整！" ,
				                    icon: "warning"
				                });
								return;
							}

						}
						if ($('#child_registeraddr').css('display') == 'block') {
							if ($("#child_addrid").val() == "") {
								swal({
				                    title:"参数未配置完整！" ,
				                    icon: "warning"
				                });
								return;
							}

						}
						var hisreg = /^[1-9]\d*$/;
						if (!hisreg.test($("#hiscycleid").val())) {
							swal({
			                    title:"周期必须是正整数！" ,
			                    icon: "warning"
			                });
							return;
						}
						if ($("#hiscycleid").val() < 1
								|| $("#hiscycleid").val() > 86400) {
							swal({
			                    title:"周期必须大于等于1或者小于等于86400的正整数！" ,
			                    icon: "warning"
			                });
							return;
						}
						if ($("#nameid").val().length > 50) {
							swal({
			                    title:"名称字符长度不能大于50位！" ,
			                    icon: "warning"
			                });
							return;
						}
						if ($("#describeid").val().length > 50) {
							swal({
			                    title:"描述字符长度不能大于50位！" ,
			                    icon: "warning"
			                });
							return;
						}
						var display = $('#registeraddr').css('display');
						if (display == 'block') {
							var rang, reg;
							if ($("#scaleid").text() == "八进制") {
								rang = $("#rangid").text().split(" ");
								reg = /^[0-7]*$/;
								if (!reg.test($("#addrid").val())) {
									swal({
					                    title:"寄存器地址主编号格式错误" ,
					                    icon: "warning"
					                });
									return;
								}
								if (parseInt($("#addrid").val(), 8) < parseInt(
										rang[0], 8)
										|| parseInt($("#addrid").val(), 8) > parseInt(
												rang[1], 8)) {
									
									swal({
					                    title:"寄存器地址主编号范围有误" ,
					                    icon: "warning"
					                });
									return;
								}

							} else if ($("#scaleid").text() == "十进制") {
								rang = $("#rangid").text().split(" ");
								reg = /^0|[1-9]\d*$/;
								if (!reg.test($("#addrid").val())) {
									swal({
					                    title:"寄存器地址主编号格式错误" ,
					                    icon: "warning"
					                });
									return;
								}
								if ($("#addrid").val() < parseInt(rang[0])
										|| $("#addrid").val() > parseInt(rang[1])) {
									swal({
					                    title:"寄存器地址主编号范围有误" ,
					                    icon: "warning"
					                });
									return;
								}

							} else if ($("#scaleid").text() == "十六进制") {
								rang = $("#rangid").text().split(" ");
								reg = /^[0-9a-fA-F]*$/;
								if (!reg.test($("#addrid").val())) {
									swal({
					                    title:"寄存器地址主编号格式错误" ,
					                    icon: "warning"
					                });
									return;
								}
								if (parseInt($("#addrid").val(), 16) < parseInt(
										rang[0], 16)
										|| parseInt($("#addrid").val(), 16) > parseInt(
												rang[1], 16)) {
									swal({
					                    title:"寄存器地址主编号范围有误" ,
					                    icon: "warning"
					                });
									return;

								}
							}
							scaliedata = $("#scaleid").text();
							scalies.push(scaliedata);
							rangdata = $("#rangid").text();
							rangs.push(rangdata);
							addrdata = $("#addrid").val();
							addrs.push(addrdata);

						} else {
							$("#rangid").html("");
							$("#scaleid").html("");
						}
						var display = $('#child_registeraddr').css('display');
						if (display == 'block') {
							var child_rang, child_reg;
							if ($("#child_scaleid").text() == "八进制") {

								child_rang = $("#child_rangid").text().split(
										" ");
								child_reg = /^[0-7]*$/;
								if (!child_reg.test($("#child_addrid").val())) {
									swal({
					                    title:"寄存器地址子编号格式错误" ,
					                    icon: "warning"
					                });
									return;
								}
								if (parseInt($("#child_addrid").val(), 8) < parseInt(
										child_rang[0], 8)
										|| parseInt($("#child_addrid").val(), 8) > parseInt(
												child_rang[1], 8)) {
									swal({
					                    title:"寄存器地址子编号范围有误" ,
					                    icon: "warning"
					                });
									return;
								}
							} else if ($("#child_scaleid").text() == "十进制") {
								child_rang = $("#child_rangid").text().split(
										" ");
								child_reg = /^0|[1-9]\d*$/;
								if (!child_reg.test($("#child_addrid").val())) {
									swal({
					                    title:"寄存器地址子编号格式错误" ,
					                    icon: "warning"
					                });
									return;
								}
								if ($("#child_addrid").val() < parseInt(child_rang[0])
										|| $("#child_addrid").val() > parseInt(child_rang[1])) {
									swal({
					                    title:"寄存器地址子编号范围有误" ,
					                    icon: "warning"
					                });
									return;
								}
							} else if ($("#child_scaleid").text() == "十六进制") {
								child_rang = $("#child_rangid").text().split(
										" ");
								child_reg = /^[0-9a-fA-F]*$/;
								if (!child_reg.test($("#child_addrid").val())) {
									swal({
					                    title:"寄存器地址子编号格式错误" ,
					                    icon: "warning"
					                });
									return;
								}
								if (parseInt($("#child_addrid").val(), 16) < parseInt(
										child_rang[0], 16)
										|| parseInt($("#child_addrid").val(),
												16) > parseInt(child_rang[1],
												16)) {
									swal({
					                    title:"寄存器地址子编号范围有误" ,
					                    icon: "warning"
					                });
									return;
								}
							}
							child_scaliedata = $("#child_scaleid").text();
							scalies.push(child_scaliedata);
							child_rangdata = $("#child_rangid").text();
							rangs.push(child_rangdata);
							child_addrdata = $("#child_addrid").val();
							addrs.push(child_addrdata);
						} else {

							$("#child_rangid").html("");
							$("#child_scaleid").html("");

						}
						if ($("#addrtypeid").val() != 0) {
							var regnum = /^0|[1-9]\d*$/;
							if ($("#datatypeid").val() == 1000) {
								$("#dataid").val("");
								$("#decid").val("");
								if (!regnum.test($("#stringid").val())) {
									swal({
					                    title:"操作字符只能输入正整数" ,
					                    icon: "warning"
					                });
									return;
								}
								if ($("#stringid").val() < 1
										|| $("#stringid").val() > 256) {
									swal({
					                    title:"操作字符范围是1~256" ,
					                    icon: "warning"
					                });
									return;
								}
								num = $("#stringid").val();
								digit_counts.push(num);

							} else {

								var datadisabled = $("#dataid")
										.prop("disabled");
								if (!datadisabled) {
									if (!regnum.test($("#dataid").val())) {
										swal({
						                    title:"整数位数格式错误" ,
						                    icon: "warning"
						                });
										return;
									}

								}
								var decdisabled = $("#decid").prop("disabled");
								if (!decdisabled) {
									if (!regnum.test($("#dataid").val())) {
										swal({
						                    title:"小数位数格式错误" ,
						                    icon: "warning"
						                });
										return;
									}
								}

								if ($("#datatypeid").val() == 100) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 16) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}

								} else if ($("#datatypeid").val() == 101) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 6) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}

								} else if ($("#datatypeid").val() == 102) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 4) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}

								} else if ($("#datatypeid").val() == 103) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 4) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 4) {
										swal({
						                    title:"小数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 4) {
										swal({
						                    title:"整数位数+小数位数范围是1~4" ,
						                    icon: "warning"
						                });
										return;

									}
								} else if ($("#datatypeid").val() == 104
										|| $("#datatypeid").val() == 105) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 5) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 5) {
										swal({
						                    title:"小数数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 5) {
										swal({
						                    title:"整数位数+小数位数范围是1~5" ,
						                    icon: "warning"
						                });
										return;

									}
								} else if ($("#datatypeid").val() == 200) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 32) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}

								} else if ($("#datatypeid").val() == 201) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 11) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}

								} else if ($("#datatypeid").val() == 202) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 8) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}

								} else if ($("#datatypeid").val() == 203) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 8) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 8) {
										swal({
						                    title:"小数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 8) {
										swal({
						                    title:"整数位数+小数位数范围是1~8" ,
						                    icon: "warning"
						                });
										return;

									}

								} else if ($("#datatypeid").val() == 204
										|| $("#datatypeid").val() == 205) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 10) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 10) {
										swal({
						                    title:"小数范围有误" ,
						                    icon: "warning"
						                });
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 10) {
										swal({
						                    title:"整数位数+小数位数范围是1~10" ,
						                    icon: "warning"
						                });
										return;

									} else if ($("#datatypeid").val() == 206) {
										if ($("#dataid").val() < 0
												|| $("#dataid").val() > 7) {
											swal({
							                    title:"整数范围有误" ,
							                    icon: "warning"
							                });
											return;
										}
										if ($("#decid").val() < 0
												|| $("#decid").val() > 7) {
											swal({
							                    title:"小数范围有误" ,
							                    icon: "warning"
							                });
											return;
										}
										var totle = parseInt($("#dataid").val())
												+ parseInt($("#decid").val());
										if (totle < 1 || totle > 7) {
											swal({
							                    title:"整数位数+小数位数范围是1~7" ,
							                    icon: "warning"
							                });
											return;

										}

									} else if ($("#datatypeid").val() == 400
											|| $("#datatypeid").val() == 401
											|| $("#datatypeid").val() == 402
											|| $("#datatypeid").val() == 403
											|| $("#datatypeid").val() == 404
											|| $("#datatypeid").val() == 405) {
										$("#dataid").val("");
										$("#decid").val("");

									} else if ($("#datatypeid").val() == 406) {
										if ($("#dataid").val() < 0
												|| $("#dataid").val() > 15) {
											swal({
							                    title:"整数范围有误" ,
							                    icon: "warning"
							                });
											return;
										}
										if ($("#decid").val() < 0
												|| $("#decid").val() > 15) {
											swal({
							                    title:"小数范围有误" ,
							                    icon: "warning"
							                });
											return;
										}
										var totle = parseInt($("#dataid").val())
												+ parseInt($("#decid").val());
										if (totle < 1 || totle > 15) {
											swal({
							                    title:"整数位数+小数位数范围是1~15" ,
							                    icon: "warning"
							                });
											return;
										}
									}

								}
								if (!$("#dataid").prop("disabled")) {
									num = $("#dataid").val();
									digit_counts.push(num);
								}
								if (!$("#decid").prop("disabled")) {
									dec = $("#decid").val();
									digit_counts.push(dec);
								}
							}

						} else {
							$('#divdatatypeid').css('display', 'none');
							$('#datadigitid').css('display', 'none');
							$('#div_stringid').css('display', 'none');
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
						}
						var rang_datas = rangs.join(",");
						var addr_datas = addrs.join(",");
						var scalie_datas = scalies.join(",");
						var digs = digit_counts.join(",");
						$('#loader-wrapper').css("display","block");// 添加转圈效果
						var params = {
							id : mid,
							plc_id : plcId,
							device_id : $scope.deviceid,
							name : $("#nameid").val(),
							data_id : $("#datatypeid").val(),
							addr_type : $("#addrtypeid").val(),
							addr : addr_datas,
							digit_binary : scalie_datas,
							rid : $("#registerid").val(),
							rang : rang_datas,
							describe : $("#describeid").val(),
							digit_count : digs,
							data_type : "1",
							his_cycle : $("#hiscycleid").val(),
							unit : $("#unitid").val()
						};

						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/addUpdataMonitor",
										params,
										function(data, code, msg) {
										
											if (code == 200) {
												$("#dataRecord").modal("hide");
											
												$scope
														.showhisconf(
																$scope.paginationConf_register.currentPage,
																$scope.paginationConf_register.itemsPerPage);
											

												if (mtype == 0) {
													swal({
									                    title:"数据登记成功" ,
									                    icon: "success"
									                });
												} else {
													swal({
									                    title:"修改数据成功" ,
									                    icon: "success"
									                });
												}

											} else {
												$('#loader-wrapper').css("display","none");// 关闭转圈圈
										        swal({
								                    title: code + "-" + msg,
								                    icon: "error"
								                });
											}
										}, function() {
											console.log("ajax error");
										});

					}
					// 删除监控点
					$scope.delmonitor = function(model) {
						$scope.delmonitorid = model.id;// 监控点id
						swal({
							  title: "确定要删除【" + model.name + "】数据吗？",
							  icon: "warning",
							  buttons: true,
							  dangerMode: true,
							})
							.then((willDelete) => {
							  if (willDelete) {
								  $scope.del_monitor_group();
							  } else {
							  }
							});
					}
					// 删除监控点
					$scope.del_monitor_group = function() {

						var params = {
							monitorid : $scope.delmonitorid,
						};

						T.common.ajax
								.request(
										"WeconBox",
										"hisDataAction/delHisMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#deletehispoint").modal(
														"hide");
												$scope
														.showhisconf(
																$scope.paginationConf_register.currentPage,
																$scope.paginationConf_register.itemsPerPage);
												swal({
								                    title:"删除成功！" ,
								                    icon: "success"
								                });

											} else {

											       swal({
									                    title: code + "-" + msg,
									                    icon: "error"
									                });
													
											}
										}, function() {
											console.log("ajax error");
										});
					}

					/**
					 * 获取历史数据配置信息
					 */
					$scope.showhisconf = function(pageIndex, pageSize) {
						console.log("数据登记显示");
						if (pageIndex == 0)
							pageIndex = 1;
						var params = {
							device_id : $scope.deviceid,
							pageIndex : pageIndex,
							pageSize : pageSize

						};
						T.common.ajax
								.request(
										"WeconBox",
										"hisDataAction/getHisConfig",
										params,
										function(data, code, msg) {
                                		$('#loader-wrapper').css("display","none");// 关闭转圈圈
											if (code == 200) {
												$scope.paginationConf_register.totalItems = data.HisAllotData.totalRecord;
												$scope.hisConfs = data.HisAllotData.list;
												$scope.$apply();
												$("i[name='his_i_state']")
														.tooltip();

											} else {
											       swal({
									                    title: code + "-" + msg,
									                    icon: "error"
									                });
													
											}
										}, function() {
											console.log("ajax error");
										});

					}

					$scope.exportExcel = function() {
						var myform = document.getElementById('myform');
						var real_his_cfg_id = $("#monitorid").val();
						var start_date = $("#startdateid").val();
						var end_date = $("#enddateid").val();
						var pageIndex = $scope.paginationConf.currentPage;
						pageIndex = 0 == pageIndex ? 1 : pageIndex;
						var pageSize = $scope.paginationConf.itemsPerPage;
						myform.innerHTML = '<input type="hidden" name="real_his_cfg_id" value="'
								+ real_his_cfg_id
								+ '"/>'
								+ '<input type="hidden" name="start_date" value="'
								+ start_date
								+ '"/>'
								+ '<input type="hidden" name="end_date" value="'
								+ end_date
								+ '"/>'
								+ '<input type="hidden" name="pageIndex" value="'
								+ pageIndex
								+ '"/>'
								+ '<input type="hidden" name="pageSize" value="'
								+ pageSize + '"/>'
						myform.action = T.common.requestUrl.WeconBox
								+ 'excelact/filedownloadExportHis';

						myform.submit();

					}
				})