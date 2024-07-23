package com.axr.starrybackend.controller;

import com.axr.starrybackend.common.BaseResponse;
import com.axr.starrybackend.common.ErrorCode;
import com.axr.starrybackend.common.ResultUtils;
import com.axr.starrybackend.exception.BusinessException;
import com.axr.starrybackend.model.domain.Team;
import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.request.Team.TeamAddRequest;
import com.axr.starrybackend.model.request.Team.TeamJoinRequest;
import com.axr.starrybackend.model.request.Team.TeamUpdateRequest;
import com.axr.starrybackend.model.request.Team.TeamVO;
import com.axr.starrybackend.service.TeamService;
import com.axr.starrybackend.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 队伍相关接口
 * @createDate 2024-07-21 15:13:42
 * @author axr
 */
@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "http://localhost:5173", originPatterns = "*", allowCredentials = "true")
public class TeamController {
    @Resource
    private UserService userService;
    @Resource
    private TeamService teamService;

    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request) {
        if (teamAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (teamAddRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long teamId = teamService.addTeam(teamAddRequest, loginUser);
        return ResultUtils.success(teamId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam(@RequestParam Long teamId, HttpServletRequest request) {
        if (teamId == null || teamId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        boolean result = teamService.deleteTeam(teamId, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest, HttpServletRequest request) {
        if (teamUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        boolean result = teamService.updateTeam(teamUpdateRequest, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request) {
        Long teamId = teamJoinRequest.getTeamId();
        if (teamId == null || teamId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        String password = teamJoinRequest.getPassword();
        boolean result = teamService.joinTeam(teamId, password, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "加入失败");
        }
        return ResultUtils.success(true);
    }


    @PostMapping("/quit")
    public BaseResponse<Boolean> quitTeam(@RequestParam Long teamId, HttpServletRequest request) {
        if (teamId == null || teamId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return ResultUtils.success(teamService.quitTeam(teamId, loginUser));
    }

    @GetMapping("/list")
    public BaseResponse<List<TeamVO>> listTeams(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(teamService.listTeams(loginUser));
    }
}
