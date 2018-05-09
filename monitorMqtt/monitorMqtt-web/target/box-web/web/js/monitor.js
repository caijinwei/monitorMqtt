/**
 * Created by Administrator on 2017/11/21.
 */

var addEmain_btn = function () {
    $("#emainWrapper").append('<div><input class="form-control" name="emainInputs" placeholder="邮箱"/></div>')
}


var addPhone_btn = function () {
    $("#phoneWrapper").append('<div><input class="form-control" name="phoneInputs" placeholder="手机号"/></div>')
}



var monitor_mqtt = function() {
    var params = {};
    T.common.ajax.request("WeconBox",
        "mqttAction/isActive", params, function(
            data, code, msg) {
            if (code == 200) {
                alert("执行成功");
            } else {
                alert(code + "-" + msg);
            }
        }, function() {
            alert("ajax error");
        });
}

var alarmModalShow=function (){
    if($('#monitor_project').val()==='connectNum'){
        $('#maxConnect').show();
    }else{
        $('#maxConnect').hide();
    }
    $('#alarmModal').modal("show");
}