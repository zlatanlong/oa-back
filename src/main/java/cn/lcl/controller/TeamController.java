package cn.lcl.controller;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.TeamAddDTO;
import cn.lcl.dto.TeamMembersDTO;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.TeamService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService service;

    @PostMapping("/add")
    public Result addTeam(@RequestBody @Valid TeamAddDTO team, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.addTeam(team));
    }

    @PostMapping("/addMember")
    public Result addTeamMember(@RequestBody @Valid TeamMembersDTO teamMembers, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.addTeamMember(teamMembers));
    }

    @PostMapping("/delMember")
    public Result delTeamMember(@RequestBody @Valid TeamMembersDTO teamMembers, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.delTeamMember(teamMembers));
    }

    @PostMapping
    public Result getTeam(@RequestBody @Valid DataPageDTO<Team> pageDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.getTeam(pageDTO));
    }

    @PostMapping("/createdList")
    public Result createdList() {
        return service.getCreatedTeams();
    }

    @PostMapping("/joinedList")
    public Result joinedList() {
        return service.getJoinedTeams();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Team team) {
        return service.updateTeam(team);
    }

    @PostMapping("/del")
    public Result del(@RequestBody Team team) {
        return service.delTeam(team);
    }
}
