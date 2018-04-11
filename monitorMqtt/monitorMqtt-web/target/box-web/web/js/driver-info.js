/**
 * Created by zengzhipeng on 2017/9/16.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    var uploader;
    $scope.onInit = function () {
        var id = T.common.util.getParameter("id");
        if (id > 0) {
            var params = {
                id: id
            };
            //加载基本信息
            T.common.ajax.request("WeconBox", "driveraction/getdriver", params, function (data, code, msg) {
                if (code == 200) {
                    if (data.driver != null) {
                        for (var p in data.driver) {
                            $('#' + p).val(data.driver[p]);
                        }
                    } else {
                        alert("参数异常");
                        return;
                    }
                }
            }, function () {
                console.log("ajax error");
            });
        }


        //<editor-fold desc="固件文件上传">
        uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: true,
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
            fileNumLimit: 1
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
                for (var p in response.result) {
                    $('#' + p).val(response.result[p]);
                }
                $("#type").val(response.result.driver_name);
                return true;
            }
            else {
                return false;
            }
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).find('p.state').text('已上传');
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

            $error.text('上传失败');
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
        var fields = $('#info .form-control');
        for (var i = 0; i < fields.length; i++) {
            var f = $(fields[i]);
            if (f.attr('required') == 'required' && $.trim(f.val()) == "") {
                alert("[" + f.attr('placeholder') + "] 为必填选项");
                return;
            }
        }

        var id = T.common.util.getParameter("id");
        var driverList = new Array();
        var model = {
            driver_id: -1,
            file_id: $("#file_id").val(),
            file_md5: $("#file_md5").val(),
            type: $("#type").val(),
            driver: $("#file_name").val(),
            description: $("#description").val()
        };
        if (id != null) {
            model['driver_id'] = id;
        }
        driverList.push(model);
        var params = {
            drivers: angular.toJson(driverList)
        }
        T.common.ajax.request("WeconBox", "driveraction/savedriver1", params, function (data, code, msg) {
            if (code == 200) {
                alert("操作成功");
                location.href = "driver-info.html?id=" + data.id;
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
})