package com.axr.starrybackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.axr.starrybackend.model.domain.UserTeam;
import com.axr.starrybackend.service.UserTeamService;
import com.axr.starrybackend.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author axr
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-07-21 15:27:32
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




