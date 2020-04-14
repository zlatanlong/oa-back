package cn.lcl.dto;

import cn.lcl.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class TeamMembersUpdateDTO {

    Integer teamId;

    List<User> members;

}
