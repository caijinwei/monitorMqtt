/**
 * Created by whp on 2017/10/16.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {
        $(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green",});
        var uploader;
        var params = {};
        //加载基本信息
        T.common.ajax.request("WeconBox", "appveraction/getversion", params, function (data, code, msg) {
            if (code == 200) {
                if (data.versions != null) {
                    $('#androidVersion').val(data.versions[0]);
                    $('#iosVersion').val(data.versions[1]);
                    $('#updateContent').val(data.versions[2]);
                    var isforce = data.versions[3];
                    if (isforce != null && "" != isforce) {
                        if (1 == isforce) {
                            $('#isforce1').attr("checked", true);
                            $('#isforce2').attr("checked", false);
                        } else if (2 == isforce) {
                            $('#isforce1').attr("checked", false);
                            $('#isforce2').attr("checked", true);
                        }
                        $(".i-checks").iCheck({
                            checkboxClass: "icheckbox_square-green",
                            radioClass: "iradio_square-green",
                        });
                    }

                } else {
                    return;
                }
            }
        }, function () {
            console.log("ajax error");
        });

        //<editor-fold desc="固件文件上传">
        uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: true,
            // swf文件路径
            swf: '/box-web/web/lib/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: T.common.config.getRequestUrl("WeconBox") + 'fileact/apkupload?act=driver',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#pickerFile',
            // 只允许选择图片文件。
            accept: {
                title: 'Apk文件上传',
                extensions: 'apk',
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

            $error.text('上传失败，请检查是否为正确的apk文件');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });
        //</editor-fold>
    }

    $scope.updateVersion = function () {
        var params = {};
        var fields = $('#search-div .form-control');
        for (var i = 0; i < fields.length; i++) {
            var f = $(fields[i]);
            params[f.attr('id')] = f.val();
        }
        params['isforce'] = $("input[name='isforce']:checked").val()

        T.common.ajax.request("WeconBox", "appveraction/updateversion", params, function (data, code, msg) {
            if (code == 200) {
                swal({
                    title: "保存成功",
                    icon: "success",
                });
                $scope.$apply();
            } else {
                swal({
                    title: code + " " + msg,
                    icon: "error",
                });
            }
        }, function () {
            console.log("ajax error");
        });
    }

})