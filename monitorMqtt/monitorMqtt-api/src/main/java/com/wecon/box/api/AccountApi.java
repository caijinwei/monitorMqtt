package com.wecon.box.api;

import com.wecon.box.entity.Account;
import com.wecon.box.filter.AccountFilter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zengzhipeng on 2017/7/31.
 */
@Component
public interface AccountApi {


//    /**
//     * 邮箱注册
//     *
//     * @param username
//     * @param email
//     * @param password
//     * @return
//     */
//    Account signupByEmail(String username, String email, String password);
//
//    /**
//     * 手机号注册
//     *
//     * @param username
//     * @param phonenum
//     * @param password
//     * @return
//     */
//    Account signupByPhone(String username, String phonenum, String password);
//
//    /**
//     * 退出登录
//     *
//     * @param sid
//     */
//    void signout(String sid);
//
//    /**
//     * 创建会话
//     *
//     * @param user
//     * @param productId
//     * @param fuid
//     * @param loginIp
//     * @param loginTime
//     * @param seconds
//     * @return
//     */
//    String createSession(Account user, int productId, String fuid, long loginIp, long loginTime, int seconds);
//
//    /**
//     * 更新邮箱
//     *
//     * @param model
//     * @return
//     */
//    boolean updateAccountEmail(Account model);
//
//    /**
//     * 更新手机号
//     *
//     * @param model
//     * @return
//     */
//    boolean updateAccountPhone(Account model);
//
//    /**
//     * 更新帐户状态
//     *
//     * @param model
//     * @return
//     */
//    boolean updateAccountState(Account model);
//
//
//    /**
//     * 获取视图帐号的管理帐号Id
//     *
//     * @param viewId
//     * @return
//     */
//    long getManagerId(long viewId);
//
//    /**
//     * 新增视图帐号
//     *
//     * @param managerId
//     * @param viewAccount
//     * @return
//     */
//    boolean addViewAccount(long managerId, Account viewAccount);
//
//    /*
//    * 更新账户信息
//    * @param accountId
//    * @param pwd
//    * */
//    void updatePwd(long accountId, String pwd);
//
//    /*
//    * 获取全部的account
//    * */
//    List<Account> getAllAccounts();
}
