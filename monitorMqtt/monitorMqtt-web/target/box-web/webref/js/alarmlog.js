var appModule = angular.module('weconweb', []);
appModule
		.controller(
				"infoController",
				function($scope, $http, $compile) {
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

						$scope.getDataType();
						$scope.getAlarmGrade();
						$scope.paginationConf_current = {
							currentPage : 1,
							itemsPerPage : 10,
							totalItems : $scope.count,
							pagesLength : 15,
							perPageOptions : [ 5, 10, 20, 50, 100 ],
							/* rememberPerPage : 'perPageItems', */
							onChange : function() {
								if (this.currentPage != 0) {

									$scope.alarm_submit(this.currentPage,
											this.itemsPerPage);

								}
							}
						}
						$scope.paginationConf_history = {
							currentPage : 1,
							itemsPerPage : 10,
							totalItems : $scope.count,
							pagesLength : 15,
							perPageOptions : [ 5, 10, 20, 50, 100 ],
						/* rememberPerPage : 'perPageItems', */
							onChange : function() {
								if (this.currentPage != 0) {

									$scope.hisalarm_submit(this.currentPage,
											this.itemsPerPage);

								}
							}
						}

					}

					$scope.paginationConf_current = {
						totalItems : $scope.count,
					}
					$scope.paginationConf_history = {
						totalItems : $scope.count,
					}
					$scope.paginationConf_alarmcfg = {
						totalItems : $scope.count,
					}
					/**
					 * 获取报警级别
					 */
					$scope.getAlarmGrade = function() {
						var params = {};
						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getAlarmGrade",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.alarmGrade = data.alarmGradeOption;
												$scope.$apply();
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
					$("#GradeSelect")
							.change(
									function() {
										$scope
												.alarm_submit(
														$scope.paginationConf_current.currentPage,
														$scope.paginationConf_current.itemsPerPage);
									});
					$("#EventSelect")
							.change(
									function() {
										$scope
												.alarm_submit(
														$scope.paginationConf_current.currentPage,
														$scope.paginationConf_current.itemsPerPage);
									});

					/**
					 * 提交当前报警接口请求
					 */
					$scope.alarm_submit = function(pageIndex, pageSize) {
						// $("#loadingModal").modal("show");
						$('#loader-wrapper').css("display", "block");
						if (pageIndex == 0)
							pageIndex = 1;
						var params = {
							grade_id : $("#GradeSelect").val(),
							event_id : $("#EventSelect").val(),
							device_id : $scope.deviceid,
							state : 1,
							pageIndex : pageIndex,
							pageSize : pageSize
						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getNowHisAlarmData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.alarmcurrents = data.alarmHisData.list;
												$scope.paginationConf_current.totalItems = data.alarmHisData.totalRecord;

												$scope.$apply();

// $("#loadingModal")
// .modal("hide");
												$('#loader-wrapper').css("display", "none");

											} else {
											/*
											 * $("#loadingModal")
											 * .modal("hide");
											 */
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
					/**
					 * 提交历史报警接口请求
					 */
					$scope.hisalarm_submit = function(pageIndex, pageSize) {

						var regnum = /^0|[1-9]\d*$/;
						if ($("#alarmcfgid").val() != "") {
							if (!regnum.test($("#alarmcfgid").val())) {
								swal({
				                    title:"编码只能是正整数" ,
				                    icon: "warning"
				                });
								return;
							}
						}
						// $("#loadingModal").modal("show");
						$('#loader-wrapper').css("display", "block");
						if (pageIndex == 0)
							pageIndex = 1;

						var params = {
							grade_id : -1,
							event_id : -1,
							alarm_cfg_id : $("#alarmcfgid").val(),
							state : 2,
							name : $("#alarmcfgname").val(),
							start_date : $("#startdateid").val(),
							end_date : $("#enddateid").val(),
							device_id : $scope.deviceid,
							pageIndex : pageIndex,
							pageSize : pageSize
						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getNowHisAlarmData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.alarmHisDatas = data.alarmHisData.list;
												$scope.accounttype = data.type;
												$scope.paginationConf_history.totalItems = data.alarmHisData.totalRecord;
												$scope.$apply();
// $("#loadingModal")
// .modal("hide");
												$('#loader-wrapper').css("display", "none");

											} else {
// $("#loadingModal")
// .modal("hide");
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

					$("#selectWith").change(function() {

						var value = $('#selectWith').val();
						if (value == "0") {
							$('#secondCondition').css('display', 'none');
						} else {
							$('#secondCondition').css('display', 'block');
						}

					});

					/**
					 * 获取报警配置列表
					 */
					$scope.alarm_group = function() {

						var params = {
							device_id : $scope.deviceid
						};
						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getAlarmGroup",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.dir_list = data.alarmGroup;
												$scope.accounttype = data.type;
												$scope.$apply();
												$scope.paginationConf_alarmcfg = {
													currentPage : 1,
													itemsPerPage : 10,
													totalItems : $scope.count,
													pagesLength : 15,
													perPageOptions : [ 5, 10,
															20, 50, 100 ],
													/*
													 * rememberPerPage :
													 * 'perPageItems',
													 */
													onChange : function() {
														if (this.currentPage != 0) {

															$scope
																	.showAlarmCfg(
																			this.currentPage,
																			this.itemsPerPage);

														}
													}
												}
												$scope
														.showAlarmCfg(
																$scope.paginationConf_alarmcfg.currentPage,
																$scope.paginationConf_alarmcfg.itemsPerPage);

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

					// 创建分组
					$scope.add_group = function() {
						if ($('#newGroupName').val() == "") {
							swal({
			                    title:"名称不能为空" ,
			                    icon: "warning"
			                });
							return;
						}
						var params = {
							id : -1,
							name : $('#newGroupName').val(),
							type : 3,
							device_id : $scope.deviceid
						};

						T.common.ajax.request("WeconBox",
								"userdiract/saveuserdir", params, function(
										data, code, msg) {
									if (code == 200) {
										$("#addGroup").modal("hide");
										$scope.alarm_group();
										swal({
						                    title: "添加成功",
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

					// 获取修改分组名称
					$scope.editGroup = function(model) {

						$("#editid").val(model.name);
						$scope.alarmgroupId = model.id;

						$("#editGroup").modal("show");

					}
					// 编辑分组
					$scope.edit_group = function() {
						if ($('#editid').val() == "") {
							swal({
			                    title:"名称不能为空" ,
			                    icon: "warning"
			                });
							return;
						}
						var params = {
							id : $scope.alarmgroupId,
							name : $('#editid').val(),
							type : 3,
							device_id : $scope.deviceid
						};

						T.common.ajax.request("WeconBox",
								"userdiract/saveuserdir", params, function(
										data, code, msg) {
									if (code == 200) {
										$("#editGroup").modal("hide");
										$scope.alarm_group();
										swal({
						                    title: "修改成功",
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
					// 获取删除分组名称
					$scope.delGroup = function(model) {
						$scope.delAlarmGroupId = model.id;
						swal({
							  title: "删除【" + model.name + "】组和组内数据，是否继续?",
							  icon: "warning",
							  buttons: true,
							  dangerMode: true,
							})
							.then((willDelete) => {
							  if (willDelete) {
								  $scope.del_group();
							  } else {
							    console.log("取消删除");
							  }
							});
					}
					// 删除分组
					$scope.del_group = function() {
						var params = {
							id : $scope.delAlarmGroupId,
						};

						T.common.ajax.request("WeconBox",
								"alarmDataAction/delAlarmGroup", params,
								function(data, code, msg) {
									if (code == 200) {

										$("#deleteGroup").modal("hide");
										$scope.alarm_group();
										swal({
						                    title: "删除成功",
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

					/*
					 * 盒子下plc配置展示
					 */
					var mtype = 0;
					$scope.showAllPlcConf = function(dealtype) {
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
													if (data.infoDatas != "") {
														$scope
																.condevice(data.infoDatas[0].plcId);
														$("#nameid ").val("");
														$("#addrid").val("");
														$("#child_addrid").val(
																"");
														$("#alarmtextid").val(
																"");

													}
												} else {

													$scope.showtype = 1;
													$("#nameid ").val(
															alarmfo.name);
													$("#conid ").val(
															alarmfo.plc_id);
													$scope
															.condevice(alarmfo.plc_id);

													$("#datatypeid").val(
															alarmfo.data_id);
													$("#addrid").val(
															alarmfo.main_addr);
													$("#alarmtextid").val(
															alarmfo.text);
													$('#selectgroup').val(
															alarmfo.dirId);
													$('#gradeid')
															.val(
																	alarmfo.alarm_level);

													if ($("#addrtypeid").val() == 0) {// 如果是位地址隐藏
														$('#datadigitid').css(
																'display',
																'none');
														$('#div_stringid').css(
																'display',
																'none');
														$("#dataid").val("");
														$("#decid").val("");
														$("#stringid").val("");

													} else {
														$('#datadigitid').css(
																'display',
																'block');
													}
													if ($("#datatypeid").val() == 1000) {
														$("#stringid").val(
																alarmfo.num);
													} else {
														$("#dataid").val(
																alarmfo.num);
														$("#decid").val(
																alarmfo.dec);
													}
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
													if (data.allAddr[0].addrkey == 0) {
														$('#firstCondition')
																.css('display',
																		'none');
														$('#bitsetting').css(
																'display',
																'block');
														$('#secondCondition')
																.css('display',
																		'none');

														$('#selectWith').val(0);
													} else {

														$('#firstCondition')
																.css('display',
																		'block');
														$('#bitsetting').css(
																'display',
																'none');
														$('#secondCondition')
																.css('display',
																		'none');
														$('#selectWith').val(0);
													}

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
												if(mtype==1){// 编辑
													if (alarmfo.child_addr != null) {
															$(
																	'#child_registeraddr')
																	.css(
																			'display',
																			'block');

															$("#child_addrid")
																	.val(
																			alarmfo.child_addr);
															$("#child_rangid")
																	.html(
																			alarmfo.child_limit);
															$("#child_scaleid")
																	.html(
																			alarmfo.child_binary);

														} else {
															$(
																	'#child_registeraddr')
																	.css(
																			'display',
																			'none');
														}

														$("#addrtypeid")
																.val(
																		alarmfo.addr_type);
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
																alarmfo.rid);
														if ($("#registerid")
																.val() == null) {
															

															if ($scope.addrvalues != null) {
																$("#registerid")
																		.val(
																				$scope.addrvalues[0].addrvalue);

																$("#rangid")
																		.val(
																				$scope.addrvalues[0].range);
																$("#scaleid")
																		.html(
																				$scope.addrvalues[0].mJinzhi);
															}

														} else {

															$("#rangid")
																	.html(
																			alarmfo.main_limit);

															$("#scaleid")
																	.html(
																			alarmfo.main_binary);


														}
												}
														$scope.initbit();// 初始化位地址类型

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
					// 地址类型点击
					$("#addrtypeid").change(function() {
						$scope.changeaddrtype($("#addrtypeid").val());
						$scope.initbit();
					});
					
					
					
					/**
					 * 地址判断后的操作
					 */
					$scope.initbit = function() {

						if ($("#addrtypeid").val() == 0) {// 如果是位地址隐藏
							
							$('#firstCondition').css('display', 'none');
							$('#bitsetting').css('display', 'block');
							$('#secondCondition').css('display', 'none');
							$('#selectWith').val(0);
							$('#divdatatypeid').css('display', 'none');
							$('#datadigitid').css('display', 'none');
							$('#div_stringid').css('display', 'none');
							$("#dataid").val("");
							$("#decid").val("");
							$("#stringid").val("");
	                        if(mtype == 1){
	                        	if (alarmfo.triggerValue == "ON") {
									$(
											"input[name='bitsetting'][value=6]")
											.attr(
													"checked",
													true);
									$(
											"input[name='bitsetting'][value=7]")
											.attr(
													"checked",
													false);
								} else {
									$(
											"input[name='bitsetting'][value=7]")
											.attr(
													"checked",
													true);
									$(
											"input[name='bitsetting'][value=6]")
											.attr(
													"checked",
													false);
								}
	                        }
						
						} else {
							$('#firstCondition').css('display', 'block');
							$('#bitsetting').css('display', 'none');
							$('#secondCondition').css('display', 'none');
							$('#selectWith').val(0);
							$('#divdatatypeid').css('display', 'block');
							if ($("#datatypeid").val() == 1000) {
								$('#div_stringid').css('display', 'block');
								if (mtype == 1) {
									$("#stringid").val(alarmfo.num);
								}
							} else {
								$('#datadigitid').css('display', 'block');
								if (mtype == 1) {
									$("#dataid").val(alarmfo.num);
									$("#decid").val(alarmfo.dec);
								}
							}
							if (mtype == 1) {// 编辑赋值
								if (alarmfo.condition_type == 0) {
									$(
											'#bitsetting')
											.css(
													'display',
													'none');
									$(
											'#secondCondition')
											.css(
													'display',
													'none');
									$(
											'#selectWith')
											.val(
													0);
									$(
											'#conditiononeid')
											.val(
													alarmfo.listAlarmTrigger[0].type);
									$(
											'#onenumid')
											.val(
													alarmfo.listAlarmTrigger[0].value);

								} else if (alarmfo.condition_type == 1) {

									$(
											'#bitsetting')
											.css(
													'display',
													'none');
									$(
											'#secondCondition')
											.css(
													'display',
													'block');

									$(
											'#selectWith')
											.val(
													1);
									$(
											'#conditiononeid')
											.val(
													alarmfo.listAlarmTrigger[0].type);
									$(
											'#onenumid')
											.val(
													alarmfo.listAlarmTrigger[0].value);
									$(
											'#conditiontwoid')
											.val(
													alarmfo.listAlarmTrigger[1].type);
									$(
											'#twonumid')
											.val(
													alarmfo.listAlarmTrigger[1].value);

								} else if (alarmfo.condition_type == 2) {
									$(
											'#bitsetting')
											.css(
													'display',
													'none');
									$(
											'#secondCondition')
											.css(
													'display',
													'block');

									$(
											'#selectWith')
											.val(
													2);
									$(
											'#conditiononeid')
											.val(
													alarmfo.listAlarmTrigger[0].type);
									$(
											'#onenumid')
											.val(
													alarmfo.listAlarmTrigger[0].value);
									$(
											'#conditiontwoid')
											.val(
													alarmfo.listAlarmTrigger[1].type);
									$(
											'#twonumid')
											.val(
													alarmfo.listAlarmTrigger[1].value);
								}
							}
						}

					}
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
					$("#dataGroupSelect")
							.change(
									function() {
										$scope
												.showAlarmCfg(
														$scope.paginationConf_alarmcfg.currentPage,
														$scope.paginationConf_alarmcfg.itemsPerPage);
									});

					/**
					 * 更新组ID获取报警配置
					 */
					$scope.showAlarmCfg = function(pageIndex, pageSize) {
						if (pageIndex == 0)
							pageIndex = 1;
						var params = {
							group_id : $("#dataGroupSelect").val(),
							device_id : $scope.deviceid,
							pageIndex : pageIndex,
							pageSize : pageSize

						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/getAlarmCfg",
										params,
										function(data, code, msg) {
											$('#loader-wrapper').css("display","none");// 关闭转圈圈
											if (code == 200) {
												$scope.paginationConf_alarmcfg.totalItems = data.listalrmCfgTrigger.totalRecord;
												$scope.listalrmCfgTrigger = data.listalrmCfgTrigger.list;
												$scope.$apply();
												$("i[name='alarm_i_state']")
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

					var alarmid = -1;
					var dirid = -1;
					var alarmfo = null;
					// 获取修改报警配置信息
					$scope.editmonitor = function(model) {
						alarmfo = model;
						alarmid = model.alarmcfg_id;
						$scope.showAllPlcConf(1);

					}
					$scope.addmonitor = function() {

						alarmid = -1;
						$scope.showAllPlcConf(0);

					}
					// 获取删除监控点信息
					$scope.delmonitor = function(model) {
						alarmid = model.alarmcfg_id;
						dirid = model.dirId;
						swal({
							  title: "确定要删除【" + model.name + "】报警条目吗？",
							  icon: "warning",
							  buttons: true,
							  dangerMode: true,
							})
							.then((willDelete) => {
							  if (willDelete) {
								$scope.del_alarmcfg();
							  } else {
							    console.log("取消删除");
							  }
							});
					}
					// 删除报警条目
					$scope.del_alarmcfg = function() {

						var params = {
							alarmcfg_id : alarmid,
							alarm_dir_id : dirid
						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/delAlrmCfg",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#deleteAlarmid").modal(
														"hide");
												$scope
														.showAlarmCfg(
																$scope.paginationConf_alarmcfg.currentPage,
																$scope.paginationConf_alarmcfg.itemsPerPage);
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
					 * 添加或者更新报警配置
					 */
					$scope.addupAlarm = function() {
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
						if ($("#nameid").val().length > 50) {
							swal({
			                    title:"名称字符长度不能大于50位！" ,
			                    icon: "warning"
			                });
							return;
						}
						if ($("#alarmtextid").val().length > 249) {
							swal({
			                    title:"名称字符长度不能大于249位！" ,
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
					                })
									return;
								}
								if (parseInt($("#child_addrid").val(), 8) < parseInt(
										child_rang[0], 8)
										|| parseInt($("#child_addrid").val(), 8) > parseInt(
												child_rang[1], 8)) {
									swal({
					                    title:"寄存器地址子编号范围有误" ,
					                    icon: "warning"
					                })
									
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
					                })
									return;
								}
								if ($("#child_addrid").val() < parseInt(child_rang[0])
										|| $("#child_addrid").val() > parseInt(child_rang[1])) {
									swal({
					                    title:"寄存器地址子编号范围有误" ,
					                    icon: "warning"
					                })
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
					                })
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
					                })
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
					                })
									return;
								}
								if ($("#stringid").val() < 1
										|| $("#stringid").val() > 256) {
									swal({
					                    title:"操作字符范围是1~256" ,
					                    icon: "warning"
					                })
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
						                })
										return;
									}

								}
								var decdisabled = $("#decid").prop("disabled");
								if (!decdisabled) {
									if (!regnum.test($("#dataid").val())) {
										swal({
						                    title:"小数位数格式错误" ,
						                    icon: "warning"
						                })
										return;
									}
								}

								if ($("#datatypeid").val() == 100) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 16) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}

								} else if ($("#datatypeid").val() == 101) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 6) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}

								} else if ($("#datatypeid").val() == 102) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 4) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}

								} else if ($("#datatypeid").val() == 103) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 4) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 4) {
										swal({
						                    title:"小数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 4) {
										swal({
						                    title:"整数位数+小数位数范围是1~4" ,
						                    icon: "warning"
						                })
										return;

									}
								} else if ($("#datatypeid").val() == 104
										|| $("#datatypeid").val() == 105) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 5) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 5) {
										swal({
						                    title:"小数数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 5) {
										swal({
						                    title:"整数位数+小数位数范围是1~5" ,
						                    icon: "warning"
						                })
										return;

									}
								} else if ($("#datatypeid").val() == 200) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 32) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}

								} else if ($("#datatypeid").val() == 201) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 11) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}

								} else if ($("#datatypeid").val() == 202) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 8) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}

								} else if ($("#datatypeid").val() == 203) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 8) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 8) {
										swal({
						                    title:"小数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 8) {
										swal({
						                    title:"整数位数+小数位数范围是1~8" ,
						                    icon: "warning"
						                })
										return;

									}

								} else if ($("#datatypeid").val() == 204
										|| $("#datatypeid").val() == 205) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 10) {
										swal({
						                    title:"整数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 10) {
										swal({
						                    title:"小数数范围有误" ,
						                    icon: "warning"
						                })
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 10) {
										swal({
						                    title:"整数位数+小数位数范围是1~10" ,
						                    icon: "warning"
						                })
										return;

									} else if ($("#datatypeid").val() == 206) {
										if ($("#dataid").val() < 0
												|| $("#dataid").val() > 7) {
											swal({
							                    title:"整数范围有误" ,
							                    icon: "warning"
							                })
											return;
										}
										if ($("#decid").val() < 0
												|| $("#decid").val() > 7) {
											swal({
							                    title:"小数数范围有误" ,
							                    icon: "warning"
							                })
											return;
										}
										var totle = parseInt($("#dataid").val())
												+ parseInt($("#decid").val());
										if (totle < 1 || totle > 7) {
											swal({
							                    title:"整数位数+小数位数范围是1~7" ,
							                    icon: "warning"
							                })
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
							                })
											return;
										}
										if ($("#decid").val() < 0
												|| $("#decid").val() > 15) {
											swal({
							                    title:"小数范围有误" ,
							                    icon: "warning"
							                })
											return;
										}
										var totle = parseInt($("#dataid").val())
												+ parseInt($("#decid").val());
										if (totle < 1 || totle > 15) {
											swal({
							                    title:"整数位数+小数位数范围是1~15" ,
							                    icon: "warning"
							                })
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
						var types = [];
						var values = [];
						if ($("#addrtypeid").val() == "0") {
							types.push($("input[name='bitsetting']:checked")
									.val());
							if ($("input[name='bitsetting']:checked").val() == 6) {
								values.push("ON");
							} else {
								values.push("OFF");
							}
						} else {
							types.push($("#conditiononeid").val());
							if ($("#onenumid").val() == "") {
								swal({
				                    title:"触发值不能为空" ,
				                    icon: "warning"
				                })
								return;
							}
							if ($("#onenumid").val().length > 60) {
								swal({
				                    title:"触发值字符长度不能大于50位" ,
				                    icon: "warning"
				                })
								return;
							}
							values.push($("#onenumid").val());
							if ($("#selectWith").val() != 0) {
								if ($("#twonumid").val() == "") {
									swal({
					                    title:"触发值不能为空" ,
					                    icon: "warning"
					                })
									return;
								}
								if ($("#twonumid").val().length > 60) {
									swal({
					                    title:"触发值字符长度不能大于50位！" ,
					                    icon: "warning"
					                })
									return;
								}
								types.push($("#conditiontwoid").val());
								values.push($("#twonumid").val());
							}

						}
						var alarmtypes = types.join(",");
						var alarmvalues = values.join(",");
						$('#loader-wrapper').css("display","block");// 添加转圈效果
						var params = {
							alarmcfg_id : alarmid,
							plc_id : plcId,
							device_id : $scope.deviceid,
							name : $("#nameid").val(),
							data_id : $("#datatypeid").val(),
							addr_type : $("#addrtypeid").val(),
							addr : addr_datas,
							digit_binary : scalie_datas,
							rid : $("#registerid").val(),
							rang : rang_datas,
							group_id : $("#selectgroup").val(),
							text : $("#alarmtextid").val(),
							condition_type : $("#selectWith").val(),
							digit_count : digs,
							type : alarmtypes,
							value : alarmvalues,
							alarm_level : $("#gradeid").val()

						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/addUpdataAlarmMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#addAlarmRecord").modal(
														"hide");
											
												$("#dataGroupSelect")
														.val(
																$(
																		"#selectgroup")
																		.val());

												$scope
														.showAlarmCfg(
																$scope.paginationConf_alarmcfg.currentPage,
																$scope.paginationConf_alarmcfg.itemsPerPage);
												if (mtype == 0) {
													
													swal({
									                    title:"添加报警配置成功" ,
									                    icon: "success"
									                });
												} else {
													swal({
									                    title:"修改报警配置成功" ,
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
					$scope.confirmData = function(model) {
						var params = {
							alarm_cfg_id : model.alarm_cfg_id,
							monitor_time : model.monitor_time
						};

						T.common.ajax
								.request(
										"WeconBox",
										"alarmDataAction/confirmData",
										params,
										function(data, code, msg) {
											if (code == 200) {
												swal({
								                    title:"报警数据已确认！" ,
								                    icon: "success"
								                });
												
												$scope
														.alarm_submit(
																$scope.paginationConf_current.currentPage,
																$scope.paginationConf_current.itemsPerPage);

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

					$scope.exportExcel = function(state) {
						var myform = document.getElementById('myform');
						var device_id = $scope.deviceid;
						if (1 == state) {
							var pageIndex = $scope.paginationConf_current.currentPage;
							pageIndex = 0 == pageIndex ? 1 : pageIndex;
							var pageSize = $scope.paginationConf_current.itemsPerPage;
							var gradeid = $("#GradeSelect").val();
							var eventid = $("#EventSelect").val();
							myform.innerHTML = '<input type="hidden" name="pageIndex" value="'
									+ pageIndex
									+ '"/>'
									+ '<input type="hidden" name="pageSize" value="'
									+ pageSize
									+ '"/>'
									+ '<input type="hidden" name="device_id" value="'
									+ device_id
									+ '"/>'
									+ '<input type="hidden" name="grade_id" value="'
									+ gradeid
									+ '"/>'
									+ '<input type="hidden" name="event_id" value="'
									+ eventid
									+ '"/>'
									+ '<input type="hidden" name="state" value="'
									+ state + '"/>'
						} else if (2 == state) {
							var alarm_cfg_id = $("#alarmcfgid").val();
							var alarmcfgname = $("#alarmcfgname").val();
							var start_date = $("#startdateid").val();
							var end_date = $("#enddateid").val();
							var pageIndex = $scope.paginationConf_history.currentPage;
							pageIndex = 0 == pageIndex ? 1 : pageIndex;
							var pageSize = $scope.paginationConf_history.itemsPerPage;
							myform.innerHTML = '<input type="hidden" name="alarm_cfg_id" value="'
									+ alarm_cfg_id
									+ '"/>'
									+ '<input type="hidden" name="alarmcfgname" value="'
									+ alarmcfgname
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
									+ pageSize
									+ '"/>'
									+ '<input type="hidden" name="device_id" value="'
									+ device_id
									+ '"/>'
									+ '<input type="hidden" name="grade_id" value="'
									+ -1
									+ '"/>'
									+ '<input type="hidden" name="event_id" value="'
									+ -1
									+ '"/>'
									+ '<input type="hidden" name="state" value="'
									+ state + '"/>'

						}

						myform.action = T.common.requestUrl.WeconBox
								+ 'excelact/filedownloadExportAlarm';

						myform.submit();

					}
				})