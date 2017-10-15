package cn.hadoop;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FSInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Demo01_HDFS {
	String uriAddr = "hdfs://weric101:8020";
	Configuration config;
	FileSystem fs;

	@Before
	public void init() throws Exception {
		config = new Configuration();
		fs = FileSystem.get(new URI(uriAddr), config, "wangjian");
	}

	@After
	public void destroy() throws Exception {
		fs.close();
	}

	// 创建目录
	@Test
	public void mkdir() throws Exception {
		boolean boo = fs.mkdirs(new Path("/hadoop"));
		System.err.println("目录创建是否成功：" + boo);
	}

	// 使用open方式打开一个文件的二进制流实现文件下载
	@Test
	public void open() throws Exception {
		// fs.copyFromLocalFile(new Path("d:/a/Hello.java"), new Path("/weric/a1.txt"));
		// 在windows上操作，需要安装winutils.exe，建议使用open方式下载字节流即可
		// fs.copyToLocalFile(new Path("/weric/a1.txt"), new Path("D:/a/Word.java"));
		FSDataInputStream in = fs.open(new Path("/weric/a1.txt"));
		FileUtils.copyInputStreamToFile(in, new File("D:/a/aaa.txt"));
	}

	// 重新命名文件，即可理解为移动文件
	@Test
	public void move() throws Exception {
		boolean boo = fs.rename(new Path("/weric/a.txt"), new Path("/hadoop/a.txt"));
		System.err.println(boo);
	}

	// 删除文件
	@Test
	public void remove() throws Exception {
		boolean boo = fs.delete(new Path("/weric/a1.txt"), true);
		System.err.println("boo is:" + boo);
	}

	// 递归方式显示所有文件
	@Test
	public void listFiles() throws Exception {
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(new URI(uriAddr), config, "wangjian");
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/"), true);
		while (it.hasNext()) {
			LocatedFileStatus file = it.next();
			System.err.println(file.getPath());
		}
		fs.close();
	}

	// 查看hdfs所有的配置信息
	@Test
	public void printConfig() throws Exception {
		Configuration conf = fs.getConf();
		Iterator<Entry<String, String>> it = conf.iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			System.err.println(en.getKey() + "=" + en.getValue());
		}
	}

	//上传文件
	@Test
	public void uploadFiles() throws Exception {
	}

	// 查看所有文件的列表信息,及每一个文件的文件块信息
	@Test
	public void listFiles2() throws Exception {
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/hadoop"), true);
		while (it.hasNext()) {
			LocatedFileStatus st = it.next();
			// 获取权限
			String permission = st.getPermission().toString();
			System.err.print(permission);
			// 获取副本数量
			short repi = st.getReplication();
			System.err.print("\t" + repi);

			// 获取用户
			String user = st.getOwner();
			System.err.print("\t" + user);
			// 获取用户组
			String group = st.getGroup();
			System.err.print("\t" + group);
			// 获取大小
			long size = st.getLen();
			System.err.print("\t" + size);
			// 获取创建时间
			long time = st.getModificationTime();
			Date date = new Date(time);
			System.err.print("\t" + date.toLocaleString());
			// 获取文件名
			String name = st.getPath().toString();
			System.err.print("\t" + name + "\n");
			
			System.err.println("------------获取文件块信息------");
			BlockLocation[] blocks = st.getBlockLocations();
			for(BlockLocation block:blocks) {
				System.err.println("文件块开始："+block.getOffset()+"，到:"+block.getLength());
				System.err.println("主机："+Arrays.toString(block.getHosts()));
				System.err.println("主机 :ip为："+Arrays.toString(block.getNames()));
				System.err.println("StorageId:"+Arrays.toString(block.getStorageIds()));
				System.err.println("-----------------------------------");
			}
		}
	}
	/**
	 * 上传时通过Java配置副本数量为2
	 */
	@Test
	public void setReplication() throws Exception{
		config.set("dfs.replication","2");
		fs = FileSystem.get(new URI(uriAddr), config, "wangjian");
		fs.copyFromLocalFile(new Path("D:/a/a.txt"),new Path("/weric/a.txt"));
	}
}
