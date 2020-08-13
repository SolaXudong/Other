package com.xu.tt.service;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xu.tt.dao.UserMapper;
import com.xu.tt.dto.User;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public List<User> getUserList() {
		List<User> list = ListUtils.emptyIfNull(userMapper.selectAll());
		return list;
	}

}
