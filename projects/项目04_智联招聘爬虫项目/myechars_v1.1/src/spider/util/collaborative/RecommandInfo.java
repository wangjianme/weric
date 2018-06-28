package spider.util.collaborative;

public class RecommandInfo implements Comparable<RecommandInfo>{
	private String itemName;
	private double rate;
	
	public RecommandInfo(){}
	
	public RecommandInfo(String itemName,double rate){
		this.itemName = itemName;
		this.rate = rate;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int compareTo(RecommandInfo o) {
		if (this.rate < o.rate){
			return 1;
		}else if (this.rate > o.rate){
			return -1;
		}else{
			if (!this.itemName.equals(o.itemName)){
				return this.itemName.hashCode() - o.itemName.hashCode();
			}
			return 0;
		}
		
	}
	
	public String toString(){
		return "["+itemName+":"+rate+"],";
	}
}
