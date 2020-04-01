package cn.lcl.exception.enums;

public enum ResultEnum {
    /**
     * 0-100: 用户异常
     * 101-200: 部门异常
     * 1001-1010: 权限异常
     * 定义了枚举实例，它的属性是类的私有变量，然后可以通过使用    该类的名称.枚举实例名.get(属性)
     */
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    USER_INFO_NOT_INTEGRITY(001, "用户信息不完整"),
    USER_ACTIVE_FAIL(002, "用户激活失败"),
    USER_NOT_FOUND(003, "用户没有找到"),
    USER_PASSWORD_FAILED(004, "用户密码错误"),
    USER_NOT_ACTIVE(005, "用户未激活"),
    DEPARTMENT_REPEAT(101,"部门重复"),
    DEPARTMENT_NO_PARENT(101,"部门重复"),
    NOT_AUTHENTICATION(1001,"未登录"),
    NOT_AUTHORIZATION(1002,"未授权"),
    NOT_FOUND_THIS_URD(1003,"此部门权限不存在"),
    NO_AUTHOR_FOR_THIS_USER(1004,"管理此用户权限不足"),
    NOT_HAVE_THIS_URD(1005,"用户无此部门权限"),
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
