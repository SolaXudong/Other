package com.xu.tt.pub.hbase.example;
//package com.tarena.elearning.web;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import kafka.producer.ProducerConfig;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.client.HBaseAdmin;
//import org.apache.hadoop.hbase.client.HTablePool;
//
//import com.alipay.config.AlipayConfig;
//import com.tarena.elearning.pay.model.PayType;
//
//public class LearningWeb {
//
//	private static LearningWeb instance = new LearningWeb();
//	public static Map<String, String> keys;// 表管理类
//	public static HBaseAdmin HBASE_ADMIN;// 表管理类
//	public static HTablePool HBASE_POOL;// 连接池
//	public static long HBASE_WRITE_BUFFER;// 批量写的buffer大小
//
//	// public static DMSDBManager DMS_DBManager ; //redisDBManager
//	// public static DMSCacheDB userDB ; //redisDBManager
//	// public static DMSCacheTable userTable ; //USER
//	// public static DMSCacheTable loginTable ;//保证单点登录LOGIN
//	// public static DMSCacheTable checkNameTable ; //验证用户名CN
//	// public static DMSCacheTable valCodeTable ; //用于验证码
//
//	public static Redis JEDIS;
//
//	public static ProducerConfig PRODUCERCONFIG;// kafka配置producer
//	public final static String NOTETOPIC = "note-topic";// kafka配置topic
//	public final static String NOTEBOOKTOPIC = "notebook-topic";// kafka配置topic
//	public final static String MOVENOTETOPIC = "movenote-topic";// kafka配置topic
//	public final static String ELEARNINGTOPIC = "elearning-topic";// kafka配置topic
//	public final static Integer PARTITION = 4;// 创建4个partition的主题“test-topic”,允许4线程消耗
//	public final static String TMOOC_REG_TOPIC = "tmooc-reg-topic";// kafka配置topic,注册逻辑topic
//	// public static Producer<String, String> PRODUCER;// kafka
//	// 生产者连接初始化-内包含ProducerPool
//	public static ProducerPool<String, String> PRODUCER; // 使用common-pool2 封装连接池
//
//	public static final String ALIPAY_PARTENR = "partner"; // 支付宝PARTNER
//	public static final String ALIPAY_KEY = "key"; // 支付宝KEY
//	public static String ALIPAY_RETURN_URL; // 会员购买 :支付宝通知页面
//	public static String ALIPAY_NOTIFY_URL; // 会员购买 :支付宝异步通知商户交易状态页面
//	public static String ALIPAY_RETURN_URL_O2O; // 职业课程购买 : 支付宝同步步通知商户交易状态页面
//	public static String ALIPAY_NOTIFY_URL_O2O; // 职业课程购买 : 支付宝异步通知商户交易状态页面
//	public static final String ALIPAY_SHOW_URL = "show_url"; // 商品地址
//	public static final String ALIPAY_SELL_MAIL = "sell_mail"; // 卖家支付宝户名
//	public static final String PRICE = "price"; // 课程单价 单月
//	public static String ALIPAY_RETURN_TV; // 技术课程购买 : 支付宝同步通知商户交易状态页面
//	public static String ALIPAY_NOTIFY_TV; // 技术课程购买 : 支付宝异步通知商户交易状态页面
//	public static String ALIPAY_RETURN_LIVE;// 直播课程购买 : 支付宝同步通知商户交易状态页面
//	public static String ALIPAY_NOTIFY_LIVE;// 直播课程购买 : 支付宝异步通知商户交易状态页面
//	public static String ALIPAY_RETURN_SERIES; // 系列课课程购买 : 支付宝同步通知商户交易状态页面
//	public static String ALIPAY_NOTIFY_SERIES; //系列课程购买 : 支付宝异步通知商户交易状态页面
//	
//	// 笔记信息
//	/** 笔记表名 **/
//	public static final String NOTE_TABLE_NAME = "n";// 表名
//	/** 笔记列族1：笔记信息 **/
//	public static final String NOTE_FAMLIY_NOTEINFO = "ni";// 列族1：笔记信息
//	/** 笔记列1：笔记名字 **/
//	public static final String NOTE_NOTEINFO_CLU_NOTENAME = "nn";// 列1：笔记名字
//	/** 笔记列2：创建时间 **/
//	public static final String NOTE_NOTEINFO_CLU_CREATETIME = "ct";// 列2：创建时间
//	/** 笔记列3：笔记状态 **/
//	public static final String NOTE_NOTEINFO_CLU_STATUS = "st";// 列3：笔记状态
//	/** 笔记列族2：笔记内容 **/
//	public static final String NOTE_FAMLIY_CONTENTINFO = "ci";// 列族2：笔记内容
//	/** 笔记列1：笔记内容 **/
//	public static final String NOTE_CONTENTINFO_CLU_CONTENT = "c";// 列1：笔记内容
//	public static String FILE_DIR; // kafka临时文件存放路径
//
//	public static String ACTIVATE_UTL;// 注册邮箱验证
//	public static String ACTIVATE_TITLE;// 注册邮箱头部
//	public static String SEM_TOPIC = "tmooc-sem-topic";
//	
//	/** 用户头像目录 **/
//	public static String DIR_HEADIMG_CA;
//	public static String DIR_HEADIMG;
//	private LearningWeb() {
//		keys = new HashMap<String, String>();
//		// 加载配置文件
//		ClassLoader loader = LearningWeb.class.getClassLoader();
//		Properties config = new Properties();
//		try {
//			config = loadfile(loader, "config.properties");
//			String tmpKey = "";
//			for (Object key : config.keySet()) {
//				tmpKey = (String) key;
//				if (StringUtils.isNotEmpty(tmpKey))
//					LearningWeb.keys.put(tmpKey, config.getProperty(tmpKey));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			// 用户注册,邮箱验证
//			LearningWeb.ACTIVATE_UTL = config.getProperty("activate_url");
//			LearningWeb.ACTIVATE_TITLE = config.getProperty("activate_title");
//			//用户头像保存目录
//			DIR_HEADIMG_CA = config.getProperty("DIR_HEADIMG_CA");
//			DIR_HEADIMG = config.getProperty("DIR_HEADIMG");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		//video 相关配置
//		try {
//			JedisUtil.VIDEO_FREE = config.getProperty(JedisUtil.VIDEO_FREE);
//			JedisUtil.VIDEO_O2O = config.getProperty(JedisUtil.VIDEO_O2O);
//			JedisUtil.VIDEO_TTS = config.getProperty(JedisUtil.VIDEO_TTS);
//			JedisUtil.VIDEO_VIP = config.getProperty(JedisUtil.VIDEO_VIP);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		// HBase配置
//		try {
//			LearningWeb.HBASE_WRITE_BUFFER = Long.parseLong(config
//					.getProperty("hbase_writebuffer"));
//			Configuration hbase_config = HBaseConfiguration.create();
////			hbase_config.set("hbase.zookeeper.quorum",
////					config.getProperty("hbase_zookeeper_quorum"));
////			hbase_config.set("hbase.zookeeper.property.clientPort",
////					config.getProperty("hbase_zookeeper_port"));
//			//LearningWeb.HBASE_ADMIN = new HBaseAdmin(hbase_config);
//			int hbase_pool_size = Integer.parseInt(config.getProperty("hbase_pool_size"));
//			LearningWeb.HBASE_POOL = new HTablePool(hbase_config, hbase_pool_size);
//			//LearningWeb.HBASE_POOL = HConnectionManager.createConnection(hbase_config);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// redis配置
//		try {
//			// String redis_ip = config.getProperty("redis_ip");
//			// int redis_port =
//			// Integer.parseInt(config.getProperty("redis_port"));
//			// LearningWeb.DMS_DBManager = DataCenter.newDMSInstance(
//			// Constant.CACHE, redis_ip+":"+redis_port);
//			// LearningWeb.userDB = LearningWeb.DMS_DBManager.openCacheDB("E");
//			// LearningWeb.checkNameTable = LearningWeb.userDB.getTable("CN");
//			// LearningWeb.userTable = LearningWeb.userDB.getTable("USER");
//			// LearningWeb.loginTable = LearningWeb.userDB.getTable("LOGIN");
//			// LearningWeb.valCodeTable =
//			// LearningWeb.userDB.getTable("VALCODE");
////			Properties redis_config = loadfile(loader, "redis.properties");
////			GenericObjectPoolConfig pool = new GenericObjectPoolConfig();
////			pool.setMaxTotal(Integer.valueOf(redis_config
////					.getProperty("redis_pool_MaxTotal")));// 最大连接数
////			pool.setMaxIdle(Integer.valueOf(redis_config
////					.getProperty("redis_pool_MaxIdle"))); // 最大空闲数
////			pool.setMinIdle(Integer.valueOf(redis_config
////					.getProperty("redis_pool_MinIdle"))); // 最小空闲数
////			Set<HostAndPort> haps = new HashSet<HostAndPort>();
////			for (Object obj : redis_config.keySet()) {
////				if (obj.toString().indexOf("redis_cluster") > -1) {
////					String[] cluster = redis_config.getProperty(obj.toString())
////							.split(":");
////					haps.add(new HostAndPort(cluster[0], Integer
////							.valueOf(cluster[1])));
////				}
////			}
//			LearningWeb.JEDIS = new Redis("redis.properties");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		// kafka配置
//		try {
//			Properties p4 = loadfile(loader, "producer.properties");
//			LearningWeb.PRODUCERCONFIG = new ProducerConfig(p4); // 创建配置文件
//			LearningWeb.FILE_DIR = config.getProperty("file_dir");// content保存目录
//			// LearningWeb.PRODUCER = new Producer<String,
//			// String>(LearningWeb.PRODUCERCONFIG);
//			LearningWeb.PRODUCER = new ProducerPool<String, String>(
//					LearningWeb.PRODUCERCONFIG);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 阿里支付配置
//		try {
//			loadAlipayType();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// inital Elastic connections  
//		try {
//			//TODO pleasy 
//			//ESUtils.inital(keys);
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//		}
//		// 邮箱配置
//		// try {
//		// MailUtil.server = config.getProperty("email_server");
//		// MailUtil.port = config.getProperty("email_port");
//		// MailUtil.user = config.getProperty("email_user");
//		// MailUtil.password = config.getProperty("email_password");
//		// MailUtil.from = config.getProperty("email_from");
//		// MailUtil.displayName = config.getProperty("email_displayName");
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// }
//	}
//
//	private static Properties loadfile(ClassLoader loader, String filname)
//			throws IOException {
//		Properties properties = new Properties();
//		InputStream ips = loader.getResourceAsStream(filname);
//		properties.load(ips);
//		return properties;
//	}
//
//	/**
//	 * 重新加载, 阿里支付类型
//	 * 
//	 * @throws IOException
//	 */
//	public static void loadAlipayType() throws IOException {
//		Properties conf = loadfile(LearningWeb.class.getClassLoader(),
//				"config.properties");
//		LearningWeb.ALIPAY_NOTIFY_URL = conf.getProperty("notify_url");
//		LearningWeb.ALIPAY_RETURN_URL = conf.getProperty("alipayReturnUrl");
//		LearningWeb.ALIPAY_NOTIFY_URL_O2O = conf.getProperty("notify_url_o2o");
//		LearningWeb.ALIPAY_RETURN_URL_O2O = conf.getProperty("alipayReturnUrl_o2o");
//		LearningWeb.ALIPAY_NOTIFY_TV = conf.getProperty("notifytv_url");
//		LearningWeb.ALIPAY_RETURN_TV = conf.getProperty("alipayReturnUrl_tv");
//		LearningWeb.ALIPAY_NOTIFY_LIVE = conf.getProperty("notifylive_url");
//		LearningWeb.ALIPAY_RETURN_LIVE = conf.getProperty("alipayReturnUrl_live");
//		LearningWeb.ALIPAY_NOTIFY_SERIES=conf.getProperty("notify_url_series");
//		LearningWeb.ALIPAY_RETURN_SERIES=conf.getProperty("return_url_series");
//		AlipayConfig.log_path=conf.getProperty("log_path");
//	}
//
//	public static List<PayType> locadAlipayType(String paytype) {
//		List<PayType> map = new ArrayList<PayType>();
//		String[] pays = paytype.split("&");
//		for (String pay : pays) {
//			String[] info = pay.split(":");
//			if (info.length == 3)
//				map.add(new PayType(info[0], Double.valueOf(info[2]), Integer
//						.valueOf(info[1])));
//		}
//		return map;
//	}
//	
//
//	public static LearningWeb getInstance() {
//		return instance;
//	}
//
//	public static void main(String[] args) throws IOException {
//		// loadAlipayType();
//		// System.out.println(LearningWeb.ALIPAY_TYPE);
//		System.out.println(LearningWeb.ALIPAY_RETURN_LIVE);
//		System.out.println(LearningWeb.ALIPAY_NOTIFY_LIVE);
//	}
//}
