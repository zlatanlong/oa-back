package generator;

public interface SysUserRoleDept {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRoleDept record);

    int insertSelective(SysUserRoleDept record);

    SysUserRoleDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRoleDept record);

    int updateByPrimaryKey(SysUserRoleDept record);
}