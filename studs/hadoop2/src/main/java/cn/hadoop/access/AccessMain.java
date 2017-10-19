package cn.hadoop.access;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Test;

public class AccessMain {
	/**
	 * 一个类，在MR程序中，如果有main函数，这个类，叫驱动类
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(AccessMain.class);
		job.setMapperClass(AccessMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Access.class);
		
		job.setCombinerClass(AccessReducer.class);
		
		//job.setReducerClass(AccessReducer.class);
		job.setOutputKeyClass(Access.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.setInputPaths(job, args[0]);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		//在开始之前，要设置有多少个Reducer运行
		//job.setNumReduceTasks(2);
		//声明Partitioner类的子类
		//job.setPartitionerClass(AccessPartitioner.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
	//开发Combiner
	
	
	

	public static class AccessMapper extends Mapper<LongWritable, Text, Text, Access> {
		@Override
		public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Access>.Context context)
				throws IOException, InterruptedException {
			String[] str = value.toString().split("\\s");
			String ip = str[0];
			String ss = str[str.length - 1];// 总是最后一个
			long sum = 0;
			if (ss.matches("\\d+")) {// 是娄字
				sum = Long.parseLong(ss);
			}
			Access access = new Access();
			access.setIp(ip);
			access.setSum(sum);
			access.setCount(1);
			context.write(new Text(ip), access);
		}
	}
	
	public static class AccessReducer extends Reducer<Text, Access, Text,Access> {
		@Override
		public void reduce(Text text, Iterable<Access> val, Reducer<Text, Access, Text, Access>.Context ctx)
				throws IOException, InterruptedException {
			Access access = new Access();
			access.setIp(text.toString());
			access.setSum(0L);
			access.setCount(0);
			for (Access a : val) {
				access.setSum(access.getSum() + a.getSum());
				access.setCount(access.getCount() + 1);
			}
			ctx.write(text, access);
		}
	}
	
	//声明分区的类,Partitioner用于在Mapper - Partitioner - >Reducer
	public static class AccessPartitioner extends Partitioner<Text,Access>{
		@Override
		public int getPartition(Text key, Access value, int numPartitions) {
			if(value.getCount()<10) {
				return 1;
			}
			return 0;//否则都返回0
		}
		
	}
}
