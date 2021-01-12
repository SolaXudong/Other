package com.xu.tt.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.tt.entity.GUser;
import com.xu.tt.entity.User;

/**
 * @tips 用户信息表
 */
public interface GUserMapper extends BaseMapper<GUser> {

	List<Map<String, Object>> selectUser1();

	List<GUser> selectUser2();

//	@Select("<script>" + "select * from g_user" + "</script>")
	List<GUser> selectUser3();

	int updateC(Map<String, Object> map);

	void insertListCustom(List<User> list);

}
