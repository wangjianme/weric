package cn.hadoop.hahdfs;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HAHdfsDemo {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("dfs.nameservices", "nameservice1");
		conf.set("dfs.ha.namenodes.nameservice1", "namenode1,namenode2");
		conf.set("dfs.namenode.rpc-address.nameservice1.namenode1", "hadoop201:8020");
		conf.set("dfs.namenode.rpc-address.nameservice1.namenode2", "hadoop202:8020");
		conf.set("dfs.client.failover.proxy.provider.nameservice1", //
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		URI uri = new URI("hdfs://nameservice1");
		FileSystem fs = FileSystem.get(uri, conf, "wangjian");
		InputStream in = fs.open(new Path("/weric/a.txt"));
		FileUtils.copyInputStreamToFile(in, new File("d:/a/aaa.txt"));
		in.close();
		fs.close();
	}
}
