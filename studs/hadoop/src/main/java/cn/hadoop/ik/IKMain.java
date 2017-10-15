package cn.hadoop.ik;
import java.io.IOException;
import java.io.StringReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
/**
 * 使用IK对中文进行分词统计
 */
public class IKMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(IKMain.class);
		job.setJobName("IKAnalyzer");
		job.setMapperClass(IKMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setReducerClass(IKReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	// 开发Mapper
	public static class IKMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
		Log log = LogFactory.getLog(IKMapper.class);//请为每一个类设置一个日志输出对象
		@Override
		public void map(LongWritable key, Text value, 
				Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			IKSegmenter ik = new IKSegmenter(new StringReader(value.toString()), true);
			Lexeme lexeme = null;
			// 获取文件名称
			InputSplit split = context.getInputSplit();
			String fileName = "";
			if (split instanceof FileSplit) {
				FileSplit fileSplit = (FileSplit) split;
				String path = fileSplit.getPath().getName();
				fileName=path;
			}
			System.err.println("获取到的文件名为：" + fileName);
			log.info("日志获取到的文件名称为："+fileName);
			while ((lexeme = ik.next()) != null) {// 判断是否有下一个元素
				String str = lexeme.getLexemeText();
				context.write(new Text(str+"\t"+fileName), new LongWritable(1));
			}
		}
	}

	// 开发Reducer
	public static class IKReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
		@Override
		protected void reduce(Text text, Iterable<LongWritable> it,
				Reducer<Text, LongWritable, Text, LongWritable>.Context ctx) throws IOException, InterruptedException {
			Long sum = 0L;
			for (LongWritable w : it) {
				sum += w.get();
			}
			ctx.write(text, new LongWritable(sum));
		}
	}

}
