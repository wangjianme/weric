package mr_job.dx;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class People implements WritableComparable<People>{
	String XWID;
	String Yjname;
	int JB;
	
	public People() {
		
	}
	


	public People(String XWID, String yjname, int jB) {
		
		this.XWID = XWID;
		this.Yjname = yjname;
		this.JB = jB;
	}



	public String getXWID() {
		return XWID;
	}



	public void setXWID(String xWID) {
		XWID = xWID;
	}



	public String getYjname() {
		return Yjname;
	}



	public void setYjname(String yjname) {
		Yjname = yjname;
	}



	public int getJB() {
		return JB;
	}



	public void setJB(int jB) {
		JB = jB;
	}



	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
          XWID=in.readUTF();
          Yjname=in.readUTF();
          JB=in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
           out.writeUTF(XWID);
           out.writeUTF(Yjname);
           out.writeInt(JB);
	}

	@Override
	public int compareTo(People o) {
		// TODO Auto-generated method stub
		if(XWID.equals(o.XWID)&&Yjname.equals(o.Yjname)){
			return JB>o.JB?-1:1;
		}
		else{
			return -1;
		}
	}



	@Override
	public String toString() {
		return  XWID +"|"+Yjname +"|"+ JB+"|";
	}

}

