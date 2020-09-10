package com.xu.tt.pub.hbase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
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

import com.google.common.collect.Lists;
import com.xu.tt.dto.GpsDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HBaseServer_GPS {

	private static Configuration conf;
	private static Connection connection;

	static {
		try {
			conf = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum", "hadoop100");
			connection = ConnectionFactory.createConnection(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @tips LOOK
	 */
	public static void main(String[] args) throws Exception {
		long cost = System.currentTimeMillis();
		String table = "gps";

		/** 创建命名空间 */
		// HBaseServer_GPS.createNameSpace("ns");
		/** 创建表(在指定命名空间下创建表用：ns:stu，表名前加上就行) */
		// HBaseServer_GPS.createTable(table, "info");
		/** 查询表 */
		// HBaseServer_GPS.scanTable(table);
		/** 删除表 */
		// HBaseServer_GPS.dropTable(table);
		/**
		 * 添加数据 (设备编号（物联网卡） 1410345411509)(卡的EUM号码 460113307174115)(卡的ICCID编号
		 * 8986111827603421158)(118.181638,33.231873)
		 */
		// for (int j = 1; j <= 1000; j++) {
		// LocalDateTime local = LocalDateTime.parse("2020-01-05T00:00:00");
		// String start =
		// DateTimeFormatter.ofPattern("HH:mm:ss").format(local).replaceAll(":",
		// "");
		// StringBuffer h1 = new StringBuffer("");
		// StringBuffer h2 = new StringBuffer("");
		// String row = "20200105_" + (1000000000000000000L + j);
		// for (int i = 1; i <= 4320; i++) {
		// String value = start + ":118." + (100000 + i) + ",33." + (100000 +
		// i);
		// if (i != 4320)
		// value = value + ";";
		// if (i <= 2160) {
		// h1.append(value);
		// } else {
		// h2.append(value);
		// }
		// local = local.plusSeconds(20);
		// start =
		// DateTimeFormatter.ofPattern("HH:mm:ss").format(local).replaceAll(":",
		// "");
		// }
		// HBaseServer_GPS.putData(table, row, "info", "hour1", h1.toString());
		// HBaseServer_GPS.putData(table, row, "info", "hour2", h2.toString());
		// }
		// HBaseServer_GPS.putData(table, "1001", "info", "name", "xudong");
		/** 查询单行数据 */
		// HBaseServer_GPS.getRowData(table, "20200103_1000000000000000001");
		/** 扫描表数据 */
		// HBaseServer_GPS.scanTable(table);
		/** 删除数据 */
		// HBaseServer_GPS.scanTable(table);
		// HBaseServer_GPS.deleteData(table, "1006", "info", "name");
		// HBaseServer_GPS.scanTable(table);

		log.info("##### cost: " + (System.currentTimeMillis() - cost) / 1000F);
	}

	/**
	 * @tips LOOK 创建命名空间
	 */
	public static void createNameSpace(String space) throws Exception {
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.info("创建命名空间... " + space);
			NamespaceDescriptor build = NamespaceDescriptor.create(space).build();
			admin.createNamespace(build);
			log.info("命名空间创建成功: " + space);
		} catch (NamespaceExistException e) {
			log.info("命名空间已存在！" + space);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
		}
	}

	/**
	 * @tips LOOK 创建表
	 */
	public static void createTable(String table, String... familys) throws Exception {
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.info("创建表... " + table);
			TableName tname = TableName.valueOf(table);
			if (familys.length < 1) {
				log.info("请设置列族信息！");
				return;
			}
			if (admin.tableExists(tname)) {
				log.info("表已存在！" + table);
				return;
			}
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tname);
			for (String family : familys) {
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
				hTableDescriptor.addFamily(hColumnDescriptor);
				log.info("添加列族: " + family);
			}
			log.info("表创建成功: " + table);
			admin.createTable(hTableDescriptor);
			// admin.createTable(hTableDescriptor, Bytes.toBytes("1000"),
			// Bytes.toBytes("2000"), 100);
			// admin.createTable(hTableDescriptor, new byte[][] {
			// Bytes.toBytes("1000"), Bytes.toBytes("2000") });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
			// connection.close();
		}
	}

	/**
	 * @tips LOOK 创建表-自定义列族+自定义版本
	 */
	public static void createTableCustom(String table, int version, String... familys) throws Exception {
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.info("创建自定义表... " + table);
			TableName tname = TableName.valueOf(table);
			// 1.判断是否传入了列族信息
			if (familys.length < 1) {
				log.info("请设置列族信息！");
				return;
			}
			// 2.判断表是否存在
			if (admin.tableExists(tname)) {
				log.info("表已存在！" + table);
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
				log.info("添加列族: " + family);
			}
			log.info("表创建成功: " + table);
			// 8.创建表操作
			admin.createTable(hTableDescriptor);
			// 9.关闭资源
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
		}
	}

	/**
	 * @tips LOOK 删除表
	 */
	public static void dropTable(String table) throws Exception {
		HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
		try {
			log.info("删除表... " + table);
			TableName tname = TableName.valueOf(table);
			if (!admin.tableExists(tname)) {
				log.info("表不存在！" + table);
				return;
			}
			admin.disableTable(tname);
			admin.deleteTable(tname);
			log.info("表删除成功: " + table);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			admin.close();
		}
	}

	/**
	 * @tips LOOK 添加数据
	 */
	public static void putData(String table, String row, String family, String qulifier, String value)
			throws Exception {
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			log.info("添加数据... " + table);
			Put put = new Put(Bytes.toBytes(row));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qulifier), Bytes.toBytes(value));
			t.put(put);
			log.info("添加数据成功: " + table);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
		}
	}

	/**
	 * @tips LOOK 查询单行数据
	 */
	public static List<GpsDto> getRowData(String table, String row) throws Exception {
		long cost = System.currentTimeMillis();
		LinkedList<GpsDto> list = Lists.newLinkedList();
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			log.info("查询单行数据... " + table + ", " + row);
			Get get = new Get(Bytes.toBytes(row));
			// get.addFamily(Bytes.toBytes("info"));
			// get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("997"));
			Result result = t.get(get);
			StringBuffer value = new StringBuffer("");
			for (Cell cell : result.rawCells()) {
				String fa = Bytes.toString(CellUtil.cloneFamily(cell));
				String qa = Bytes.toString(CellUtil.cloneQualifier(cell));
				String va = Bytes.toString(CellUtil.cloneValue(cell));
				log.info("Rowkey: " + new String(result.getRow()) + ", Family: " + fa + ", Qualifier: " + qa
						+ ", Value: " + va);
				value.append(va);
			}
			list.add(GpsDto.builder().gpsStr(value.toString()).build());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
		}
		log.info("##### cost: " + (System.currentTimeMillis() - cost) / 1000F);
		return ListUtils.emptyIfNull(list);
	}

	/**
	 * @tips LOOK 扫描表数据
	 */
	public static void scanTable(String table) throws Exception {
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
					log.info("Rowkey: " + rw + ", Family: " + fa + ", Qualifier: " + qa + ", Value: " + va);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
		}
	}

	/**
	 * @tips LOOK 删除数据
	 */
	public static void deleteData(String table, String row, String family, String qualifier) throws Exception {
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
		}
	}

	/**
	 * @tips LOOK 查询全表
	 */
	public static void scanTable2(String table) throws Exception {
		Table t = connection.getTable(TableName.valueOf(table));
		try {
			log.info("查询全表... " + table);
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
					log.info(sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			t.close();
		}
	}

}
