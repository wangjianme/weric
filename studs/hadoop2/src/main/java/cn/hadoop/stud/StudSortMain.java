package cn.hadoop.stud;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.hadoop.stud.domain.Stud;

public class StudSortMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(StudSortMain.class);
		job.setMapperClass(StudSortMapper.class);
		job.setOutputKeyClass(Stud.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.setInputPaths(job, args[0]);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	public static class StudSortMapper extends Mapper<LongWritable, Text, Stud, NullWritable> {
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Stud, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String[] str = value.toString().split("\\s");
			Stud stud = new Stud();
			stud.setName(str[0]);
			stud.setAvg(Double.parseDouble(str[1]));
			// 直接输出
			context.write(stud, NullWritable.get());
		}
	}
}
