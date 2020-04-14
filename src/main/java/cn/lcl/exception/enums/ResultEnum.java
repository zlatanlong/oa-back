package cn.lcl.exception.enums;

public enum ResultEnum {
    /**
     * 0-100: 通用异常
     * 101-200: 用户异常
     * 201-300: 角色权限异常
     * 301-400: 小组权限异常
     * 定义了枚举实例，它的属性是类的私有变量，然后可以通过使用    该类的名称.枚举实例名.get(属性)
     */
    SUCCESS(0, "成功"),
    UNKONW_ERROR(1, "未知错误"),
    MISS_FIELD(2, ""), // 确实字段，错误信息由外层添加
    NOT_AUTHENTICATION(101, "未登录"),
    USER_INFO_NOT_INTEGRITY(102, "用户信息不完整"),
    USER_LIST_HAS_REPEAT(103, "用户列表中与已有用户有重复"),
    USER_INSERT_FAILED(104, "用户列表中与已有用户有重复"),
    USER_NOT_FOUND(105, "用户没有找到"),
    USER_PASSWORD_FAILED(106, "用户密码错误"),
    USERS_FIND_ERROR(107, "用户密码错误"),
    PERMISSION_NOT_FOUND(201, "此权限没有找到"),
    ROLE_NOT_FOUND(202, "此角色没有找到"),
    DELETE_ROLE_PERMISSION_FAILED(203, "删除此角色的此权限失败"),
    DELETE_USER_ROLE_FAILED(204, "删除此用户的此角色失败"),
    ROLE_ALREADLY_HAS_THIS_PERMISSION(206, "此角色已经有此权限"),
    TEAM_MEMBER_ALREADLY_EXIST(301, "此小组用户对应关系已经存在"),
    TEAM_MEMBER_NOT_FOUND(302, "此小组用户对应关系已经存在"),
    TEAM_MEMBER_INSERT_ERROR(303, "此小组用户对应关系插入失败"),
    TEAM_MEMBER_DELETE_ERROR(304, "此小组用户对应关系删除失败"),
    TEAM_NOT_FOUND(305, "小组没有找到"),
    TEAM_UPDATE_FAILED(306, "小组更新失败"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /*属性已经枚举列出,只有get方法,去获取属性,不再设置,是通过  枚举实例名.get属性()*/
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
