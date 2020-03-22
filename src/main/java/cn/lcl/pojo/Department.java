package cn.lcl.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * department
 *
 * @author
 */
@Data
public class Department {
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
     * 上机部门id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 等级[0校,1院,2系]
     */
    private Integer level;

    /**
     * 所有上级部门
     */
    private String parentPath;

    /**
     * 部门编号
     */
    private Integer number;
}