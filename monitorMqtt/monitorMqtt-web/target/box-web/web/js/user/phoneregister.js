/**
 * Created by zengzhipeng on 2017/8/15.
 */
$(function () {
    T.common.user.checkAuth(2);

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

    $('#sendsms').bind('click', function () {
        $btn = this;
        //settime($btn);
        var params =
        {
            phonenum: $('#phonenum').val().trim()
        };
        if (params.phonenum == '') {
            alert("请输入手机号码");
            return;
        }
        $btn.setAttribute("disabled", true);
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
    })

    $('#signup').bind('click', function () {
        if (!$('#cbRegister').is(':checked')) {
            alert("请阅读并接受注册条款");
            return;
        }
        $("#loadingModal").modal("show");
        var params =
        {
            username: $('#username').val().trim(),
            phonenum: $('#phonenum').val().trim(),
            vercode: $('#vercode').val().trim(),
            password: $('#password').val().trim()
        };
        if (params.username == '' || params.password == '' || params.phonenum == '' || params.vercode == '') {
            alert("请填写完整");
            $("#loadingModal").modal("hide");
            return;
        }
        if (params.password.length < 6) {
            alert("密码长度至少6个字符");
            $("#loadingModal").modal("hide");
            return;
        }
        if (params.password != $('#confirmpwd').val().trim()) {
            alert("输入两次的密码不一致");
            $("#loadingModal").modal("hide");
            return;
        }
        params.password = T.common.util.md5(params.password);
        T.common.ajax.request("WeconBox", "user/signupphone", params, function (data, code, msg) {
            $("#loadingModal").modal("hide");
            if (code == 200) {
                alert("注册成功");
                T.common.user.setSid(data.sid);
                location = "../../../main.html";
            }
            else {
                alert(msg);
            }
        });

    })
})
/*
 * modal框显示
 * */
showUserLicenseModal = function () {
    $("#userLicenseModal").modal("show");
}