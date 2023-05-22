package cn.withwang.cs.service;

import cn.withwang.cs.entity.Team;
import cn.withwang.cs.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamMapper teamMapper;
    public List<Team> getAllTeams() {
        return teamMapper.selectAll();
    }


    public Team getTeamById(int id) {
        return teamMapper.selectById(id);
    }
}
