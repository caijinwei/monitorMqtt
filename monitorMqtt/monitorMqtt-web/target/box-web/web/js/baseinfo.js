/**
 * Created by caijinw on 2017/8/8.
 */

var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    /*
     * --------------------------------------------------------基本信息------------------------------------------------------------------------------
     * */
    /*
     * 盒子基本信息展示
     * */
    $scope.onInit = function () {
        $("#loadingModal").modal("hide");
        $scope.device_id = T.common.util.getParameter("device_id");
        $scope.device_name = T.common.util.getParameter("device_name");
        $("#loadingModal").modal("show");
        $scope.showBaseInfo();
        $scope.showPlcSetDefault();
        $("#loadingModal").modal("hide");
        $("#map_body").css("display", "none");
    }
    $scope.showBaseInfo = function () {
        $scope.device_id = T.common.util.getParameter("device_id");
        var params = {device_id: $scope.device_id};
        T.common.ajax.request("WeconBox", "baseInfoAction/showBaseInfo", params, function (data, code, msg) {
            if (code == 200) {
                $scope.deviceUseOptions = data.deviceUseOptions;
                $scope.$apply();
                $scope.infoData = data.device;
                $("#maxHisDataCount").val($scope.infoData.max_his_data_count);
                //行业类型对象
                var deviceUse = data.deviceUse;
                if (deviceUse != null) {
                    if (deviceUse.useCode == 999) {
                        $("#deviceUse").val(deviceUse.useCode);
                        $("#otherDeviceUseName").show();
                        $scope.$apply();
                        $("#otherDeviceUseName").val(deviceUse.otherUseName);
                    }else{
                        $("#deviceUse").val(deviceUse.useCode);
                    }
                }

                //固件更新时候要用到
                $scope.dev_model = data.device.dev_model;

                $scope.accounttype = data.userType;
                var map = data.device.map;
                if (map != "" && map != null) {
                    var maps = map.split(",");
                    $scope.map_a = maps[0];
                    $scope.map_o = maps[1];
                }
                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 点击刷新  更新用户的state状态
     * */
    $scope.flushState = function () {
        $scope.infoData.state = "";
        $scope.device_id = T.common.util.getParameter("device_id");
        var params = {device_id: $scope.device_id};
        T.common.ajax.request("WeconBox", "baseInfoAction/showBaseInfo", params, function (data, code, msg) {
            if (code == 200) {
                $scope.infoData.state = data.device.state;
                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    /*
     * 盒子与账户解除关联
     * */
    $scope.deletePIBoxBtn = function (device_id) {
        $("#btn-delete-PiBoxCancel").attr("disabled", "disabled");
        var parmas =
        {
            device_id: device_id
        }
        T.common.ajax.request("WeconBox", "baseInfoAction/deletePIBox", parmas, function (data, code, msg) {
            if (code == 200) {
                $("#deletePIBox").modal('hide');
                alert("解除绑定成功！");
                if (self != top) {
                    window.parent.reloadBoxList();
                }
                window.location.href = "overview.html";
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    $scope.getDeviceByAccId = function(){
        cleanCopyCheccbox();
        var params = {currentDeviceId:$scope.infoData.device_id};
        T.common.ajax.request("WeconBox", "baseInfoAction/getOtherDeviceByAccId", params, function (data, code, msg) {
            if (code == 200) {
                $scope.devices=data.deviceList;
                $scope.$apply();
                $("#copyConfigModal").modal("show");
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    $scope.copyDeviceConfig = function(){

        var fromDeviceId=$("#fromDeviceId").val();
        var baseInfoChck = ($("#baseInfoChck").get(0).checked == true ?  1 : 0) +"";
        var comSelCheck = ($("#comSelCheck").get(0).checked == true ?  1 : 0)+"";
        var isRealSelCheck = ($("#isRealSelCheck").get(0).checked == true ?  1 : 0+"");
        var isAlarmSelCheck = ($("#isAlarmSelCheck").get(0).checked == true ?  1 : 0+"");
        var isHisSelCheck = ($("#isHisSelCheck").get(0).checked == true ?  1 : 0)+"";
        var params = {
            fromDeviceId: fromDeviceId+"",
            toDeviceId : $scope.infoData.device_id,
            isCopyBaseInfo : baseInfoChck,
            isCopyCom : comSelCheck,
            isCopyReal : isRealSelCheck,
            isCopyHis : isHisSelCheck,
            isCopyAlarm:isAlarmSelCheck
        };
        /*
        * isCopyBaseInfo='null', isCopyCom='null', isCopyReal='null', isCopyHis='null', isCopyAlarm='null'
        * */
        T.common.ajax.request("WeconBox", "baseInfoAction/copyDeviceConfig", params, function (data, code, msg) {
            if (code == 200) {
                var alarmMes ="";
                $("#copyConfigModal").modal("hide");

                $scope.copyMessage=data.data;
                $.each(data.data,function(name,value){
                    if(value == "成功"){
                        alarmMes += name+"&nbsp;&nbsp;&nbsp;&nbsp;<span  style='color: green;'>"+value+"</span></br>";

                    }else{
                        alarmMes += name+"&nbsp;&nbsp;&nbsp;&nbsp;<span  style='color: red;'>"+value+"</span></br>";

                    }
                });
                $("#noticeMessage").empty();
                $("#noticeMessage").append(alarmMes);
                console.log("消息是:   "+alarmMes);
                $("#noticeCopy").modal("show");
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });

    }


    /*
     * --------------------------------------------------------通讯口配置------------------------------------------------------------------------------
     * */
    /*
     * 盒子下plc配置展示
     * */
    $scope.initPlcSetting = function () {
        $scope.showPlcList();
    }

    /*
     * plc配置展示
     * */
    $scope.showPlcList = function () {
        $scope.device_id = T.common.util.getParameter("device_id");
        $("#device_id").val($scope.device_id);
        var params = {device_id: $scope.device_id};
        T.common.ajax.request("WeconBox", "plcInfoAction/showAllPlcConf", params, function (data, code, msg) {
            var test = 1;
            if (code == 200) {
                $scope.infoDatas = data.infoDatas;
                $scope.delinfoDatas = data.delInfoDatas;
                $(function () {
                    $("[data-toggle='tooltip']").tooltip();
                });
                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 修改时候 单个plc展示
     * 根据plc_id展示plc
     * */
    $scope.showPlcInfoById = function (plc_id) {
        var params = {plc_id: plc_id};
        T.common.ajax.request("WeconBox", "plcInfoAction/findPlcInfoById", params, function (data, code, msg) {
            if (code == 200) {
                $scope.plcInfoById = data.plcInfo;
                $scope.selectedPort = $scope.plcInfoById.port;
                $("#port").val($scope.plcInfoById.port);
                if ($scope.selectedPort == "USB") {
                    $scope.temPtype = $scope.usbDeviceMapListByPtype;
                    $scope.pTypeSet = $scope.usbDeviceMapListKeyByPtype;
                    $scope.temType = $scope.usbDeviceMapListByType;
                } else if ($scope.selectedPort == "Ethernet") {
                    $scope.temPtype = $scope.ethernetMapListByPtype;
                    $scope.pTypeSet = $scope.ethernetMapListKeyByPtype;
                    $scope.temType = $scope.ethernetMapListByType;
                } else {
                    $scope.temPtype = $scope.comMapListByPtype;
                    $scope.pTypeSet = $scope.comMapListKeyByPtype;
                    $scope.temType = $scope.comMapListByType;
                }
                var temType = $scope.plcInfoById.type;
                $scope.$apply();
                $scope.selectedPtype = $scope.plcInfoById.ptype;
                /*
                 * 修改Ptype
                 * */
                $scope.selectedPtype = $scope.temType[temType][0].ptype;
                var TemPtype = $scope.selectedPtype;
                $scope.pType = $scope.temPtype[TemPtype];
                $scope.$apply();

                if ($scope.plcInfoById.port == 'Ethernet') {
                    $scope.ethernetShow = 1;
                    $scope.portIfShow = 0;
                    $('#net_ipaddr').val($scope.plcInfoById.net_ipaddr);
                    $('#net_type').val($scope.plcInfoById.net_type);
                    $('#net_port').val($scope.plcInfoById.net_port);
                    $('#net_isbroadcast').val($scope.plcInfoById.net_isbroadcast);
                    $('#net_broadcastaddr').val($scope.plcInfoById.net_broadcastaddr);
                    $("#state").val($scope.plcInfoById.state);
                } else if ($scope.plcInfoById.port == 'COM1' || $scope.plcInfoById.port == 'COM2') {
                    $scope.ethernetShow = 0;
                    $scope.portIfShow = 1;
                    $scope.$apply();
                    $("#comtype").val($scope.plcInfoById.comtype);
                    $("#baudrate").val($scope.plcInfoById.baudrate);
                    $("#stop_bit").val($scope.plcInfoById.stop_bit);
                    $("#data_length").val($scope.plcInfoById.data_length);
                    $("#check_bit").val($scope.plcInfoById.check_bit);
                    $scope.$apply();
                } else {
                    $scope.ethernetShow = 0;
                    $scope.portIfShow = 0;
                }
                $("#driver").val($scope.plcInfoById.driver);
                $("#retry_times").val($scope.plcInfoById.retry_times);
                $("#wait_timeout").val($scope.plcInfoById.wait_timeout);
                $("#rev_timeout").val($scope.plcInfoById.rev_timeout);
                $("#com_stepinterval").val($scope.plcInfoById.com_stepinterval);
                $("#com_iodelaytime").val($scope.plcInfoById.com_iodelaytime);
                $("#retry_timeout").val($scope.plcInfoById.retry_timeout);
                $('#box_stat_no').val($scope.plcInfoById.box_stat_no);
                $('#plc_stat_no').val($scope.plcInfoById.plc_stat_no);
                $scope.createSwitchState();
                if ($scope.plcInfoById.net_isbroadcast == 1) {
                    $("#net_isbroadcast").attr("checked", "true");
                    $('[name="switch-state"]').bootstrapSwitch('state', true, true);
                    $('#net_broadcastaddr').removeAttr('disabled');
                } else {
                    $("#net_isbroadcast").attr("checked", "false");
                    $('[name="switch-state"]').bootstrapSwitch('state', false, true);
                    $('#net_broadcastaddr').attr("disabled", "disabled");
                }
                $scope.selectedType = temType;
                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }


    ///**
    // * 提交接口请求
    // */
    //    //取消按钮
    //$scope.btn_plcInfo_cancel =function(){
    //    $("#btn_plcInfo_submit").removeAttr("disabled");
    //}
    //提醒
    $scope.chgTypeNotice = function () {
        $("#btn_plcInfo_submit").attr("disabled", "true");
        if ($("#plc_id").val() == 0) {
            $scope.addPlcInfosetting_submit();
        } else {
            var oldType = $scope.plcInfoById.type;
            var oldPort = $scope.plcInfoById.port;

            if($("#port").val() != oldPort ){
                $("#noticeType").modal("show");
            }else {
                if ($('#type').val() != oldType) {
                    $("#noticeType").modal("show");
                } else {
                    $scope.addPlcInfosetting_submit();
                }
            }

        }
    }
    //提交后台
    $scope.addPlcInfosetting_submit = function () {
        var params = {
            plc_id: $("#plc_id").val(),
            device_id: $("#device_id").val(),
            type: $("#type").val(),
            box_stat_no: $("#box_stat_no").val(),
            plc_stat_no: $("#plc_stat_no").val(),
            port: $("#port").val(),
            comtype: $("#comtype").val(),
            baudrate: $("#baudrate").val(),
            stop_bit: $("#stop_bit").val(),
            data_length: $("#data_length").val(),
            check_bit: $("#check_bit").val(),
            retry_times: $("#retry_times").val(),
            wait_timeout: $("#wait_timeout").val(),
            rev_timeout: $("#rev_timeout").val(),
            com_stepinterval: $("#com_stepinterval").val(),
            com_iodelaytime: $("#com_iodelaytime").val(),
            retry_timeout: $("#retry_timeout").val(),
            net_port: $("#net_port").val(),
            net_type: $("#net_type").val(),
            net_isbroadcast: $("#net_isbroadcast").val(),
            net_broadcastaddr: $("#net_broadcastaddr").val(),
            net_ipaddr: $("#net_ipaddr").val(),
            state: "1",
            driver: $('#driver').val()
        };
        var selectport = $("#port").val();
        if (params.device_id == "" || params.type == "" || $scope.selectedType == "" || $scope.selectedPtype == "" || $("#ptype").val() == "" || params.driver == "" || params.box_stat_no == "" || params.plc_stat_no == "" || params.port == "" || params.retry_times == ""
            || params.wait_timeout == "" || params.rev_timeout == "" || params.com_stepinterval == "" || params.com_iodelaytime == "" || params.retry_timeout == "") {
            alert("配置参数未填写");
            $("#btn_plcInfo_submit").removeAttr("disabled");
            return;
        }
        if (selectport == 'Ethernet') {
            if (params.net_port == "" || params.net_type == "" || params.net_isbroadcast == "" || params.net_broadcastaddr == "" || params.net_ipaddr == "") {
                alert("配置参数未填写");
                $("#btn_plcInfo_submit").removeAttr("disabled");
                return;
                if (isValidIP(params.net_ipaddr) != true) {
                    alert("情输入正确的IP地址");
                    $("#btn_plcInfo_submit").removeAttr("disabled");
                    return;
                }
            }
            params.comtype = "0";
            params.baudrate = "0";
            params.stop_bit = "0";
            params.data_length = "0";
            params.check_bit = "0";
        } else if (selectport == 'COM1' || selectport == 'COM2') {
            params.net_port = "0";
            params.net_type = "0";
            params.net_isbroadcast = "0";
            params.net_broadcastaddr = "0";
            params.net_ipaddr = "0";
            //params.state = "0";
            if (params.baudrate == "" || params.stop_bit == "" || params.data_length == "" || params.check_bit == "" || params.comtype == "") {
                alert("配置参数填未填写");
                return;
            }
        } else {
            params.net_port = "0";
            params.net_type = "0";
            params.net_isbroadcast = "0";
            params.net_broadcastaddr = "0";
            params.net_ipaddr = "0";
            params.state = "1";
            params.comtype = "0";
            params.baudrate = "0";
            params.stop_bit = "0";
            params.data_length = "0";
            params.check_bit = "0";
        }
        /*
         * 当plc_id 不等于0
         * 对plc_id的通讯口就行修改编辑
         * */
        T.common.ajax.request("WeconBox", "plcInfoAction/savePlcInfo", params, function (data, code, msg) {
            if (code == 200) {
                alert("操作成功");
                $("#addConfig").modal("hide");
                $("#btn_plcInfo_submit").removeAttr("disabled");
                $scope.showPlcList();
                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
                $("#btn_plcInfo_submit").removeAttr("disabled");
            }
        }, function () {
            console.log("ajax error");
            $("#btn_plcInfo_submit").removeAttr("disabled");
        });
    }
    /*
     * 设置 点击的plc_id
     * 用于区别是添加通讯口配置还是修改通讯口配置
     * */
    $scope.setplc_id = function (plc_id) {
        $("#btn_plcInfo_submit").removeAttr("disabled");
        $("#plc_id").val(plc_id);
        $scope.showPlcInfoById(plc_id);
    }

    $scope.showPlcSetDefault = function () {
        var params = {};
        T.common.ajax.request("WeconBox", "plcInfoAction/showPlcSetDefault", params, function (data, code, msg) {
            if (code == 200) {
                //$scope.usbDeviceList = data.usbDeviceList;
                //$scope.ethernetList = data.ethernetList;
                $scope.usbDeviceMapListByType = data.usbDeviceMapListByType;
                $scope.ethernetMapListByType = data.ethernetMapListByType;

                $scope.usbDeviceMapListByPtype = data.usbDeviceMapListByPtype;
                $scope.ethernetMapListByPtype = data.ethernetMapListByPtype;
                $scope.usbDeviceMapListKeyByPtype = data.usbDeviceMapListKeyByPtype;
                $scope.ethernetMapListKeyByPtype = data.ethernetMapListKeyByPtype;

                $scope.comMapListKeyByPtype = data.comMapListKeyByPtype;
                $scope.comMapListByPtype = data.comMapListByPtype;
                $scope.comMapListByType = data.comMapListByType;

                $scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 修改 port通讯协议
     * 以后改 switch
     * */
    $scope.chgPort = function () {
        if ($scope.selectedPort == "USB") {
            $scope.temPtype = $scope.usbDeviceMapListByPtype;
            $scope.pTypeSet = $scope.usbDeviceMapListKeyByPtype;
            $scope.temType = $scope.usbDeviceMapListByType;

        } else if ($scope.selectedPort == "Ethernet") {
            $scope.temPtype = $scope.ethernetMapListByPtype;
            $scope.pTypeSet = $scope.ethernetMapListKeyByPtype;
            $scope.temType = $scope.ethernetMapListByType;
        } else {
            $scope.temPtype = $scope.comMapListByPtype;
            $scope.pTypeSet = $scope.comMapListKeyByPtype;
            $scope.temType = $scope.comMapListByType;
        }
        $scope.selectedPtype = "";
        $scope.selectedType = "";

        $scope.portIfShow = 0;
        $scope.ethernetShow = 0;

        //清空 所有 form下的所有表单
        $("input[type!='hidden'][name!='device_id'][id!='port']", $('#addConfig')).each(function () {
            var type = this.type;
            //var tag = this.tagName.toLowerCase();
            if (type == 'text' || type == 'number') {
                this.value = "";
            }
        });
        //$("select", $('#addConfig')).each(function () {
        //    this.selectedIndex = -1;
        //});
    }
    /*
     * 修改设备类型
     * */
    $scope.chgPtype = function () {
        $scope.selectedType = "";
        var temPtype = $scope.temPtype;
        $scope.pType = temPtype[$scope.selectedPtype];
    }
    /*
     * 修改驱动名称
     * */
    $scope.chgType = function () {
        //if ($scope.selectedPort == "USB") {
        //    $scope.temPtype = $scope.usbDeviceMapListByPtype;
        //    $scope.pTypeSet = $scope.usbDeviceMapListKeyByPtype;
        //    $scope.temType = $scope.usbDeviceMapListByType;
        //
        //} else if ($scope.selectedPort == "Ethernet") {
        //    $scope.temPtype = $scope.ethernetMapListByPtype;
        //    $scope.pTypeSet = $scope.ethernetMapListKeyByPtype;
        //    $scope.temType = $scope.ethernetMapListByType;
        //} else {
        //    $scope.temPtype = $scope.comMapListByPtype;
        //    $scope.pTypeSet = $scope.comMapListKeyByPtype;
        //    $scope.temType = $scope.comMapListByType;
        //}
        var temType = $scope.temType;
        $scope.type = temType[$scope.selectedType];
        var type = temType[$scope.selectedType];
        $('#comtype').val(type[0].comtype);
        $('#box_stat_no').val(type[0].box_stat_no);
        $('#plc_stat_no').val(type[0].plc_stat_no);
        $('#retry_times').val(type[0].retry_times);
        $('#wait_timeout').val(type[0].wait_timeout);
        $('#rev_timeout').val(type[0].rev_timeout);
        $('#driver').val(type[0].driver);
        $('#retry_timeout').val(type[0].retry_timeout);
        $('#com_iodelaytime').val("0");
        $('#com_stepinterval').val("0");
        if ($('#port').val() == 'Ethernet') {
            $scope.portIfShow = 0;
            $scope.ethernetShow = 1;
            $('#net_ipaddr').val(type[0].net_ipaddr);
            $('#net_type').val(type[0].net_type);
            $('#net_port').val(type[0].net_port);
            $('#net_isbroadcast').val(type[0].net_isbroadcast);
            $('#net_broadcastaddr').attr("disabled", "disabled");
            $('#net_broadcastaddr').val(type[0].net_broadcastaddr);
            //默认设置
            $scope.createSwitchState();
            $('[name="switch-state"]').bootstrapSwitch('state', false, true);
            $('#net_broadcastaddr').attr("disabled", "disabled");

        } else if ($('#port').val() == 'COM1' || $('#port').val() == "COM2") {
            $scope.portIfShow = 1;
            $scope.ethernetShow = 0;
            $('#baudrate').val(type[0].baudrate);
            $('#stop_bit').val(type[0].stop_bit);
            $('#data_length').val(type[0].data_length);
            $('#check_bit').val(type[0].check_bit);
        } else {
            $scope.portIfShow = 0;
            $scope.ethernetShow = 0;
        }
    }
    $scope.clearPlcInput = function () {
        $scope.pTypeSet = "";
        $scope.pTypeSet = "";
        $scope.$apply();
    }
    $scope.chgPiboxInFoName = function (device_id) {
        var piBoxName = $('#PIBoxName').val();
        var remark = $("#remark").val();
        var map_a = $("#map_a").val();
        var map_o = $("#map_o").val();
        var maxHisDataCount = $("#maxHisDataCount").val();
        var regMaxHisCount=/^[1-9]{1}[0-9]{0,4}$/
        if(!regMaxHisCount.test(maxHisDataCount)||maxHisDataCount>50000){
            alert("历史数据最多保存条数设置错误,请输入1-50000");
            return;
        }
        if (map_a != "" || map_o != "") {
            if (isNaN(map_a) || isNaN(map_o)) {
                alert("地图坐标格式错误");
                return;
            }
            var reg_a = /^[\-\+]?(0|0\.\d{1,6}|\d{1}|\d{1}\.\d{1,6}|[1-9]{1}\d{1}|[1-9]{1}\d{1}\.\d{1,6}|1[0-7]{1}\d{1}\.\d{1,6}|1[0-7]{1}\d{1}|180\.[0]{1,6}|180)$/;
            if (!reg_a.test(map_a)) {
                alert("经度输入错误，请输入经度-180.000000~180.000000的数");
                return;
            }
            var reg_o = /^[\-\+]?(0|0\.\d{1,6}|\d{1}|\d{1}\.\d{1,6}|[1-8]\d{1}|[1-8]\d{1}\.\d{1,6}|90|90\.[0]{1,6})$/;
            if (!reg_o.test(map_o)) {
                alert("纬度输入错误，请输入纬度-90.000000~90.000000的数");
                return;
            }
        }
        /*
         * 如果选择其他行业   去读输入框值
         * */
        var deviceUseCode = $("#deviceUse").val();
        var deviceUseName = "";
        if (deviceUseCode == 999) {
            deviceUseName = $("#otherDeviceUseName").val();
            if (deviceUseName == "" || deviceUseName == undefined) {
                alert("其他行业参数未填写！");
                return false;
            }
        } else {
            deviceUseName = $("#deviceUse").text();
        }

        var map = $("#map_a").val() + "," + $("#map_o").val();
        var params = {
            deviceId: device_id,
            piBoxName: piBoxName,
            remark: remark,
            deviceUseCode: deviceUseCode,
            deviceUseName: deviceUseName,
            maxHisDataCount:maxHisDataCount,
            map: map
        }
        T.common.ajax.request("WeconBox", "baseInfoAction/chgPiboxInFoName", params, function (data, code, msg) {
            if (code == 200) {
                alert("保存成功！");
                window.parent.reloadBoxList();
                //$scope.$apply();
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    };
    /*
     * 解绑plc
     * */
    $scope.deletePlc = function (plc_id) {
        $scope.deletePlc_id = "";
        $scope.deletePlc_id = plc_id;
    }
    $scope.unbundledPlc = function (plc_id) {
        var params = {plc_id: plc_id}
        T.common.ajax.request("WeconBox", "plcInfoAction/unbundledPlc", params, function (data, code, msg) {
            if (code == 200) {
                $("#deletePlc").modal("hide");
                $scope.showPlcList();
                $scope.$apply();
            }
            else {
                $("#deletePlc").modal("hide");
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    /*
     * 进行输入框IP地址验证
     * */
    function isValidIP(ip) {
        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
        return reg.test(ip);
    }

    $scope.cleanInput = function () {
        $("#btn_plcInfo_submit").removeAttr("disabled");
        $("#addConfig").modal("show");
        clearForm($('#addConfig'));
        $("#port").val("COM1");
        $scope.selectedPort = "COM1";
        $scope.chgPort();
        $scope.portIfShow = 0;
        $scope.ethernetShow = 0;
    };
    function clearForm(form) {
        $("input[type!='hidden'][name!='device_id']", form).each(function () {
            var type = this.type;
            //var tag = this.tagName.toLowerCase();
            if (type == 'text' || type == 'number') {
                this.value = "";
                $('#plc_id').val("0");
            }
        });
        $("select", form).each(function () {
            this.selectedIndex = -1;
        });
    };

    //--------------------------------------------------------------------debugInfo上报调试信息-------------------------------------------------------------------------------------------------------------------------------
    //var ws;
    //$scope.ws_send = function (msg) {
    //    ws.send(msg);
    //}
    //$scope.ws_close = function (state) {
    //    if (state != 1) {
    //        alert("盒子已经离线");
    //        return;
    //    }
    //    ws.close();
    //    ws = "";
    //}
    //$scope.ws_log = function (data) {
    //    $('#wsLog').prepend('<p>' + data + '</p>');
    //}
    //$scope.ws_clear = function () {
    //    $("#wsLog").empty();
    //    $scope.ws_log("Clear :)");
    //}
    //$scope.ws_connect = function (machine_code) {
    //    if ("WebSocket" in window) {
    //        ws = new WebSocket(T.common.requestUrl['WeconBoxWs'] + 'debugInfo-websocket/websocket?' + T.common.websocket.getParams());
    //        ws.onopen = function () {
    //            $scope.ws_send(machine_code);
    //        };
    //        ws.onmessage = function (evt) {
    //            $scope.ws_log(evt.data);
    //        };
    //        ws.onclose = function (evt) {
    //            $scope.ws_log('>>>关闭获取调试' + " ");
    //            console.log(evt);
    //        };
    //        ws.onerror = function (evt) {
    //            $scope.ws_log('>>>error' + " " + evt.data);
    //            console.log(evt);
    //        };
    //    } else {
    //        alert("WebSocket isn't supported by your Browser!");
    //    }
    //}
    $scope.openGetDeviceDebugInfo = function (machine_code, state) {
        if (state != 1) {
            alert("盒子已经离线");
            return;
        }
        if (ws == "" || ws == undefined) {
            $scope.ws_connect(machine_code);
        }
    };

    //--------------------------------------------------------------------------固件信息------------------------------------------------------------------------------------------------------------

    $scope.dev_firmShow = function (state) {
        var device_id = T.common.util.getParameter("device_id");
        var params = {
            device_id: device_id
        }
        T.common.ajax.request("WeconBox", "dirFirmAction/getDirFirmInfoByDevId", params, function (data, code, msg) {
            if (code == 200) {
                if (data.devFirmInfo != null) {
                    $scope.devFirmInfo = data.devFirmInfo;
                    //固件更新要用到
                    $scope.localVersionCode = data.devFirmInfo.f_ver;
                    $scope.$apply();
                }
            }
            else {
                $("#deletePlc").modal("hide");
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    //检查更新
    $scope.checkUpdate = function () {
        var localVersionCode = $scope.localVersionCode;
        var dev_model = $scope.dev_model;
        if (dev_model == "" || dev_model == undefined) {
            alert("无法获取可更新设备类型");
            return;
        }
        if (localVersionCode == "" || localVersionCode == undefined) {
            alert("无法获取本地版本号");
            return;
        }
        var device_id = $scope.device_id;
        if (device_id == "" || device_id == undefined) {
            alert("系统忙，稍后操作");
            return;
        }
        var params = {
            device_id: device_id,
            dev_model: dev_model,
            localVersionCode: localVersionCode
        }
        T.common.ajax.request("WeconBox", "driveraction/checkUpdate", params, function (data, code, msg) {
            if (code == 200) {
                $("#checkUpdateFir").modal("show");
                $scope.driverIsUpdate = data.driverData.isUpdate;
                $scope.firmIsUpdate = data.firmData.isNewVersion;

                //提示信息
                if ($scope.driverIsUpdate == "true" || $scope.firmIsUpdate == "true") {
                    //用于展示提示内容
                    $("#noticeDiv").empty();
                    $("#noticeDiv").prepend('<div>确定更新</div>');
                } else {
                    $("#noticeDiv").empty();
                    $("#noticeDiv").prepend('<div>已经是最新版了，不需要更新</div>');
                    $("#btn-ckgUpd").css('display', 'none');
                    $("#btn-ckgUpd-cancel").text("确定");
                }
                if ($scope.firmIsUpdate) {
                    $scope.file_id = data.firmData.file_id;
                    $scope.fileData = data.firmData.fileData;
                }
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    //更新操作
    $scope.update = function () {
        var device_id = $scope.device_id;
        var updateType = 0;
        if ($scope.driverIsUpdate == true && $scope.firmIsUpdate == "true") {
            updateType = 3;
        } else if ($scope.driverIsUpdate == true) {
            updateType = 1;
        } else if ($scope.firmIsUpdate == "true") {
            updateType = 2
        } else {
            $("#checkUpdateFir").modal("hide");
            return;
        }
        var params = {
            updateType: updateType,
            device_id: device_id
        }
        if ($scope.fileData != "" && $scope.fileData != undefined) {
            var fileData = $scope.fileData;
            params["versionName"] = fileData.version_name;
            params["version_code"] = fileData.version_code;
            params["file_id"] = fileData.file_id;
        }

        T.common.ajax.request("WeconBox", "driveraction/update", params, function (data, code, msg) {
            if (code == 200) {
                if (data.isUpdated == 1) {
                    $("#checkUpdateFir").modal("hide");
                    var count = 0;
                    if (data.driUpcount != "undefined" && data.driUpcount.count != "") {
                        count = parseInt(data.driUpcount.count) + parseInt(count);
                    }
                    if (data.frimUpcount != "undefined" && data.frimUpcount.count != "") {
                        count = parseInt(data.frimUpcount.count) + parseInt(count);
                    }
                    $scope.wsf_connect(data.machine_code.toString(), count.toString(), data.resultData);
                    //$("#loadingModal").modal("show");
                } else {
                    $("#checkUpdateFir").modal("hide");
                }
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    //检查更新所有驱动文件
    $scope.chkUpdateDriver = function () {
        var device_id = $scope.device_id;
        if (device_id == "" || device_id == undefined) {
            alert("系统忙，稍后操作");
            return;
        }
        var params = {
            device_id: device_id
        }
        T.common.ajax.request("WeconBox", "driveraction/checkUpdatePlcDriver", params, function (data, code, msg) {
            if (code == 200) {
                $("#checkUpdateFir").modal("show");
                if (data.isUpdate) {
                    //用于展示提示内容
                    $("#noticeDiv").empty();
                    $("#noticeDiv").prepend('<div>确定更新</div>');
                } else {
                    $("#noticeDiv").empty();
                    $("#noticeDiv").prepend('<div>已经是最新版了，不需要更新</div>');
                }
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    //更新 plc全部驱动
    $scope.updateAllDriver = function (machine_code) {
        var device_id = $scope.device_id;
        if (device_id == "" || device_id == undefined) {
            alert("系统忙，稍后操作");
            return;
        }
        var params = {
            device_id: device_id,
            machine_code: machine_code
        }
        T.common.ajax.request("WeconBox", "driveraction/updateAllDriver", params, function (data, code, msg) {
            if (code == 200) {
                alert("指令已下发盒子成功！");
            }
            else {
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    //检查更新驱动文件
    $scope.checkUpdateFir = function () {
        var localVersionCode = $scope.localVersionCode;
        var dev_model = $scope.dev_model;
        if (dev_model == "" || dev_model == undefined || localVersionCode == "" || localVersionCode == undefined) {
            alert("系统错误");
            return;
        }
        var params = {
            dev_model: dev_model,
            localVersionCode: localVersionCode
        }
        T.common.ajax.request("WeconBox", "dirFirmAction/getLatestFirmVersion", params, function (data, code, msg) {
            if (code == 200) {
                $("#checkUpdateFir").modal("show");
                if (data.isNewVersion) {
                    //用于展示提示内容
                    $("#noticeDiv").empty();
                    $("#noticeDiv").prepend('<div>最新版本:' + data.versionCode + '</div>');


                    $scope.file_id = data.file_id;
                    $scope.fileData = data.fileData;
                } else {
                    //用于展示提示内容
                    $("#noticeDiv").empty();
                    $("#noticeDiv").prepend('<div>已经是最新版了，不需要更新</div>');
                }
            }
            else {
                $("#deletePlc").modal("hide");
                alert(code + "-" + msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }
    $scope.updateFirFile = function (device_id) {
        var fileData = $scope.fileData;
        var versionName = fileData.version_name;
        var version_code = fileData.version_code;
        var file_id = fileData.file_id;
        var params = {
            versionName: versionName,
            version_code: version_code,
            file_id: file_id,
            device_id: device_id
        }
        if (machine_code == "" || machine_code == undefined || versionName == "" || versionName == undefined || version_code == "" || version_code == undefined || file_id == "" || file_id == undefined) {
            $("#checkUpdateFir").modal("hide");
            alert("系统忙，稍后操作！");
        }
        T.common.ajax.request("WeconBox", "dirFirmAction/updateFirmFile", params, function (data, code, msg) {
            $("#checkUpdateFir").modal("hide");
            alert("指令已下发盒子成功！");
        }, function () {
            console.log("ajax error");
        });
    }


    /*
     * 是否使用广播地址
     * */
    //$scope.isBroadcast = function () {
    //    if ($('#net_isbroadcast').is(':checked')) {
    //        $('#net_broadcastaddr').attr("disabled", "disabled");
    //        //$('#net_broadcastaddr').css('','');
    //    } else {
    //        $('#net_broadcastaddr').removeAttr('disabled');
    //    }
    //}
    //使用广播地址  js方法
    $scope.createSwitchState = function () {
        $('[name="switch-state"]').bootstrapSwitch({
            onText: "启用",
            offText: "禁用",
            onColor: "success",
            offColor: "danger",
            size: "small",
            onSwitchChange: function (event, state) {
                if (state == false) {
                    $('#net_broadcastaddr').attr("disabled", "disabled");
                    $('#net_isbroadcast').val(0);
                } else {
                    $('#net_broadcastaddr').removeAttr('disabled');
                    $('#net_isbroadcast').val(1);
                }
            }
        })
    }

//-----------------------------------------------------更新 等待反馈------------------------------------------------------------------
    var wsf;

    $scope.wsf_close = function (state) {
        if (state != 1) {
            alert("盒子已经离线");
            return;
        }
        wsf.close();
        wsf = "";
    }
    $scope.wsf_log = function (data) {
        console.log(data);
    }

    $scope.wsf_connect = function (machine_code, msgNum, vdata) {
        if ("WebSocket" in window) {
            wsf = new WebSocket(T.common.requestUrl['WeconBoxWs'] + 'updateFile-websocket/websocket?' + T.common.websocket.getParams());
            wsf.onopen = function () {
                $("#loadingModal").modal("show");
                //发送websocket的消息体
                var params = {
                    machine_code: machine_code,
                    msgNum: msgNum,
                    data: vdata
                }
                wsf.send((JSON.stringify(params)));
            };
            wsf.onmessage = function (evt) {
                $scope.wsf_log("--------------------------websocket收到的消息-----------------------------" + evt.data);

                var jsonResult = $.parseJSON(evt.data);

                //离线判断
                if (jsonResult.errorMsg != undefined) {
                    alert("盒子已经离线！");
                    $("#loadingModal").modal("hide");
                    wsf.onclose();
                    return;
                }

                $scope.wsf_log("----------------" + jsonResult);
                $('#upNotice').empty();
                for (var i in jsonResult) {
                    if (i == "firm") {
                        //console.log(i);
                        //console.log(jsonResult[i]);
                        $('#upNotice').prepend('<p>固件升级' + (jsonResult[i] == 1 ? '成功' : (jsonResult[i] == -1 ? '文件写入失败' : (jsonResult[i] == -2 ? 'md5不匹配' : (jsonResult[i] == -3 ? '文件拷贝失败' : (jsonResult[i] == -4 ? '服务器下发版本较低，不更新' : '未知错误'))))) + '</p>');
                    } else {
                        $('#upNotice').prepend('<p>驱动' + i + (jsonResult[i] == 1 ? '成功' : (jsonResult[i] == -1 ? '文件写入失败' : (jsonResult[i] == -2 ? 'md5不匹配' : (jsonResult[i] == -3 ? '文件拷贝失败' : (jsonResult[i] == -4 ? '服务器下发版本较低，不更新' : '未知错误'))))) + '</p>');
                    }
                    wsf.onclose();
                }
                $("#noticeModal").modal("show");
                $("#loadingModal").modal("hide");
            };
            wsf.onclose = function (evt) {
                $scope.wsf_log('>>>关闭获取调试' + " ");
                console.log(evt);
            };
            wsf.onerror = function (evt) {
                $scope.wsf_log('>>>error' + " " + evt.data);
                console.log(evt);
            };
        } else {
            alert("WebSocket isn't supported by your Browser!");
        }
    }


    /*
     *                      地图测试
     * */
    $scope.mapShow = function () {
        console.log($("#map_body").css("display"));
        if ($("#map_body").css("display") == "none") {
            $("#btn_map_show").text("关闭地图");
            $("#map_body")[0].style.display = "block";
            $("#map_input")[0].style.display = "block";
            // 百度地图API功能
            $scope.$map = new BMap.Map("allmap");
            var map = $scope.$map;
            map.enableScrollWheelZoom(true);

            /*
             * 根据盒子经纬度定位为中心
             * */
            var map_a = $("#map_a").val();
            var map_o = $("#map_o").val();
            if (map_a == "" && map_o == "") {
                map.centerAndZoom(new BMap.Point(119.310369, 26.082246), 11);
            } else if (map_a == "") {
                map_a = 0;
                map.centerAndZoom(new BMap.Point(map_a, map_o), 11);
            } else if (map_o == "") {
                map_o = 0;
                map.centerAndZoom(new BMap.Point(map_a, map_o), 11);
            } else {
                map.centerAndZoom(new BMap.Point(map_a, map_o), 11);
            }
            /*
             * 设置标注点
             * */
            var boxTag = new BMap.Point(map_a, map_o);
            //var label = new BMap.Label($scope.device_name,{offset:new BMap.Size(20,-10)});
            var marker = new BMap.Marker(boxTag);
            map.addOverlay(marker);
            //marker.setLabel(label);
            /*
             * 地图搜索功能
             * */
            var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
                {
                    "input": "suggestId",
                    "location": $scope.$map
                });
            ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件
                var str = "";
                var _value = e.fromitem.value;
                var value = "";
                if (e.fromitem.index > -1) {
                    value = _value.province + _value.city + _value.district + _value.street + _value.business;
                }
                str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
                value = "";
                if (e.toitem.index > -1) {
                    _value = e.toitem.value;
                    value = _value.province + _value.city + _value.district + _value.street + _value.business;
                }
                str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value + "style='z-index:999;'position:'fixed'";
                G("searchResultPanel").innerHTML = str;
            });
            var myValue;
            ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
                var _value = e.item.value;
                myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
                G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
                setPlace();
            });
            function setPlace() {
                map.clearOverlays();    //清除地图上所有覆盖物
                function myFun() {
                    var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                    map.centerAndZoom(pp, 18);
                    map.addOverlay(new BMap.Marker(pp));    //添加标注
                }

                var local = new BMap.LocalSearch(map, { //智能搜索
                    onSearchComplete: myFun
                });
                local.search(myValue);
            }

            function G(id) {
                return document.getElementById(id);
            }


            /*
             * 添加右键功能
             * */
            var s;//经度
            var w;//纬度
            map.addEventListener("rightclick", function (e) {
                if (e.overlay) {//判断右键单击的是否是marker
                } else {
                    s = e.point.lng;//经度
                    w = e.point.lat;//维度
                    RightClick();//右键单击map出现右键菜单事件
                }
            });
            //右键单击map出现右键菜单事件
            function RightClick() {
                //alert('你点击的是地图');
                var createMarker = function (map) {//右键更新站名
                    if (true) {
                        $scope.mapinfoModalShow(s, w);
                    }
                };
                var markerMenu = new BMap.ContextMenu();
                markerMenu.addItem(new BMap.MenuItem('盒子位置', createMarker.bind(map)));
                map.addContextMenu(markerMenu);//给标记添加右键菜单
            }
        } else {
            $("#btn_map_show").text("定位");
            $("#map_body").css("display", "none");
            $("#map_input")[0].style.display = "none";
        }
    }
    $scope.mapinfoModalShow = function (s, w) {
        $("#mapinfo").modal("show");
        //var strMaps=s.toString();
        //var strMapw= w.toString();
        //var reg=/[\-\+]?\d{1,}\.\d{1,3}/;
        //s=reg.exec(strMaps);
        //w=reg.exec(strMapw);
        $scope.map_s = s;
        $scope.map_w = w;
        $scope.$apply();
    }
    $scope.mapinfo = function () {
        $("#mapinfo").modal("hide");
        $("#map_a").val($scope.map_s);
        $("#map_o").val($scope.map_w);
    }
    /*
     * 结果版面
     * */
    $scope.mapSearchCommit = function () {

        var local = new BMap.LocalSearch($scope.$map, {
            renderOptions: {map: $scope.$map, panel: "r-result"}
        });
        local.search($("#suggestId").val());
    }
});

/*
 * 是否选择了其他行业;
 *   是  展示input框
 * */
var isOtherOption = function () {
    if ($("#deviceUse").val() == 999) {
        $("#otherDeviceUseName").show();
    } else {
        $("#otherDeviceUseName").hide();
    }
}

var cleanCopyCheccbox =function(){
    $("input[name= copyCheckbox]").each(function (index,element) {
        element.checked =false;
    });
    isShowOtherSel();
}


var isShowOtherSel = function(){
    if(($("#comSelCheck").get(0).checked)){
        $("tr[name='otherSel']").each(function(index,element){
            $(element).show();
        });
    }else{
        $("tr[name='otherSel']").each(function(index,element){
            $(element).hide();
        });
    }
}