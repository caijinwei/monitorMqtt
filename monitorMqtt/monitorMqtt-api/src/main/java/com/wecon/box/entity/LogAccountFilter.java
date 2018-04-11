package com.wecon.box.entity;

import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import com.wecon.common.util.EnumUtil;

/**
 * Created by zengzhipeng on 2017/8/30.
 */
public class LogAccountFilter {
    public Long id;
    public Long account_id;
    public String username;
    public Integer client_platform;
    public OpTypeOption op_type;
    public Long res_id;
    public ResTypeOption res_type;
    public int op_date_begin;
    public int op_date_end;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClient_platform(Integer client_platform) {
        this.client_platform = client_platform;
    }

    public void setOp_type(int op_type) {
        this.op_type = EnumUtil.toEnum(OpTypeOption.class, op_type);
    }

    public void setRes_id(Long res_id) {
        this.res_id = res_id;
    }

    public void setRes_type(int res_type) {
        this.res_type = EnumUtil.toEnum(ResTypeOption.class, res_type);

    }

    public void setOp_date_begin(String op_date_begin) {
        if (op_date_begin != null && !op_date_begin.isEmpty()) {
            this.op_date_begin = Integer.valueOf(op_date_begin.replace("/", "").replace("-", ""));
        } else {
            this.op_date_begin = -1;
        }
    }

    public void setOp_date_end(String op_date_end) {
        if (op_date_end != null && !op_date_end.isEmpty()) {
            this.op_date_end = Integer.valueOf(op_date_end.replace("/", "").replace("-", ""));
        } else {
            this.op_date_end = -1;
        }
    }
}
