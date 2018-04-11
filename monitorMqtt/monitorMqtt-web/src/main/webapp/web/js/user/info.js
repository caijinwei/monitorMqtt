/**
 * Created by zengzhipeng on 2017/8/4.
 */
var appModule = angular.module('weconweb', []);
appModule.controller("infoController", function ($scope, $http, $compile) {
    $scope.onInit = function () {

        T.common.ajax.request('WeconBox', "user/userinfod", new Object(), function (data, code, msg) {
            if (code == 200) {
                $scope.userInfo = data.userInfo;
                $scope.userExt = data.userExt;
                $scope.$apply();
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        });
    }

    /**
     * 修改密码
     */
    $scope.chgpwd = function () {
        if ($("#oldpwd").val().trim() == "" || $("#newpwd").val().trim() == "" || $("#newpwdconfirm").val().trim() == "") {
            swal({
                title: "请输入完整信息",
                icon: "warning"
            });
            return;
        }
        if ($("#newpwd").val().trim() != $("#newpwdconfirm").val().trim()) {
            swal({
                title: "两个输入的密码不一致",
                icon: "warning"
            });
            return;
        }
        var params =
        {
            oldpwd: T.common.util.md5($('#oldpwd').val().trim()),
            newpwd: T.common.util.md5($('#newpwd').val().trim())
        };
        T.common.ajax.request('WeconBox', "user/chgpwd", params, function (data, code, msg) {
            if (code == 200) {
                swal({
                    title: "修改成功",
                    icon: "success"
                });
                $("#updatePwd").modal("hide");
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        });
    }

    /**
     * 修改 email
     */
    $scope.chgemail = function () {
        if ($("#newemail").val().trim() == "") {
            swal({
                title: "请邮箱地址",
                icon: "warning"
            });
            return;
        }
        var params =
        {
            email: $('#newemail').val().trim()
        };
        T.common.ajax.request('WeconBox', "user/chgemail", params, function (data, code, msg) {
            if (code == 200) {
                swal({
                    title: "修改成功,请到新邮箱激活",
                    icon: "success"
                });
                $("#updateEmail").modal("hide");
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        });
    }

    var countdown = 60;

    function settime(obj) {
        if (countdown == 0) {
            obj.removeAttribute("disabled");
            obj.innerHTML = "获取验证码";
            countdown = 60;
            return;
        } else {
            obj.setAttribute("disabled", true);
            obj.innerHTML = "" + countdown + "秒后再获取";
            countdown--;
        }
        setTimeout(function () {
            settime(obj)
        }, 1000)
    }

    /**
     * 发送验证码
     */
    $scope.sendsms = function () {
        var oldphone = $scope.userInfo.phonenum;
        var newphone = $("#newphone").val().trim();
        if (newphone == "") {
            swal({
                title: "请填写手机号码",
                icon: "warning"
            });
            return;
        }
        if (oldphone != "" && oldphone == newphone) {
            swal({
                title: "手机号码与旧号码一样,请填写新号码",
                icon: "warning"
            });
            return;
        }
        var params =
        {
            phonenum: newphone
        };
        $btn = document.getElementById("sendsms");
        $btn.setAttribute("disabled", true);
        T.common.ajax.request("WeconBox", "user/sendvercode", params, function (data, code, msg) {
            if (code == 200) {
                settime($btn);
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
                $btn.removeAttribute("disabled");
            }
        }, function () {
            console.log("ajax error");
            $btn.removeAttribute("disabled");
        });
    }

    $scope.chgphone = function () {
        var params =
        {
            phonenum: $("#newphone").val().trim(),
            vercode: $("#vercode").val().trim()
        };
        T.common.ajax.request("WeconBox", "user/chgphonenum", params, function (data, code, msg) {
            if (code == 200) {
                swal({
                    title: "修改成功",
                    icon: "success"
                });
                $("#updatePhoneNum").modal("hide");
                $scope.userInfo.phonenum = data.phonenum;
                $scope.$apply();
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        }, function () {
            console.log("ajax error");
        });
    }

    $scope.chgcompany = function (){
        var params =
        {
            company: $("#company").val().trim(),
            company_business: $("#company_business").val().trim(),
            company_contact: $("#company_contact").val().trim(),
            company_phone: $("#company_phone").val().trim()
        };
        T.common.ajax.request("WeconBox", "user/chgcompany", params, function (data, code, msg) {
            if (code == 200) {
                swal({
                    title: "修改成功",
                    icon: "success"
                });
                $("#updateCompany").modal("hide");
                $scope.onInit();
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        }, function () {
            console.log("ajax error");
        });
    }
})