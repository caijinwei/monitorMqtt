/**
 * Created by zengzhipeng on 2017/8/2.
 */
$(function () {
    $(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green",});
    T.common.user.checkAuth(2);
    $('#signup').bind('click', function () {
        if (!$('#cbRegister').is(':checked')) {
            swal({
                title: "请阅读并接受注册条款",
                icon: "warning"
            });
            return;
        }
        var params =
        {
            username: $('#username').val().trim(),
            email: $('#email').val().trim(),
            password: $('#password').val().trim()
        };
        if (params.username == '' || params.password == '' || params.email == '') {
            swal({
                title: "请填写完整",
                icon: "warning"
            });
            return;
        }
        if (params.password.length < 6) {
            swal({
                title: "密码长度至少6个字符",
                icon: "warning"
            });
            return;
        }
        if (params.password != $('#confirmpwd').val().trim()) {
            swal({
                title: "输入两次的密码不一致",
                icon: "warning"
            });
            return;
        }
        params.password = T.common.util.md5(params.password);
        T.common.ajax.request("WeconBox", "user/signupemail", params, function (data, code, msg) {
            if (code == 200) {
                swal({
                    title: "注册成功,请去邮箱激活帐号",
                    icon: "success"
                });
            }
            else {
                swal({
                    title: msg,
                    icon: "error"
                });
            }
        });
    })
})