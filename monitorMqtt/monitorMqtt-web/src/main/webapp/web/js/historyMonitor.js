/**
 * Created by Administrator on 2017/7/19.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    var connect_chart;
    var hisDataChart;
    $scope.count = 1;

    $scope.onInit = function () {
        $('#loader-wrapper').css("display", "block");
        $scope.server_id = T.common.util.getParameter("server_id");

        if ($scope.server_id == undefined || $scope.server_id == "") {
            swal({title: "参数传输错误！", icon: "error"});

        } else {
            $('#loader-wrapper').css("display", "none");


            //hisDataChart = echarts.init($("#hisDataChart")[0]);
            //var option_connect = {
            //    title: {
            //        text: 'mqtt历史连接数'
            //    },
            //    tooltip: {
            //        trigger: 'axis'
            //    },
            //    xAxis: {
            //        type: 'category',
            //        boundaryGap: false,
            //        data: [0]
            //    },
            //    yAxis: {
            //        type: 'value',
            //        min: 0,
            //        max: function (value) {
            //            if (value < 5) {
            //                return 5;
            //            } else {
            //                return value.max + 5;
            //            }
            //        }
            //    },
            //    grid: [{x: '2%', y: '3%', width: '100%', height: '70%'}],
            //    series: [{
            //        data:[0],
            //        type: 'line'
            //    }]
            //}
            //hisDataChart.setOption(option_connect);
            //$scope.showChartList();

            //$('.pagination1').pagination();
        }
    }

    //列表 和图标 按钮
    $scope.showListOrCurves = function(inputId){
        if(inputId =='btn-list'){

        }else{

        }
        $('#'+inputId).attr('class','btn btn-primary');
    }


    //分页初始化
    $scope.initPagi = function(){
        var serverId = $scope.server_id;
        var params = {
            serverId: serverId,
            pageIndex: 1,
            pageSize: 5,
            start_date: $("#startdateid").val(),
            end_date: $("#enddateid").val()
        }

        if (params.start_date == "" || params.end_date == "" || params.start_date == undefined || params.end_date == undefined) {
            swal({
                title: "请填写查询时间段",
                icon: "error"
            });
            return;
        }

        T.common.ajax.request("WeconBox", "hisData/getHisData", params,
            function (data, code, msg) {
                if (code == 200) {
                    console.log(data.data.totalRecord);
                    $scope.count = data.data.totalRecord;
                    $scope.data1 = data.data.list;
                    $('.pagination1').pagination( {
                            totalData: $scope.count,
                            showData: 5,
                            callback: function (api) {
                                $('.now').text(api.getCurrent());
                                $scope.getTableList(api.getCurrent(),5);
                            }
                        }
                    );
                    $scope.$apply();
                } else {
                    swal(code + " " + msg);
                    console.log("error失败");
                }
            }, function () {
                console.log("ajax error");
            });



    }
    //表格展示
    $scope.getTableList = function (pageIndex, pageSize) {
        if (pageIndex == 0)
            pageIndex = 1;
        var serverId = $scope.server_id;
        var params = {
            serverId: serverId,
            pageIndex: pageIndex,
            pageSize: pageSize,
            start_date: $("#startdateid").val(),
            end_date: $("#enddateid").val()
        }

        if (params.start_date == "" || params.end_date == "" || params.start_date == undefined || params.end_date == undefined) {
            swal({
                title: "请填写查询时间段",
                icon: "error"
            });
            return;
        }

        T.common.ajax.request("WeconBox", "hisData/getHisData", params,
            function (data, code, msg) {
                if (code == 200) {
                    console.log(data.data.totalRecord);
                    $scope.count = data.data.totalRecord;
                    $scope.data1 = data.data.list;

                    $scope.$apply();
                } else {
                    swal(code + " " + msg);
                    console.log("error失败");
                }
            }, function () {
                console.log("ajax error");
            });
    }

    $scope.showChartList = function () {

        var serverId = $scope.server_id;
        var params = {
            serverId: serverId,
            start_date: $("#startdateid").val(),
            end_date: $("#enddateid").val()
        }

        if (params.start_date == "" || params.end_date == "" || params.start_date == undefined || params.end_date == undefined) {
            swal({
                title: "请填写查询时间段",
                icon: "error"
            });
            return;
        }

        T.common.ajax.request("WeconBox", "hisData/getHisChartData", params,
            function (data, code, msg) {
                if (code == 200) {
                    $scope.data = data.list;

                    var option_connect = {
                        title: {
                            text: 'mqtt历史连接数'
                        }, dataZoom: {
                            orient: "horizontal", //水平显示
                            show: true, //显示滚动条
                            start: 0, //起始值为0%
                            end: (1000 / data.dataList.length)  //结束值为40%
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            data: data.timeList
                        },
                        yAxis: {
                            type: 'value',
                            min: 0,
                            max: function (value) {
                                if (value < 5) {
                                    return 5;
                                } else {
                                    return value.max + 5;
                                }
                            }
                        },
                        grid: [{x: '2%', y: '3%', width: '100%', height: '70%'}],
                        series: [{
                            data: data.dataList,
                            type: 'line'
                        }]
                    }

                    hisDataChart.setOption(option_connect);
                    $scope.$apply();
                } else {
                    swal(code + " " + msg);
                    console.log("error失败");
                }
            }, function () {
                console.log("ajax error");
            });

    }


})

