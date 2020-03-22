package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * user_meeting
 *
 * @author
 */
@Data
public class UserMeeting {
    /**
     * 主键
     */
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    /**
     * [0正常, 1删除]
     */
    @TableField(select = false)
    private Integer deleted;

    /**
     * 会议id
     */
    private Long meetingId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * [0未签到, 1签到]
     */
    private Byte ifSign;

    /**
     * [0未读, 1已读]
     */
    private Byte ifRead;
}