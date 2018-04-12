package com.wecon.box.action.user;

import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zengzhipeng on 2017/8/4.
 */
@Label("登出")
@RestController
public class SignoutAction extends UserBaseAction {
    @RequestMapping("user/signout")
    @WebApi(forceAuth = true, master = true)
    public Output signout(){
        //<editor-fold desc="操作日志">
        dbLogUtil.addOperateLog(OpTypeOption.Signout, ResTypeOption.Account,AppContext.getSession().client.userId, null);
        //</editor-fold>
        accountApi.signout(AppContext.getSession().client.sid);
        return new Output();
    }
}
