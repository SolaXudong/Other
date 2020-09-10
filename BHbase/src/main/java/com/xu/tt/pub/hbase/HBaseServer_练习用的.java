package com.xu.tt.pub.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

import com.xu.tt.dto.Phone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({ "deprecation", "unused" })
public class HBaseServer_练习用的 {

	@Autowired
	HBaseAdmin hBaseAdmin;// 表管理类
	@Autowired
	HTablePool hTablePool;// 连接池
	public static long HBASE_WRITE_BUFFER;// 批量写的buffer大小

	public HTablePool gethTablePool() {
		return hTablePool;
	}

	/**
	 * HBase配置
	 */
	public void init() {
		try {
			// 创建配置对象，指定zookeeper集群的地址
			Configuration hbase_config = HBaseConfiguration.create();
			hbase_config.set("hbase.zookeeper.quorum", "hadoop10"); // a,b,c
			// hbase_config.set("hbase.zookeeper.port", "2181");
			hbase_config.set("hbase.zookeeper.property.clientPort", "2181");// client连zk的端口
			// java.io.IOException: HADOOP_HOME or hadoop.home.dir are not set.
			// java.io.IOException: Could not locate executable
			// null\bin\winutils.exe in the Hadoop binaries.
			// System.setProperty("hadoop.home.dir",
			// "D:\\tt\\hadoop-common-2.2.0-bin-master");
			// 获取连接对象
			hBaseAdmin = new HBaseAdmin(hbase_config);
			hTablePool = new HTablePool(hbase_config, 20000);
			HBASE_WRITE_BUFFER = 524288;
			// 释放资源
			// hBaseAdmin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @tips LOOK http://hbase.apache.org/book.html
	 */
	public static void main(String[] args) {
		long cost = System.currentTimeMillis();
		HBaseServer_练习用的 hb = new HBaseServer_练习用的();
		hb.init();
		String table = "user";

		/** HBase_create_table */
		{
//			System.out.println("########## HBase_create_table");
//			hb.HBase_create_table(table, new String[] { "info" });
		}
		/** HBase_put */
		{
			// System.out.println("########## HBase_put");
			// hb.HBase_put(table, "10001", "info", new String[] { "id", "name"
			// }, new String[] { "10001", "徐东10001" });// *****
		}
		/** HBase_put_new_family */
		{
			// System.out.println("########## HBase_put_new_family");
			// hb.HBase_put_new_family(table, new String[] { "photo" });
			// hb.HBase_put(table, "10001", "photo", new String[] { "data" },
			// new String[] { "我是图片:picture" });
			// hb.HBase_put_new_family(table, new String[] { "other" });
			// hb.HBase_put(table, "10002", "info", new String[] { "id", "name"
			// }, new String[] { "10002", "徐东10002" });
		}
		/** HBase_get_row */
		{
			// System.out.println("########## HBase_get_row");
			// hb.HBase_get_row(table, "10001", "info", new String[] { "name"
			// });
			// hb.HBase_get_row(table, "10001", "info", null);
			// hb.HBase_get_row(table, "10001", null, null);
			// hb.HBase_get_row(table, "10002", null, null);
		}
		/** HBase_get_rows */
		{
			// System.out.println("########## HBase_get_rows");
			// hb.HBase_get_rows(table, null, "info", new String[] { "name" },
			// null, null, null, null);
			// hb.HBase_get_rows(table, null, "info", new String[] { "id",
			// "name" }, "10001", "10001", "1",
			// new String[] { "10001", "徐东10001" });
		}
		/** HBase_delete_qualifiers */
		{
			// System.out.println("########## HBase_delete_qualifiers");
			// hb.HBase_delete_qualifiers(table, "10001", new String[] { "info"
			// }, new String[] { "id", "name" });
		}
		/** HBase_delete_family */
		{
			// System.out.println("########## HBase_delete_family");
			// hb.HBase_delete_family(table, new String[] { "photo", "other" });
		}
		/** HBase_drop_table */
		{
			// System.out.println("########## HBase_drop_table");
			// hb.HBase_drop_table(new String[] { table });
		}
		/** HBase_Protobuf_Put */
		{
			// System.out.println("########## HBase_Protobuf_Put");
			// Phone.pdetail.Builder dto = Phone.pdetail.newBuilder();
			// dto.setPnum("157001");
			// dto.setTime("2018-9-18 00:00:00");
			// dto.setType("0");
			// hb.HBase_Protobuf_Put("phone", "phone_157001", "cf1", "info",
			// dto);
		}
		/** HBase_Protobuf_Get */
		{
			// System.out.println("########## HBase_Protobuf_Get");
			// Phone.pdetail dto = hb.HBase_Protobuf_Get("phone",
			// "phone_157001", "cf1", "info");
			// System.out.println(dto.getPnum() + "|" + dto.getTime() + "|" +
			// dto.getType());
		}

		log.info("########## cost : " + (System.currentTimeMillis() - cost) / 1000F + "s");
	}

	/**
	 * @tips LOOK HBase_Protobuf_Put
	 */
	public void HBase_Protobuf_Put(String table, String row, String family, String qualifier,
			Phone.pdetail.Builder dto) {
		HTableInterface tab = null;
		try {
			tab = hTablePool.getTable(table);
			Put put = new Put(row.getBytes());
			put.add(family.getBytes(), qualifier.getBytes(), dto.build().toByteArray());
			tab.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)
			try {
				hTablePool.putTable(tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @tips LOOK HBase_Protobuf_Get
	 */
	public Phone.pdetail HBase_Protobuf_Get(String table, String row, String family, String qualifier) {
		HTableInterface tab = null;
		try {
			tab = hTablePool.getTable(table);
			Get get = new Get(row.getBytes());
			get.addColumn(family.getBytes(), qualifier.getBytes());
			Result rs = tab.get(get);
			Cell cell = rs.getColumnLatestCell(family.getBytes(), qualifier.getBytes());
			if (cell != null) {
				Phone.pdetail dto = Phone.pdetail.parseFrom(CellUtil.cloneValue(cell));
				return dto;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)
			try {
				hTablePool.putTable(tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @tips LOOK 创建-表+列族
	 * @param table
	 * @param familys
	 */
	public void HBase_create_table(String table, String[] familys) {
		try {
			// 如果存在要创建的表，先删除，再创建
			if (hBaseAdmin.tableExists(table)) {
				System.out.println("表已存在 : " + table);
				return;
			}
			HTableDescriptor tableDescriptor = new HTableDescriptor(table);
			for (String column : familys) {
				HColumnDescriptor family = new HColumnDescriptor(column);
				family.setBlockCacheEnabled(true);// 缓存，默认启动
				family.setInMemory(true);// 默认false
				family.setMaxVersions(1);// 最大版本数，默认1
				// family.setTimeToLive(86400);//过剘时间，默认forever
				tableDescriptor.addFamily(family);
			}
			hBaseAdmin.createTable(tableDescriptor);
			System.out.println("表创建成功 : " + table);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @tips LOOK 添加-列族 重复会报错：java.lang.IllegalArgumentException: Family 'other'
	 *       already exists so cannot be added
	 * @param table
	 * @param familys
	 */
	public void HBase_put_new_family(String table, String[] familys) {
		try {
			hBaseAdmin.disableTable(table);
			for (String family : familys) {
				// 可以用，但很慢
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
				HTableDescriptor tTableDescriptor = hBaseAdmin.getTableDescriptor(Bytes.toBytes(table));
				tTableDescriptor.addFamily(hColumnDescriptor);
				hBaseAdmin.modifyTable(table, tTableDescriptor);
				// 老报错
				// HColumnDescriptor hColumnDescriptor = new
				// HColumnDescriptor(column);
				// hBaseAdmin.addColumn(row, hColumnDescriptor);
				System.out.println("添加列族 : " + family);
			}
			hBaseAdmin.enableTable(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @tips LOOK 添加-多个值
	 * @param table
	 * @param row
	 * @param family
	 * @param qualifiers
	 * @param values
	 */
	public void HBase_put(String table, String row, String family, String[] qualifiers, String[] values) {
		HTableInterface tab = null;
		try {
			tab = hTablePool.getTable(table);
			tab.setAutoFlush(false);// 官方建议：数据入库之前先设置此项为false
			tab.setWriteBufferSize(HBaseServer_练习用的.HBASE_WRITE_BUFFER);
			ArrayList<Put> list = new ArrayList<Put>();
			for (int i = 0; i < qualifiers.length; i++) {
				// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据，每行一个唯一的ROWKEY，此处ROWKEY为PUT构造方法中传入的值
				Put p = new Put(Bytes.toBytes(row));
				p.add(Bytes.toBytes(family), Bytes.toBytes(qualifiers[i]), Bytes.toBytes(values[i]));
				list.add(p);
				System.out.println("put : " + p);
			}
			tab.put(list);
			tab.flushCommits();// 官方建议：入库完成后，手动刷入数据
			tab.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)
			try {
				hTablePool.putTable(tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @tips LOOK 删除-表
	 * @param tables
	 */
	public void HBase_drop_table(String[] tables) {
		try {
			for (String table : tables) {
				hBaseAdmin.disableTable(table);
				hBaseAdmin.deleteTable(table);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @tips LOOK 删除-列族 必须有一个列族
	 *       org.apache.hadoop.hbase.InvalidFamilyOperationException: Family 'other'
	 *       is the only column family in the table, so it cannot be deleted
	 * @param table
	 * @param familys
	 */
	public void HBase_delete_family(String table, String[] familys) {
		try {
			for (String column : familys) {
				hBaseAdmin.deleteColumn(table, column);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @tips LOOK 删除-指定列限定符
	 * @param table
	 * @param row
	 * @param familys
	 * @param qualifiers
	 */
	public void HBase_delete_qualifiers(String table, String row, String[] familys, String[] qualifiers) {
		HTableInterface tab = null;
		try {
			tab = hTablePool.getTable(table);
			ArrayList<Delete> list = new ArrayList<Delete>();
			for (String family : familys) {
				for (String qualifier : qualifiers) {
					Delete d = new Delete(Bytes.toBytes(row));
					d.deleteColumns(Bytes.toBytes(family), Bytes.toBytes(qualifier));// 从行中删除一个单元，去删除单元的内容
					list.add(d);
				}
			}
			tab.delete(list);
			tab.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)
			try {
				hTablePool.putTable(tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @tips LOOK 查询-表中一行
	 * @param table
	 * @param row
	 * @param family(可null)
	 * @param qualifiers(可null)
	 */
	public String HBase_get_row(String table, String row, String family, String[] qualifiers) {
		String result = "";
		HTableInterface tab = null;
		try {
			tab = hTablePool.getTable(table);
			Get get = new Get(Bytes.toBytes(row));
			// 列族过滤
			if (qualifiers == null && StringUtils.isNotEmpty(family)) {
				get.addFamily(Bytes.toBytes(family));
			}
			// 列过滤
			if (qualifiers != null) {
				for (String value : qualifiers) {
					get.addColumn(Bytes.toBytes(family), Bytes.toBytes(value));
				}
			}
			// 返回结果对象
			Result rs = tab.get(get);
			{
				// 获取cell的扫描器
				CellScanner cs = rs.cellScanner();
				// 遍历扫描器
				while (cs.advance()) {
					// 获取单词扫描的Cell
					Cell c = cs.current();
					// 行键
					new String(CellUtil.cloneRow(c));
					// 列族
					new String(c.getFamilyArray(), c.getFamilyOffset(), c.getFamilyLength());
					new String(CellUtil.cloneFamily(c));
					// 列名
					new String(c.getQualifierArray(), c.getQualifierOffset(), c.getQualifierLength());
					new String(CellUtil.cloneQualifier(c));
					// 列值
					new String(c.getValueArray(), c.getValueOffset(), c.getValueLength());
					new String(CellUtil.cloneValue(c));
				}
			}
			StringBuffer sb = new StringBuffer("");
			for (KeyValue keyValue : rs.raw()) {
				sb.append(""
						// +table
						// +":"+new String(rs.getRow())
						// //+":"+new String(keyValue.getFamily())//ROWKEY
						// +":"+new String(keyValue.getQualifier())//列
						// +"="
						+ new String(keyValue.getValue()));// 值
				sb.append("|");
			}
			if (sb.length() > 0)
				sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
			result = sb.toString();
			tab.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)
			try {
				hTablePool.putTable(tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @tips LOOK 查询-表中多行
	 * @param table
	 * @param row
	 * @param family
	 * @param qualifiers
	 * @param startRow(开始行，可null)
	 * @param stopRow(结束行，可null)
	 * @param preRow(匹配前缀，可null)
	 * @param eqValues(匹配值，可null)
	 */
	public String HBase_get_rows(String table, String row, String family, String[] qualifiers, String startRow,
			String stopRow, String preRow, String[] eqValues) {
		String result = "";
		HTableInterface tab = null;
		ResultScanner rs = null;
		try {
			tab = hTablePool.getTable(table);
			// 创建Scan对象
			Scan scan = new Scan();
			FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);// 满足所有过滤器
			// 前缀过滤
			if (StringUtils.isNotEmpty(preRow)) {
				PrefixFilter PrefixFilter = new PrefixFilter(preRow.getBytes());
				filterList.addFilter(PrefixFilter);
			}
			// 值过滤
			if (eqValues != null) {
				for (int i = 0; i < eqValues.length; i++) {
					SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(family.getBytes(),
							qualifiers[i].getBytes(), CompareOp.EQUAL, eqValues[i].getBytes());
					filterList.addFilter(singleColumnValueFilter);
				}
			}
			// 列过滤
			if (qualifiers != null) {
				for (String value : qualifiers) {
					scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(value));
				}
			}
			// 行过滤
			if (StringUtils.isNotEmpty(startRow) && StringUtils.isNotEmpty(stopRow)) {
				scan.setStartRow(startRow.getBytes());// startRow
				scan.setStopRow(stopRow.getBytes());// stopRow
			}
			if (filterList.getFilters().size() > 0)
				scan.setFilter(filterList);
			// 创建单列值的过滤器
			if (null == table) {
				// 单值过滤器
				// 1.如果过滤条件是age，那么如果对应的行没有age列，不会被过滤
				// 2.我们大多数情况put的数据虽然都是字节数组，但是都是通过字符串转换的
				// 所以默认是按照【字典顺序】进行比较的
				// 如果想要按照数字进行比较，那么在插入值的时候就要插入数字类型
				// age = 10
				SingleColumnValueFilter ageFilter = new SingleColumnValueFilter(Bytes.toBytes("base_info"),
						Bytes.toBytes("age"), CompareFilter.CompareOp.LESS, Bytes.toBytes(10));
				// name = 'lixi'
				SingleColumnValueFilter nameFilter = new SingleColumnValueFilter(Bytes.toBytes("base_info"),
						Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("lixi"));
				// RegexString过滤器(CompareOp.EQUAL，只能用EQUAL)(name like '^li')
				SingleColumnValueFilter nameLikeFilter = new SingleColumnValueFilter(Bytes.toBytes("base_info"),
						Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^lixi"));
				// Substring过滤器(CompareOp.EQUAL，只能用EQUAL)(name like '%i%')
				SingleColumnValueFilter nameContainsFilter = new SingleColumnValueFilter(Bytes.toBytes("base_info"),
						Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL, new SubstringComparator("i"));
				// BinaryPrefix比较器(CompareOp.EQUAL，只能用EQUAL)(效率高)(name like
				// '^li')
				SingleColumnValueFilter nameBinaryPrefixFilter = new SingleColumnValueFilter(Bytes.toBytes("base_info"),
						Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,
						new BinaryPrefixComparator(Bytes.toBytes("i")));
				// Binary比较器(CompareOp.EQUAL，只能用EQUAL)(效率高)(name = 'li')
				SingleColumnValueFilter nameBinaryFilter = new SingleColumnValueFilter(Bytes.toBytes("base_info"),
						Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,
						new BinaryComparator(Bytes.toBytes("li")));
				// 缺失值过滤
				nameFilter.setFilterIfMissing(true);
				// scan.setFilter(nameFilter);

				// 列族过滤器
				FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL,
						new BinaryComparator(Bytes.toBytes("base_info")));

				// 列过滤器
				QualifierFilter qualifierFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL,
						new BinaryComparator(Bytes.toBytes("name")));
				// 列前缀过滤器
				ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("a"));
				// 多列前缀过滤器
				byte[][] prefixes = new byte[][] { Bytes.toBytes("a"), Bytes.toBytes("h") };
				MultipleColumnPrefixFilter multipleColumnPrefixFilter = new MultipleColumnPrefixFilter(prefixes);

				// 行键过滤器
				RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
						new BinaryComparator(Bytes.toBytes("001")));

				// 创建过滤器链(MUST_PASS_ALL=and,MUST_PASS_OR=or)
				FilterList tmpFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
				tmpFilterList.addFilter(nameFilter);
				scan.setFilter(tmpFilterList);
			}
			// 通过scan获取到结果的扫描器
			rs = tab.getScanner(scan);
			{
				Iterator<Result> it = rs.iterator();
				while (it.hasNext()) {
					Result trs = it.next();
					CellScanner cs = trs.cellScanner();
					while (cs.advance()) {
						Cell c = cs.current();
						// 全表扫描(行键、列族、列名、列值)
						StringBuffer sb = new StringBuffer("");
						sb.append(new String(CellUtil.cloneRow(c))).append("\t")
								.append(new String(CellUtil.cloneFamily(c))).append("\t")
								.append(new String(CellUtil.cloneQualifier(c))).append("\t")
								.append(new String(CellUtil.cloneValue(c)));
					}
				}
			}
			for (Result r : rs) {
				StringBuffer sb = new StringBuffer("");
				for (KeyValue keyValue : r.raw()) {
					sb.append(table + ":" + new String(r.getRow())
					// +":"+new String(keyValue.getFamily())//ROWKEY
							+ ":" + new String(keyValue.getQualifier())// 列
							+ "=" + new String(keyValue.getValue()));// 值
					sb.append("|");
				}
				if (sb.length() > 0)
					sb.deleteCharAt(sb.length() - 1);
				System.out.println(sb.toString());
				result = sb.toString();
			}
			tab.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 实际应用过程中，pool获取实例的方式应该抽取为单例模式的，不应在每个方法都重新获取一次(单例明白？就是抽取到专门获取pool的逻辑类中，具体逻辑为如果pool存在着直接使用，如果不存在则new)
			try {
				hTablePool.putTable(tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
