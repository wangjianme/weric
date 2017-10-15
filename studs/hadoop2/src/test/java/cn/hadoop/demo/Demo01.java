package cn.hadoop.demo;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Test;

public class Demo01 {
	@Test
	public void test1() throws Exception {
		Configuration configuration = new Configuration();
		URI uri = new URI("hdfs://hadoop101:8020");
		// 获取文件系统
		FileSystem fs = //
				FileSystem.get(uri, configuration);
		// 获取根目录下的所有文件 hdfs dfs -ls /
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/"), true);
		while (it.hasNext()) {
			LocatedFileStatus f = it.next();
			System.err.println(f.getPath() + "," + f.getPermission());
		}
		fs.close();

	}

	@Test
	public void test2() throws Exception {
		Configuration configuration = new Configuration();
		URI uri = new URI("hdfs://hadoop101:8020");
		FileSystem fs = //
				FileSystem.get(uri, configuration, "wangjian");// 保存时要写入用户名
		fs.copyFromLocalFile(new Path("D:/a/aa.txt"), new Path("/weric/b.txt"));
		fs.close();

	}

	@Test
	public void test3() throws Exception {
		Configuration configuration = new Configuration();
		URI uri = new URI("hdfs://hadoop101:8020");
		FileSystem fs = //
				FileSystem.get(uri, configuration, "wangjian");// 保存时要写入用户名

		InputStream in = fs.open(new Path("/weric/b.txt"));
		FileUtils.copyInputStreamToFile(in, new File("D:/a/1.txt"));
		fs.close();

	}
}
