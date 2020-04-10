package cn.lcl.mapper;

import cn.lcl.pojo.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
}
