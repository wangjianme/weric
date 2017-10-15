package cn.hadoop.excel;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * 解析Excel中的数据
 * @version 2017-08
 */
public class ExcelMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJobName("ExcelParser");
		job.setJarByClass(ExcelMain.class);
		job.setMapperClass(ExcelMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Telephone.class);
		job.setReducerClass(ExcelReducer.class);
		job.setOutputKeyClass(Telephone.class);
		job.setOutputValueClass(NullWritable.class);
		job.setInputFormatClass(ExcelInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.err.println("启动.....");
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	// 输入什么直接输出即可
	public static class ExcelMapper extends Mapper<Text, Telephone, Text, Telephone> {
		@Override
		public void map(Text key, Telephone value, Mapper<Text, Telephone, Text, Telephone>.Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}
	}

	public static class ExcelReducer extends Reducer<Text, Telephone, Telephone, NullWritable> {
		@Override
		public void reduce(Text text, Iterable<Telephone> it,
				Reducer<Text, Telephone, Telephone, NullWritable>.Context ctx)
				throws IOException, InterruptedException {
			Telephone tel = new Telephone();
			for (Telephone tt : it) {
				tel.setTel(tt.getTel());
				tel.setType(tt.getType());
				tel.addTimeLong(tt.getTime());
			}
			ctx.write(tel, NullWritable.get());
		}
	}

	// 自定义输入类
	public static class ExcelInputFormat extends FileInputFormat<Text, Telephone> {
		@Override
		public RecordReader<Text, Telephone> createRecordReader(InputSplit split, TaskAttemptContext context)
				throws IOException, InterruptedException {
			return new ExcelReader();
		}
	}

	// 自定义读取类
	public static class ExcelReader extends RecordReader<Text, Telephone> {
		private Telephone telephone;
		private HSSFWorkbook book;
		private HSSFSheet sheet;
		private float lastRowNum;
		private float currentRowNum = 1;
		@Override
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			FileSplit file = (FileSplit) split;
			FileSystem dfs = FileSystem.get(context.getConfiguration());
			FSDataInputStream in = dfs.open(file.getPath());
			book = new HSSFWorkbook(in);
			sheet = book.getSheetAt(0);
			lastRowNum = sheet.getLastRowNum() - 1;// 获取最后的行号,第一行不算
			System.err.println("Excel读取文件对象初始化完成,一共多少行：" + lastRowNum);
		}
		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			if (currentRowNum < lastRowNum) {// 说明后面还有记录
				HSSFRow row = sheet.getRow((int) currentRowNum);
				String celTel = row.getCell(5).getStringCellValue();
				String celType = row.getCell(4).getStringCellValue();
				String celTime = row.getCell(3).getStringCellValue();
				telephone = new Telephone(celTel, celType, celTime);
				currentRowNum++;
				return true;
			}
			return false;
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			Text txt = new Text(telephone.getTel() + "\t" + telephone.getType());
			return txt;
		}

		@Override
		public Telephone getCurrentValue() throws IOException, InterruptedException {
			return telephone;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			System.err.println("当前进度：" + currentRowNum + "/" + lastRowNum);
			return (currentRowNum / lastRowNum);
		}

		@Override
		public void close() throws IOException {
			if (book != null) {
				book.close();
			}
		}
	}
}
