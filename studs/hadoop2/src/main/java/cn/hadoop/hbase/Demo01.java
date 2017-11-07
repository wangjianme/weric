package cn.hadoop.hbase;

import java.util.List;
import java.util.function.Consumer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

public class Demo01 {
	@Test
	public void test1() throws Exception {
		// 创建配置对象
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.rootdir", "hdfs://hadoop101:8020/hbase");
		// 还要配置zookeeper的地址
		config.set("hbase.zookeeper.quorum", "hadoop101:2181");
		// 获取连接
		Connection con = ConnectionFactory.createConnection(config);
		// 获取
		Admin admin = con.getAdmin();
		TableName[] tn = admin.listTableNames();
		for (TableName n : tn) {
			System.err.println("表名：" + n.getNameAsString());
		}
		con.close();
	}

	// 保存数据到一个表中去
	@Test
	public void testPut() throws Exception {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.rootdir", "hdfs://hadoop101:8020/hbase");
		// 还要配置zookeeper的地址
		config.set("hbase.zookeeper.quorum", "hadoop101:2181");
		// 获取连接
		Connection con = ConnectionFactory.createConnection(config);

		Table table = con.getTable(TableName.valueOf("stud"));
		// 先写入RowKey
		Put put = new Put(Bytes.toBytes("S004"));
		// 再写入数据
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("张三"));
		table.put(put);
		con.close();
	}

	// 查询
	@Test
	public void testQuery() throws Exception {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.rootdir", "hdfs://hadoop101:8020/hbase");
		// 还要配置zookeeper的地址
		config.set("hbase.zookeeper.quorum", "hadoop101:2181");
		// 获取连接
		Connection con = ConnectionFactory.createConnection(config);

		Table table = con.getTable(TableName.valueOf("stud"));
		ResultScanner rs = table.getScanner(new Scan());
		rs.forEach(new Consumer<Result>() {
			@Override
			public void accept(Result t) {
				// 获取列
				List<Cell> list = t.listCells();
				for(Cell c:list) {
					System.err.println("行键："+Bytes.toString(CellUtil.cloneRow(c))+//
							"列旆："+Bytes.toString(CellUtil.cloneFamily(c))+//
							"列名:"+Bytes.toString(CellUtil.cloneQualifier(c))+//
							"列值："+Bytes.toString(CellUtil.cloneValue(c)));
				}
			}
		});
		con.close();
	}
}
