package com.axr.starrybackend.service;

import com.axr.starrybackend.common.BaseResponse;
import com.axr.starrybackend.model.domain.Team;
import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.request.Team.TeamAddRequest;
import com.axr.starrybackend.model.request.Team.TeamUpdateRequest;
import com.axr.starrybackend.model.request.Team.TeamVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author axr
* @description 针对表【team(队伍表)】的数据库操作Service
* @createDate 2024-07-21 15:13:42
*/
public interface TeamService extends IService<Team> {

    long addTeam(TeamAddRequest teamAddRequest, User loginUser);

    boolean quitTeam(Long teamId, User loginUser);

    boolean deleteTeam(Long teamId, User loginUser);

    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);
    Team getTeamById(Long teamId);

    public boolean joinTeam(Long teamId, String password, User loginUser);

    List<TeamVO> listTeams(User loginUser);
}
