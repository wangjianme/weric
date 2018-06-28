package mr_job.dx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import mr_job.tools.StringComparator;
import mr_job.tools.TProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.HashPartitioner;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DxFileMatch {

	public static void main(String[] args) {

		try {
			Configuration conf = new Configuration();
//			conf.set("mapreduce.map.memory.mb", "5120");
			//不检查超时，由于集群环境不稳定有超时现象，所以设置为不检查，但需要保证程序无死循环
//			conf.set("mapred.task.timeout", "0");
//			conf.set("dfs.client.block.write.replace-datanode-on-failure.policy","NEVER"); 
//			conf.set("dfs.client.block.write.replace-datanode-on-failure.enable","true"); 
			//conf.set("mapreduce.reduce.memory.mb", "2048");
			// 获取命令行的参数
//			String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

			// 添加到内存中的文件(随便添加多少个文件)
			DistributedCache.addCacheFile(new Path("hdfs://192.168.217.3:9000/DXMatch/t_dx_basic_msg_addr.txt").toUri(), conf);

			Job job = new Job(conf, "Dx_FileMatch");
			job.setJarByClass(DxFileMatch.class);
			FileInputFormat.setInputPaths(job, "hdfs://192.168.217.3:9000/DX01out");

			job.setMapperClass(MapJoinMapper.class);
			job.setMapOutputKeyClass(NullWritable.class);
			job.setMapOutputValueClass(Text.class);

			job.setNumReduceTasks(0);

			FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.217.3:9000/DXMatchout"));
			System.exit(job.waitForCompletion(true) ? 0 : 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * map绔痡oin
	 * */
	public static class MapJoinMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
		
		private Text ovalue = new Text();
		//Map<一级域名, TreeMap<全部内容>>，用treemap按优先级排序
		private Map<String, TreeMap<String, String>> joinData = new HashMap<String, TreeMap<String, String>>();

		/**
		 * setup在map之前执行，加载地址库
		 * */
		@Override
		protected void setup( Mapper<LongWritable, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException {
			// 预处理把要关联的文件加载到缓存中
			Path[] paths = DistributedCache.getLocalCacheFiles(context.getConfiguration());
			// 我们这里只缓存了一个文件，所以取第一个即可，创建BufferReader去读取
			BufferedReader reader = new BufferedReader(new FileReader("t_dx_basic_msg_addr.txt"));

			String str = null;
			try {
				// 一行一行读取
				while ((str = reader.readLine()) != null) {
					// 对缓存中的表进行分割
					String[] splits = str.split(TProperties.getValue("fileoutsplit"));
					//map是否包含一级域名
					if (joinData.containsKey(splits[1])) {
//						System.out.println(splits[1]);
						//增加treemap中url值
						joinData.get(splits[1]).put(splits[2] + "," + splits[3] + "," + splits[0] +","+ splits[4]+ "," + splits[5], "");
					} else {
						//创建map中key值，添加treemap，StringComparator修改treemap排序方式
						TreeMap<String, String> treemap = new TreeMap<String, String>( new StringComparator());
						//url地址,匹配级别,行为ID,是否产品
						treemap.put(splits[2] + "," + splits[3]+ "," + splits[0] +","+ splits[4] + "," + splits[5], "");
						//key一级域名
						joinData.put(splits[1], treemap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		}
		/**
		 * map端循环匹配数据，不使用reduce部分提高处理效率。
		 * */
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException {
			// 获取从HDFS中加载的表
			String[] values = value.toString().split(TProperties.getValue("fileoutsplit"));

			if (joinData.containsKey(values[1])) {
				//获取一级域名下匹配地址
				TreeMap<String, String> treeMap1 = joinData.get(values[1]);
				Iterator iterator = treeMap1.keySet().iterator();
				//按级别循环匹配
				while (iterator.hasNext()) {
					String[] krule = ((String) iterator.next()).split(",");
					if (values[2].indexOf(krule[0]) >= 0) {
						//行为id，用户号码，是否产品，url,预购类型
						StringBuffer sb = new StringBuffer();
						
						sb.append(krule[2]).append(TProperties.getValue("outfilesplit"))
						.append(values[0]).append(TProperties.getValue("outfilesplit"))
						.append(krule[3]).append(TProperties.getValue("outfilesplit"))
						.append(values[2]).append(TProperties.getValue("outfilesplit"))
						.append(krule[4]);

						ovalue = new Text( sb.toString() );
						//匹配后输出并退出匹配循环
						context.write(NullWritable.get(), ovalue);
						return ;
					}
				}
			}
		}
	}
}
