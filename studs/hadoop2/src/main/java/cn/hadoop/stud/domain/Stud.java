package cn.hadoop.stud.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class Stud implements WritableComparable<Stud> {
	private String name;
	private Integer math=0;
	private Integer chinese=0;
	private Integer english=0;
	private Double avg=0.0D;
	private Integer sum=0;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMath() {
		return math;
	}
	public void setMath(Integer math) {
		this.math = math;
	}
	public Integer getChinese() {
		return chinese;
	}
	public void setChinese(Integer chinese) {
		this.chinese = chinese;
	}
	public Integer getEnglish() {
		return english;
	}
	public void setEnglish(Integer english) {
		this.english = english;
	}
	public Double getAvg() {
		return avg;
	}
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	@Override
	public String toString() {
		return name + "\t" + avg;
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(name);
		out.writeInt(math);
		out.writeInt(chinese);
		out.writeInt(english);
		out.writeDouble(avg);
		out.writeInt(sum);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		name = in.readUTF();
		math = in.readInt();
		chinese=in.readInt();
		english=in.readInt();
		avg=in.readDouble();
		sum = in.readInt();
	}
	@Override
	public int compareTo(Stud o) {
		return o.getName().compareTo(this.name);
	}
}
