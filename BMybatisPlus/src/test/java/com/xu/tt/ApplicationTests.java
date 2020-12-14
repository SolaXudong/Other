package com.xu.tt;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.xu.tt.entity.GUser;
import com.xu.tt.mapper.GUserMapper;

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

	@Test
	public void testSelectOne() {
		log.info("##### select one");
		GUser u1 = userMapper.selectById("763");
		log.info("##### {}", u1);
		GUser u2 = userMapper.selectById(763);
		log.info("##### {}", u2);
		QueryWrapper<GUser> sparamU = new QueryWrapper<>();
		sparamU.eq("user_nm", "sola1");
		GUser u3 = userMapper.selectOne(sparamU);
		log.info("##### {}", u3);
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
		log.info("##### select custom sql");
//		List<Map<String, Object>> list1 = userMapper.selectUser1();
//		log.info("##### {}", list1.stream().map(t -> t.get("user_nm")).collect(Collectors.toList()));
		List<GUser> list2 = userMapper.selectUser2();
		log.info("##### {}", list2.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
//		List<GUser> list3 = userMapper.selectUser3();
//		log.info("##### {}", list3.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
//		log.info("##### {}", userMapper.updateC(ImmutableMap.of("id", 402, "name", "moumou")));
	}

}
