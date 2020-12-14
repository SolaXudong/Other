package com.xu.tt.service;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import com.xu.tt.common.Const;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-14 22:47:49
 * @tips 消费者
 */
@Slf4j
public class MyConsumer {

	public static void main(String[] args) throws InterruptedException, MQClientException {
		// 实例化消费者
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
		// 设置NameServer的地址
		consumer.setNamesrvAddr(Const.IP + ":" + Const.PORT);
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		try {
			// 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
			// 订阅PushTopic下Tag为push的消息("PushTopic", "push")
			consumer.subscribe("TopicTest", "*");
			// 注册回调实现类来处理从broker拉取回来的消息
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
						ConsumeConcurrentlyContext context) {
					System.out.printf("##### %s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
					Message msg = list.get(0);
					String topic = msg.getTopic();
					String body = new String(msg.getBody());
					String keys = msg.getKeys();
					String tags = msg.getTags();
					log.info("##### topic-{}, body-{}, keys-{}, tags-{}", topic, body, keys, tags);
					// 标记该消息已经被成功消费
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			// 启动消费者实例
			consumer.start();
			System.out.printf("##### Consumer Started.%n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
