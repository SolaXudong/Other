package com.xu.tt.service;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xu.tt.dao.UserMapper;
import com.xu.tt.dto.User;
import com.xu.tt.pub.datasource.DBContextHolder;
import com.xu.tt.pub.datasource.RoutingDB;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public List<User> getUserList() throws Exception {
		List<User> list = ListUtils.emptyIfNull(userMapper.selectAll());
		return list;
	}

	@RoutingDB(DBContextHolder.DBType.MASTER)
	public List<User> getUserList1() throws Exception {
		List<User> list = ListUtils.emptyIfNull(userMapper.selectAll());
		return list;
	}

	@RoutingDB(DBContextHolder.DBType.SLAVE)
	public List<User> getUserList2() throws Exception {
		List<User> list = ListUtils.emptyIfNull(userMapper.selectAll());
		return list;
	}

}
