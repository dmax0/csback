package cn.withwang.cs.controller;

import cn.withwang.cs.dto.ResponseDto;
import cn.withwang.cs.entity.Team;
import cn.withwang.cs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;


    /**
     * 获取所有团队
     */
    @GetMapping
    ResponseDto<List<Team>> getAllTeams() {

        List<Team> teams = this.teamService.getAllTeams();
        return new ResponseDto<>(HttpStatus.OK,teams,"获取成功", true);


    }
    /**
     * @description 根据id获取团队
     * @param id 团队id
     * @return Team 团队
     */
    @GetMapping("/{id}")
    public ResponseDto<Team> getTeamsByID(@PathVariable("id") Integer id) {

        Team teams = this.teamService.getTeamById(id);
        return new ResponseDto<>(HttpStatus.OK,teams,"获取成功", true);

    }

    /**
     * @description 创建团队
     * 创建成员的角色
     * @param Team 团队
     * @return 创建成功的消息

    */
    @PostMapping
    public ResponseDto<Team> createTeams(Team team) {

        int rows = this.teamService.createTeam(team);
        return ResponseDto.success("创建成功");
    }
}

