package com.xu.tt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.tt.entity.GUser;

/**
 * @tips 用户信息表
 */
public interface GUserMapper extends BaseMapper<GUser> {

	List<Map<String, Object>> selectUser1();

	List<GUser> selectUser2();

//	@Select("select * from g_user")
	List<GUser> selectUser3();

	int updateC(Map<String, Object> map);

}
