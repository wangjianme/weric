package cn.hadoop.accessflow.sort;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import cn.hadoop.accessflow.AccessFlowWritable;

/**
 * 对输出的结果再排序
 * 
 * @author wangjian
 */
public class AccessFlowSortByCountMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJobName("AccessFlowSortByCount");
		job.setJarByClass(AccessFlowSortByCountMain.class);
		job.setMapperClass(AccessFlowSortByCountMapper.class);
		job.setMapOutputKeyClass(AccessFlowWritable.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setNumReduceTasks(2);
		Class cls = job.getPartitionerClass();
		System.err.println("默认的Partitioner类：" + cls);
		// 现在设置partitioner
		job.setPartitionerClass(AccessFlowPartitioner.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	// 只需要一个Mapper即可
	public static class AccessFlowSortByCountMapper
			extends Mapper<LongWritable, Text, AccessFlowWritable, NullWritable> {
		@Override
		public void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, AccessFlowWritable, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String[] strs = value.toString().split("\\s+");
			AccessFlowWritable accessFlowWritable = new AccessFlowWritable();
			accessFlowWritable.setIp(strs[0]);
			accessFlowWritable.setCount(Long.valueOf(strs[1]));
			accessFlowWritable.setFlow(Long.valueOf(strs[2]));
			context.write(accessFlowWritable, NullWritable.get());
		}
	}
	/**
	 * 开发Partitoner,如果访问量为50或以下，则为0，否则为1
	 */
	public static class AccessFlowPartitioner 
			extends Partitioner<AccessFlowWritable, NullWritable> {
		@Override
		public int getPartition(AccessFlowWritable key,
					NullWritable value, int numPartitions) {
			if (key.getCount() <= 50) {// 如果访问量小于=50
				return 0;
			} else {
				return 1;
			}
		}
	}
}
