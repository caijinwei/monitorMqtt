/**
 * Created by zengzhipeng on 2017/9/16.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    var uploader;
    $scope.onInit = function () {
        $scope.fileInfos = new Array();

        //<editor-fold desc="固件文件上传">
        uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: false,
            // swf文件路径
            swf: '/box-web/web/lib/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: T.common.config.getRequestUrl("WeconBox") + 'fileact/fileupload?act=driver',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#pickerFile',
            // 只允许选择图片文件。
            accept: {
                title: '固件升级文件上传',
                extensions: '*',
                mimeTypes: '*/*'
            },
            compress: false,
            fileNumLimit: 1000
        });
        // 当有文件添加进来的时候
        uploader.on('fileQueued', function (file) {
            $('#thelist').append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');
        });
        //添加header内容
        uploader.on('uploadBeforeSend', function (object, data, headers) {
            var paramsverify = {
                act: "driver"
            };
            headers['common'] = JSON.stringify(T.common.ajax.getHead(paramsverify));
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 100%">' +
                    '</div>' +
                    '</div>').appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');

            $percent.css('width', percentage * 100 + '%');
        });
        //成功前会派送一个事件
        uploader.on('uploadAccept', function (file, response) {
            if (response.code == 200) {
                var model = {
                    file_id: response.result.file_id,
                    file_name: response.result.file_name,
                    file_md5: response.result.file_md5,
                    file_size: response.result.file_size,
                    file_url: response.result.file_url,
                    driver_name: response.result.driver_name
                }
                $scope.fileInfos.push(model);
                $scope.$apply();
                return true;
            }
            else {
                return false;
            }
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file) {
            //$('#' + file.id).find('p.state').text('已上传');
            //$('#' + file.id).find('p.state').text('');
            $('#thelist').text('上传成功');
        });

        // 文件上传失败，显示上传出错。
        uploader.on('uploadError', function (file) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');

            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }

            $error.text(file.name + '上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });
        //</editor-fold>
    }

    /**
     * 保存操作
     */
    $scope.save = function () {
        var driverList = new Array();
        angular.forEach($scope.fileInfos, function (value, key) {
            if ($("#checkbox_" + value.file_id).is(':checked')) {
                var model = {
                    driver_id: -1,
                    file_id: value.file_id,
                    file_md5: value.file_md5,
                    type: value.driver_name,
                    driver: value.file_name
                };
                driverList.push(model);
            }
        });
        var params = {
            drivers: angular.toJson(driverList)
        }
        T.common.ajax.request("WeconBox", "driveraction/savedriver", params, function (data, code, msg) {
            if (code == 200) {
                alert("操作成功");
                location.href = "driver-list.html";
            } else {
                alert(msg);
            }
        }, function () {
            console.log("ajax error");
        });
    }

    /**
     * 返回列表
     */
    $scope.backList = function () {
        location.href = "driver-list.html";
    }

    $scope.uploadBatch = function () {
        uploader.upload();
    }
})