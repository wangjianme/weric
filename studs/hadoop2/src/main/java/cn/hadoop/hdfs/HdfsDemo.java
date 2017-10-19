package cn.hadoop.hdfs;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsDemo {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		//config的功能，可以修改hdfs配置的信息
		conf.set("dfs.replication","2");
		URI uri = new URI("hdfs://hadoop201:8020");
		FileSystem fs = 
				FileSystem.get(uri,conf,"wangjian");
		fs.copyFromLocalFile(new Path("D:/a/aa.txt"),new Path("/weric/b.txt"));
		fs.close();
	}
}
