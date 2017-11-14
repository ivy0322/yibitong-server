package com.yibitong.entity.user;

import cn.jpush.api.report.UsersResult;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("yzj_t_user")
public class UserEntity extends Model<UserEntity>{

    @TableId
    private long user_id;

    @TableField("user_phone")
    private String user_phone;

    @TableField("password")
    private String password;

    @TableField("user_type")
    private int user_type;

    @TableField("user_bespeak")
    private int user_bespeak;

    @TableField("user_consult")
    private int user_consult;

    @TableField("user_head")
    private String user_head;

    @TableField("user_email")
    private String user_email;

    @TableField("user_sex")
    private int user_sex;

    @TableField("user_age")
    private int user_age;

    @TableField("jpushtag")
    private String jpushtag;

    @TableField("createtime")
    private long createtime;

    @TableField("reg_ip")
    private String reg_ip;

    @TableField("forbidden")
    private int forbidden;

    @TableField("recommend")
    private long recommend;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
