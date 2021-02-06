package com.xu.tt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xu.tt.dto.User;

/**
 * @author XuDong 2021-02-06 21:58:56
 */
public interface UserDao extends JpaRepository<User, Integer> {

}
