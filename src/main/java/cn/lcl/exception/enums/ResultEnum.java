package cn.lcl.exception.enums;

public enum ResultEnum {
    /**
     * 0-100: 用户异常
     * 101-200: 部门异常
     * 定义了枚举实例，它的属性是类的私有变量，然后可以通过使用    该类的名称.枚举实例名.get(属性)
     */
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    USER_INFO_NOT_INTEGRITY(001, "用户信息不完整"),
    USER_ACTIVE_FAIL(002, "用户激活失败"),
    DEPARTMENT_REPEAT(101,"部门重复"),
    DEPARTMENT_NO_PARENT(101,"部门重复"),
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
