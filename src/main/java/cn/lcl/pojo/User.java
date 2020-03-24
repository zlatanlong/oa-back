package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * user
 *
 * @author
 */
@Data
public class User {
    /**
     * 主键
     */
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    @TableField(select = false)
    private Integer deleted;

    /**
     * 微信的openid
     */
    private String wxOpenid;

    /**
     * 学号/工号
     */
    private Long number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 职业 [1'学生', 0'老师']
     */
    private Byte occupation;

    /**
     * [0未激活, 1 激活]
     */
    private Byte state;

    /**
     * 操作人
     */
    private Long operateUserId;
}