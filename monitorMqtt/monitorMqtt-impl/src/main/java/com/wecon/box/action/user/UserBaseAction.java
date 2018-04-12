package com.wecon.box.action.user;

import com.wecon.box.api.AccountApi;
import com.wecon.box.util.DbLogUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zengzhipeng on 2017/8/1.
 */
public class UserBaseAction {
    @Autowired
    protected AccountApi accountApi;

    @Autowired
    protected DbLogUtil dbLogUtil;
}
