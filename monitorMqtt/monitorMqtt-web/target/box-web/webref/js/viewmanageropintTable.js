/**
 * Created by caijinw on 2017/8/9.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("listController", function($scope, $http, $compile) {
	$scope.onInit = function() {
		$scope.accounttype = T.common.util.getParameter("accounttype");
		$scope.actgroupId = T.common.util.getParameter("actgroupId");
		$scope.conf = {
			currentPage : 1,
			itemsPerPage : 10,
			totalItems : $scope.count,
			pagesLength : 15,
			perPageOptions : [ 5, 10, 20, 50, 100 ],
			rememberPerPage : 'perPageItems',
			onChange : function() {
				if (this.currentPage != 0) {
					$scope.getRestList(this.currentPage, this.itemsPerPage);
				}
			}
		}

		$scope.showDeviceList();

	}
	$scope.conf = {
		totalItems : $scope.count
	}
	/*
	 * 展示所有监控点
	 */
	$scope.getRestList = function(pageIndex, pageSize) {

		if (pageIndex == 0)
			pageIndex = 1;
		$scope.getHisRealrestList(pageIndex, pageSize);

	}
	$scope.getHisRealrestList = function(pageIndex, pageSize) {

		var device_id;
		if ($scope.accounttype == 1) {
			device_id = $("#check_device_id").val();
		} else {

			device_id = -1;
		}
		var params = {
			device_id : device_id,
			pageIndex : pageIndex,
			pageSize : pageSize
		}
		T.common.ajax.request("WeconBox", "actDataAction/getAllotMonitor",
				params, function(data, code, msg) {
					if (code == 200) {
						$scope.conf.totalItems = data.actAllotData.totalRecord;
						$scope.actDatas = data.actAllotData.list;
						$scope.$apply();
					} else {
						alert(code + "-" + msg);
					}
				}, function() {
					console.log("ajax error");
				});
	}

	/*
	 * 展示管理账户的盒子
	 */
	$scope.showDeviceList = function() {
		if ($scope.accounttype == 1) {
			var params = {};
			T.common.ajax.request("WeconBox", "Viewpoint/getDeviceName",
					params, function(data, code, msg) {
						if (code == 200) {
							$scope.deviceList = data.list;
							$scope.$apply();
						} else {
							alert(code + "-" + msg);
						}
					}, function() {
						console.log("ajax error");
					});

		}

	}
	/*
	 * 全选功能实现
	 */
	// 默认没有选中全选
	$scope.isChecked = 0;
	$scope.selectAll = function() {
		console.log("点击了");
		if ($scope.isChecked == 0) {
			$scope.isChecked = 1;
		} else {
			$scope.isChecked = 0;
		}
		if ($scope.isChecked == 1) {
			$scope.allCheck = 1;
		} else {
			$scope.allCheck = 0;
		}
	};
});
