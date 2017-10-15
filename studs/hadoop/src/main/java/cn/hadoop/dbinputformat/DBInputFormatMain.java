package cn.hadoop.dbinputformat;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * MySql表导入hdfs<br>
 * 请将mysql-driver.jar包放到share/mapreduce/lib目录下
 * 
 * @author wangjian
 */
public class DBInputFormatMain {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJobName("DBInputFormat");
		// 指定mysql数据库地址
		String url = "jdbc:mysql://192.168.56.1:3306/weric?characterEncoding=UTF-8";
		DBConfiguration.configureDB(job.getConfiguration(), "com.mysql.jdbc.Driver", 
				url, "root", "1234");
		job.setJarByClass(DBInputFormatMain.class);
		job.setMapperClass(DBMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		// 指定输入类为 DBInputFormat,并指定SQL语句
		job.setInputFormatClass(DBInputFormat.class);
		DBInputFormat.setInput(job, UserBean.class, "select id,name,pwd from users",
					"select count(1) from users");
		// 指定输出目标
		FileOutputFormat.setOutputPath(job, new Path(args[0]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	// 内部类必须定义成静态
	public static class DBMapper extends Mapper<LongWritable, UserBean, Text, NullWritable> {
		@Override
		public void map(LongWritable key, UserBean value,
				Mapper<LongWritable, UserBean, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(new Text(value.toString()), NullWritable.get());
		}
	}
	// 1:声明JavaBean如果是内部类，必须定义成静态
	public static class UserBean implements Writable, DBWritable {
		private String id;
		private String name;
		private String pwd;
		@Override
		public void write(PreparedStatement st) throws SQLException {
			st.setString(1, id);
			st.setString(2, name);
			st.setString(3, pwd);
		}

		@Override
		public void readFields(ResultSet rs) throws SQLException {
			id = rs.getString("id");
			name = rs.getString("name");
			pwd = rs.getString("pwd");
		}

		@Override
		public void write(DataOutput out) throws IOException {
			out.writeUTF(id);
			out.writeUTF(name);
			out.writeUTF(pwd);
		}

		@Override
		public void readFields(DataInput in) throws IOException {
			id = in.readUTF();
			name = in.readUTF();
			pwd = in.readUTF();
		}

		@Override
		public String toString() {
			return id + "\t" + name + "\t" + pwd;
		}
	}

}
