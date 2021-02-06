package com.xu.tt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xu.tt.dto.User;

/**
 * @author XuDong 2021-02-06 21:58:56
 */
public interface UserDao extends JpaRepository<User, Integer> {

	List<User> findByIdBetween(int min, int max);

	/** 自定义HQL */
	@Query("select user from User user where id < 3")
	List<User> findUserByIdLt();

}
