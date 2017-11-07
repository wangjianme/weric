package cn.hadoop.demo;

import java.util.function.Consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

public class Demo02_HBase {
	/**
	 * 查询HBase数据库中所有的表名
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		Configuration conf = HBaseConfiguration.create();
		// 设置属性
		conf.set("hbase.rootdir", "hdfs://hadoop101:8020/hbase");
		conf.set("hbase.zookeeper.quorum", "hadoop101:2181");
		// 获取连接对象
		Connection con = ConnectionFactory.createConnection(conf);
		// 获取数据操作对象
		Admin admin = con.getAdmin();
		if (admin instanceof HBaseAdmin) {
			HBaseAdmin hadmin = (HBaseAdmin) admin;
			TableName[] tns = hadmin.listTableNames();
			for (TableName tn : tns) {
				System.err.println("表名：" + tn.getNameAsString());
			}
		}
		admin.close();
	}

	// 查询Hbase某个表中的所有数据
	@Test
	public void test2() throws Exception {
		Configuration conf = HBaseConfiguration.create();
		// 设置属性
		conf.set("hbase.rootdir", "hdfs://hadoop101:8020/hbase");
		conf.set("hbase.zookeeper.quorum", "hadoop101:2181");
		// 获取连接对象
		Connection con = ConnectionFactory.createConnection(conf);
		Table table = con.getTable(TableName.valueOf("stud"));
		ResultScanner result = table.getScanner(new Scan());
		result.forEach(new Consumer<Result>() {
			@Override
			public void accept(Result t) {
				for (Cell cell : t.listCells()) {
					System.err.println("RowKey:" + Bytes.toString(CellUtil.cloneRow(cell))//
							+ ",Family:" + //
					Bytes.toString(CellUtil.cloneFamily(cell))//
					+",Column:"+Bytes.toString(CellUtil.cloneQualifier(cell))//
							+ ",value:" + //
					Bytes.toString(CellUtil.cloneValue(cell)));
				}
			}
		});
		con.close();
	}
}
