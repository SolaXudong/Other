package com.xu.tt.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.tt.entity.GUser;

/**
 * @tips 用户信息表
 */
public interface GUserMapper extends BaseMapper<GUser> {

	List<Map<String, Object>> selectUser1();

	List<GUser> selectUser2();

}
