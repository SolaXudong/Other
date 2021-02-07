package com.xu.tt;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xu.tt.dao.GUserMapper;
import com.xu.tt.dao.UserMapper;
import com.xu.tt.dao.UserMapper2;
import com.xu.tt.dao.UserNewMapper2;
import com.xu.tt.dto.CCase;
import com.xu.tt.dto.GUser;
import com.xu.tt.dto.TExcel;
import com.xu.tt.dto.User;
import com.xu.tt.service.B_UserService;
import com.xu.tt.service.CCaseService;
import com.xu.tt.service.UserService;
import com.xu.tt.util.POI2ReadExcel;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private B_UserService b_userService;
	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;
	@Resource(name = "dynamicDataSource")
	private DataSource dynamicDataSource;

	@Autowired
	private CCaseService caseService;
	@Autowired
	private GUserMapper userMapper;
	@Autowired
	private UserMapper uMapper;
	@Autowired
	private UserMapper2 uMapper2;
	@Autowired
	private UserNewMapper2 userNewMapper2;

	/**
	 * @tips LOOK 测批量添加
	 */
	@Test
	public void testBatchInsert() throws Exception {
		long cost = System.currentTimeMillis();
		long cost2 = System.currentTimeMillis();
		{ // 手写 vs 生成
//			List<User> insertList = Lists.newArrayList();
//			int _total = 100_0000;
//			for (int i = 1; i <= _total; i++) {
//				insertList.add(User.builder().name("哈哈_" + i).age(10 + i).birth(new Date()).build());
//				if (insertList.size() == 1_0000) {
////					System.out.println(uMapper.insertListCustom(insertList)); // 100_0000-20s
//					System.out.println(uMapper2.insertList(insertList)); // 100_0000-21s
////					System.out.println(userNewMapper2.insertList(list));
//					log.info("##### insert cost : {}s, {}/{}", (System.currentTimeMillis() - cost2) / 1000F, i, _total);
//					cost2 = System.currentTimeMillis();
//					insertList.clear();
//				}
//			}
		}
		{ // 不用批量
//			List<User> insertList = Lists.newArrayList();
//			int _total = 1000;
//			for (int i = 1; i <= _total; i++)
//				insertList.add(User.builder().name("哈哈_" + i).age(10 + i).birth(new Date()).build());
//			for (User dto : insertList)
//				userService.insert(dto); // 1000-4s
		}
		log.info("##### cost : {}s", (System.currentTimeMillis() - cost) / 1000F);
	}

	/**
	 * @tips LOOK 测批量查询
	 */
	@Test
	public void testBatchSelect() throws Exception {
		long cost = System.currentTimeMillis();
		long cost2 = System.currentTimeMillis();
		ArrayList<User> dataList = Lists.newArrayList();
		ArrayList<Integer> userIdList = Lists.newArrayList();
		for (int i = 0; i < 20_0000; i++) {
			userIdList.add(i);
			if (userIdList.size() == 1_0000) {
				Example exUser = new Example(User.class);
				exUser.createCriteria().andIn("id", userIdList);
//				int _countUser = uMapper.selectCountByExample(exUser);
//				System.out.println(_countUser);
				List<User> uList = uMapper.selectByExample(exUser);
				dataList.addAll(uList);
				log.info("##### select cost : {}s", (System.currentTimeMillis() - cost2) / 1000F);
				cost2 = System.currentTimeMillis();
				userIdList.clear();
			}
		}
		log.info("##### cost : {}s, size-{}", (System.currentTimeMillis() - cost) / 1000F, dataList.size());
	}

	/**
	 * @tips LOOK 测Service
	 */
	@Test
	public void testService() throws Exception {
		log.info("########## 【test Service from different datasource】");
//		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList()));
//		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList1()));
//		log.info("########## list: {}", JSONObject.toJSON(userService.getUserList2()));
		log.info("########## prepare data");
//		System.out.println(uMapper.selectOne(User.builder().id(1).build()));
//		System.out.println(uMapper.selectMap());
//		System.out.println(uMapper.selectObj());
	}

	/**
	 * @tips LOOK 测数据源
	 */
	@Test
	public void testDataSource() throws Exception {
		log.info("########## 【test DataSource is ok】");
		Connection c1 = masterDataSource.getConnection();
		log.info("########## c1: {}", c1.getMetaData().getURL());
		Connection c2 = slaveDataSource.getConnection();
		log.info("########## c2: {}", c2.getMetaData().getURL());
		Connection c3 = dynamicDataSource.getConnection();
		log.info("########## c3: {}", c3.getMetaData().getURL());
	}

	/**
	 * @tips LOOK 测SQL执行
	 */
	@Test
	public void testQuerySQL() throws Exception {
		log.info("########## 【test QuerySQL】");
//		JdbcTemplate jt1 = new JdbcTemplate(masterDataSource);
//		JdbcTemplate jt2 = new JdbcTemplate(slaveDataSource);
//		List<Map<String, Object>> l1 = ListUtils.emptyIfNull(jt1.queryForList("select id, name from user"));
//		log.info("########## l1: count-{}, data-{}", l1.size(), l1);
//		jt1.execute("update user set name = \"" + LocalDateTime.now() + "\" where id = 2");
//		l1 = ListUtils.emptyIfNull(jt1.queryForList("select id, name from user"));
//		log.info("########## l1: count-{}, data-{}", l1.size(), l1);
//		List<Map<String, Object>> l2 = ListUtils.emptyIfNull(jt2.queryForList("select id, name from user"));
//		log.info("########## l2: count-{}, data-{}", l2.size(), l2);
		GUser dto = GUser.builder().userNm("abc").build();
		Example ex = new Example(GUser.class);
		ex.createCriteria().andEqualTo("id", 762);
		int rs = userMapper.updateByExample(dto, ex);
		log.info("########## {}", rs);
	}

	/**
	 * @tips LOOK 测事务
	 */
	@Test
	public void testTransaction() throws Exception {
		log.info("########## 【test transaction】");
//		userService.transaction();
//		b_userService.transactionMulti();
	}

	/**
	 * @tips LOOK 读Excel，查数据库，写Excel
	 */
	@Test
	public void testExcel() throws Exception {
		// 第一步：读
		LinkedList<TExcel> list = POI2ReadExcel.readExcel();
		log.info("########## 读取完毕");
		list.removeFirst(); // 表头不要
		// 第二步：查
		LinkedList<TExcel> outList = Lists.newLinkedList();
		TreeMap<String, Integer> newMap = Maps.newTreeMap(); // Excel表
		list.stream().forEach(obj -> {
			String _str = obj.getProductV() + "#" + obj.getUName() + "#" + obj.getUIdno();
			if (_str.length() > 0 && !newMap.keySet().contains(_str))
				newMap.put(_str, 1);
		});
		log.info("########## Excel表数据，newMap-{}", newMap.size());
		long cost = System.currentTimeMillis();
		int num = 10_0000;
		int per = 10_0000;
		int total = caseService.selectCountCCaseByCondition(null);
		int loop = total / per + 1;
		log.info("########## total-{}, loop-{}", total, loop);
		for (int i = 1; i <= loop; i++) {
			Example sexc = new Example(CCase.class);
			log.info("########## page-【{}/{}】【{}/{}】", i, loop, i, per);
			PageHelper.startPage(i, per);
			List<CCase> dataList = caseService.selectCCaseByExample(sexc);
			log.info("########## size-{}/{}", dataList.size(), per);
			{
				dataList.stream().forEach(obj -> {
					if (StringUtils.isNotEmpty(obj.getProductV()) && StringUtils.isNotEmpty(obj.getUName())
							&& (StringUtils.isNotEmpty(obj.getUIdno()) && obj.getUIdno().length() == 18)
							&& StringUtils.isNotEmpty(obj.getUPhone())) {
						String _name = "*" + obj.getUName().substring(1);
						String _card = obj.getUIdno().substring(0, 4) + "**" + obj.getUIdno().substring(6, 12) + "****"
								+ obj.getUIdno().substring(16);
						String _str = obj.getProductV() + "#" + _name + "#" + _card;
						if (_str.length() > 0 && newMap.keySet().contains(_str)) {
							// 方案二：多条记录放多行（用户名+身份证，算一条）
							outList.add(TExcel.builder().productV(obj.getProductV()).uName(obj.getUName()).uIdno(_card)
									.uPhone(obj.getUPhone()).caseNo(obj.getCaseNo()).build());
							// 方案一：多条记录放一行，改为放多行（用户名+身份证，算一条）
//							Integer cc = Integer.valueOf(newMap.get(_str));
//							if (cc.intValue() == 1) {
//								newMap.put(_str, cc + 1);
//							} else {
//								outList.forEach(cur -> {
//									String _name2 = "*" + cur.getUName().substring(1);
//									String _card2 = cur.getUIdno().substring(0, 4) + "**"
//											+ cur.getUIdno().substring(6, 12) + "****" + cur.getUIdno().substring(16);
//									String _tmp = "#" + _name2 + "#" + _card2 + "#";
//									if (_str.equals(_tmp) && !cur.getCaseNo().contains(obj.getCaseNo())) {
//										cur.setCaseNo(cur.getCaseNo() + "," + obj.getCaseNo());
//									}
//								});
//								newMap.put(_str, cc + 1);
//							}
						}
					}
				});
				log.info("########## 输出-outList-{}", outList.size());
			}
			num = num + per;
			if (CollectionUtils.isEmpty(dataList))
				break;
		}
		log.info("########## outList-{}", outList.size());
		log.info("########## 查询完毕，cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
		// 第三步：写
		POI2ReadExcel.writeExcel(outList);
		log.info("########## 写入完毕");
	}

}
