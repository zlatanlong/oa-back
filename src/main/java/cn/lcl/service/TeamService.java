package cn.lcl.service;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.TeamAddDTO;
import cn.lcl.dto.TeamMembersDTO;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.result.Result;

public interface TeamService {

    Result addTeam(TeamAddDTO team);

    Result addTeamMember(TeamMembersDTO teamMembers);

    Result delTeamMember(TeamMembersDTO teamMembers);

    /**
     * 只更新基本信息
     *
     * @param team 要包含id属性
     * @return 修改后的result
     */
    Result updateTeam(Team team);

    Result delTeam(Team team);

    /**
     * @param dataPageDTO data中是team对象
     * @return team信息和对应的页的学生信息
     */
    Result getTeam(DataPageDTO<Team> dataPageDTO);

    Result getCreatedTeams();

    Result getJoinedTeams();

}
