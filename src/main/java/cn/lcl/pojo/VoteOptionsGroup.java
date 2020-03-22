package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * vote_options_group
 *
 * @author
 */
@Data
public class VoteOptionsGroup {
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
     * 单选组题目
     */
    private String groupTitle;

    /**
     * 投票id
     */
    private Long voteId;

    /**
     * 第n项
     */
    private Integer number;
}