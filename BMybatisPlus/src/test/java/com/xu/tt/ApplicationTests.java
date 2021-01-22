package com.xu.tt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.xu.tt.entity.GUser;
import com.xu.tt.entity.User;
import com.xu.tt.mapper.GUserMapper;
import com.xu.tt.mapper.UserMapper;
import com.xu.tt.service.IUserService;

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
	private UserMapper uMapper;
	@Autowired
	private IUserService userService;

	@Test
	public void testSelectOne() {
		log.info("##### select one");
		GUser u1 = userMapper.selectById("763");
		log.info("##### u1-{}", u1);
		GUser u2 = userMapper.selectById(1);
		log.info("##### u2-{}", u2);
		QueryWrapper<GUser> sparamU = new QueryWrapper<>();
		sparamU.eq("user_nm", "sola1");
		GUser u3 = userMapper.selectOne(sparamU);
		log.info("##### u3-{}", u3);
		QueryWrapper<GUser> cparamUser = new QueryWrapper<>();
		cparamUser.eq("user_nm", "xiaohongzi");
		Integer count = userMapper.selectCount(cparamUser);
		log.info("##### count-{}", count);
	}

	/**
	 * @tips 无匹配时返回的List对象不为空，是[]，size是0
	 */
	@Test
	public void testSelectList() {
		log.info("##### select list");
		QueryWrapper<GUser> sparamU = new QueryWrapper<>();
		sparamU.and(t -> t.eq("group_id", 2).likeRight("user_nm", "sola").or().eq("user_nm", "sola4"));
//		sparamU.or(t -> t.eq("user_nm", "sola1"));
//		sparamU.and(t -> t.eq("user_nm", "xxx"));
		sparamU.orderByAsc("id");
		PageHelper.startPage(2, 5, false);
		List<GUser> list1 = userMapper.selectList(sparamU);
		log.info("##### {}", list1.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
	}

	@Test
	public void testPage() {
		log.info("##### select page");
		QueryWrapper<GUser> sparamU = new QueryWrapper<>();
		sparamU.eq("group_id", 2);
		PageHelper.startPage(2, 5, "user_nm");
		List<GUser> list1 = userMapper.selectList(sparamU);
		log.info("##### {}", list1.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
		Integer count = userMapper.selectCount(sparamU);
		log.info("##### {}", count);
	}

	@Test
	public void testInsert() {
		log.info("##### select insert");
		GUser dto = GUser.builder().password("东").build();
		int rs = userMapper.insert(dto);
		log.info("##### {}", rs);
	}

	@Test
	public void testUpdate() {
		log.info("##### select update");
		UpdateWrapper<GUser> uparamU = new UpdateWrapper<>();
		uparamU.set("password", null).set("user_nm", "user_nm002").eq("id", "750");
//		int rs = userMapper.update(null, uparamU);
		GUser udtoU = GUser.builder().id(750).userNm("xxx").password(null).build();
		int rs = userMapper.updateById(udtoU);
		log.info("##### {}", rs);
	}

	@Test
	public void testDelete() {
		log.info("##### select delete");
		UpdateWrapper<GUser> dparamU = new UpdateWrapper<>();
		dparamU.eq("password", "东");
		userMapper.delete(dparamU);
	}

	@Test
	public void testCustomSQL() {
		long cost = System.currentTimeMillis();
		log.info("##### select custom sql");
//		List<Map<String, Object>> list1 = userMapper.selectUser1();
//		log.info("##### {}", list1.stream().map(t -> t.get("user_nm")).collect(Collectors.toList()));
//		List<GUser> list2 = userMapper.selectUser2();
//		log.info("##### {}", list2.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
//		List<GUser> list3 = userMapper.selectUser3();
//		log.info("##### {}", list3.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
//		log.info("##### {}", userMapper.updateC(ImmutableMap.of("id", 402, "name", "moumou")));
		ArrayList<User> list = Lists.newArrayList();
		for (int i = 1; i <= 100000; i++)
			list.add(User.builder().name("哈哈_" + i).age(10 + i).birth(new Date()).build());
//		userMapper.insertListCustom(list); // 100000-3.0s)
//		userService.saveBatch(list); // 100000-15s
//		for (User dto : list)
//			uMapper.insert(dto); // 1000-5s
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}
