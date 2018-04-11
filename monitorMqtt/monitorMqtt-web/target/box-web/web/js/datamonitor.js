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
									$("body").css("display", "block");
								});

						$scope.type = 0;
						$scope.getDataType();
						$scope.act_group($scope.deviceid);

					}

					// 获取分组
					$scope.act_group = function(deviceid) {
						inivalize = 0;
						var params = {
							device_id : deviceid
						};
						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/getActGroup",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope.dir_list = data.ActGroup;
												$scope.$apply();
												if (data.ActGroup != null
														&& $scope.type == 0) {
													var fristGroupId = data.ActGroup[0].id;

													actgroupId = fristGroupId;
													$scope.createWebSocket();
												}

											} else {

												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}

					$scope.paginationConf = {
						totalItems : $scope.count,
					}

					var ws;// websocket实例
					var actgroupId;// 分组id
					var lockReconnect = false;// 避免重复连接
					var inivalize = 0;
					var isclick = false;// 下发数据是否点击确定键

					$scope.createWebSocket = function() {
						try {
							ws = new WebSocket(
									T.common.requestUrl['WeconBoxWs']
											+ '/actdataweb-websocket/websocket?'
											+ T.common.websocket.getParams());
							console.log("run createWebSocket");
							$scope.initEventHandle();
						} catch (e) {
							$scope.reconnect();
						}
					}

					$scope.initEventHandle = function() {
						ws.onclose = function() {
							$scope.reconnect();
						};
						ws.onerror = function() {
							$scope.reconnect();
						};
						ws.onopen = function() {
							console.log("ws.onopen");
							$scope.paginationConf = {
								currentPage : 1,
								itemsPerPage : 10,
								totalItems : $scope.count,
								pagesLength : 15,
								perPageOptions : [ 5, 10, 20, 50, 100 ],
								rememberPerPage : 'perPageItems',
								onChange : function() {
									if (this.currentPage != 0) {
										$scope.ws_send(this.currentPage,
												this.itemsPerPage, actgroupId);
									}
								}
							}
							$scope.ws_send($scope.paginationConf.currentPage,
									$scope.paginationConf.itemsPerPage,
									actgroupId);

							// 心跳检测重置
							heartCheck.reset().start();
						};
						ws.onmessage = function(evt) {
							if (JSON.parse(evt.data).piBoxActDateMode != null) {
								console.log(inivalize);
								if (inivalize == 0) {
									$scope.paginationConf.totalItems = JSON
											.parse(evt.data).piBoxActDateMode.totalRecord;
									$scope.actDatas = JSON.parse(evt.data).piBoxActDateMode.list;
									console.log($scope.actDatas);
									$scope.$apply();

									angular.forEach($scope.actDatas, function(
											data, index, array) {

										$scope.editable_name(data, 0);
										if (data.addr_type != 0) {
											$scope.editable_value(data, 0);
										} else {
											$scope.createSwitchState(data);
										}

									});
									$("i[name='act_i_state']").tooltip();

								}

							} else {
								// 下发数据到盒子反馈
								$scope.resultData = JSON.parse(evt.data).resultData;

								$("#loadingModal").modal("hide");
								if ($scope.resultData == 0) {
									alert(JSON.parse(evt.data).resultError);
									$scope.ws_send(
											$scope.paginationConf.currentPage,
											$scope.paginationConf.itemsPerPage,
											actgroupId);
								} else {
									if (inivalize == 1) {
										alert("数据下发盒子成功！");
									}

								}
								inivalize = 0;
								isclick = false;

								// $scope.ws_send(
								// $scope.paginationConf.currentPage,
								// $scope.paginationConf.itemsPerPage,
								// actgroupId);
							}

							// 如果获取到消息，心跳检测重置
							// 拿到任何消息都说明当前连接是正常的
							heartCheck.reset().start();

						}

					}

					$scope.reconnect = function() {
						if (lockReconnect)
							return;
						lockReconnect = true;
						// 没连接上会一直重连，设置延迟避免请求过多
						setTimeout(function() {
							$scope.createWebSocket();
							lockReconnect = false;
						}, 2000);
					}

					// 心跳检测
					var heartCheck = {
						timeout : 60000,// 60秒
						timeoutObj : null,
						serverTimeoutObj : null,
						reset : function() {
							clearTimeout(this.timeoutObj);
							clearTimeout(this.serverTimeoutObj);
							return this;
						},
						start : function() {
							var self = this;
							this.timeoutObj = setTimeout(function() {
								// 这里发送一个心跳，后端收到后，返回一个心跳消息，
								// onmessage拿到返回的心跳就说明连接正常
								var params = {
									markid : -1,

								};
								ws.send(angular.toJson(params));
								/*
								 * self.serverTimeoutObj = setTimeout(function ()
								 * {// 如果超过一定时间还没重置，说明后端主动断开了 ws.close();//
								 * 如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect //
								 * 会触发onclose导致重连两次 }, self.timeout)
								 */
							}, this.timeout)
						}
					}
					$scope.ws_send = function(pageIndex, pageSize, groupId) {
						actgroupId = groupId;
						inivalize = 0;
						if (pageIndex == 0)
							pageIndex = 1;
						var params = {
							markid : 0,
							device_id : $scope.deviceid,
							acc_dir_id : groupId,
							pageIndex : pageIndex,
							pageSize : pageSize

						};
						ws.send(angular.toJson(params));
					}

					$scope.ws_close = function() {
						ws.close();
					}
					// 下发数据到盒子
					$scope.putMess = function(model, value) {

						var params = {
							markid : 1,
							value : value,
							addr_id : model.id
						};
						ws.send(angular.toJson(params));

					}
					// 位地址初始化控件
					$scope.createSwitchState = function(data) {
						$swithId = $('#mySwitch_' + data.id + ' ' + 'input');
						$swithId.bootstrapSwitch({
							onText : "ON",
							offText : "OFF",
							onColor : "success",
							offColor : "danger",
							size : "small",
							state : data.re_value == 1 ? true : false
						}).on(
								"switchChange.bootstrapSwitch",
								function(event, state) {

									var model = JSON.parse($(this).attr(
											"data-bit"));

									if (model.box_state != 1) {
										alert("检查盒子是否在线！");
										if (state != false) {
											$(this).bootstrapSwitch('state',
													false, true);
										}
										if (state != true) {
											$(this).bootstrapSwitch('state',
													true, true);
										}
										return false;

									} else if (model.state != 0) {
										alert("条目未下发！");
										if (state != false) {
											$(this).bootstrapSwitch('state',
													false, true);
										}
										if (state != true) {
											$(this).bootstrapSwitch('state',
													true, true);
										}
										return false;
									} else if (model.re_state != 1) {
										alert("检查监控点是否在线！");
										if (state != false) {
											$(this).bootstrapSwitch('state',
													false, true);
										}
										if (state != true) {
											$(this).bootstrapSwitch('state',
													true, true);
										}
										return false;
									}
									var value = 0;
									if (state) {
										value = 1;
									} else {
										value = 0;
									}
									inivalize = 2;
									$scope.putMess(model, value);

								});

					}

					// 复制监控点
					$scope.copymonitor = function(model) {
						$scope.monitorid = model.id;// 监控点id
						$scope.alais = model.ref_alais;// 监控点别名

						angular.forEach($scope.dir_list, function(data, index,
								array) {
							if (actgroupId == data.id) {
								$("#nowgroupid").html(data.name);
								$scope.groupName = data.name;
							}
						});

					}
					// 复制监控点到其他组
					$scope.copy_monitor_group = function() {
						if ($('#copymonitorid').val() == actgroupId) {
							alert("【" + $scope.groupName + "】已经存在该监控点！");
							return;

						}
						var params = {
							monitorid : $scope.monitorid,
							alais : $scope.alais,
							acc_dir_id : $('#copymonitorid').val()
						};

						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/copyMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#copyDataGroup").modal(
														"hide");
												$scope
														.ws_send(
																$scope.paginationConf.currentPage,
																$scope.paginationConf.itemsPerPage,
																actgroupId);
												alert("复制成功！");

											} else {

												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}
					// 移动监控点
					$scope.movemonitor = function(model) {
						$scope.moveMonitorid = model.id;// 监控点id
						$scope.moveAlais = model.ref_alais;// 监控点别名

						angular.forEach($scope.dir_list, function(data, index,
								array) {
							if (actgroupId == data.id) {
								$("#movenowgroupid").html(data.name);
								$scope.movegroupName = data.name;
							}
						});

					}
					// 移动监控点到其他组
					$scope.move_monitor_group = function() {
						if ($('#movemonitorid').val() == actgroupId) {
							alert("【" + $scope.movegroupName + "】已经存在该监控点！");
							return;

						}
						var params = {
							monitorid : $scope.moveMonitorid,
							alais : $scope.moveAlais,
							to_acc_dir_id : $('#movemonitorid').val(),
							from_acc_dir_id : actgroupId
						};

						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/moveMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#moveDataGroup").modal(
														"hide");
												$scope
														.ws_send(
																$scope.paginationConf.currentPage,
																$scope.paginationConf.itemsPerPage,
																actgroupId);
												alert("移动成功！");

											} else {

												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}

					// 获取移除监控点信息
					$scope.remonitor = function(model) {
						$scope.delmonitorid = model.id;// 监控点id
						$scope.isdelmonitor = 1; // 1.移除监控点 2.删除监控点配置
						$("#delgroupid").html(
								"确定要从该分组移除【" + model.ref_alais + "】监控点吗？");
					}
					// 获取删除监控点信息
					$scope.delmonitor = function(model) {
						$scope.delmonitorid = model.id;// 监控点id
						$scope.isdelmonitor = 2; // 1.移除监控点 2.删除监控点配置
						$("#delgroupid").html(
								"确定要删除【" + model.ref_alais + "】监控点配置吗？");
					}
					// 移除监控点
					$scope.del_monitor_group = function() {

						var params = {
							monitorid : $scope.delmonitorid,
							acc_dir_id : actgroupId,
							isdel : $scope.isdelmonitor
						};

						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/delMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#deletePoint").modal("hide");
												$scope
														.ws_send(
																$scope.paginationConf.currentPage,
																$scope.paginationConf.itemsPerPage,
																actgroupId);
												if ($scope.isdelmonitor == 1) {
													alert("移除成功！");

												} else {
													alert("删除成功！");
												}

											} else {

												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}
					// 创建分组
					$scope.add_group = function() {
						var params = {
							id : -1,
							name : $('#newGroupName').val(),
							type : 1,
							device_id : $scope.deviceid
						};

						T.common.ajax
								.request(
										"WeconBox",
										"userdiract/saveuserdir",
										params,
										function(data, code, msg) {
											if (code == 200) {

												var name = $('#newGroupName')
														.val();
												var length = $('#monitorTab')
														.children().length;
												var lastPosition = length - 2;
												($("#monitorTab li:eq("
														+ lastPosition + ")"))
														.after("<li><a href=\"#data-item-1\" data-toggle=\"tab\">"
																+ name
																+ "</a></li>");

												$("#addDataGroup")
														.modal("hide");
												$scope.type = 1;

												$scope
														.act_group($scope.deviceid);

											} else {

												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}
					$scope.showAddGroup = function() {
						$('#identifier').modal('show');
					}
					// 获取修改分组名称
					$scope.editGroup = function(model) {

						$("#editid").val(model.name);
						$scope.editGroupId = model.id;

						$("#editGroupName").modal("show");

					}
					// 修改分组
					$scope.edit_group = function() {
						var params = {
							id : $scope.editGroupId,
							name : $('#editid').val(),
							type : 1,
							device_id : $scope.deviceid
						};

						T.common.ajax.request("WeconBox",
								"userdiract/saveuserdir", params, function(
										data, code, msg) {
									if (code == 200) {

										$("#editGroupName").modal("hide");
										$scope.type = 1;

										$scope.act_group($scope.deviceid);

									} else {

										alert(code + "-" + msg);
									}
								}, function() {
									console.log("ajax error");
								});
					}
					// 获取删除分组名称
					$scope.delGroup = function(model) {
						var text = "确定删除【" + model.name + "】分组?"
						$("#delid").html(text);
						$scope.delActGroupId = model.id;

						$("#deleteGroup").modal("show");

					}
					// 删除分组
					$scope.del_group = function() {
						var params = {
							id : $scope.delActGroupId,
						};

						if ($scope.deviceid > 0) {
							T.common.ajax.request("WeconBox",
									"actDataAction/delActGroup", params,
									function(data, code, msg) {
										if (code == 200) {

											$("#deleteGroup").modal("hide");
											$scope.type = 1;

											$scope.act_group($scope.deviceid);

										} else {

											alert(code + "-" + msg);
										}
									}, function() {
										console.log("ajax error");
									});

						} else {
							T.common.ajax.request("WeconBox",
									"userdiract/deluserdir", params, function(
											data, code, msg) {
										if (code == 200) {

											$("#deleteGroup").modal("hide");
											$scope.type = 1;

											$scope.act_group($scope.deviceid);

										} else {

											alert(code + "-" + msg);
										}
									}, function() {
										console.log("ajax error");
									});

						}

					}

					$scope.editable_name = function(model, index) {
						if (index == 1) {
							inivalize = 1;
							isclick = false;
						}
						$act_name = $('#act_name_' + model.id);
						$act_name.editable({
							type : "text", // 编辑框的类型。支持text|textarea|select|date|checklist等
							title : "监控点名称", // 编辑框的标题
							disabled : false, // 是否禁用编辑
							emptytext : "空文本", // 空值的默认文本
							mode : "inline", // 编辑框的模式：支持popup和inline两种模式，默认是popup
							validate : function(value) { // 字段验证
								if (!$.trim(value)) {
									return '不能为空';
								}
								$scope.upActcfgName(model, value);
							}
						}).click(function() {
							$(".editable-cancel").click(function() {
								inivalize = 0;
							});
						});
						$act_name.on('hidden.bs.modal', function() {// 点击空白处的时候触发
							inivalize = 0;
						});
					}

					$scope.editable_value = function(model, index) {

						$act_value = $('#act_value_' + model.id);
						if (index == 1) {
							isclick = false;
							inivalize = 1;
						}
						$act_value.editable({
							type : "text",
							title : "监控点数值",
							pk : 1,
							url : '',
							disabled : false,
							emptytext : "0",
							mode : "inline",

							validate : function(value) {
								if (!$.trim(value)) {
									return '不能为空!';
								}
								if (model.box_state != 1) {
									return '检查盒子是否在线！';
								}
								if (model.state != 0) {
									return '条目未下发！';
								}
								if (model.re_state != 1) {
									return '检查监控点是否在线！';
								}
								if (value.length > 256) {
									return '数值长度超出范围！';
								}

								$("#loadingModal").modal("show");
								isclick = true;
								console.log("isclick==" + isclick);
								$scope.putMess(model, value);

							}

						}).click(function() {
							$(".editable-cancel").click(function() {
								inivalize = 0;
							});
						});
						$act_value.on('hidden.bs.modal', function() {// 点击空白处的时候触发
							console.log("isclick33==" + isclick);
							if (isclick == false) {
								inivalize = 0;
							}

						});

					}

					// 修改监控点名称
					$scope.upActcfgName = function(model, name) {
						var params = {

							name : name,
							id : model.id,
							actgroupId : actgroupId

						};
						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/upActcfgName",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$scope
														.ws_send(
																$scope.paginationConf.currentPage,
																$scope.paginationConf.itemsPerPage,
																actgroupId)
											} else {

												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					}

					/*
					 * 展示所有监控点设置iframe的url属性
					 */
					$scope.showRestList = function() {
						console.log();
						var path = "viewmanagerpointTable.html?accounttype="
								+ $scope.accounttype + "&actgroupId="
								+ actgroupId;
						$("#myiframe").attr('src', path);
					}

					/*
					 * 提交选中的监控点
					 */
					$scope.setViewOpint = function() {
						var rightOption = [];
						var chk_value = [];
						$("#myiframe").contents().find(
								'input[name="cbid"]:checked').each(function() {
							chk_value.push($(this).val());
						});
						var ids = chk_value.join(",");
						if (chk_value.length == 0) {
							alert("请选择至少一条监控点");

							return;
						}
						var params = {
							acc_dir_id : actgroupId,
							selectedId : ids
						};
						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/allotMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#dispatchpoint").modal(
														"hide");
												$scope
														.ws_send(
																$scope.paginationConf.currentPage,
																$scope.paginationConf.itemsPerPage,
																actgroupId);

												alert("分配监控点成功");

											} else {
												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});
					};
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
												if (dealtype == 1) {

													$scope.showtype = 1;
													$("#nameid ").val(
															minfo.name);
													$("#conid ").val(
															minfo.plc_id);
													$scope
															.condevice(minfo.plc_id);

													$("#datatypeid").val(
															minfo.data_id);
													$("#addrid").val(
															minfo.main_addr);
													$("#describeid").val(
															minfo.describe);

												} else {
													if (dealtype == 0) {
														$scope.showtype = 0;
													} else {
														$scope.showtype = 2;// 批量添加
													}
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
														$("#describeid")
																.val("");
													}

												}
												$scope.datatype();

											} else {
												alert(code + "-" + msg);
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
										$scope.datatype();
									} else {
										alert(code + "-" + msg);
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
															$("#unitid")
																	.html(
																			minfo.ext_unit);

														}

													}
													// 判断地址，相应的隐藏界面
													$scope.initbit();
												}

											} else {
												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});

					}
					/**
					 * 地址判断后的操作 0-添加 1-修改
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
							console.log("dsada=" + $("#datatypeid").val());
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
						$('#divbatchid').css('display', 'none');
						$scope.showAllPlcConf(1);

					}
					$scope.addmonitor = function() {

						mid = -1;
						$("#dataid").val("");
						$("#decid").val("");
						$('#divbatchid').css('display', 'none');
						$scope.showAllPlcConf(0);

					}
					// 批量添加监控点
					$scope.batchmonitor = function() {
						mid = -1;
						$("#dataid").val("");
						$("#decid").val("");
						$('#divbatchid').css('display', 'block');
						$scope.showAllPlcConf(2);

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
							alert("检查是否配置好通讯口配置！");
							return;
						}
						if ($("#nameid").val() == "" || plcId == ""
								|| $("addrtypeid").val() == ""
								|| $("registerid").val() == "") {
							alert("参数未配置完整！");
							return;
						}
						if ($('#registeraddr').css('display') == 'block') {
							if ($("#addrid").val() == "") {
								alert("参数未配置完整！");
								return;
							}

						}
						if ($('#child_registeraddr').css('display') == 'block') {
							if ($("#child_addrid").val() == "") {
								alert("参数未配置完整！");
								return;
							}

						}
						if ($("#nameid").val().length > 50) {
							alert("名称字符长度不能大于50位！");
							return;
						}
						if ($("#describeid").val().length > 64) {
							alert("描述字符长度不能大于50位！");
							return;
						}

						var display = $('#registeraddr').css('display');
						if (display == 'block') {
							var rang, reg;
							if ($("#scaleid").text() == "八进制") {
								rang = $("#rangid").text().split(" ");
								reg = /^[0-7]*$/;
								if (!reg.test($("#addrid").val())) {
									alert("寄存器地址主编号格式错误");
									return;
								}
								if (parseInt($("#addrid").val(), 8) < parseInt(
										rang[0], 8)
										|| parseInt($("#addrid").val(), 8) > parseInt(
												rang[1], 8)) {
									alert("寄存器地址主编号范围有误");
									return;
								}

							} else if ($("#scaleid").text() == "十进制") {
								rang = $("#rangid").text().split(" ");
								reg = /^0|[1-9]\d*$/;
								if (!reg.test($("#addrid").val())) {
									alert("寄存器地址主编号格式错误");
									return;
								}
								if ($("#addrid").val() < parseInt(rang[0])
										|| $("#addrid").val() > parseInt(rang[1])) {
									alert("寄存器地址主编号范围有误");
									return;
								}

							} else if ($("#scaleid").text() == "十六进制") {
								rang = $("#rangid").text().split(" ");
								reg = /^[0-9a-fA-F]*$/;
								if (!reg.test($("#addrid").val())) {
									alert("寄存器地址主编号格式错误");
									return;
								}
								if (parseInt($("#addrid").val(), 16) < parseInt(
										rang[0], 16)
										|| parseInt($("#addrid").val(), 16) > parseInt(
												rang[1], 16)) {
									alert("寄存器地址主编号范围有误");
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
									alert("寄存器地址子编号格式错误");
									return;
								}
								if (parseInt($("#child_addrid").val(), 8) < parseInt(
										child_rang[0], 8)
										|| parseInt($("#child_addrid").val(), 8) > parseInt(
												child_rang[1], 8)) {
									alert("寄存器地址子编号范围有误");
									return;
								}
							} else if ($("#child_scaleid").text() == "十进制") {
								child_rang = $("#child_rangid").text().split(
										" ");
								child_reg = /^0|[1-9]\d*$/;
								if (!child_reg.test($("#child_addrid").val())) {
									alert("寄存器地址子编号格式错误");
									return;
								}
								if ($("#child_addrid").val() < parseInt(child_rang[0])
										|| $("#child_addrid").val() > parseInt(child_rang[1])) {
									alert("寄存器地址子编号范围有误");
									return;
								}
							} else if ($("#child_scaleid").text() == "十六进制") {
								child_rang = $("#child_rangid").text().split(
										" ");
								child_reg = /^[0-9a-fA-F]*$/;
								if (!child_reg.test($("#child_addrid").val())) {
									alert("寄存器地址子编号格式错误");
									return;
								}
								if (parseInt($("#child_addrid").val(), 16) < parseInt(
										child_rang[0], 16)
										|| parseInt($("#child_addrid").val(),
												16) > parseInt(child_rang[1],
												16)) {
									alert("寄存器地址子编号范围有误");
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
									alert("操作字符只能输入正整数");
									return;
								}
								if ($("#stringid").val() < 1
										|| $("#stringid").val() > 256) {
									alert("操作字符范围是1~256");
									return;
								}
								num = $("#stringid").val();
								digit_counts.push(num);

							} else {

								var datadisabled = $("#dataid")
										.prop("disabled");
								if (!datadisabled) {
									if (!regnum.test($("#dataid").val())) {
										alert("整数位数格式错误");
										return;
									}

								}
								var decdisabled = $("#decid").prop("disabled");
								if (!decdisabled) {
									if (!regnum.test($("#dataid").val())) {
										alert("小数位数格式错误");
										return;
									}
								}

								if ($("#datatypeid").val() == 100) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 16) {
										alert("整数范围有误");
										return;
									}

								} else if ($("#datatypeid").val() == 101) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 6) {
										alert("整数范围有误");
										return;
									}

								} else if ($("#datatypeid").val() == 102) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 4) {
										alert("整数范围有误");
										return;
									}

								} else if ($("#datatypeid").val() == 103) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 4) {
										alert("整数范围有误");
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 4) {
										alert("小数数范围有误");
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 4) {
										alert("整数位数+小数位数范围是1~4");
										return;

									}
								} else if ($("#datatypeid").val() == 104
										|| $("#datatypeid").val() == 105) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 5) {
										alert("整数范围有误");
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 5) {
										alert("小数数范围有误");
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 5) {
										alert("整数位数+小数位数范围是1~5");
										return;

									}
								} else if ($("#datatypeid").val() == 200) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 32) {
										alert("整数范围有误");
										return;
									}

								} else if ($("#datatypeid").val() == 201) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 11) {
										alert("整数范围有误");
										return;
									}

								} else if ($("#datatypeid").val() == 202) {
									if ($("#dataid").val() < 1
											|| $("#dataid").val() > 8) {
										alert("整数范围有误");
										return;
									}

								} else if ($("#datatypeid").val() == 203) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 8) {
										alert("整数范围有误");
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 8) {
										alert("小数数范围有误");
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 8) {
										alert("整数位数+小数位数范围是1~8");
										return;

									}

								} else if ($("#datatypeid").val() == 204
										|| $("#datatypeid").val() == 205) {
									if ($("#dataid").val() < 0
											|| $("#dataid").val() > 10) {
										alert("整数范围有误");
										return;
									}
									if ($("#decid").val() < 0
											|| $("#decid").val() > 10) {
										alert("小数数范围有误");
										return;
									}
									var totle = parseInt($("#dataid").val())
											+ parseInt($("#decid").val());
									if (totle < 1 || totle > 10) {
										alert("整数位数+小数位数范围是1~10");
										return;

									} else if ($("#datatypeid").val() == 206) {
										if ($("#dataid").val() < 0
												|| $("#dataid").val() > 7) {
											alert("整数范围有误");
											return;
										}
										if ($("#decid").val() < 0
												|| $("#decid").val() > 7) {
											alert("小数数范围有误");
											return;
										}
										var totle = parseInt($("#dataid").val())
												+ parseInt($("#decid").val());
										if (totle < 1 || totle > 7) {
											alert("整数位数+小数位数范围是1~7");
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
											alert("整数范围有误");
											return;
										}
										if ($("#decid").val() < 0
												|| $("#decid").val() > 15) {
											alert("小数数范围有误");
											return;
										}
										var totle = parseInt($("#dataid").val())
												+ parseInt($("#decid").val());
										if (totle < 1 || totle > 15) {
											alert("整数位数+小数位数范围是1~15");
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

						var divbatchid = $('#divbatchid').css('display');
						if (divbatchid == 'block') {
							if ($("#increaseid").val() == "") {
								alert("请输入增量");
								return;
							}
							var regincrease = /^-?[1-9]\d*$/;
							if (!regincrease.test($("#increaseid").val())) {
								alert("请输入增量不为0的整数");
								return;
							}
							if ($("#batchid").val() == "") {
								alert("请输入批量个数");
								return;
							}
							var regbatchid = /^[1-9]\d*$/;
							if (!regbatchid.test($("#batchid").val())) {
								alert("请输入批量个数大于0的正整数");
								return;
							}
							if ($("#batchid").val() > 40) {
								alert("请输入批量个数大于0小于等于40的正整数");
								return;
							}

						}
						var batchvalue;
						if (mtype != 2) {
							batchvalue = "0";
						} else {
							batchvalue = $("#batchid").val();
						}
						if (mtype == 2) {

							$("#loadingModalid").modal("show");// 批量添加转圈效果

						}
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
							data_type : "0",
							batch : batchvalue,
							increase : $("#increaseid").val(),
							group_id : actgroupId,
							unit : $("#unitid").val()

						};
						T.common.ajax
								.request(
										"WeconBox",
										"actDataAction/addUpdataMonitor",
										params,
										function(data, code, msg) {
											if (code == 200) {
												$("#addpoint").modal("hide");
												$scope
														.ws_send(
																$scope.paginationConf.currentPage,
																$scope.paginationConf.itemsPerPage,
																actgroupId);
												if (mtype == 0) {
													alert("添加实时监控点成功");
												} else if (mtype == 1) {
													alert("修改实时监控点成功");
												} else {
													$("#loadingModalid").modal(
															"hide");
													alert("批量添加实时监控点成功");
												}

											} else {
												$("#loadingModalid").modal(
														"hide");
												alert(code + "-" + msg);
											}
										}, function() {
											console.log("ajax error");
										});

					}
					//批量导出实时监控点
					$scope.exportExcel = function() {
						var myform = document.getElementById('myform');
						var device_id = $scope.deviceid;
						var pageIndex = $scope.paginationConf.currentPage;
						pageIndex = 0 == pageIndex ? 1 : pageIndex;
						var pageSize = $scope.paginationConf.itemsPerPage;
						myform.innerHTML = '<input type="hidden" name="pageIndex" value="'
								+ pageIndex
								+ '"/>'
								+ '<input type="hidden" name="pageSize" value="'
								+ pageSize
								+ '"/>'
								+ '<input type="hidden" name="device_id" value="'
								+ device_id
								+ '"/>'

						myform.action = T.common.requestUrl.WeconBox
								+ 'excelact/filedownloadExportReal';

						myform.submit();

					}

				})