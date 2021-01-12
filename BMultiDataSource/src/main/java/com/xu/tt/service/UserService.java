package com.xu.tt.service;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xu.tt.dao.UserMapper;
import com.xu.tt.dto.User;
import com.xu.tt.pub.datasource.DBContextHolder;
import com.xu.tt.pub.datasource.RoutingDB;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public User selectOne() {
		return userMapper.selectOne(User.builder().id(1).build());
	}

	public void insert(User dto) throws Exception {
		userMapper.insert(dto);
	}

	public void insertList(List<User> list) throws Exception {
		userMapper.insertList(list);
	}

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

	@Transactional
	@RoutingDB(DBContextHolder.DBType.SLAVE)
	public void transaction() throws Exception {
		int rs = userMapper.updateByPrimaryKeySelective(User.builder().id(2).age(1).build());
		log.info("########## update-{}", rs);
//		int a = 1 / 0;
	}

	@Transactional
	@RoutingDB(DBContextHolder.DBType.MASTER)
	public void transactionMultiP1() throws Exception {
		int rs = userMapper.updateByPrimaryKeySelective(User.builder().id(2).age(1).build());
		log.info("########## update 1: {}", rs);
	}

	@Transactional
	@RoutingDB(DBContextHolder.DBType.SLAVE)
	public void transactionMultiP2() throws Exception {
		int rs = userMapper.updateByPrimaryKeySelective(User.builder().id(2).age(2).build());
		log.info("########## update 2: {}", rs);
	}

}
