package cn.hadoop.accessflow;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;

/**
 * 定义Writabble序列化类
 * 
 * @author wangjian
 */
public class AccessFlowWritable implements WritableComparable<AccessFlowWritable> {
	private String ip;// ip地址
	private Long count;// 访问次数
	private Long flow;// 流量
	@Override
	public int compareTo(AccessFlowWritable o) {
		return (int)(this.count-o.count);//实现按访问量排序
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(ip);
		out.writeLong(count);
		out.writeLong(flow);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		ip = in.readUTF();
		count = in.readLong();
		flow = in.readLong();
	}

	@Override
	public String toString() {
		return ip + "\t" + count + "\t" + flow;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getFlow() {
		return flow;
	}

	public void setFlow(Long flow) {
		this.flow = flow;
	}

}
