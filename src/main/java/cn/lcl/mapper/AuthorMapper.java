package cn.lcl.mapper;

import cn.lcl.pojo.UserRoleDept;
import cn.lcl.pojo.ov.RoleApiOV;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AuthorMapper {
    List<Long> selectManagedUser(Long userRoleDeptId);

    List<RoleApiOV> getRolePermission();

    String getRoleInDept(Long id);
}
