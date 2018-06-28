package mr_job.dx;

import java.io.IOException;

import mr_job.tools.TMatcher;
import mr_job.tools.TProperties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DxFileClear {

	/**
	 * @param 
	 * 
	 * 
	 * Description：通过map处理，将不符合规范、特殊格式（配置文件、图片等）数据过滤掉。
	 * */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		//设置map内存使用
//		conf.set("mapreduce.map.memory.mb", "2048");
		Job job = new Job(conf, "Dx_FileClear");

		job.setJarByClass(DxFileClear.class);
		
		job.setMapperClass(ClearMapper.class);
	
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setNumReduceTasks(0);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		
	    TextInputFormat.setMinInputSplitSize(job,1024*1024*64L); 
	    TextInputFormat.setMaxInputSplitSize(job,1024*1024*128L); 
	   
		FileInputFormat.addInputPath(job, new Path("hdfs://192.168.217.3:9000/DX01"));
		
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.217.3:9000/DX01out"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static class ClearMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
		//定义输出类型
		

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String[] str = value.toString().split("");
			 	// 判断数据字段数量，判断url字段是否有效，去除特定格式
//			System.out.println("str"+str);
//			System.out.println(str[1]);
//			System.out.println("TProperties.getValue"+TProperties.getValue("filesplit"));
			if ((str.length + "").equals("15")
					&& !"http://".equals(str[14])
					
					&& !"https://".equals(str[14])
					&& !"".equals(str[14])
					&& !(str[14].toLowerCase()).matches(".*\\.(jpg|png|bmp|jpeg|tif|gif|psd|ico|pdf|css|tmp|js|gz|rar|gzip|zip|txt|csv|xlsx|xls)(\\W.*|$)")) {
//				toLowerCase()对字符串进行小写转换
//				System.out.println("str[14]"+str[14]);
				StringBuffer sb = new StringBuffer();
				if (!str[14].startsWith("http://") && !str[14].startsWith("https://")) {
					str[14] = "http://" + str[14];
				}
				// 获取域名
				String domain = str[14].split("/", -1)[2];
				System.out.println(domain);
				// 去除端口
				if (domain.indexOf(":") >= 0) {
					domain = domain.split("\\:", -1)[0];
				}
				System.out.println(domain);
//				\\:的意思的转义：
				// 用户号码|一级域名|URL地址
				sb.append(str[1]).append("|")
						.append(TMatcher.getDomain(domain)).append("|")
						.append(str[14]);

				
				context.write(NullWritable.get(),new Text(sb.toString()));
			}
		}
	}

}


