package cn.lcl.exception.enums;

public enum ResultEnum {
    /**
     * 0-100: 普通异常
     * 定义了枚举实例，它的属性是类的私有变量，然后可以通过使用    该类的名称.枚举实例名.get(属性)
     */
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
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
