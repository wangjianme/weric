package cn.hadoop.access;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class Access implements Writable {
	private String ip;
	private Long sum;
	private Integer count;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(ip);
		out.writeLong(sum);
		out.writeInt(count);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		ip = in.readUTF();
		sum = in.readLong();
		count = in.readInt();
	}

	@Override
	public String toString() {
		return ip + "\t" + sum + "\t" + count;
	}
	
}
