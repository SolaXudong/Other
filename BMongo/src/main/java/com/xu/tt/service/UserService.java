package com.xu.tt.service;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xu.tt.dto.User;

/**
 * @author XuDong 2020-10-20 02:40:39
 * @tips 代码使用参考：https://www.cnblogs.com/williamjie/p/11193175.html
 */
@Service
public class UserService {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * @tips LOOK 添加
	 */
	public User save(User user) {
		return mongoTemplate.save(user);
	}

	/**
	 * @tips LOOK 修改
	 */
	public int update(Query query, Update update) {
		UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
		return result != null ? (int) result.getMatchedCount() : 0;
	}

	/**
	 * @tips LOOK 修改
	 */
	public int update(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update().set("name", user.getName()).set("passWord", user.getPwd());
		// 更新查询返回结果集的第一条
		UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
		// 更新查询返回结果集的所有
//		mongoTemplate.updateMulti(query, update, User.class);
		return result != null ? (int) result.getMatchedCount() : 0;
	}

	/**
	 * @tips LOOK 删除
	 */
	public int delete(Query query) {
		DeleteResult result = mongoTemplate.remove(query, User.class);
		return result != null ? (int) result.getDeletedCount() : 0;
	}

	/**
	 * @tips LOOK 查询单个
	 */
	public User find(Query query) {
		return mongoTemplate.findOne(query, User.class);
	}

	/**
	 * @tips LOOK 查询多个
	 */
	public List<User> findList(Query query) {
		return ListUtils.emptyIfNull(mongoTemplate.find(query, User.class));
	}

	/**
	 * @tips LOOK 查询多个
	 */
	public List<Document> findListDocument(Query query) {
		return ListUtils.emptyIfNull(mongoTemplate.find(query, Document.class, "t_case_info"));
	}

	/**
	 * @tips LOOK 查数量
	 */
	public int count(Query query) {
		return (int) mongoTemplate.count(query, User.class);
	}

	/**
	 * @tips LOOK 分页
	 */
	public List<User> page(Query query, int pageNum, int pageSize) {
		query = query.skip((pageNum - 1) * pageSize).limit(pageSize);
		return ListUtils.emptyIfNull(mongoTemplate.find(query, User.class));
	}

}
