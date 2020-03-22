package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * note
 *
 * @author
 */
@Data
public class Note {
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
     * 创建者id
     */
    private Long createUserId;

    /**
     * 内容
     */
    private String content;

    /**
     * [0没有附件, 1有附件]
     */
    private Byte ifFile;

    /**
     * [0不需要投票, 1需要投票]
     */
    private Byte ifVote;
}