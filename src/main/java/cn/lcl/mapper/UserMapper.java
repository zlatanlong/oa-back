package cn.lcl.mapper;

import cn.lcl.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    Long selectMaxNumber(Long deptId, Integer occupation);
}
