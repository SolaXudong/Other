package com.xu.tt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.xu.tt.dto.User;
import com.xu.tt.util.mybatis.MyMapper;

public interface UserMapper extends MyMapper<User> {

	@Select("<script>" + "select * from user" + "</script>")
	List<Map<String, Object>> selectMap();

	@Select("<script>" + "select * from user" + "</script>")
	List<User> selectObj();

	@Insert("<script>" //
			+ "insert into user(id, name, age, birth)" //
			+ " values" //
			+ "<foreach collection=\"list\" item=\"data\" separator=\",\">" //
			+ "(#{data.id}, #{data.name}, #{data.age}, #{data.birth})" //
			+ "</foreach>" //
			+ "</script>")
	void insertListCustom(List<User> list);

}