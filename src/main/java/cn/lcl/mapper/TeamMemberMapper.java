package cn.lcl.mapper;

import cn.lcl.pojo.TeamMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TeamMemberMapper extends BaseMapper<TeamMember> {
}
