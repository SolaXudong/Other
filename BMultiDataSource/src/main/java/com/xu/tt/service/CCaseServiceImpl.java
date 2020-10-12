package com.xu.tt.service;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xu.tt.dao.CCaseMapper;
import com.xu.tt.dto.CCase;

import tk.mybatis.mapper.entity.Example;

@Service
public class CCaseServiceImpl implements CCaseService {

	@Autowired
	private CCaseMapper CCaseMapper;

	@Override
	public CCase insertCCase(CCase dto) {
		CCaseMapper.insertSelective(dto);
		return dto;
	}

	@Override
	public CCase updateCCase(CCase dto) {
		CCaseMapper.updateByPrimaryKeySelective(dto);
		return dto;
	}

	@Override
	public CCase updateCCaseByExample(CCase dto, Example ex) {
		CCaseMapper.updateByExampleSelective(dto, ex);
		return dto;
	}

	@Override
	public int deleteCCase(CCase dto) {
		int result = CCaseMapper.delete(dto);
		return result;
	}

	@Override
	public List<CCase> selectCCaseByCondition(CCase dto) {
		List<CCase> list = ListUtils.emptyIfNull(CCaseMapper.select(dto));
		return list;
	}

	@Override
	public CCase selectOneCCaseByCondition(CCase dto) {
		return CCaseMapper.selectOne(dto);
	}

	@Override
	public CCase selectOneCCaseByExample(CCase dto) {
		return CCaseMapper.selectOneByExample(dto);
	}

	@Override
	public List<CCase> selectCCaseByExample(Example ex) {
		List<CCase> list = ListUtils.emptyIfNull(CCaseMapper.selectByExample(ex));
		return list;
	}

	@Override
	public int selectCountCCaseByCondition(CCase dto) {
		return CCaseMapper.selectCount(dto);
	}

}
