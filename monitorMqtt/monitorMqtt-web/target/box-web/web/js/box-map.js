var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
	$scope.boxsGroup = [];
	//查询盒子列表
	$scope.findBoxsList = function() {
		var selAlarm = T.common.util.getParameter("selAlarm");
		var params = {};
		if(null != selAlarm){
			params = {selAlarm : selAlarm};
		}
		T.common.ajax.request("WeconBox",
				"data/boxs", params, function(
						data, code, msg) {
					if (code == 200) {
						$scope.boxsGroup = data.list;
						$scope.$apply();
						$scope.initMap();
					} else {
						alert(code + "-" + msg);
					}
				}, function() {
					alert("ajax error");
				});
	}

	//初始化地图
	$scope.initMap = function() {
		var mapStr = T.common.util.getParameter("map");
		//初始化地图
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r) {
			if(this.getStatus() == BMAP_STATUS_SUCCESS) {
				var mk = new BMap.Marker(r.point);
				if(mapStr != null && mapStr !="") {
					$scope.mapArray = mapStr.split(",");
					var mPoint = new BMap.Point($scope.mapArray[0],$scope.mapArray[1]);
					map.centerAndZoom(mPoint, 10) //标注当前位置
				}else{
					map.centerAndZoom(r.point, 10) //标注当前位置
				}

			} else {
				alert('failed' + this.getStatus());
			}
		}, {
			enableHighAccuracy: true
		})
		//return;
		console.log("将要产生标注点");
		var marker;
		angular.forEach($scope.boxsGroup, function(value, key) {
			angular.forEach(value.boxList, function(box, boxkey) {
				var positionStr = box.map;
				var boxName = box.boxName;
				var state = box.state;
				console.log("盒子的位置信息：" + positionStr);
				if(positionStr != null) {
					$scope.positions = positionStr.split(",");
					if($scope.positions.length == 2) {
						console.log("经度：" + $scope.positions[0] + "纬度：" + $scope.positions[1]);
						var boxTag = new BMap.Point($scope.positions[0], $scope.positions[1]);
						var label = new BMap.Label(boxName, {
							offset: new BMap.Size(20, -10)
						});
						var myIcon = new BMap.Icon("../image/"+(1==state?"box_location_online.svg":"box_location_offline.svg"), new BMap.Size(25, 25),{
							anchor: new BMap.Size(12, 23)
						});
						marker = new BMap.Marker(boxTag, {
							icon: myIcon
						});
						map.addOverlay(marker);
						marker.setLabel(label);
						marker.addEventListener('click', function() {
							$state.go("box", {
								box: angular.toJson(box)
							});
						});
					}
				}
			})
		})
		console.log("已经产生标注点");
	}

	var map = new BMap.Map("allmap");
	map.enableScrollWheelZoom(); //激活滚轮调整大小功能
	$scope.findBoxsList(); //查询盒子列表

})