package com.wecon.box.impl;

import com.wecon.box.api.AccountApi;
import com.wecon.box.entity.Account;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.filter.AccountFilter;
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.core.SessionManager;
import com.wecon.restful.core.SessionState;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zengzhipeng on 2017/8/1.
 */
@Component
public class AccountImpl implements AccountApi {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PlatformTransactionManager transactionManager;

    private final String SEL_COL = " account_id,username,`password`,phonenum,email,create_date,`type`,state,update_date,secret_key ";

//    @Override
//    public Account signupByEmail(final String username, final String email, final String password) {
//        String sql = "select count(1) from account where username = ? or username = ? or email = ?  or email = ? or phonenum = ?  or phonenum = ? ";
//
//        int ret = jdbcTemplate.queryForObject(sql,
//                new Object[]{username, email, username, email, username, email},
//                Integer.class);
//        if (ret == 0) {
//            KeyHolder key = new GeneratedKeyHolder();
//            jdbcTemplate.update(new PreparedStatementCreator() {
//                @Override
//                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                    PreparedStatement preState = con.prepareStatement("insert into account(username,`password`,email,secret_key,create_date,`type`,state,update_date) values (?,?,?,?,current_timestamp(),?,?,current_timestamp() );", Statement.RETURN_GENERATED_KEYS);
//                    String secret_key = DigestUtils.md5Hex(UUID.randomUUID().toString());
//                    preState.setString(1, username);
//                    preState.setString(2, DigestUtils.md5Hex(password + secret_key));
//                    preState.setString(3, email);
//                    preState.setString(4, secret_key);
//                    preState.setInt(5, 1);//注册帐号为管理帐号
//                    preState.setInt(6, -1);//未激活
//
//                    return preState;
//                }
//            }, key);
//            //从主键持有者中获得主键值
//            long account_id = key.getKey().longValue();
//            return getAccount(account_id);
//        } else {
//            Account model = getAccount(username);
//            if (!username.equalsIgnoreCase(email) && model != null && !model.email.equalsIgnoreCase(email)) {
//                //用户名和邮箱不一样 且 用户名被占用，不能注册
//                throw new BusinessException(ErrorCodeOption.AccountExisted_UserName.key, ErrorCodeOption.AccountExisted_UserName.value);
//            }
//            model = getAccount(email);
//            if (model != null && model.state == -1) {
//                //邮箱被占用，且状态为邮箱未激活，可以再注册，在原来帐号上修改用户名
//                sql = "update account set username=?,`password`=?,email=?,secret_key=?,state=?,create_date=current_timestamp(),update_date=current_timestamp()  where account_id=?";
//                String secret_key = DigestUtils.md5Hex(UUID.randomUUID().toString());
//                jdbcTemplate.update(sql, new Object[]{username, DigestUtils.md5Hex(password + secret_key), model.email, secret_key, model.state, model.account_id});
//                return getAccount(model.account_id);
//            }
//        }
//
//        throw new BusinessException(ErrorCodeOption.AccountExisted_Email.key, ErrorCodeOption.AccountExisted_Email.value);
//    }

//    @Override
//    public Account signupByPhone(final String username, final String phonenum, final String password) {
//        String sql = "select count(1) from account where username = ? or username = ? or email = ?  or email = ? or phonenum = ?  or phonenum = ?  ";
//
//        int ret = jdbcTemplate.queryForObject(sql,
//                new Object[]{username, phonenum, username, phonenum, username, phonenum},
//                Integer.class);
//        if (ret > 0) {
//            Account model = getAccount(username);
//            if (model != null) {
//                throw new BusinessException(ErrorCodeOption.AccountExisted_UserName.key, ErrorCodeOption.AccountExisted_UserName.value);
//            } else {
//                throw new BusinessException(ErrorCodeOption.AccountExisted_Phone.key, ErrorCodeOption.AccountExisted_Phone.value);
//            }
//        }
//        KeyHolder key = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement preState = con.prepareStatement("insert into account(username,`password`,phonenum,secret_key,create_date,`type`,state,update_date) values (?,?,?,?,current_timestamp(),?,?,current_timestamp() );", Statement.RETURN_GENERATED_KEYS);
//                String secret_key = DigestUtils.md5Hex(UUID.randomUUID().toString());
//                preState.setString(1, username);
//                preState.setString(2, DigestUtils.md5Hex(password + secret_key));
//                preState.setString(3, phonenum);
//                preState.setString(4, secret_key);
//                preState.setInt(5, 1);//注册帐号为管理帐号
//                preState.setInt(6, 1);//注册成功为启用
//
//                return preState;
//            }
//        }, key);
//        //从主键持有者中获得主键值
//        long account_id = key.getKey().longValue();
//        return getAccount(account_id);
//    }
//
//    @Override
//    public void signout(String sid) {
//        SessionManager.deleteSession(sid);
//    }
//
//    @Override
//    public String createSession(Account user, int productId, String fuid, long loginIp, long loginTime, int seconds) {
//        SessionState.UserInfo.Builder builder = SessionState.UserInfo.newBuilder();
//        builder.setCuid(fuid);
//        builder.setProductId(productId);
//        builder.setLoginIp(loginIp);
//        builder.setLoginTime(loginTime);
//
//        builder.setUserID(user.account_id);
//        builder.setAccount(user.username);
//        builder.setUserType(user.type);
//        SessionState.UserInfo builderUser = builder.build();
//
//        String sid = UUID.randomUUID().toString().replace("-", "");
//        SessionManager.persistSession(sid, builderUser, seconds);//3600 * 24 * 30
//        return sid;
//    }
//
//    @Override
//    public boolean updateAccountEmail(Account model) {
//        //判断新邮箱不能被使用
//        String sql = "select count(1) from account where account_id<>? and (username = ? or email = ? or phonenum = ? ) ";
//
//        int ret = jdbcTemplate.queryForObject(sql,
//                new Object[]{model.account_id, model.email, model.email, model.email},
//                Integer.class);
//        if (ret > 0) {
//            throw new BusinessException(ErrorCodeOption.AccountEmailExisted.key, ErrorCodeOption.AccountEmailExisted.value);
//        }
//
//        sql = "update account set email=?,state=?,update_date=current_timestamp()  where account_id=?";
//        jdbcTemplate.update(sql, new Object[]{model.email, model.state, model.account_id});
//        return true;
//    }
//
//    @Override
//    public boolean updateAccountPhone(Account model) {
//        String sql = "select count(1) from account where account_id<>? and (username = ? or email = ? or phonenum = ? ) ";
//
//        int ret = jdbcTemplate.queryForObject(sql,
//                new Object[]{model.account_id, model.phonenum, model.phonenum, model.phonenum},
//                Integer.class);
//        if (ret > 0) {
//            throw new BusinessException(ErrorCodeOption.AccountPhoneExisted.key, ErrorCodeOption.AccountPhoneExisted.value);
//        }
//
//        sql = "update account set phonenum=?,state=?,update_date=current_timestamp()  where account_id=?";
//        jdbcTemplate.update(sql, new Object[]{model.phonenum, model.state, model.account_id});
//        return true;
//    }
//
//    @Override
//    public boolean updateAccountState(Account model) {
//        String sql = "update account set state=?,update_date=current_timestamp()  where account_id=?";
//        jdbcTemplate.update(sql, new Object[]{model.state, model.account_id});
//        SessionManager.deleteUserSession(model.account_id);
//        return true;
//    }
//
////    @Override
////    public boolean saveAccountExt(AccountExt model) {
////        String sql = "update account_ext set company=?,company_business=?,company_contact=?,company_phone=?,update_date=current_timestamp()  where account_id=?";
////        if (jdbcTemplate.update(sql, new Object[]{model.company, model.company_business, model.company_contact, model.company_phone, model.account_id}) == 0) {
////            sql = "insert into account_ext(account_id,company,company_business,company_contact,company_phone,create_date,update_date) values (?,?,?,?,?,current_timestamp(),current_timestamp() );";
////            jdbcTemplate.update(sql, new Object[]{model.account_id, model.company, model.company_business, model.company_contact, model.company_phone});
////        }
////        return true;
////    }
//
//    @Override
//    public boolean updateAccountPwd(long account_id, String oldpwd, String newpwd) {
//        Account model = getAccount(account_id);
//        if (model == null) {
//            throw new BusinessException(ErrorCodeOption.AccountNotExisted.key, ErrorCodeOption.AccountNotExisted.value);
//        }
//        String pwd = DigestUtils.md5Hex(oldpwd + model.secret_key);
//        if (oldpwd != "" && !pwd.equals(model.password)) {
//            throw new BusinessException(ErrorCodeOption.OldPwdError.key, ErrorCodeOption.OldPwdError.value);
//        }
//        pwd = DigestUtils.md5Hex(newpwd + model.secret_key);
//        String sql = "update account set password=?,update_date=current_timestamp()  where account_id=?";
//        jdbcTemplate.update(sql, new Object[]{pwd, model.account_id});
//
//        return true;
//    }
//
//    @Override
//    public Account getAccount(long account_id) {
//        String sql = "select " + SEL_COL + " from account where account_id=?";
//        List<Account> list = jdbcTemplate.query(sql,
//                new Object[]{account_id},
//                new DefaultAccountRowMapper());
//        if (!list.isEmpty()) {
//            return list.get(0);
//        }
//        return null;
//    }
//
//    @Override
//    public Account getAccount(String alias) {
//        String sql = "select " + SEL_COL + " from account where username=? or phonenum=? or email=?";
//        System.out.println("sql---->"+sql);
//        List<Account> list = jdbcTemplate.query(sql,
//                new Object[]{alias, alias, alias},
//                new DefaultAccountRowMapper());
//        if (!list.isEmpty()) {
//            return list.get(0);
//        }
//        return null;
//    }
//
//    @Override
//    public AccountExt getAccountExt(long account_id) {
//        String sql = "select account_id,company,company_business,company_contact,company_phone,create_date,update_date from account_ext where account_id=?";
//        List<AccountExt> list = jdbcTemplate.query(sql,
//                new Object[]{account_id},
//                new DefaultAccountExtRowMapper());
//        if (!list.isEmpty()) {
//            return list.get(0);
//        }
//        return null;
//    }
//
//    @Override
//    public Page<Account> getAccountList(AccountFilter filter, int pageIndex, int pageSize) {
//        String sqlCount = "select count(0) from account where 1=1";
//        String sql = "select " + SEL_COL + " from account where 1=1";
//        String condition = "";
//        List<Object> params = new ArrayList<Object>();
//        if (filter.account_id > 0) {
//            condition += " and account_id = ? ";
//            params.add(filter.account_id);
//        }
//        if (filter.alias != null && !filter.alias.isEmpty()) {
//            condition += " and (username like ? or phonenum like ? or email like ?)";
//            params.add("%" + filter.alias + "%");
//            params.add("%" + filter.alias + "%");
//            params.add("%" + filter.alias + "%");
//        }
//        if (filter.username != null && !filter.username.isEmpty()) {
//            condition += " and username like ? ";
//            params.add("%" + filter.username + "%");
//        }
//        if (filter.phonenum != null && !filter.phonenum.isEmpty()) {
//            condition += " and phonenum like ? ";
//            params.add("%" + filter.phonenum + "%");
//        }
//        if (filter.email != null && !filter.email.isEmpty()) {
//            condition += " and email like ? ";
//            params.add("%" + filter.email + "%");
//        }
//        if (filter.type > -1) {
//            condition += " and type = ? ";
//            params.add(filter.type);
//        }
//        if (filter.state > -1) {
//            condition += " and state = ? ";
//            params.add(filter.state);
//        }
//
//
//        sqlCount += condition;
//        int totalRecord = jdbcTemplate.queryForObject(sqlCount,
//                params.toArray(),
//                Integer.class);
//        Page<Account> page = new Page<Account>(pageIndex, pageSize, totalRecord);
//        String sort = " order by account_id desc";
//        sql += condition + sort + " limit " + page.getStartIndex() + "," + page.getPageSize();
//        List<Account> list = jdbcTemplate.query(sql,
//                params.toArray(),
//                new DefaultAccountRowMapper());
//        page.setList(list);
//        return page;
//    }
//
//    @Override
//    public Page<Account> getViewAccountList(long managerId, int pageIndex, int pageSize) {
//        String sqlCount = "SELECT count(1) FROM account a " +
//                "INNER JOIN account_relation b on b.view_id=a.account_id ";
//        String sql = "SELECT  a.* FROM account a " +
//                "INNER JOIN account_relation b on b.view_id=a.account_id ";
//        String condition = "WHERE b.manager_id=?";
//        List<Object> params = new ArrayList<Object>();
//        params.add(managerId);
//        sqlCount += condition;
//        int totalRecord = jdbcTemplate.queryForObject(sqlCount,
//                params.toArray(),
//                Integer.class);
//        Page<Account> page = new Page<Account>(pageIndex, pageSize, totalRecord);
//        String sort = " order by a.account_id desc";
//        sql += condition + sort + " limit " + page.getStartIndex() + "," + page.getPageSize();
//        List<Account> list = jdbcTemplate.query(sql,
//                params.toArray(),
//                new DefaultAccountRowMapper());
//        page.setList(list);
//        return page;
//    }
//
//    @Override
//    public long getManagerId(long viewId) {
//        String sql = "SELECT manager_id FROM account_relation WHERE view_id=?;";
//        long managerId = jdbcTemplate.queryForObject(sql,
//                new Object[]{viewId},
//                Long.class);
//        return managerId;
//    }
//
//    @Override
//    public boolean addViewAccount(final long managerId, final Account viewAccount) {
//        TransactionTemplate tt = new TransactionTemplate(transactionManager);
//        try {
//            tt.execute(new TransactionCallback() {
//                @Override
//                public Object doInTransaction(TransactionStatus ts) {
//                    //视图帐号，只能管理员用用户名注册
//                    String sql = "select count(1) from account where username = ? or email = ?  or phonenum = ?  ";
//
//                    int ret = jdbcTemplate.queryForObject(sql,
//                            new Object[]{viewAccount.username, viewAccount.username, viewAccount.username},
//                            Integer.class);
//                    if (ret > 0) {
//                        throw new BusinessException(ErrorCodeOption.AccountExisted.key, ErrorCodeOption.AccountExisted.value);
//                    }
//
//                    KeyHolder key = new GeneratedKeyHolder();
//                    jdbcTemplate.update(new PreparedStatementCreator() {
//                        @Override
//                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                            PreparedStatement preState = con.prepareStatement("insert into account(username,`password`,secret_key,create_date,`type`,state,update_date) values (?,?,?,current_timestamp(),?,?,current_timestamp() );", Statement.RETURN_GENERATED_KEYS);
//                            String secret_key = DigestUtils.md5Hex(UUID.randomUUID().toString());
//                            preState.setString(1, viewAccount.username);
//                            preState.setString(2, DigestUtils.md5Hex(viewAccount.password + secret_key));
//                            preState.setString(3, secret_key);
//                            preState.setInt(4, 2);//视图帐号
//                            preState.setInt(5, viewAccount.state);
//
//                            return preState;
//                        }
//                    }, key);
//                    //从主键持有者中获得主键值
//                    long view_id = key.getKey().longValue();
//                    viewAccount.account_id = view_id;
//
//                    //增加关联
//                    jdbcTemplate.update("insert into account_relation(manager_id,view_id) values (?,?);", new Object[]{managerId, view_id});
//
//                    return true;
//                }
//            });
//        } catch (Exception e) {
//            Logger.getLogger(AccountImpl.class.getName()).log(Level.SEVERE, null, e);
//            throw new BusinessException(ErrorCodeOption.AccountExisted.key, ErrorCodeOption.AccountExisted.value);
////            throw new RuntimeException(e);
//        }
//        return true;
//    }
//
//    @Override
//    public void updatePwd(long accountId, String pwd) {
//        String sql = "UPDATE account a SET a.`password`=?,a.secret_key=?,a.update_date=CURRENT_TIMESTAMP() WHERE account_id=? ";
//        String secret_key = DigestUtils.md5Hex(UUID.randomUUID().toString());
//        String newPwd = DigestUtils.md5Hex(pwd + secret_key);
//        jdbcTemplate.update(sql, new Object[]{newPwd, secret_key, accountId});
//    }
//
//    @Override
//    public List<Account> getAllAccounts() {
//        String sql = "SELECT " + SEL_COL + " FROM account where 1=1";
//        return jdbcTemplate.query(sql, new DefaultAccountRowMapper());
//    }
//
//    public static final class DefaultAccountRowMapper implements RowMapper<Account> {
//        @Override
//        public Account mapRow(ResultSet rs, int i) throws SQLException {
//            Account model = new Account();
//            model.account_id = rs.getLong("account_id");
//            model.username = rs.getString("username");
//            model.password = rs.getString("password");
//            model.phonenum = rs.getString("phonenum");
//            model.email = rs.getString("email");
//            model.type = rs.getInt("type");
//            model.state = rs.getInt("state");
//            model.create_date = rs.getTimestamp("create_date");
//            model.update_date = rs.getTimestamp("update_date");
//            model.secret_key = rs.getString("secret_key");
//
//            return model;
//        }
//    }
//
//    public static final class DefaultAccountExtRowMapper implements RowMapper<AccountExt> {
//        @Override
//        public AccountExt mapRow(ResultSet rs, int i) throws SQLException {
//            AccountExt model = new AccountExt();
//            model.account_id = rs.getLong("account_id");
//            model.company = rs.getString("company");
//            model.company_business = rs.getString("company_business");
//            model.company_contact = rs.getString("company_contact");
//            model.company_phone = rs.getString("company_phone");
//            model.create_date = rs.getTimestamp("create_date");
//            model.update_date = rs.getTimestamp("update_date");
//
//            return model;
//        }
//    }
}
