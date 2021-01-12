package com.xu.test;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2021-01-13 00:38:59
 * @tips 官网API：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.10/java-rest-high.html
 * @tips 中文官 网：https://elasticsearch.cn
 * @tips 代码参考：https://www.cnblogs.com/laoqing/p/11693144.html
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.xu.tt.Application.class)
public class Application {

	@Test
	public void testES() {
		long cost = System.currentTimeMillis();
		/***/
		try {
			/** init */
			RestHighLevelClient client = new RestHighLevelClient(
					RestClient.builder(new HttpHost("localhost", 9200, "http"))); // https://47.96.26.156:9200
			log.info("##### init: {}", client.toString());
			/** insert */
			/** get */
			GetRequest getRequest = new GetRequest("posts", "1");
			GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			log.info("##### get: {}", getResponse.getSource());
			/** update */
//			UpdateRequest updateRequest = new UpdateRequest("posts", "1");
//			UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//			log.info("##### update: {}", updateResponse.getResult());
			/** delete */
//			DeleteRequest deleteRequest = new DeleteRequest("posts", "1");
//			DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
//			log.info("##### delete: {}", deleteResponse.getResult());
			/** close */
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/***/
		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

}
