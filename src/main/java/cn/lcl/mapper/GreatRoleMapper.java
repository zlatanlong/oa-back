package cn.lcl.mapper;

import cn.lcl.pojo.UserRoleDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GreatRoleMapper {
    List<Long> selectManagedUser(Long userRoleDeptId);
}
