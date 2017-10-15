package cn.hadoop.accessflow;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Tomcat访问统计，统计访问次数，访问的流量总和<br 文件为：tomcat_home/log/access_log.txt
 */
public class AccessFlowMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJobName("AccessFlow");
		job.setJarByClass(AccessFlowMain.class);
		job.setMapperClass(AccessFlowMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(AccessFlowWritable.class);
		job.setReducerClass(AccessFlowReducer.class);
		job.setOutputKeyClass(AccessFlowWritable.class);
		job.setOutputValueClass(NullWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.err.println("启动...");
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	// 定义Mapper
	public static class AccessFlowMapper extends Mapper<LongWritable, Text, 
						Text, AccessFlowWritable> {
		@Override
		public void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, AccessFlowWritable>.Context context)
				throws IOException, InterruptedException {
			String[] strs = value.toString().split("\\s+");
			AccessFlowWritable ac = new AccessFlowWritable();
			if (strs[strs.length - 1].matches("\\d+")) {// 判断最后是数字
				ac.setFlow(Long.parseLong(strs[strs.length - 1]));
			} else {
				ac.setFlow(0L);// 有可能字节流量为-,即没有流量则为0
			}
			ac.setCount(1L);// 只要访问，访问次数+1
			ac.setIp(strs[0]);// 设置ip地址
			context.write(new Text(ac.getIp()), ac);
		}
	}

	// 开发Reducer,注意最后的输出是一个NullWritable
	public static class AccessFlowReducer extends Reducer<Text, AccessFlowWritable, 
										AccessFlowWritable, NullWritable> {
		@Override
		protected void reduce(Text text, Iterable<AccessFlowWritable> it,
				Reducer<Text, AccessFlowWritable, AccessFlowWritable, NullWritable>.Context ctx)
				throws IOException, InterruptedException {
			Long count = 0L;
			Long flowSum = 0L;
			AccessFlowWritable accessFlowWritable = new AccessFlowWritable();
			accessFlowWritable.setIp(text.toString());
			for (AccessFlowWritable ac : it) {// 现在开始合并数据
				count += ac.getCount();
				flowSum += ac.getFlow();
			}
			accessFlowWritable.setCount(count);
			accessFlowWritable.setFlow(flowSum);
			ctx.write(accessFlowWritable, NullWritable.get());// 输出数据
		}
	}
}
