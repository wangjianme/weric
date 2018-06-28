package mr_job.dx;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DxFileMatchClent {
	public static class MatchMapper extends Mapper<LongWritable, Text, Text, Text>{

		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
//			System.out.println("111111111111111111111");
			String line =value.toString();		
//			System.out.println(line);
			String XWID=line.split("\\|")[0];
//			行为id
			String YJname=line.split("\\|")[1];
//			一级域名
			String path=line.split("\\|")[2];
//			地址
			int JB=Integer.parseInt(line.split("\\|")[3]);
//			地址级别
			String BS=line.split("\\|")[4];
//			是否产品标识
			String CP=line.split("\\|")[5];
//			System.out.println(CP);
//			预购类型
			context.write(new Text("1"), new Text(path+"|"+BS+"|"+CP));
			System.out.println(new People(XWID, YJname, JB).toString());
			
//			System.out.println("-----------------");
		}
	}
	public static class Mathreduce extends Reducer<Text, Text, Text, Text>{
	
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println("22222222222");
			// TODO Auto-generated method stub
			ArrayList<String> arr=new ArrayList<String>();
			System.out.println(key.toString());
			for (Text text : values) {
				arr.add(key.toString()+text.toString());
			}
				context.write(new Text(arr.get(1)), new Text());
		}
	}
	
	
	public static void main(String[] args)  {
		try{
		Configuration conf =new Configuration();
		Job job=Job.getInstance(conf);
		job.setJarByClass(DxFileMatchClent.class);
		
		job.setMapperClass(MatchMapper.class);
		
//		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setReducerClass(Mathreduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job,  new Path("hdfs://192.168.217.3:9000/DXMatch"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.217.3:9000/DXMatchclentout1"));
		job.waitForCompletion(true);
		} catch (Exception e) {
			System.out.println(e.getMessage()+"11111111111");
		}
	}
}
