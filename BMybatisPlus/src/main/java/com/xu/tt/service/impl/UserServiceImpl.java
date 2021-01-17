package com.xu.tt.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.tt.entity.User;
import com.xu.tt.mapper.UserMapper;
import com.xu.tt.service.IUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XuDong
 * @since 2021-01-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
