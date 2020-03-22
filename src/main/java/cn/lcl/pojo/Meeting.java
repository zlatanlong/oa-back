package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * meeting
 *
 * @author
 */
@Data
public class Meeting {
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
     * 标题
     */
    private String title;

    /**
     * 时间
     */
    private Date time;

    /**
     * 地点
     */
    private String location;

    /**
     * 描述
     */
    private String description;

    /**
     * [0未结束, 1结束]
     */
    private Byte ifEnd;

    /**
     * 创建者id
     */
    private Long createUserId;

    /**
     * 记录员id
     */
    private Long sumUserId;

    /**
     * 总结
     */
    private String sum;

    /**
     * [0没有附件, 1有附件]
     */
    private Byte ifFile;
}