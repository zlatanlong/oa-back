package cn.lcl.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_permission
 * @author 
 */
@Data
public class SysPermission implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称（即左侧菜单栏的栏目名称，比如：用户管理、小组管理、事务管理、标签管理、用户-角色-权限管理）
     */
    private String permissionName;

    /**
     * 编号（需要与前端沟通一致，以显示哪些权限）
     */
    private Integer number;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建者在user表的id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新者的id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer updatorId;

    /**
     * 删除标志（0表示未删除，id表示已删除）
     */
    @TableField(select = false)
    private Integer deleteFlg;

    private static final long serialVersionUID = 1L;
}