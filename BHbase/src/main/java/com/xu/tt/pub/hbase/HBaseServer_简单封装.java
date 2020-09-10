package com.xu.tt.pub.hbase;

import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceExistException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HBaseServer_简单封装 {

	private static Configuration conf;

	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "hadoop10");
	}

	/**
	 * @tips LOOK
	 */
	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		String table = "user";

		/** 创建命名空间 */
//		 HBaseServer_简单封装.createNameSpace("ns");
		/** 创建表(在指定命名空间下创建表用：ns:stu，表名前加上就行) */
//		HBaseServer_简单封装.createTable(table, "info", "pic");
		/** 查询表 *//** 扫描表数据 */
		HBaseServer_简单封装.scanTable(table);
		/** 删除表 */
//		 HBaseServer_简单封装.dropTable(table);
		/** 添加数据 */
//		 HBaseServer_简单封装.putData(table, "1001", "info", "name", "xudong");
		/** 查询单行数据 */
//		 HBaseServer_简单封装.getRowData(table, "1001");
		/** 删除数据 */
//		 HBaseServer_简单封装.scanTable(table);
//		 HBaseServer_简单封装.deleteData(table, "1006", "info", "name");
//		 HBaseServer_简单封装.scanTable(table);

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

	/**
	 * @tips LOOK 创建命名空间
	 */
	public static void createNameSpace(String space) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.error("创建命名空间... " + space);
			NamespaceDescriptor build = NamespaceDescriptor.create(space).build();
			admin.createNamespace(build);
			log.error("命名空间创建成功: " + space);
		} catch (NamespaceExistException e) {
			log.error("命名空间已存在！" + space);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 创建表
	 */
	public static void createTable(String table, String... familys) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.error("创建表... " + table);
			TableName tname = TableName.valueOf(table);
			if (familys.length < 1) {
				log.error("请设置列族信息！");
				return;
			}
			if (admin.tableExists(tname)) {
				log.error("表已存在！" + table);
				return;
			}
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tname);
			for (String family : familys) {
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
				hTableDescriptor.addFamily(hColumnDescriptor);
				log.error("添加列族: " + family);
			}
			log.error("表创建成功: " + table);
			admin.createTable(hTableDescriptor);
			// admin.createTable(hTableDescriptor, Bytes.toBytes("1000"),
			// Bytes.toBytes("2000"), 100);
			// admin.createTable(hTableDescriptor, new byte[][] {
			// Bytes.toBytes("1000"), Bytes.toBytes("2000") });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 创建表-自定义列族+自定义版本
	 */
	public static void createTableCustom(String table, int version, String... familys) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.error("创建自定义表... " + table);
			TableName tname = TableName.valueOf(table);
			// 1.判断是否传入了列族信息
			if (familys.length < 1) {
				log.error("请设置列族信息！");
				return;
			}
			// 2.判断表是否存在
			if (admin.tableExists(tname)) {
				log.error("表已存在！" + table);
				return;
			}
			// 3.获取Connection对象
			// 4.获取Admin对象
			// 5.创建表描述器
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tname);
			// 6.添加列族信息
			for (String family : familys) {
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
				// 7.设置版本
				hColumnDescriptor.setMaxVersions(version);
				hTableDescriptor.addFamily(hColumnDescriptor);
				log.error("添加列族: " + family);
			}
			log.error("表创建成功: " + table);
			// 8.创建表操作
			admin.createTable(hTableDescriptor);
			// 9.关闭资源
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 删除表
	 */
	public static void dropTable(String table) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.error("删除表... " + table);
			TableName tname = TableName.valueOf(table);
			if (!admin.tableExists(tname)) {
				log.error("表不存在！" + table);
				return;
			}
			admin.disableTable(tname);
			admin.deleteTable(tname);
			log.error("表删除成功: " + table);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 添加数据
	 */
	public static void putData(String table, String row, String family, String qulifier, String value)
			throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			log.error("添加数据... " + table);
			Put put = new Put(Bytes.toBytes(row));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qulifier), Bytes.toBytes(value));
			t.put(put);
			log.error("添加数据成功: " + table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			t.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 查询单行数据
	 */
	public static void getRowData(String table, String row) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			log.error("查询单行数据... " + table + ", " + row);
			Get get = new Get(Bytes.toBytes(row));
			// get.addFamily(Bytes.toBytes(family));
			// get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
			Result result = t.get(get);
			for (Cell cell : result.rawCells()) {
				String fa = Bytes.toString(CellUtil.cloneFamily(cell));
				String qa = Bytes.toString(CellUtil.cloneQualifier(cell));
				String va = Bytes.toString(CellUtil.cloneValue(cell));
				log.error("Rowkey: " + new String(result.getRow()) + ", Family: " + fa + ", Qualifier: " + qa
						+ ", Value: " + va);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 扫描表数据
	 */
	public static void scanTable(String table) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			Scan scan = new Scan();
			// Scan scan = new
			// Scan().withStartRow(Bytes.toBytes("1002")).withStopRow(Bytes.toBytes("1004"));
			// // 左闭右开
			ResultScanner scanner = t.getScanner(scan);
			for (Result result : scanner) {
				for (Cell cell : result.rawCells()) {
					String rw = Bytes.toString(CellUtil.cloneRow(cell));
					String fa = Bytes.toString(CellUtil.cloneFamily(cell));
					String qa = Bytes.toString(CellUtil.cloneQualifier(cell));
					String va = Bytes.toString(CellUtil.cloneValue(cell));
					log.error("Rowkey: " + rw + ", Family: " + fa + ", Qualifier: " + qa + ", Value: " + va);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 删除数据
	 */
	public static void deleteData(String table, String row, String family, String qualifier) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			Delete delete = new Delete(Bytes.toBytes(row));
			// delete.addFamily(Bytes.toBytes("family"));
			// 生产环境慎用，删除最新的一个版本（如果没的flush，旧版本可能会被查出来）（时间戳比对的是是否相等）
			// delete.addColumn(Bytes.toBytes("family"),
			// Bytes.toBytes("qualifier"));
			// 删除所有版本
			// delete.addColumns(Bytes.toBytes("family"),
			// Bytes.toBytes("qualifier"));
			t.delete(delete);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
			connection.close();
		}
	}

	/**
	 * @tips LOOK 查询全表
	 */
	public static void scanTable2(String table) throws Exception {
		Connection connection = ConnectionFactory.createConnection(conf);
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			log.error("查询全表... " + table);
			Scan scan = new Scan();
			ResultScanner scanner = t.getScanner(scan);
			Iterator<Result> it = scanner.iterator();
			while (it.hasNext()) {
				Result trs = it.next();
				CellScanner cs = trs.cellScanner();
				while (cs.advance()) {
					Cell c = cs.current();
					// 全表扫描(行键、列族、列名、列值)
					StringBuffer sb = new StringBuffer("");
					sb.append(new String(CellUtil.cloneRow(c))).append("\t").append(new String(CellUtil.cloneFamily(c)))
							.append("\t").append(new String(CellUtil.cloneQualifier(c))).append("\t")
							.append(new String(CellUtil.cloneValue(c)));
					log.error(sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
			connection.close();
		}
	}

}
