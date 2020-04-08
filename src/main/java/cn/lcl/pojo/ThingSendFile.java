package cn.lcl.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * thing_send_file
 * @author 
 */
@Data
public class ThingSendFile implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 事务Id
     */
    private Integer thingId;

    /**
     * 文件的原始名字
     */
    private String originName;

    /**
     * 文件存储的ip地址（记得给附件加上随机前缀）
     */
    private String fileIp;

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