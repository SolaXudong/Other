package com.xu.tt.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

import com.xu.tt.dto.User;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.additional.insert.InsertListProvider;

public interface UserMapper2 extends InsertListMapper<User> {

	@Override
	@InsertProvider(type = InsertListProvider.class, method = "dynamicSQL")
	public int insertList(List<? extends User> recordList);

}