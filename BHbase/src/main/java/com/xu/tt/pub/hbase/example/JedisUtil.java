package com.xu.tt.pub.hbase.example;
//package com.tarena.elearning.web;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class JedisUtil {
//	public static final String UNIVERSITY_FACULTY="UNIVERSITY_FACULTY_";//存放院校信息
//	public static final String UNIVERSITY_CLUSTER="UNIVERSITY_CLUSTER_";//存放院校课程组信息
//	public static final String UNIVERSITY_COURSE="UNIVERSITY_COURSE_";//存放院校课程信息
//	public static final String UNIVERSITY_VIDEO="UNIVERSITY_VIDEO_";//存放院校视频信息
//	
//	public static String TMOOC_TCTM_CUP="TMOOC-TCTM-CUP";//存放发现杯账号信息
//	public static String TMOOC_TCTM="TMOOC-TCTM";//存放童程的账号信息
//	public static String TMOOC_ROBOT="TMOOC-ROBOT";//存放童创的账号信息
//	public static final String HANXL_VIDEOID= "HANXL-VIDEOID";//用于存属于hanxl帐号的videoId
//	
//	
//	public static final String WEIXIN_USER_BIND= "E-USER-EXPAND-";//用于标识TMOOC账号绑定微信账号的次数（现用于判断赠送周卡）
//	public static final String WEIXIN_USER= "WEIXIN-USER";//用于标识是从微信注册的用户
//	public static final String WEIXIN= "WEIXIN-";//与微信相关
//	public static final String ALLOPENID= "ALLOPENID";//所有关注的openid
//	public static final String WEIXIN_ACTIVITY= "WEIXIN-ACTIVITY-";//与微信活动相关
//	public static final String WEIXIN_ACTIVITY_SEPARATOR= "@#@#@";//与微信活动相关
//	public static final String WEEK_CARD_TYPE= "day-7";//与微信活动相关
//	public static final String ACCESS_TOKEN= "ACCESS_TOKEN";//微信令牌
//	
//	public static final String ONE_HOUR_USER_NUM= "ONE-HOUR-USERNUM-";//保存“编程一小时”填写用户信息 的数目
//	public static final String ONE_HOUR_USERD_ETAIL= "ONE-HOUR-USERDETAIL-";//保存“编程一小时”用户信息HourOfCodeUser
//	public static final String ONE_HOUR_USER = "ONE-HOUR-USER-";//用于标识注册是从http://www.hourofcode.org.cn
//	public static final String USER_NUM = "USER-NUM";//用于存放用户数目
//	public static final String STUDY_BOOK = "STUDY-BOOK-";//存放正在学习的课程(例 ："STUDY-BOOK-"+loginName :List<bookId> 注：存3个月)
//	public static final String FINISH_BOOK = "FINISH-BOOK-";//存放已经学完的课程(例 ："FINISH-BOOK-"+loginName :List<bookId>)
//	
//	public static final String TMOOC_BOOK = "TMOOC-BOOK-";    //存放某类课程（例 TMOOC-BOOK-hot 热门课程）
//	public static final String TMOOC_CLICK = "TMOOC-CLICK-";   //存放某课程点击数(例 ："TMOOC-CLICK-"+bookId :点击数)
//	public static final String TMOOC_STUDY = "TMOOC-STUDY-";   //存放某课程学习人数(例 ："TMOOC-STUDY-"+bookId :学习数)
//	public static final String TMOOC_PWD = "TMOOC-PWD-";   //存放临时密码
//	public static final String TM_ONLINE_O2O_PAY = "O2O-PAY-TYPE"; // tmooc o2o 课程在线支付
//	public static final String TM_ONLINE_SERIES_PAY="SERIES-PAY-TYPE";//tmooc 系列课在线支付
//	
//	public static final String E_user = "E-USER-";   //存放用户，key为用户名
//	public static final String E_login = "E-LOGIN-"; //存放两个键值对 1.单点登录，  loginname-sessionid 2.用于接口传送，sessionid-loginname
//	public static final String E_valcode = "E-CN-";  //存放验证码，key为js传过来的uuid
//	public static final String TM_O2O= "TM-O2OS-";	// 存放用户O2O 购买信息
//	public static final String TMOOC_CARD="TCARD_";  //存放o2o 卡片信息
//	public static final String TTS_login = "TTS-LOGIN-"; //tts8
//	public static final String TTS8_PRE_E = "E#";
//	public static final String TTS8_PRE_P = "P#";
//	public static final String VALIDTIMES = "VALIDTIMES-"; //登录验证次数
//	public static final String VIDEO_BROADCAST="VIDEO_BROADCAST-";//存储视频id信息
//	public static final String TMOOC_CARD_TYPE = "TMOOC_CARD_TYPE-"; //Redis存TMOOC学习卡(帐号删除-卡删掉;卡用过后-卡删掉)
//	
//	
//	public static final String VIDEO_STUDENT_UID="VIDEO_STUDENT_UID-";//存储报名后的学员uid+登录账号
//	public static final String VIDEO_CLASS="VIDEO_CLASS_";//存储实施课程信息 (NUMBER 课程编号)(APPLY 储存已报名学生登录账号)
//	public static final String VIDEO_LIVE="VIDEO_LIVE-";//存储视频直播信息+实施课堂序号
//	public static final String VIDEO_TMOOC_LIVE="VIDEO_TMOOC_LIVE";//存储tmooc直播课程信息+课程编号
//	public static final String VIDEO_APPLY="VIDEO_APPLY-";//存储报名后的登录名，+实时课堂编号
//	public static final String VIDEO_LIVE_DOCUMENT="VIDEO_LIVE_DOCUMENT-";//(存入视频直播的页面信息+课程编号)
//	public static final String VIDEO_LIVE_K="VIDEO_LIVE_K-";//存储k值+学员登录账号
//	public static final String VIDEO_STUDENT="VIDEO_STUDENT-";//根据学生id存直播课程id
//	public static final String VIDEO_APPLY_MAP="VIDEO_APPLY_MAP-";//以map存报名信息  （+课程id  k（登录名） vlue（报名信息） ）
//	public static final String VIDEO_APPLY_SET="VIDEO_APPLY_SET-";//以set形式验证报名是否重复
//	public static final String VIDEO_APPLY_MES="VIDEO_APPLY_MES-";//存储报名提交信息
//	public static final String VIDEO_ID_MAP="VIDEO_VIDEOID_MAP";//直接存放课堂id
//	public static final String VIDEO_CLASSMES_MAP="VIDEO_CLASSMES_MAP-";//存放课堂信息key（EMAIL 邮箱；APPLYSTUDENT 学生报名；CLASSMES 课堂信息）
//	
//	public static final String LIVE_TODAY="LIVE_TODAY-";//存储今日直播（例 ："LIVE_TODAY-"+"2015-12-16"）
//	
//	public static final String VIDEO_CLICK =  "VIDEO-CLICK-";   //存放某视频点击数(例 ："VIDEO-CLICK-"+videoId :点击数)
//	public static final String TMOOC_TEACHER = "TMOOC-TEACHER-"; //存放讲师信息
//	public static final String TEACHER_VAL = "$#@!"; //存放用于分割VALUE值的字符串常量
//	/*
//	 * @Modify by bjchenxx@tarena.com.cn
//	 */
//	public static final String TV_SIG = "TV-SIG-"; //存储用户购买单个课程信息 -  TV-SIG-[Login_name] - 存为Hash 格式
//	public static final String TV_INFO = "TV-INFO-"; //存储用技术课程信息 -  TV-SIG-[Login_name] - 存为String 格式 - 分为价格和过期时间  price#Outtime
//	public static final String TV_LIVE="TV-LIVE-";//存储用视频直播课程信息-	TV-LIVE-[Login_name]-
//	public static final String TV_SERIES="TV-SERIES-";//存储系列课支付信息；
//	
//	public static final String TBS_PHONE_USER= "TBS-PHONE-USER-";//用于标识是达内商学院手机注册的用户(map +账号，创建时间)
//	public static final String TBS_USER_MES="TBS-USER-MES-";//用于存储达内商学院学员的基本信息(map +账号，{json})
//	public static final String TBS_PAY_SET_COURSE="TBS-PAY-SET-COURSE-";	//用于查看学员是否购买课程（）
//	public static final String TBS_PAY_LIST_COURSE="TBS-PAY-LIST-COURSE-";  //用于存储学员购买课程信息（课程编号，课程购买时间）
//	public static final String TBS_COLLECT_COURSE="TBS-COLLECT-COURSE-";//存储学员收藏的课程信息	
//	public static final String TBS_COURSE_COUNT="TBS-COURSE-COUNT-"; //存储课程统计数据MAP（studyNum、is（学员数量），flowerNum，flowerNum，playNum，collectNum,评论点赞）
//	public static final String TBS_SERIES_LIST="TBS-SERIES-COURSE-";//存储系列课  <id>
//	public static final String TBS_MICRO_LIST="TBS-MICRO-COURSE-"; //存储微课<id>
//	public static final String TBS_COURSES_MAP="TBS-ALL-CORUSES";  //所有的课程以系列课的方式存储map
//	public static final String TBS_COURSE_DETAIL="TBS-CPIRSE-DETAIL-";//存储课程的详细信息list
//	public static final String TBS_MARKETINGCOURSE_DETAIL="TBS-MARKETING-COURSE";//营销课程 list
//	public static final String TBS_COURSE_PURCHASER="TBS-COURSE-PURCHASER-";//课程的购买人信息lsit 
//	public static final String TBS_USER_PERFECT="TBS-USER-PERFECT-";//完善用户账号信息，+账号，string
//	public static final String TBS_REIVEW_QUEUE="TBS-REIVEW-QUEUE-";//作为评论的缓存中转站，+课程id
//	public static final String TBS_REIVEW_MESS="TBS-REIVEW_MESS-";//客户获取评论信息；
//	public static final String TBS_REIVEW_ALL="TBS-REIVEW-ALL-";//存储所有的评论信息
//	public static final String TBS_REIVEW_ID="TBS-REVIEW-ID-";//自增id
//	public static final String TBS_USER_ID="TBS-USER-ID-";//存储账号副id
//	public static final String TBS_REIVEW_HOT="TBS-REIVEW-HOT-";//评论点赞量排行
//	public static final String TBS_REIVEW_VOTE="TBS-REIVEW-VOTE-";//评论点赞人
//	public static final String TBS_COURSE_COLLECT="TBS-COURSE-COLLECT-";//课程收藏
//	public static final String TBS_USER_COLLECT="TBS-COURSE-COLLECT-";//用户收藏
//	public static final String TBS_COURSE_FLOWER="TBS-COURSE-FLOWER";//课程鲜花数量
//	public static final String TBS_FLOWER_QUEUE="TBS-FLOWER-QUEUE-"; //鲜花队列
//	public static final String TBS_FLOWER_LIST="TBS-FLOWER-LIST-";//
//	public static final String TBS_TEACHER_MES="TBS-TEACHER-MES-";//存储讲师的基本信息
//	public static final String TBS_CONCERN_MESSAGE="TBS-CONCERN-MESSAGE-";//关注后推送的信息
//	public static final String TBS_COURSE_ALL="TBS_COURSE_ALL_";
//	/*
//	 * 存放CC视屏信息  : 
//	 * [数字1]-[字符1]
//	 * 数字1: 0 表示免费,1 表示vip ,2表示 tts , 3 表示 o2o 课程
//	 * 字符1: o2o-职业课程名称 , vip-课程ID
//	 */
//	public static String E_ccvideo = "E-CC-";
//	public static String VIDEO_FREE = "video.free"; //  免费
//	public static String VIDEO_VIP = "video.vip"; // vip 
//	public static String VIDEO_O2O = "video.o2o"; // 职业课程
//	public static String VIDEO_TTS = "video.tts"; // tts
//	public static String VIDEO_BACKFUN_VIP = "video.backfun.vip"; // vip 回调
//	public static String VIDEO_BACKFUN_LOGIN = "video.backfun.login"; // tts 回调
//	public static String VIDEO_BACKFUN_O2O = "video.backfun.o2o"; // o2o 回调
//	public static String VIDEO_BACKFUN_TTS = "video.backfun.tts"; // tts 回调
//	
//	public static String TMOOC_PHONE_VALIDATE = "TMOOC-PHONE-VALIDATE-"; // tts 回调
//	
//	public static String getValue(String table , String key ) throws Exception{
//		return LearningWeb.JEDIS.get( table + key );
//	}
//	
//	public static String setValue(String table ,String key, String value )throws Exception{
//		return LearningWeb.JEDIS.set( table + key , value);
//	}
//	//单位：秒
//	public static String setValueTime(String table ,String key, String value,int seconds )throws Exception{
//		return LearningWeb.JEDIS.setex( table + key , seconds, value);
//	}
//	
//	public static void delValue(String table , String key ) throws Exception{
//		LearningWeb.JEDIS.del( table + key );
//	} 
//	//自增
//	public static long incr(String table , String key ) throws Exception{
//		return LearningWeb.JEDIS.incr( table + key );
//	} 
//	//自减
//	public static long decr(String table , String key ) throws Exception{
//		return LearningWeb.JEDIS.decr( table + key );
//	} 
//	public static long setHash(String table,String key,String value){
//		return LearningWeb.JEDIS.hset(table, key, value);
//	}
//	public static Map getAllMap(String table){
//		return LearningWeb.JEDIS.hgetAll(table);
//	}
//	
//	public static String getHash(String table,String key){
//		return LearningWeb.JEDIS.hget(table, key);
//	}
//	//删除哈希表 中的一个指定域
//	public static long hdel(String table,String key){
//		return LearningWeb.JEDIS.hdel(table, key);
//	}
//	//插入后链表元素的数量
//	public static long setLpush(String table, String key,String... value ){
//		return LearningWeb.JEDIS.lpush(table+key, value);
//	}
//	//链表中的数量
//	public static long setLlen(String table,String key){
//		return LearningWeb.JEDIS.llen(table+key);
//	}
//	//返回指定范围的元素
//	public static List<String > getlrange(String table ,String key,int start,int end){
//		return LearningWeb.JEDIS.lrange(table+key, start, end);
//	}
//	//为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。//单位：秒
//	public static long setTime(String table ,String key,int seconds ){
//		return LearningWeb.JEDIS.expire( table + key , seconds);
//	}
//	//将元素加入到集合 key(table+key) 当中，已经存在于集合的元素将被忽略。
//	public static long addSet(String table,String key,String value){
//		return LearningWeb.JEDIS.sadd(table+key, value);
//	}
//	/**
//	 * map
//	 */
//	public static String setMap(String table,String name,Map<String,String> hash){
//		return LearningWeb.JEDIS.hmset(table+name, hash);
//	}
//	public static List<String> getMap(String table, String name,String... fields ){
//		return LearningWeb.JEDIS.hmget(table+name, fields);
//	}
//	/**
//	 * hash
//	 */
//	public static long setHash(String table,String name,String key,String value){
//		return LearningWeb.JEDIS.hset(table+name, key, value);
//	}
//	public static Map getAllMap(String table,String name){
//		return LearningWeb.JEDIS.hgetAll(table+name);
//	}
//	
//	public static String getHash(String table,String name,String key){
//		return LearningWeb.JEDIS.hget(table+name, key);
//	}
//	//删除哈希表 中的一个指定域
//	public static long hdel(String table,String name,String key){
//		return LearningWeb.JEDIS.hdel(table+name, key);
//	}
//	//模糊匹配key,由于服务器太多，效率就会低；慎用
//	public static Set<String> keys(String key,String value){
//		return LearningWeb.JEDIS.keys(key+value);
//	}
//	//Redis RPOP命令删除，并返回列表保存在key的最后一个元素。
//	public static String getRpop(String table,String  name){
//		return LearningWeb.JEDIS.rpop(table+name);
//	}
//	//验证key是否存在
//	public static boolean isKey(String table,String name){
//		return LearningWeb.JEDIS.exists(table+name);
//	}
//	//加入有序集合
//	public static double setZadd(String table,String name,double score,String value){
//		return LearningWeb.JEDIS.zadd(table+name, score, value);
//	}
//	//根据分数获取有序集合中的元素
//	public static Set<String > getZrangeByScore(String table,String name,String min,String max){
//		return LearningWeb.JEDIS.getZrangeByScore(table+name, min, max);
//	}
//	/**
//	 * 移除有序序列中的元素
//	 * @param table
//	 * @param name
//	 * @param start
//	 * @param end
//	 * @return
//	 */
//	public static long delZremrangeByScore(String table,String name,String start,String end){
//		return LearningWeb.JEDIS.delZremrangeByScore(table+name, start, end);
//	}
//	/**
//	 *有序序列
//	 *根据分数降序排列
//	 * @param table
//	 * @param name
//	 * @param max
//	 * @param min
//	 * @return
//	 */
//	public static Set<String > getZrevrangeByScore(String table,String name,String max,String min ){
//		return LearningWeb.JEDIS.getZrevrangeByScore( table+name, max, min);
//	}
//	/**
//	 * 有序序列
//	 *降序排列
//	 * @param table
//	 * @param name
//	 * @param start
//	 * @param end
//	 * @return
//	 */
//	public static Set<String > getZrevrange(String table,String name,long start,long end){
//		return LearningWeb.JEDIS.getZrevrange(table+name, start, end);
//	}
//	
//	/**
//	 * 有序集合
//	 * 分数增加或删除
//	 * @param table
//	 * @param name
//	 * @param score
//	 * @param member
//	 * @return
//	 */
//	public static Double setZincrby(String table,String name,double score,String member){
//		return LearningWeb.JEDIS.setZincrby(table+name, score, member);
//	}
//	/**
//	 * 移除有序集合lsit中的元素
//	 * @param table
//	 * @param name
//	 * @param count
//	 * @param value
//	 * @return
//	 */
//	public static long delLrem(String table,String name,long count,String value){
//		return LearningWeb.JEDIS.delLrem(table+name, count, value);
//	}
//	/**
//	 * 根据list下标更换元素内容
//	 * @param table
//	 * @param name
//	 * @param index
//	 * @param value
//	 * @return
//	 */
//	public static String setLset(String table,String name,long index,String value){
//		return LearningWeb.JEDIS.setLset(table+name, index, value);
//	}
//	
//	/**
//	 * list 在队未添加元素
//	 * @param table
//	 * @param name
//	 * @param value
//	 * @return
//	 */
//	public static long setRpush(String table,String name,String... value){
//		return LearningWeb.JEDIS.setRpush(table+name,value);
//	}
//}
