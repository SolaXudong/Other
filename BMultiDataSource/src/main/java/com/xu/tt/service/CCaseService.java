package com.xu.tt.service;

import java.util.List;

import com.xu.tt.dto.CCase;

import tk.mybatis.mapper.entity.Example;

public interface CCaseService {

	CCase insertCCase(CCase dto);

	CCase updateCCase(CCase dto);

	int deleteCCase(CCase dto);

	List<CCase> selectCCaseByCondition(CCase dto);

	List<CCase> selectCCaseByExample(Example ex);

	CCase selectOneCCaseByCondition(CCase dto);

	int selectCountCCaseByCondition(CCase dto);

	CCase selectOneCCaseByExample(CCase dto);

	CCase updateCCaseByExample(CCase dto, Example ex);

}
