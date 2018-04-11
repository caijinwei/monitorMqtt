/**
 * Created by Administrator on 2017/7/19.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $('#loader-wrapper').css("display", "block");
        $scope.server_id = T.common.util.getParameter("server_id");

        if ($scope.server_id == undefined || $scope.server_id == "") {
            swal({title: "参数传输错误！", icon: "error"});

        } else {

            var serverId = $scope.server_id;
            var params = {
                serverId: serverId
            }

            T.common.ajax.request("WeconBox", "mqttConfig/getMqttConfigById", params,
                function (data, code, msg) {
                    if (code == 200) {
                        console.log(data.mqttConfig);
                        $scope.mqttConfig = data.mqttConfig;
                        /*
                         * mqtt连接代码
                         * */
                        connectMqtt(data.mqttConfig);
                        $scope.$apply();
                    } else {
                        swal(code + " " + msg);
                        console.log("error失败");
                    }
                }, function () {
                    console.log("ajax error");
                });
        }

        $('#loader-wrapper').css("display", "none");
    }

//全局变量 MqttConfig
    var pageMqttConfig;

    /*
     * 连接mqtt服务器
     * */
    // UI globals
    var connected = false;
    var client;
    var connectMqtt = function (mqttConfig) {
        console.log("获取得到的mqttConfig是:--->" + mqttConfig);
        console.log("获取得到的mqttConfig.host是:--->" + mqttConfig.serverIP);
        var host = mqttConfig.serverIP;
        var port = 9001;
        var clientId = "jsmqtt_" + Math.random();

        console.log("获取得到的mqttClient---"+clientId);
        //创建连接对象
        client = new Paho.MQTT.Client(host, parseInt(port), clientId);

        // 设置回调函数
        client.onConnectionLost = onConnectionLost;
        client.onMessageArrived = onMessageArrived;
        // connect the client

        //连接参数
        var options = {
            timeout: 1,
            useSSL: false,
            userName: mqttConfig.username,
            password: mqttConfig.password,
            onSuccess: onConnect,
            onFailure: function (message) {
                console.log("连接失败---有执行到这边么");
//                    setTimeout(MQTTconnect, reconnectTimeout);
                swal({
                    title: "MQTT服务器连接失败，是否再次尝试连接",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                }).then(function (isok) {
                    if (isok) {
                        $('#loader-wrapper').css("display", "block");
                        connectMqtt(pageMqttConfig);
                    }
                });
                console.log("连接失败");
            }
        }

        client.connect(options);

        // Do UI stuff
        connected = true;
    }

    /*
     * 订阅

     3.$SYS/broker/clients/connected 当前在线的客户端数
     6.$SYS/broker/clients/maximum  最大并发连接数
     4.$SYS/broker/clients/expired  超时的连接数
     5.$SYS/broker/clients/disconnected 断开的连接数

     7.$SYS/broker/clients/total  所有连接数（活动的和非活动的）
     8.$SYS/broker/heap/current size  当前用到的内
     9.$SYS/broker/heap/maximum size  用到的最大内存
     * 1.$SYS/broker/bytes/received  收到的所有字节数
     2.$SYS/broker/bytes/sent  发送的所有字节数
     *
     *
     * */
    // Subscribe
    /*
     * 获取订阅上面的信息
     * */
    var subscribeData = function () {
        if (!client.isConnected()) {
            console.error("MQTT client is not connected to a broker. Impossible to subscribe to channel");
            swal({
                title: "该页面失去和mqtt服务器的连接？是否从新连接MQTT服务器",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            }).then(function (isok) {
                if (isok) {
                    connectMqtt(pageMqttConfig);
                }
            });
        } else {

            client.subscribe("$SYS/broker/clients/connected");
            client.subscribe("$SYS/broker/clients/maximum");
            client.subscribe("$SYS/broker/clients/expired");
            client.subscribe("$SYS/broker/clients/disconnected");
            client.subscribe("$SYS/broker/clients/total");
            client.subscribe("$SYS/broker/heap/current size");
            client.subscribe("$SYS/broker/heap/maximum size");
            client.subscribe("$SYS/broker/bytes/received");
            client.subscribe("$SYS/broker/bytes/sent");

        }
    }


    function onConnect() {
        // Once a connection has been made, make a subscription and send a message.
        swal("MQTT连接成功", "", "success");
        console.log("onConnect");
        subscribeData();
    }


    // called when the client loses its connection
    function onConnectionLost(responseObject) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
        if (responseObject.errorCode !== 0) {
            swal({
                title: "该页面失去和mqtt服务器的连接是否从新连接MQTT服务器",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            }).then(function (isok) {
                if (isok) {
                    connectMqtt(pageMqttConfig);
                }
            });

            console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

    /*
     * 获取到订阅的消息
     * */
    // called when a message arrives
    function onMessageArrived(message) {
        var date = new Date();
        var t = date.toLocaleString();
        var topic= message.destinationName
        var msg = message.payloadString;
        console.log(t + "     主题：" + message.destinationName + "   消息体：" + msg);

        setAllData(topic,msg);
        //try {
        //    var jsonObj = $.parseJSON(message.payloadString);
        //    from = jsonObj.from;
        //    msg = jsonObj.extras.cnt;
        //
        //} catch (e) {
        //    swal("获取订阅消息异常", "", "warning")
        //    console.info(e);
        //}


        //console.log(t + "     主题：" + message.destinationName + "      发送者：" + from + "   消息体：" + msg);
    }

    /*
     * 设置获取得到message
     * 3.$SYS/broker/clients/connected 当前在线的客户端数
     6.$SYS/broker/clients/maximum  最大并发连接数
     4.$SYS/broker/clients/expired  超时的连接数
     5.$SYS/broker/clients/disconnected 断开的连接数

     7.$SYS/broker/clients/total  所有连接数（活动的和非活动的）
     8.$SYS/broker/heap/current size  当前用到的内
     9.$SYS/broker/heap/maximum size  用到的最大内存
     * 1.$SYS/broker/bytes/received  收到的所有字节数
     2.$SYS/broker/bytes/sent  发送的所有字节数
     * */
    var setAllData = function (topic, msg) {
        switch (topic) {
            case '$SYS/broker/clients/connected':
                $("#connectedId").text(msg);
                queue.enqueue(msg);

                break;
            case '$SYS/broker/clients/maximum':
                $("#maximumId").text(msg);
                break;
            case '$SYS/broker/clients/expired':
                $("#expiredId").text(msg);
                break;
            case '$SYS/broker/clients/disconnected':
                $("#disconnectedId").text(msg);
                break;
            case '$SYS/broker/clients/total':
                $("#totalId").text(msg);
                break;
            case '$SYS/broker/heap/current size':
                $("#currentSizeId").text(msg);
                break;
            case '$SYS/broker/heap/maximum size':
                $("#maximumSizeId").text(msg);
                break;
            case '$SYS/broker/bytes/received':
                $("#receivedId").text(msg);
                break;
            case '$SYS/broker/bytes/sent':
                $("#sentId").text(msg);
                break;
        }
    }


})


/*
 * 折线图实现
 * */
var queue = new Queue();
for(var i=0 ;i<14;i++){
    queue.enqueue(0);
}
console.log("获取得到queue的值是   ",queue.getItems());

var config = {
    type: 'line',
    data: {
        labels: ['60s', '', '', '', '', '', '', '', '', '', '', '', '', '', '0'],
        datasets: [{
            label: '',
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            data: queue.getItems(),
            fill: false,
        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'mqtt实时连接数'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: false
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: false,
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: '连接数'
                }
            }]
        }
    }
};

window.onload = function () {
    var ctx = document.getElementById('canvas').getContext('2d');
    window.myLine = new Chart(ctx, config);
    /*
     * 线性图实时获取数据
     * */

        var setData = function () {
            console.log("有执行到这里么");
            queue.print();
            config.data.datasets.forEach(function (dataset) {
                dataset.data = queue.getItems();
            });
            window.myLine.update();
        }
        setInterval(setData, 1000);

};


var colorNames = Object.keys(window.chartColors);


function Queue() {
    var items = [];

    this.size = function () {
        return items.length;
    }
    this.clear = function () {
        items = [];
    }
    this.getItems = function () {
        return items;
    }
    /*
     * 设置固定长度的队列
     * */

    this.dequeue = function () {
        items.shift();
    }

    this.enqueue = function (element) {
        items.push(element);
        while (items.length >= 14) {
            items.shift();
        }
    }

    this.print = function () {
        console.log(items.toString());
    }
}



