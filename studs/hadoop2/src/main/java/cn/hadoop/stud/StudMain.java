package cn.hadoop.stud;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.hadoop.stud.domain.Stud;

public class StudMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(StudMain.class);
		job.setMapperClass(StudMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Stud.class);
		job.setReducerClass(StudReducer.class);
		job.setOutputKeyClass(Stud.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.setInputPaths(job, args[0]);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		//启动
		System.exit(job.waitForCompletion(true)?0:1);
		
	}

	public static class StudMapper extends Mapper<LongWritable, Text, Text, Stud> {
		// 1:处理输入的value1
		@Override
		public void map(LongWritable key1, Text value1, Mapper<LongWritable, Text, Text, Stud>.Context context)
				throws IOException, InterruptedException {
			String[] str = value1.toString().split("\\s");
			Stud stud = new Stud();
			stud.setName(str[0]);
			stud.setMath(Integer.parseInt(str[1]));
			stud.setChinese(Integer.parseInt(str[2]));
			stud.setEnglish(Integer.parseInt(str[3]));
			// 输出
			context.write(new Text(str[0]), stud);
		}
	}
	
	public static class StudReducer extends Reducer<Text,Stud, Stud, NullWritable>{
		@Override
		public void reduce(Text key3, Iterable<Stud> value3, Reducer<Text, Stud, Stud, NullWritable>.Context context)
				throws IOException, InterruptedException {
			//进行平均值的算法
			Double avg = 0.0;
			int times =0;
			for(Stud stud:value3) {
				times++;
				stud.setAvg((stud.getMath()+stud.getChinese()+stud.getEnglish())/3.0);
				System.err.println("这个学生:"+stud.getName()+"的平均分是："+stud.getAvg());
				avg+=stud.getAvg();
			}
				
			System.err.println("这个学生平均总分是："+avg+",出现次数："+times);
			//最后的平均分
			avg = avg/times;
			//声明stud
			Stud stud  = new Stud();
			stud.setName(key3.toString());
			stud.setAvg(avg);
			//输出
			context.write(stud, NullWritable.get());
		}
	}
	

}
