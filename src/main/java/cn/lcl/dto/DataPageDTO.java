package cn.lcl.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DataPageDTO<T> {

    @NotNull(message = "当前页数不能为空")
    Integer pageCurrent; // 这个直接对应的是第几页，Mybatis Plus 映射的sql会自动减一开始limit

    @NotNull(message = "每页数量不能为空")
    Integer pageSize;

    T data;
}
