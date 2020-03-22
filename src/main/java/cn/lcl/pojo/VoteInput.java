package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * vote_input
 *
 * @author
 */
@Data
public class VoteInput {
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
     * 投票id
     */
    private Long voteId;

    /**
     * 输入框标题
     */
    private String inputTitle;

    /**
     * 第n项
     */
    private Integer number;

    /**
     * 平均值
     */
    private Integer mean;
}