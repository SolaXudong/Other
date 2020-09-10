package com.xu.tt.pub.hbase;

import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({ "deprecation" })
public class HBaseServer_微博 {

	private static Configuration conf;
	// 命名空间
	public static final String NAMESPACE = "weibo";
	// 微博内容表
	public static final String CONTENT_TABLE = "weibo:content";
	public static final String CONTENT_TABLE_CF = "info";
	public static final int CONTENT_TABLE_VERSIONS = 1;
	// 用户关系表
	public static final String RELATION_TABLE = "weibo:relation";
	public static final String RELATION_TABLE_CF1 = "attends";
	public static final String RELATION_TABLE_CF2 = "fans";
	public static final int RELATION_TABLE_VERSIONS = 1;
	// 收件箱表
	public static final String INBOX_TABLE = "weibo:inbox";
	public static final String INBOX_TABLE_CF = "info";
	public static final int INBOX_TABLE_VERSIONS = 2;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop100");
	}

	/**
	 * @tips LOOK
	 */
	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();

		// 初始化
		if ("1".equals("")) {
			// 创建命名空间
			HBaseServer_简单封装.createNameSpace(NAMESPACE);
			// 创建微博内容表
			HBaseServer_简单封装.createTableCustom(CONTENT_TABLE, CONTENT_TABLE_VERSIONS, CONTENT_TABLE_CF);
			// 创建用户关系表
			HBaseServer_简单封装.createTableCustom(RELATION_TABLE, RELATION_TABLE_VERSIONS, RELATION_TABLE_CF1,
					RELATION_TABLE_CF2);
			// 创建收件箱表
			HBaseServer_简单封装.createTableCustom(INBOX_TABLE, INBOX_TABLE_VERSIONS, INBOX_TABLE_CF);

			// 1001发布微博
			publishWeiBo("1001", "赶紧下课吧！");
			log.error("##### 1001，发了一条微博");
			// 1002关注1001和1003
			addAttends("1002", "1001", "1003");
			log.error("##### 1002，关注了，1001、1003");
			// 获取1002初始化页面
			log.error("##### 1002，主页面");
			getInit("1002");
			log.error("##### 111 #####");
			// 1003发布3条微博，同时1001发布2条微博
			publishWeiBo("1003", "谁说的赶紧下课！");
			log.error("##### 1003，发了一条微博");
			Thread.sleep(10);
			publishWeiBo("1001", "我没说话！");
			log.error("##### 1001，发了一条微博");
			Thread.sleep(10);
			publishWeiBo("1003", "那谁说的！");
			log.error("##### 1003，发了一条微博");
			Thread.sleep(10);
			publishWeiBo("1001", "反正飞机是下线了！");
			log.error("##### 1001，发了一条微博");
			Thread.sleep(10);
			publishWeiBo("1003", "你们爱咋咋地！");
			log.error("##### 1003，发了一条微博");
			// 获取1002初始化页面
			log.error("##### 1002，主页面");
			getInit("1002");
			log.error("##### 222 #####");
			// 1002取关1003
			deleteAttends("1002", "1003");
			log.error("##### 1002，取关了，1003");
			// 获取1002初始化页面
			log.error("##### 1002，主页面");
			getInit("1002");
			log.error("##### 333 #####");
			// 1002再次关注1003
			addAttends("1002", "1003");
			log.error("##### 1002，关注了，1003");
			// 获取1002初始化页面
			log.error("##### 1002，主页面");
			getInit("1002");
			log.error("##### 获取1001微博详情");
			// 获取1001微博详情
			getWeiBo("1001");
		}

		/** 发布微博 */
		// publishWeiBo("1002", "hh");
		/** 删除微博 */
		// deleteWeiBo("1001");
		/** 关注用户 */
		// addAttends("1002", "1003");
		/** 取关用户 */
		// deleteAttends("1002", "1001");
		/** 获取用户微博详情 */
		// getWeiBo("1002");
		/** 获取用户的初始化页面 */
		getInit("1002");

		cost = System.currentTimeMillis() - cost;
		log.error("##### cost: " + cost);
	}

	/**
	 * @tips LOOK 发布微博
	 */
	public static void publishWeiBo(String uid, String content) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		// 第一部分：操作微博内容表
		// 1.获取微博内容表对象
		Table contTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));
		// 2.获取当前时间戳
		long ts = System.currentTimeMillis();
		// 3.获取RowKey
		String rowKey = uid + "_" + ts;
		// 4.创建Put对象
		Put contPut = new Put(Bytes.toBytes(rowKey));
		// 5.给Put对象赋值
		contPut.addColumn(Bytes.toBytes(CONTENT_TABLE_CF), Bytes.toBytes("content"), Bytes.toBytes(content));
		// 6.执行插入数据操作
		contTable.put(contPut);
		// 第二部分：操作微博收件箱表
		// 1.获取用户关系表对象
		Table relaTable = connection.getTable(TableName.valueOf(RELATION_TABLE));
		// 2.获取当前发布微博人的fans列族数据
		Get get = new Get(Bytes.toBytes(uid));
		get.addFamily(Bytes.toBytes(RELATION_TABLE_CF2));
		Result result = relaTable.get(get);
		// 3.创建一个集合，用于存放微博内容表的Put对象
		ArrayList<Put> inboxPuts = new ArrayList<>();
		// 4.遍历粉丝
		for (Cell cell : result.rawCells()) {
			// 5.构建微博收件箱表的Put对象
			Put inboxPut = new Put(CellUtil.cloneQualifier(cell));
			// 6.给收件箱表的Put对象赋值
			inboxPut.addColumn(Bytes.toBytes(INBOX_TABLE_CF), Bytes.toBytes(uid), Bytes.toBytes(rowKey));
			// 7.将收件箱表的Put对象存入集合
			inboxPuts.add(inboxPut);
		}
		// 8.判断是否有粉丝
		if (inboxPuts.size() > 0) {
			// 获取收件箱表对象
			Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));
			// 执行收件箱表数据插入操作
			inboxTable.put(inboxPuts);
			// 关闭收件箱表
			inboxTable.close();
		}
		// 关闭资源
		relaTable.close();
		contTable.close();
	}

	/**
	 * @tips LOOK 删除微博
	 */
	public void deleteWeiBo(String weiboid) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		Table contTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));
		Delete delete = new Delete(Bytes.toBytes(weiboid));
		contTable.delete(delete);
		contTable.close();
		connection.close();
	}

	/**
	 * @tips LOOK 关注用户
	 */
	public static void addAttends(String uid, String... attends) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		// 校验是否添加了待关注的人
		if (attends.length <= 0) {
			log.error("请选择待关注的人！");
			return;
		}
		// 第一部分：操作用户关系表
		// 1.获取用户关系表对象
		Table relaTable = connection.getTable(TableName.valueOf(RELATION_TABLE));
		// 2.创建一个集合，用于存放用户关系表Put对象
		ArrayList<Put> relaPuts = new ArrayList<>();
		// 3.创建操作者的Put对象
		Put uidPut = new Put(Bytes.toBytes(uid));
		// 4.循环创建被关注者的Put对象
		for (String attend : attends) {
			// 5.给操作者的Put对象赋值
			uidPut.addColumn(Bytes.toBytes(RELATION_TABLE_CF1), Bytes.toBytes(attend), Bytes.toBytes(attend));
			// 6.创建被关注者的Put对象
			Put attendPut = new Put(Bytes.toBytes(attend));
			// 7.给被关注者的Put对象赋值
			attendPut.addColumn(Bytes.toBytes(RELATION_TABLE_CF2), Bytes.toBytes(uid), Bytes.toBytes(uid));
			// 8.将被关注者的Put对象放入集合
			relaPuts.add(attendPut);
		}
		// 9.将操作者的Put对象添加至集合
		relaPuts.add(uidPut);
		// 10.执行用户关系表的插入数据操作
		relaTable.put(relaPuts);
		// 第二部分：操作收件箱表
		// 1.获取微博内容表对象
		Table contTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));
		// 2.创建收件箱表的Put对象
		Put inboxPut = new Put(Bytes.toBytes(uid));
		// 3.循环attends，获取每个被关注者的近期发布的微博
		for (String attend : attends) {
			// 4.获取当前被关注者的近期发布的微博（scan）->集合ResultScanner
			Scan scan = new Scan().withStartRow(Bytes.toBytes(attend + "_")).withStopRow(Bytes.toBytes(attend + "_|"));
			ResultScanner resultScanner = contTable.getScanner(scan);
			// 定义一个时间戳
			long ts = System.currentTimeMillis();
			// 5. 对获取的值进行遍功
			for (Result result : resultScanner) {
				// 6.给收件箱表的Put对象赋值
				inboxPut.addColumn(Bytes.toBytes(INBOX_TABLE_CF), Bytes.toBytes(attend), ts++, result.getRow());
			}
		}
		// 7.判断当前的Put对象是否为空
		if (!inboxPut.isEmpty()) {
			// 获取收件箱表对象
			Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));
			// 插入数据
			inboxTable.put(inboxPut);
			// 关闭收件箱表连接
			inboxTable.close();
		}
		// 关闭资源
		relaTable.close();
		contTable.close();
		connection.close();
	}

	/**
	 * @tips LOOK 取关用户
	 */
	public static void deleteAttends(String uid, String... dels) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		if (dels.length <= 0) {
			log.error("请添加待取关的用户！");
			return;
		}
		// 第一部分：操作用户关系表
		// 1.获取用户关系表对象
		Table relaTable = connection.getTable(TableName.valueOf(RELATION_TABLE));
		// 2.创建一个集合，用于存放用户关系表的Delete对象
		ArrayList<Delete> relaDeletes = new ArrayList<>();
		// 3.创建操作者的Delete对象
		Delete uidDelete = new Delete(Bytes.toBytes(uid));
		// 4.循环创建被取关者的Delete对象
		for (String del : dels) {
			// 5.给操作者的Delete对象赋值
			uidDelete.addColumns(Bytes.toBytes(RELATION_TABLE_CF1), Bytes.toBytes(del));
			// 6.创建被取关的Delete对象
			Delete delDelete = new Delete(Bytes.toBytes(del));
			// 7.给被取关者的Delete对象赋值
			delDelete.addColumns(Bytes.toBytes(RELATION_TABLE_CF2), Bytes.toBytes(uid));
			// 8.将被取关者的Delete对象添加至集合
			relaDeletes.add(delDelete);
		}
		// 9.将操作者的Delete对象添加至集合
		relaDeletes.add(uidDelete);
		// 10.执行用户关系表的删除操作
		relaTable.delete(relaDeletes);
		// 第二部分：操作收件箱表
		// 1.获取收件箱表对象
		Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));
		// 2.创建操作者的Delete对象
		Delete inboxDelete = new Delete(Bytes.toBytes(uid));
		// 3.给操作者的Delete对象赋值
		for (String del : dels) {
			inboxDelete.addColumns(Bytes.toBytes(INBOX_TABLE_CF), Bytes.toBytes(del));
		}
		// 4.执行收件箱表的删除操作
		inboxTable.delete(inboxDelete);
		// 关闭资源
		relaTable.close();
		inboxTable.close();
	}

	/**
	 * @tips LOOK 获取用户的初始化页面
	 */
	public static void getInit(String uid) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		// 1.获取收件箱表对象
		Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));
		// 2.获取微博内容表对象
		Table contTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));
		// 3.创建收件箱表Get对象，并获取数据（设置最大版本）
		Get inboxGet = new Get(Bytes.toBytes(uid));
		inboxGet.setMaxVersions();
		Result result = inboxTable.get(inboxGet);
		// 4.遍历获取的数据
		for (Cell cell : result.rawCells()) {
			// 5.构建微博内容表Get对象
			Get contGet = new Get(CellUtil.cloneValue(cell));
			// 6.获取该Get对象的数据内容
			Result contResult = contTable.get(contGet);
			// 7.解析内容并打印
			for (Cell contCell : contResult.rawCells()) {
				String rw = Bytes.toString(CellUtil.cloneRow(contCell));
				String fa = Bytes.toString(CellUtil.cloneFamily(contCell));
				String qa = Bytes.toString(CellUtil.cloneQualifier(contCell));
				String va = Bytes.toString(CellUtil.cloneValue(contCell));
				log.error("Rowkey: " + rw + ", Family: " + fa + ", Qualifier: " + qa + ", Value: " + va);
			}
		}
		// 关闭资源
		inboxTable.close();
		contTable.close();
		connection.close();
	}

	/**
	 * @tips LOOK 获取用户微博详情
	 */
	public static void getWeiBo(String uid) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		// 1.获取微博内容表对象
		Table contTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));
		// 2.构建Scan对象
		Scan scan = new Scan();
		// 构建过滤器
		RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(uid + "_"));
		scan.setFilter(rowFilter);
		// 3.获取数据
		ResultScanner scanner = contTable.getScanner(scan);
		// 4.解析数据并打印
		for (Result result : scanner) {
			for (Cell contCell : result.rawCells()) {
				String rw = Bytes.toString(CellUtil.cloneRow(contCell));
				String fa = Bytes.toString(CellUtil.cloneFamily(contCell));
				String qa = Bytes.toString(CellUtil.cloneQualifier(contCell));
				String va = Bytes.toString(CellUtil.cloneValue(contCell));
				log.error("Rowkey: " + rw + ", Family: " + fa + ", Qualifier: " + qa + ", Value: " + va);
			}
		}
		// 5.关闭资源
		contTable.close();
		connection.close();
	}

}
