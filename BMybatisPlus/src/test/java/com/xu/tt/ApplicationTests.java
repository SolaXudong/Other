package com.xu.tt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xu.tt.entity.GUser;
import com.xu.tt.mapper.GUserMapper;
import com.xu.tt.service.IGUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-11-29 20:00:25
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTests {

	@Autowired
	private GUserMapper userMapper;
	@Autowired
	private IGUserService userService;

	@Test
	public void testSelect() {
		log.info("##### select");
		List<GUser> list = userMapper.selectList(null);
		System.out.println(list.size());
		List<GUser> list2 = userService.list();
		System.out.println(list2.size());
	}

}
