package spider.bean;
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
	
	
}
