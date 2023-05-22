package cn.withwang.cs.controller;

import cn.withwang.cs.entity.Team;
import cn.withwang.cs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;


    /**
     * 获取所有团队
     */
    @GetMapping
    List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }
    /**
     * @description 根据id获取团队
     * @param id 团队id
     * @return Team 团队
     */
    @GetMapping("/{id}")
    Team getTeamById(int id) {
        return teamService.getTeamById(id);
    }

}
