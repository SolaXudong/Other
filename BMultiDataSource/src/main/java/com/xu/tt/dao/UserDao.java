package com.xu.tt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.xu.tt.dto.User;

public interface UserDao {

	@Select("<script>" + "select * from user limit 1" + "</script>")
	List<Map<String, Object>> selectMap();

	@Select("<script>" + "select * from user limit 1" + "</script>")
	List<User> selectObj();

	@Insert("<script>" //
			+ "insert into user()" //
			+ " values" //
			+ "<foreach collection=\"list\" item=\"data\" separator=\",\">" //
			+ "(#{data.id}, #{data.name}, #{data.age}, #{data.birth})" //
			+ "</foreach>" //
			+ "</script>")
	int insertListCustom(List<User> list);

}