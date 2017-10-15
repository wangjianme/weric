package cn.hadoop.wordcount;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * wordcount示例
 * @author wangjian
 */
public class WordCountDemo {
	public static void main(String[] args) throws Exception {
		//3:声明有一个mapper和reducer任务
		Job job = Job.getInstance(new Configuration());
		//设置这个任务所在的类
		job.setJarByClass(WordCountDemo.class);
		//设置Mapper
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		//设置Reducer
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		//设置读取哪一个文件,接收用户的参数 args[0]
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		//设置输出的目录
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		//开始这个任务
		job.waitForCompletion(true);
	}
	//1:声明一个Map的子类
	public static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		@Override
		public void map(LongWritable key1, Text value1, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			//对value1进行计算 
			String value_1=value1.toString();
			String[] values = value_1.split("\\s");//可以根据空格，也可以是 tab
			//分别输出
			for(String v:values) {
				context.write(new Text(v),new LongWritable(1));
			}
		}
	}
	//2:开发Reducer
	public static class WordCountReducer extends Reducer<Text,LongWritable, Text,LongWritable>{
		@Override
		public void reduce(Text key3, Iterable<LongWritable> value3,
				Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
			long sum = 0;
			for(LongWritable w : value3) {
				sum+=w.get();//1+1+1+...
			}
			//输出
			context.write(key3, new LongWritable(sum));
		}
	}
}
