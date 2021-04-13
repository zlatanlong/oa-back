package cn.lcl.logging.mapper;

import cn.lcl.logging.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/13 19:13
 */
@Repository
@Mapper
public interface LogMapper extends BaseMapper<Log> {
}
