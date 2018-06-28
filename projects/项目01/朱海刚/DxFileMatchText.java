package mr_job.dx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class DxFileMatchText {
public static class MatchMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FileSplit fS=(FileSplit)context.getInputSplit();
		String DXname=fS.getPath().getParent().getName();
		if(DXname.equals("DX01out")){
			String line=value.toString();
			String userId=line.split("\\|")[0];
//			用户id
			String YJname=line.split("\\|")[1];
//			一级域名
			String url=line.split("\\|")[2];
//			URL
			context.write(new Text(), new Text("A:"+"|"+userId+"|"+YJname+"|"+url));
		}if (DXname.equals("DXMatch")){
			String line=value.toString();
			String XWId=line.split("\\|")[0];
//			行为ID
			String CP=line.split("\\|")[4];
//			是否为产品
			String YJname=line.split("\\|")[1];
//					一级域名
			String YG=line.split("\\|")[5];
//			预购类型
			
			context.write(new Text(), new Text("B:"+"|"+XWId+"|"+CP+"|"+YJname+"|"+YG));
		}
		
	}
}
public static class Mathreduce extends Reducer<Text, Text, NullWritable, Text>{
	HashMap<String,ArrayList<String>> hashMap1=new HashMap<String,ArrayList<String>>();
	HashMap<String,ArrayList<String>> hashMap2=new HashMap<String,ArrayList<String>>();
	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		
		
		for (Text t : values) {
			
			if(t.toString().startsWith("A:")){
				ArrayList<String> list =new ArrayList<String>();
				String YJname=t.toString().split("\\|")[2];
				
//				截取一级域名
				String userID=t.toString().split("\\|")[1];
//				用户ID
				String url=t.toString().split("\\|")[3];
//				url
				list.add(url+" "+userID);
				hashMap1.put(YJname, list);
			}if(t.toString().startsWith("B:")){
				ArrayList<String> list =new ArrayList<String>();
				String XWID=t.toString().split("\\|")[1];
//				行为ID
				String CP=t.toString().split("\\|")[2];
//				产品
				String YJname=t.toString().split("\\|")[3];
//				System.out.println(YJname);
//				一级域名
				String YG=t.toString().split("\\|")[4];		
//				预购类型
				
				list.add(YG+" "+CP+" "+XWID+"");
				hashMap2.put(YJname, list);
			}
			
		}
		
		     
		      
	}
	@Override
	protected void cleanup(Reducer<Text, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		 Set<String> mapkey=hashMap1.keySet();
//		 for (String str : mapkey) {
//			System.out.println(str);
//		}
	      for(String k:mapkey){
//    	  System.out.println(k);
	    	  ArrayList<String> list1 =new ArrayList<String>();
	    	  
	    	  ArrayList<String> array1=hashMap2.get(k);
	    	  ArrayList<String> array=hashMap1.get(k);
	    	  
	    	  for (int i = 0; i < array.size(); i++) {
	    		  StringBuffer sb=new StringBuffer();
				sb.append(array.get(i));
				if(hashMap2.containsKey(k)){
					sb.append(array1.get(i));
				}
				list1.add(sb.toString());
				
			} /*{
//	    		  System.out.println(str);
				sb.append(str);
			}
	    	  if(hashMap2.containsKey(k)){
	    		  ArrayList<String> array1=hashMap2.get(k);
	    		  for (String str1 : array1) {
//	    			  System.out.println(str1);
	    			  sb.append(str1);
				}
	    	  }
	    	  list1.add(sb.toString());*/
	    	for (String arr : list1) {
	    		System.out.println(arr);
//	    		String XWID=arr.split(" ")[4];
//	    		String userID=arr.split(" ")[1];
//	    		String CP =arr.split(" ")[3];
//	    		String url=arr.split(" ")[0];
//	    		String YG=arr.split(" ")[2];
//				context.write(NullWritable.get(), new Text(XWID+"|"+userID+"|"+CP+"|"+url+"|"+YG));
			}
	      }
	     
	}
}
public static void main(String[] args)  {
	try{
	Configuration conf =new Configuration();
	Job job=Job.getInstance(conf);
	job.setJarByClass(DxFileMatchText.class);
	job.setMapperClass(MatchMapper.class);
//	job.setInputFormatClass(KeyValueTextInputFormat.class);
	job.setReducerClass(Mathreduce.class);
	job.setOutputKeyClass(NullWritable.class);
	job.setOutputValueClass(Text.class);
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(Text.class);
	FileInputFormat.addInputPath(job,  new Path("hdfs://192.168.217.3:9000/DX01out"));
	FileInputFormat.addInputPath(job,  new Path("hdfs://192.168.217.3:9000/DXMatch"));
	FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.217.3:9000/DXMatchout"));
	job.waitForCompletion(true);
	} catch (Exception e) {
		System.out.println(e.getMessage()+"11111111111");
	}
}
}
