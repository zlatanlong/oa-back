package cn.lcl.dto;

import lombok.Data;

@Data
public class DataPageDTO<T> {

    Integer pageCurrent; // 这个直接对应的是第几页，Mybatis Plus 映射的sql会自动减一开始limit

    Integer pageSize;

    T data;
}
