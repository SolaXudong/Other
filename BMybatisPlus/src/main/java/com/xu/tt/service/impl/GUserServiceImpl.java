package com.xu.tt.service.impl;

import com.xu.tt.entity.GUser;
import com.xu.tt.mapper.GUserMapper;
import com.xu.tt.service.IGUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author XuDong
 * @since 2020-11-29
 */
@Service
public class GUserServiceImpl extends ServiceImpl<GUserMapper, GUser> implements IGUserService {

}
