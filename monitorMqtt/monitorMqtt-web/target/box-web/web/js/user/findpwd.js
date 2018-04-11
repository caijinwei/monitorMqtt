/**
 * Created by zengzhipeng on 2017/9/11.
 */
$(function () {
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

    $('#getvercode').bind('click', function () {
        if (checkSubmitEmail() || checkSubmitMobil()) {
            $btn = this;
            //settime($btn);
            var paramsAccount =
            {
                account: $('#account').val().trim()
            };
            var params =
            {
                phonenum: $('#account').val().trim()
            };
            if (params.phonenum == '') {
                alert("请输入手机号码");
                return;
            }
            $btn.setAttribute("disabled", true);
            T.common.ajax.request("WeconBox", "user/checkaccount", paramsAccount, function (data, code, msg) {
                if (code == 200) {
                    if (data.account != null) {
                        T.common.ajax.request("WeconBox", "user/sendvercode", params, function (data, code, msg) {
                            $("#loadingModal").modal("hide");
                            if (code == 200) {
                                settime($btn);
                            }
                            else {
                                alert(msg);
                                $btn.removeAttribute("disabled");
                            }
                        }, function () {
                            console.log("ajax error");
                            $btn.removeAttribute("disabled");
                        });
                    } else {
                        alert("帐号不存在");
                        $btn.removeAttribute("disabled");
                    }
                }
                else {
                    alert(msg);
                    $btn.removeAttribute("disabled");
                }
            }, function () {
                console.log("ajax error");
                $btn.removeAttribute("disabled");
            });
        } else {
            alert("请输入正确的邮箱或者手机号码");
        }
    })

    $('#btnConfirm').bind('click', function () {
        var params =
        {
            account: $('#account').val().trim(),
            vercode: $('#vercode').val().trim(),
            newpwd: $('#password').val().trim()
        };
        if (params.account == '' || params.vercode == '' || params.oldpwd == '' || params.newpwd == '') {
            alert("请填写完整");
            return;
        }
        if (params.newpwd.length < 6) {
            alert("密码长度至少6个字符");
            return;
        }
        if (params.newpwd != $('#confirmpwd').val().trim()) {
            alert("输入两次的密码不一致");
            return;
        }
        params.newpwd = T.common.util.md5(params.newpwd);

        T.common.ajax.request("WeconBox", "user/findpwd", params, function (data, code, msg) {
            $("#loadingModal").modal("hide");
            if (code == 200) {
                alert("密码修改成功");
            }
            else {
                alert(msg);
            }
        });

    })

    //jquery验证邮箱
    function checkSubmitEmail() {
        if ($("#account").val() == "") {
            //alert("邮箱不能为空!")
            $("#account").focus();
            return false;
        }
        if (!$("#account").val().match(/^([a-z0-9A-Z]+[-|\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/)) {
            //alert("邮箱格式不正确");
            $("#account").focus();
            return false;
        }
        return true;
    }

    //jquery验证手机号码
    function checkSubmitMobil() {
        if ($("#account").val() == "") {
            //alert("手机号码不能为空！");
            $("#account").focus();
            return false;
        }

        if (!$("#account").val().match(/^[1]\d{10}$/)) {
            //alert("手机号码格式不正确！");
            $("#account").focus();
            return false;
        }
        return true;
    }
})