package com.xu.tt;

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
	public void testSelectOne() {
		log.info("##### select one");
		GUser u1 = userService.getById("763");
		log.info("##### {}", u1);
		GUser u2 = userService.getById(763);
		log.info("##### {}", u2);
		QueryWrapper<GUser> sparamU = new QueryWrapper<>();
		sparamU.eq("user_nm", "sola1");
		GUser u3 = userService.getOne(sparamU);
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
		sparamU.or(t -> t.eq("user_nm", "sola1"));
//		sparamU.and(t -> t.eq("user_nm", "xxx"));
		List<GUser> list1 = userService.list(sparamU);
		log.info("##### {}", list1.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
	}

	@Test
	public void testPage() {
		log.info("##### select page");
		QueryWrapper<GUser> sparamU = new QueryWrapper<>();
		sparamU.eq("group_id", 2);
		PageHelper.startPage(2, 5, "user_nm");
		List<GUser> list1 = userService.list(sparamU);
		log.info("##### {}", list1.stream().map(t -> t.getUserNm()).collect(Collectors.toList()));
	}

	@Test
	public void testInsert() {
		log.info("##### select insert");
		GUser dto = GUser.builder().password("东").build();
		boolean b1 = userService.save(dto);
		log.info("##### {}", b1);
	}

	@Test
	public void testUpdate() {
		log.info("##### select update");
		UpdateWrapper<GUser> uparamU = new UpdateWrapper<>();
		uparamU.set("password", null).set("user_nm", "user_nm001").eq("id", "762");
		boolean b1 = userService.update(uparamU);
		log.info("##### {}", b1);
	}

	@Test
	public void testDelete() {
		log.info("##### select delete");
		UpdateWrapper<GUser> dparamU = new UpdateWrapper<>();
		dparamU.eq("password", "东");
		userService.remove(dparamU);
	}

}
