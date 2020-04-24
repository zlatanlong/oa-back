package cn.lcl.service;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.TeamAddDTO;
import cn.lcl.pojo.dto.TeamMembersDTO;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.result.Result;

public interface TeamService {

    Result saveTeam(TeamAddDTO team);

    Result saveTeamMember(TeamMembersDTO teamMembers);

    Result deleteTeamMember(TeamMembersDTO teamMembers);

    /**
     * 只更新基本信息
     *
     * @param team 要包含id属性
     * @return 修改后的result
     */
    Result updateTeam(Team team);

    Result deleteTeam(Team team);

    /**
     * @param searchPageDTO data中是team对象
     * @return team信息和对应的页的学生信息
     */
    Result getTeam(SearchPageDTO<Team> searchPageDTO);

    Result listCreatedTeams();

    Result listJoinedTeams();

}
