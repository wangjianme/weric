package cn.hadoop.hdfs;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class HdfsBlockDemo {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		URI uri = new URI("hdfs://hadoop201:8020");
		FileSystem fs = FileSystem.get(uri, conf, "wangjian");
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/weric"), true);
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
			for (BlockLocation block : blocks) {
				System.err.println("文件块开始：" + block.getOffset() + "，到:" + block.getLength());
				System.err.println("主机：" + Arrays.toString(block.getHosts()));
				System.err.println("主机 :ip为：" + Arrays.toString(block.getNames()));
				System.err.println("-----------------------------------");
			}
		}

	}
}
