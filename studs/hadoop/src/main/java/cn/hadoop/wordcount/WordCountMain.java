package cn.hadoop.wordcount;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * WordCount的示例
 */
public class WordCountMain {
	public static void main(String[] args) throws Exception {
		Log log = LogFactory.getLog(WordCountMain.class);//输出日志到控制台
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJobName("WordCountExample");// 可选：设置这个任务的名称将显示到MR中的appname中
		job.setJarByClass(WordCountMain.class);// 设置驱动类
		job.setMapperClass(WordCountMapper.class);// 可选:设置Mapper
		job.setMapOutputKeyClass(Text.class);// 可选：设置Mapper的输出类型Key2
		job.setMapOutputValueClass(LongWritable.class);// 设置Mapper的输出类型Value2
		
		job.setCombinerClass(WordCountCombiner.class);
		
		job.setReducerClass(WordCountReducer.class);// 设置Reducer
		job.setOutputKeyClass(Text.class);// 设置最后的输出类型Key4
		job.setOutputValueClass(LongWritable.class);// 设置最后的输出类型Value4
		job.setInputFormatClass(TextInputFormat.class);//可选：设置输入类，默认值
		FileInputFormat.setInputPaths(job, new Path(args[0]));// 只能使用FileInputFormat设置读取目录
		FileOutputFormat.setOutputPath(job, new Path(args[1]));// 设置输出目录
		log.info("程序提交完成等待运行完成......");
		System.exit(job.waitForCompletion(true) ? 0 : 1);// 启动MapReduce任务
	}

	/**
	 * 定义Mapper
	 */
	public static class WordCountMapper extends Mapper<LongWritable, Text, 
	    Text, LongWritable> {
		@Override
		public void map(LongWritable key, Text value, Mapper<LongWritable, Text,
				Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			String[] letters = value.toString().split("\\s+");// 根据空格或是Tab进行分割字符串
			for (String letter : letters) {// 遍历被分割的字符串然后直接输出，一个字符计数1次所以后面都是1
				context.write(new Text(letter), new LongWritable(1));
			}
		}
	}

	/**
	 * 定义Reducer，接收Mapper的输入
	 */
	public static class WordCountReducer extends Reducer<Text, LongWritable,
		Text, LongWritable> {
		@Override
		protected void reduce(Text value, Iterable<LongWritable> it,
				Reducer<Text, LongWritable, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			Long count = 0L;
			for (LongWritable t : it) {
				count += t.get();// 进行计数
			}
			context.write(value, new LongWritable(count));// 将计数的结果输出
		}
	}
	/**
	 * Combiner
	 */
	public static class WordCountCombiner 
	    extends Reducer<Text, LongWritable, Text, LongWritable>{
		@Override
		public void reduce(Text key3, Iterable<LongWritable> value3,
				Reducer<Text, LongWritable, Text, LongWritable>.Context ctx) 
						throws IOException, InterruptedException {
			Long count = 0L;
			for (LongWritable t : value3) {
				count += t.get();// 进行计数
			}
			ctx.write(key3, new LongWritable(count));// 将计数的结果输出
		}
	}
}
