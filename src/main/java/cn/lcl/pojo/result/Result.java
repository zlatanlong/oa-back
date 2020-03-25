package cn.lcl.pojo.result;

import lombok.Data;

/**
 * 返回给前端的最外层对象
 * @param <T>
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

}
