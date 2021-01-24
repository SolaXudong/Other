package com.xu.tt.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.tt.entity.UserNew;
import com.xu.tt.mapper.UserNewMapper;
import com.xu.tt.service.UserNewService;

@Service
public class UserNewServiceImpl extends ServiceImpl<UserNewMapper, UserNew> implements UserNewService {

}
