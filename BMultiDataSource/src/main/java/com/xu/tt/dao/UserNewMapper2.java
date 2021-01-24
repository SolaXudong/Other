package com.xu.tt.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

import com.xu.tt.dto.UserNew;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.additional.insert.InsertListProvider;

public interface UserNewMapper2 extends InsertListMapper<UserNew> {

	@Override
	@InsertProvider(type = InsertListProvider.class, method = "dynamicSQL")
	public int insertList(List<? extends UserNew> recordList);

}