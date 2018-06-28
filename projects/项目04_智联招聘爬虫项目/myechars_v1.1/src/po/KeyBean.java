package po;
import java.util.Set;
import java.util.TreeSet;


public class KeyBean implements Comparable<KeyBean>{
	private String keyName;
	private int count;
	
	public KeyBean(){}
	
	public KeyBean(String keyName,int count){
		this.keyName = keyName;
		this.count = count;
	}
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int compareTo(KeyBean o) {
		if (this.keyName.equals(o.keyName)){
			return 0;
		}
		if(o.count == this.count){
			return this.keyName.compareTo(o.keyName);
		}
		return o.count - this.count;
	}
	
	public String toString(){
		return "["+this.keyName+":"+this.count+"]";
	}
	
	public static void main(String[] args){
		Set<KeyBean> tree = new TreeSet<KeyBean>();
		KeyBean k1 = new KeyBean("a",10);
		KeyBean k2 = new KeyBean("b",10);
		KeyBean k3 = new KeyBean("c",11);
		KeyBean k4 = new KeyBean("d",12);
		KeyBean k5 = new KeyBean("e",9);
		KeyBean k6 = new KeyBean("f",14);
		tree.add(k1);
		tree.add(k2);
		tree.add(k3);
		tree.add(k4);
		tree.add(k5);
		tree.add(k6);
		System.out.println(tree);
	}
}
