package com.xu.tt;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author XuDong 2020-01-20 22:53:47
 */
@Slf4j
public class TT {

	/**
	 * @tips LOOK
	 */
	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		/***/
//		testThreadPool();
		/***/
		log.info("##### cost : {}s", (System.currentTimeMillis() - cost) / 1000F);
	}

	/**
	 * @tips 测试
	 */
	@Test
	public void test() {
		long cost = System.currentTimeMillis();
		/***/
		/** 正则替换 */
		System.out.println("15701222612".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		/** 随机选N个 */
		System.out.println(RandomStringUtils.random(4, "0123456789"));
		/** 读配置文件 */
		ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
		System.out.println(resourceBundle.getString("logging.file"));
		/** 转数字 */
		System.out.println(String.format("%03d", 1));
		/** 加密（不可逆） */
//		System.out.println(BCrypt.hashpw("Shijie526", BCrypt.gensalt(10)));
		/** 求和 */
		System.out.println(LongStream.rangeClosed(1, 3_0000_0000).parallel().reduce(0, Long::sum));
		/***/
		log.info("##### cost : {}s", (System.currentTimeMillis() - cost) / 1000F);
	}

	/**
	 * @tips 线程池
	 */
	public static void testThreadPool() {
		/** 初始化 */
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(5);
		pool.setMaxPoolSize(10);
		pool.setQueueCapacity(8);
		pool.setKeepAliveSeconds(60);
		pool.setThreadNamePrefix("Test-");
		pool.initialize();
		/** 模拟从线程池取线程，取的数量超出线程池最大数量 */
		AtomicInteger _successCount = new AtomicInteger(0);
		AtomicInteger _failCount = new AtomicInteger(0);
		for (int i = 1; i <= 100; i++) {
			boolean over = false;
			while (!over) {
				if (pool.getActiveCount() >= pool.getMaxPoolSize())
					continue;
				try {
					pool.execute(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(100);
							} catch (Exception e) {
							}
						}
					});
					log.info("##### {}", _successCount.incrementAndGet());
					over = true;
				} catch (Exception e) {
					log.info("##### 没抢到资源，再接再励", e.getMessage());
					_failCount.addAndGet(1);
				}
			}
		}
		log.info("##### over failCount-{}", _failCount.get());
		pool.shutdown();
	}

}
