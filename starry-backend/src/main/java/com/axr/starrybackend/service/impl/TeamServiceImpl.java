package com.axr.starrybackend.service.impl;

import com.axr.starrybackend.common.ErrorCode;
import com.axr.starrybackend.exception.BusinessException;
import com.axr.starrybackend.mapper.TeamMapper;
import com.axr.starrybackend.model.domain.Team;
import com.axr.starrybackend.model.domain.User;
import com.axr.starrybackend.model.domain.UserTeam;
import com.axr.starrybackend.model.enums.TeamStatusEnum;
import com.axr.starrybackend.model.request.Team.TeamAddRequest;
import com.axr.starrybackend.model.request.Team.TeamUpdateRequest;
import com.axr.starrybackend.model.request.Team.TeamVO;
import com.axr.starrybackend.service.TeamService;
import com.axr.starrybackend.service.UserService;
import com.axr.starrybackend.service.UserTeamService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
* @author axr
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2024-07-21 15:13:42
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{
    @Resource
    private UserService userService;
    @Resource
    private UserTeamService userTeamService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 创建队伍
     * @param teamAddRequest
     * @param loginUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addTeam(TeamAddRequest teamAddRequest, User loginUser) {
        if (StringUtils.isBlank(teamAddRequest.getName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍名称不能为空");
        }
        if (teamAddRequest.getName().length() > 25) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍名称长度不能超过25个字符");
        }
        if (StringUtils.isNotBlank(teamAddRequest.getDescription()) && teamAddRequest.getDescription().length() > 120) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍描述长度不能超过120个字符");
        }
        int maxNum = Optional.ofNullable(teamAddRequest.getMaxNum()).orElse(0);
        if (maxNum < 1 || maxNum > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍人数上限必须在1-100之间");
        }

        // status校验
        int status = Optional.ofNullable(teamAddRequest.getStatus()).orElse(0);
        TeamStatusEnum teamStatusEnum = TeamStatusEnum.getEnumByValue(status);
        if (teamStatusEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍状态不合要求");
        }
        String password = teamAddRequest.getPassword();
        if (TeamStatusEnum.SECRET.equals(teamStatusEnum) && (StringUtils.isBlank(password) || password.length() > 20)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍密码长度不符合要求");
        }
        if (TeamStatusEnum.PUBLIC.equals(teamStatusEnum)) {
            teamAddRequest.setPassword(null);
        }

        // 超时时间 > 当前时间
        LocalDateTime expireTime = teamAddRequest.getExpireTime();
        if (expireTime != null && expireTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍超时时间不能早于当前时间");
        }

        // 最多创建 5 个队伍
        // TODO 同时创建 100 个，加锁
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", loginUser.getId());
        long hasTeamNum = count(queryWrapper);
        if (hasTeamNum >= 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "最多创建5个队伍");
        }

        Team team = new Team();
        Long userId = loginUser.getId();
        BeanUtils.copyProperties(teamAddRequest, team);
        team.setUserId(userId);
        team.setUsername(loginUser.getUsername());
        boolean result = this.save(team);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加队伍失败");
        }

        // 将队伍插入到关系表
        Long teamId = team.getId();
        UserTeam userTeam = new UserTeam();
        userTeam.setUserId(userId);
        userTeam.setTeamId(teamId);
        if (TeamStatusEnum.SECRET.equals(teamStatusEnum)) {
            userTeam.setExpireTime(expireTime);
        }

        result = userTeamService.save(userTeam);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入失败");
        }

        return teamId;
    }

    /**
     * 退出队伍
     * @param teamId
     * @param loginUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean quitTeam(Long teamId, User loginUser) {
        Team team = getTeamById(teamId);
        if (team.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队长不能退出队伍");
        }
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        userTeamQueryWrapper.eq("teamId", teamId);
        userTeamQueryWrapper.eq("userId", loginUser.getId());
        long count = userTeamService.count(userTeamQueryWrapper);
        if (count == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未加入该队伍");
        }
        return userTeamService.remove(userTeamQueryWrapper);
    }

    @Override
    public boolean deleteTeam(Long teamId, User loginUser) {
        Team team = this.getTeamById(teamId);
        // 校验你是不是队伍的队长
        if (!team.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无访问权限");
        }
        // 移除所有加入队伍的关联信息
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        userTeamQueryWrapper.eq("teamId", teamId);
        boolean result = userTeamService.remove(userTeamQueryWrapper);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除队伍关联信息失败");
        }
        // 删除队伍
        return this.removeById(teamId);
    }

    @Override
    public boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser) {
        Long id = teamUpdateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team oldTeam = this.getById(id);
        if (oldTeam == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        TeamStatusEnum statusEnum = TeamStatusEnum.getEnumByValue(teamUpdateRequest.getStatus());
        if (statusEnum.equals(TeamStatusEnum.SECRET) && StringUtils.isBlank(teamUpdateRequest.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能为空");
        }

        if (!oldTeam.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Team updateTeam = new Team();
        BeanUtils.copyProperties(teamUpdateRequest, updateTeam);
        if (updateTeam.getName() != null) {
            if (updateTeam.getName().length() > 25) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍名称长度不能超过25个字符");
            }
        }
        if (updateTeam.getDescription() != null) {
            if (updateTeam.getDescription().length() > 120) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍描述长度不能超过120个字符");
            }
        }
        if (updateTeam.getMaxNum() != null) {
            if (updateTeam.getMaxNum() < 1 || updateTeam.getMaxNum() > 100) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍人数上限必须在1-100之间");
            }
        }
        if (updateTeam.getExpireTime() != null) {
            if (updateTeam.getExpireTime().isBefore(LocalDateTime.now())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍超时时间不能早于当前时间");
            }
        }
        return this.updateById(updateTeam);
    }


    /**
     * 根据 id 获取队伍信息
     * @param teamId
     * @return
     */
    @Override
    public Team getTeamById(Long teamId) {
        if (teamId == null || teamId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = this.getById(teamId);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "队伍不存在");
        }
        return team;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean joinTeam(Long teamId, String password, User loginUser) {
        Team team = this.getTeamById(teamId);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "队伍不存在");
        }

        if (team.getExpireTime() != null && team.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "队伍已过期");
        }

        if (TeamStatusEnum.SECRET.equals(TeamStatusEnum.getEnumByValue(team.getStatus()))) {
            if (StringUtils.isBlank(password)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能为空");
            }
            if (!password.equals(team.getPassword())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
            }
        }



        long userId = loginUser.getId();
        RLock lock = redissonClient.getLock("starry:join_team:");
        try {
            // 抢到锁
            while (true) {
                if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                    QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
                    userTeamQueryWrapper.eq("userId", userId);
                    Long joinNum = userTeamService.count(userTeamQueryWrapper);
                    if (joinNum >= 5) {
                        throw new BusinessException(ErrorCode.PARAMS_ERROR, "最多加入5个队伍");
                    }

                    userTeamQueryWrapper.eq("teamId", teamId);
                    long count = userTeamService.count(userTeamQueryWrapper);
                    if (count > 0) {
                        throw new BusinessException(ErrorCode.PARAMS_ERROR, "已经加入过该队伍");
                    }

                    UserTeam userTeam = new UserTeam();
                    userTeam.setUserId(loginUser.getId());
                    userTeam.setTeamId(teamId);
                    userTeam.setExpireTime(team.getExpireTime());

                    return userTeamService.save(userTeam);
                }
            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendUser error", e);
            return false;
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                // System.out.println("unLock: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }

    @Override
    public List<TeamVO> listTeams(User loginUser) {
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.gt("expireTime", LocalDateTime.now()).or().isNull("expireTime");

        List<Team> teamList = this.list(teamQueryWrapper);
        if (CollectionUtils.isEmpty(teamList)) {
            return new ArrayList<>();
        }
        List<TeamVO> teamVOList = new ArrayList<>();
        QueryWrapper<UserTeam> userTeamQueryWrapper;
        for (Team team : teamList) {
            TeamVO teamVO = new TeamVO();
            BeanUtils.copyProperties(team, teamVO);
            userTeamQueryWrapper = new QueryWrapper<>();
            userTeamQueryWrapper.eq("teamId", team.getId());
            long count = userTeamService.count(userTeamQueryWrapper);
            teamVO.setHasJoinNum((int) count);
            userTeamQueryWrapper.eq("userId", loginUser.getId());
            count = userTeamService.count(userTeamQueryWrapper);
            teamVO.setHasJoin(count > 0);
            teamVOList.add(teamVO);
        }
        return teamVOList;
    }
}




