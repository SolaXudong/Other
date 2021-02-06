package com.xu.tt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xu.tt.dao.UserDao;
import com.xu.tt.dto.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-02-06 21:59:41
 */
@Slf4j
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User getOne(Integer id) {
		log.info("##### service getOne");
		User user = userDao.getOne(id);
		return user;
	}

}
