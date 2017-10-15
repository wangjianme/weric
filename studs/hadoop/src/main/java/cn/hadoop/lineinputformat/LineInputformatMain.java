package cn.hadoop.lineinputformat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
//mapreduce包下的类，都是新的api
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * 自定义输入流示例，定义行读，返回行号+TextLine
 */
public class LineInputformatMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJobName("LineReader");
		job.setJarByClass(LineInputFormat.class);
		job.setMapperClass(LineMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setInputFormatClass(LineInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	// 输入以后直接输出
	public static class LineMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
		@Override
		public void map(LongWritable key, Text value, Mapper<LongWritable, Text, LongWritable, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}
	}
	// 自定义InputFormat
	public static class LineInputFormat extends FileInputFormat<LongWritable, Text> {
		@Override
		public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
				throws IOException, InterruptedException {
			return new LineReader();// 直接返回自定义LineReader的实例
		}
	}
	// 读取数据的Reader
	public static class LineReader extends RecordReader<LongWritable, Text> {
		private Long key = 0L;// 行号
		private String line;
		BufferedReader reader = null; // 声明行读对象
		float size;// 文件总大小
		float len;// 已经读取的文件大小
		@Override // 用于读取文件分片信息获取IO流对象
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			FileSplit fs = (FileSplit) split;
			FileSystem hdfs = // 获取hdfs操作对象
					FileSystem.get(context.getConfiguration());
			FSDataInputStream fsDataInputStream = hdfs.open(fs.getPath());
			size = fsDataInputStream.available();// 获取文件总大小
			reader = new BufferedReader(new InputStreamReader(fsDataInputStream));
			System.err.println("输入流初始化完成，文件总大小为："+size);
		}
		@Override // 用于判断是否还存在数据
		public boolean nextKeyValue() throws IOException, InterruptedException {
			line = reader.readLine();
			if (line != null) {
				return true;// 如果有数据则返回true
			}
			return false;
		}
		@Override // 读取当前的key,只是nextKeyValue返回true时才有数据
		public LongWritable getCurrentKey() throws IOException, InterruptedException {
			key++;// 行号+1
			return new LongWritable(key);
		}
		@Override // 返回当前的Line,只有当nextKeyValue返回true时才有数据
		public Text getCurrentValue() throws IOException, InterruptedException {
			//只要返回数据，当前读取的数据就自动增加
			float bytes = line.getBytes().length;
			len+=bytes;
			return new Text(line);
		}
		@Override // 返回进度，如果知道进度的话
		public float getProgress() throws IOException, InterruptedException {
			System.err.println("进度："+(len/size));
			return len/size;
		}
		@Override // 关闭
		public void close() throws IOException {
			if (reader != null) {
				System.err.println("关闭输入流.....");
				reader.close();
			}
		}
	}
}
