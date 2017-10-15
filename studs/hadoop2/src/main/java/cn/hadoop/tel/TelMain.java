package cn.hadoop.tel;

import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TelMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(TelMain.class);
		job.setMapperClass(TelMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Telphone.class);
		job.setReducerClass(TelReduer.class);
		job.setOutputKeyClass(Telphone.class);
		job.setOutputValueClass(NullWritable.class);
		job.setInputFormatClass(TelInputFormat.class);
		FileInputFormat.setInputPaths(job, args[0]);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
	}

	// 19088 主叫=[ {},{},{} ,{} ]
	public static class TelMapper extends Mapper<Text, Telphone, Text, Telphone> {
		@Override
		public void map(Text key, Telphone value, Mapper<Text, Telphone, Text, Telphone>.Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}
	}

	public static class TelReduer extends Reducer<Text, Telphone, Telphone, NullWritable> {
		@Override
		public void reduce(Text key3, Iterable<Telphone> value3,
				Reducer<Text, Telphone, Telphone, NullWritable>.Context ctx) throws IOException, InterruptedException {
			long sum = 0;
			Telphone tt = new Telphone();
			for (Telphone t : value3) {
				sum += t.getSeconds();
				tt.setTel(t.getTel());
				tt.setType(t.getType());
			}
			tt.setSeconds(sum);
			ctx.write(tt, NullWritable.get());
		}
	}

	// 1:自定义input 【19087675 主叫】 :
	public static class TelInputFormat extends FileInputFormat<Text, Telphone> {
		@Override
		public RecordReader<Text, Telphone> createRecordReader(InputSplit split, TaskAttemptContext context)
				throws IOException, InterruptedException {
			return new TelRecordReader();
		}
	}

	// 2:开发读取excel的类
	public static class TelRecordReader extends RecordReader<Text, Telphone> {
		// 声明返回的key
		private Telphone telphone;
		// 声明book
		private HSSFWorkbook book;
		HSSFSheet sheet;
		//
		int size = 0;
		int current = 1;

		@Override
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			FileSplit fileSplit = (FileSplit) split;
			FileSystem fs = FileSystem.get(context.getConfiguration());
			InputStream in = fs.open(fileSplit.getPath());
			book = new HSSFWorkbook(in);
			sheet = book.getSheetAt(0);
			// 获取行数
			size = sheet.getLastRowNum() - 1;
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			if (current <= size) {
				// 获取里面的数据
				HSSFRow row = sheet.getRow(current);
				HSSFCell cel = row.getCell(3);// 通话时间长度
				String str = cel.getStringCellValue();// 1分24秒 = 84秒
				// 转成秒
				long second = toSecond(str);
				//
				String type = row.getCell(4).getStringCellValue();
				String tel = row.getCell(5).getStringCellValue();
				telphone = new Telphone();
				telphone.setTel(tel);
				telphone.setSeconds(second);
				telphone.setType(type);
				current++;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			return new Text(telphone.getTel() + "\t" + telphone.getType());
		}

		@Override
		public Telphone getCurrentValue() throws IOException, InterruptedException {
			return telphone;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			return 1F * current / size;
		}

		@Override
		public void close() throws IOException {
			if (book != null) {
				book.close();
			}

		}

	}

	private static long toSecond(String str) {
		long sec = 0;
		if (str.contains("分")) {// 1分24秒
			String ss = str.substring(0, str.lastIndexOf("分"));
			sec = Long.parseLong(ss) * 60;
			str = str.substring(str.lastIndexOf("分") + 1);
		}
		str = str.substring(0, str.length() - 1);
		sec += Long.parseLong(str);
		return sec;
	}
}
