package com.xu.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.xu.tt.dto.User;
import com.xu.tt.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-10-20 02:45:26
 */
@Slf4j
@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.xu.tt.Application.class)
public class Application {

	@Autowired
	private UserService userService;

	@Test
	public void testMongo() throws Exception {
		long cost = System.currentTimeMillis();
		/***/
		log.info("########## 添加");
		for (int i = 1; i <= 3; i++) {
			User ipu = User.builder().id(1_0000L + i).name("徐_" + i).idCard("410223_" + i).birth(new Date()).build();
//			System.out.println(userService.save(ipu));
		}
		log.info("########## 修改");
		User upu = User.builder() //
				.id(1L) //
//				.idCard("410223_1") //
				.name("徐_1-***") //
				.build();
//		System.out.println(userService.update(upu));
		Query query = new Query(
				Criteria.where("idCard").in(Lists.newArrayList("410223_1,410223_2,410223_3".split(","))));
		Update mupdate = new Update().set("tag", "B");
//		System.out.println(userService.update(query, mupdate));
		log.info("########## 删除");
		Query dpu = new Query(Criteria.where("id").is(1L));
//		System.out.println(userService.delete(dpu));
		log.info("########## 查询单个");
		Query spu = new Query(Criteria.where("id").is(10030L));
//		System.out.println(userService.find(spu));
		log.info("########## 查询多个");
//		Query lpu = new Query(Criteria.where("id").gte(10095L));
//		userService.findList(lpu).stream().forEach(System.out::println);
//		Query lpu = new Query(Criteria.where("case_no").regex("^CESHI-WLD-000097.*$"));
//		List<Document> rs = userService.findListDocument(lpu);
//		rs.stream().forEach(System.out::println);
//		JSONObject _obj = JSONObject.parseObject(JSONObject.toJSONString(rs.get(0)));
//		log.info("{}", _obj);
//		log.info("########## size-{}", rs.size());
//		List<Map<String, Object>> rs2 = JSONObject.parseObject(JSONArray.toJSONString(rs),
//				new TypeReference<List<Map<String, Object>>>() {
//				});
//		log.info("########## rs2-{}", rs2);
		log.info("########## 数量");
		Query cpu = new Query(Criteria.where("id").gte(10095L));
//		System.out.println(userService.count(cpu));
		log.info("########## 分页");
		Query ppu = new Query(Criteria.where("id").gte(10090L));
//		userService.page(ppu, 1, 2).stream().forEach(System.out::println);
//		userService.page(ppu, 3, 2).stream().forEach(System.out::println);
		/***/
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}
