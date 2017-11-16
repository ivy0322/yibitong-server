package com.yibitong.entity.user;

import cn.jpush.api.report.UsersResult;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("customer_user")
public class UserEntity extends Model<UserEntity>{

    @TableId
    private long user_id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @Override
    protected Serializable pkVal() {
        return this.user_id;
    }
}
