package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * vote_option
 *
 * @author
 */
@Data
public class VoteOption {
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
     * 选项名
     */
    private String optionKey;

    /**
     * 选项对应值
     */
    private Integer optionValue;

    /**
     * 单选组id
     */
    private Long groupId;

    /**
     * 选择此选项人数
     */
    private Integer count;
}