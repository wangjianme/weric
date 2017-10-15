package cn.hadoop.tel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class Telphone implements Writable {
	private String tel;
	private String type;// 主叫，被叫
	private long seconds;

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

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return tel + "\t" + type + "\t" + toStr(seconds);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(tel);
		out.writeUTF(type);
		out.writeLong(seconds);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		tel = in.readUTF();
		type = in.readUTF();
		seconds = in.readLong();
	}

	private String toStr(long second) {// 84
		String str = "";
		if(second>60) {
			long mi = second/60;//只取整数 
			str=mi+"分";
		}
		long sec = second%60;
		str=str+sec+"秒";
		return str;
	}

}
