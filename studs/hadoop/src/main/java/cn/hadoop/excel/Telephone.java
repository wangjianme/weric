package cn.hadoop.excel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 封装对象并定义一些方法进行数据转换
 */
public class Telephone implements Writable {
	private String tel;
	private String type;// 主叫，裭叫
	private long time=0L;// 时长

	public Telephone() {
	}

	public Telephone(String tel, String type, String time) {
		this.tel = tel;
		this.type = type;
		this.time = toTimeLong(time);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(tel);
		out.writeUTF(type);
		out.writeLong(time);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		tel = in.readUTF();
		type = in.readUTF();
		time = in.readLong();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setTime(String time) {
		this.time = toTimeLong(time);
	}
	public long getTime() {
		return time;
	}

	public String getTimeString() {
		return toTimeString(this.time);
	}

	public void addTimeLong(long time) {//时间加
		this.time += time;
	}

	@Override
	public String toString() {
		return tel + "\t" + getType() + "\t" + getTimeString();
	}

	// 换算成秒
	private long toTimeLong(String time) {
		long tt = 0L;
		if (time.contains("分")) {
			String min = time.substring(0, time.indexOf("分"));
			tt = Long.parseLong(min) * 60;
			time = time.substring(time.indexOf("分") + 1);
		}
		time = time.substring(0, time.length() - 1);
		tt += Long.parseLong(time);
		return tt;
	}

	// 换成秒
	private String toTimeString(long time) {
		String tt = null;
		if (time > 60) {
			tt = time / 60 + "分" + (time % 60) + "秒";
		} else {
			tt = time + "秒";
		}
		return tt;

	}
}
