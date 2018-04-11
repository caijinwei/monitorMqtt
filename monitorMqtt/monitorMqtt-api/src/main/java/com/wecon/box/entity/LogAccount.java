package com.wecon.box.entity;

import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;

import java.sql.Timestamp;

/**
 * Created by zengzhipeng on 2017/8/1.
 */
public class LogAccount {
    public long id;
    public long account_id;
    public String username;
    public int client_platform;
    public String client_ip;
    public OpTypeOption op_type;
    public String op_type_name;
    public int op_date;
    public Timestamp op_time;
    public String message;
    public String url;
    public long res_id;
    public ResTypeOption res_type;
    public String res_type_name;
}
