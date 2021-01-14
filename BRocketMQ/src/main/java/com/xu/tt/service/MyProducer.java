package com.xu.tt.service;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.xu.tt.common.Const;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-12-14 22:40:33
 * @tips 生产者
 * @tips 代码参考：https://github.com/apache/rocketmq/blob/master/docs/cn/RocketMQ_Example.md
 */
@Slf4j
public class MyProducer {

	public static void main(String[] args) throws Exception {
		sendSyncMSG();
//		sendAsyncMSG();
//		sendOnewayMSG();
	}

	/**
	 * @tips LOOK 发送同步消息（这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知）
	 */
	public static void sendSyncMSG() throws Exception {
		// 实例化消息生产者Producer
		DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
		// 设置NameServer的地址
		producer.setNamesrvAddr(Const.MQ_ADDR);
		try {
			// 启动Producer实例
			producer.start();
			for (int i = 1; i <= 10; i++) {
				// 创建消息，并指定Topic，Tag和消息体
				Message msg = new Message(
						/* Topic */
						"TopicTest",
						/* Tag */
						"TagA",
						/* Message body */
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				// 发送消息到一个Broker
				SendResult sendResult = producer.send(msg);
				// 通过sendResult返回消息是否成功送达
				System.out.printf("##### %s%n", sendResult);
				log.info("##### id-{}, status-{}", sendResult.getMsgId(), sendResult.getSendStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 如果不再发送消息，关闭Producer实例。
			producer.shutdown();
		}
	}

	/**
	 * @tips LOOK 发送异步消息（异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应）
	 */
	public static void sendAsyncMSG() throws Exception {
		// 实例化消息生产者Producer
		DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
		// 设置NameServer的地址
		producer.setNamesrvAddr(Const.MQ_ADDR);
		// 启动Producer实例
		producer.start();
		producer.setRetryTimesWhenSendAsyncFailed(0);
		int messageCount = 100;
		// 根据消息数量实例化倒计时计算器
		final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
		for (int i = 0; i < messageCount; i++) {
			final int index = i;
			// 创建消息，并指定Topic，Tag和消息体
			Message msg = new Message("TopicTest", "TagA", "OrderID188",
					"Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
			// SendCallback接收异步返回结果的回调
			producer.send(msg, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {
					System.out.printf("##### %-10d OK %s %n", index, sendResult.getMsgId());
				}

				@Override
				public void onException(Throwable e) {
					System.out.printf("##### %-10d Exception %s %n", index, e);
					e.printStackTrace();
				}
			});
		}
		// 等待5s
		countDownLatch.await(5, TimeUnit.SECONDS);
		// 如果不再发送消息，关闭Producer实例。
		producer.shutdown();
	}

	/**
	 * @tips LOOK 发送单向消息（这种方式主要用在不特别关心发送结果的场景，例如日志发送）
	 */
	public static void sendOnewayMSG() throws Exception {
		// 实例化消息生产者Producer
		DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
		// 设置NameServer的地址
		producer.setNamesrvAddr(Const.MQ_ADDR);
		// 启动Producer实例
		producer.start();
		for (int i = 0; i < 100; i++) {
			// 创建消息，并指定Topic，Tag和消息体
			Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
					("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
			);
			// 发送单向消息，没有任何返回结果
			producer.sendOneway(msg);
		}
		// 如果不再发送消息，关闭Producer实例。
		producer.shutdown();
	}

}
