package cn.lcl.mapper;

import cn.lcl.pojo.UserRoleDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleDeptMapper extends BaseMapper<UserRoleDept> {
}
