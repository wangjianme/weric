package cn.hadoop.input1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Input1Main {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(Input1Main.class);
		job.setMapperClass(Input1Mapper.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		//注册自定义输入类
		job.setInputFormatClass(Input1Format.class);
		//
		FileInputFormat.setInputPaths(job, args[0]);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	public static class Input1Mapper extends Mapper<IntWritable, Text, LongWritable, Text> {
		@Override
		public void map(IntWritable key, Text value, Mapper<IntWritable, Text, LongWritable, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(new LongWritable(key.get()), value);
		}
	}

	// 1:自定义输入类
	public static class Input1Format extends FileInputFormat<IntWritable, Text> {
		// 创建一个读取文件的输入流对象
		@Override
		public RecordReader<IntWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
				throws IOException, InterruptedException {
			return new Input1Reader();
		}
	}

	// 2:自定义读取文件的io流
	public static class Input1Reader extends RecordReader<IntWritable, Text> {
		// 初始化声明io流的，用于读取数据
		private BufferedReader br;
		// 声明行号
		private int line = 0;
		// 声明这一行的数据
		private String data = "";
		// 声明字节总量
		private int size;// 字节总量
		private int sum;// 读取到多少字节了

		@Override
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			// 初始化行读 InputSplit - 文件块 - 128M
			// 获取这个文件块的io流
			FileSplit fs = (FileSplit) split;
			FileSystem fileSystem = fs.getPath().getFileSystem(context.getConfiguration());
			InputStream in = fileSystem.open(fs.getPath());
			//获取字节总最
			size = in.available();
			// 实例化br
			br = new BufferedReader(new InputStreamReader(in));
		}

		// 此方法用于判断是否还有下一行数据
		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			data = br.readLine();
			if (data != null) {// 有数据
				line++;
				return true;
			} else {// 没有读取到数据
				return false;
			}
		}

		// 获取当前的key
		@Override
		public IntWritable getCurrentKey() throws IOException, InterruptedException {
			return new IntWritable(line);
		}

		// 获取当前的数据
		@Override
		public Text getCurrentValue() throws IOException, InterruptedException {
			sum+=data.getBytes().length;
			return new Text(data);
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			return 1F*sum/size;
		}

		// 关闭输入流
		@Override
		public void close() throws IOException {
			if (br != null) {
				br.close();
			}
		}

	}
}
