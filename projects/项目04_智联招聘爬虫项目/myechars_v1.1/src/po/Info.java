package po;

public class Info {
	private String compy;
	private float maxPay;
	private float minPay;
	private int maxWorkExp;
	private int minWorkExp;
	public String getCompy() {
		return compy;
	}
	public void setCompy(String compy) {
		this.compy = compy;
	}
	public float getMaxPay() {
		return maxPay;
	}
	public void setMaxPay(float maxPay) {
		this.maxPay = maxPay;
	}
	public float getMinPay() {
		return minPay;
	}
	public void setMinPay(float minPay) {
		this.minPay = minPay;
	}
	public int getMaxWorkExp() {
		return maxWorkExp;
	}
	public void setMaxWorkExp(int maxWorkExp) {
		this.maxWorkExp = maxWorkExp;
	}
	public int getMinWorkExp() {
		return minWorkExp;
	}
	public void setMinWorkExp(int minWorkExp) {
		this.minWorkExp = minWorkExp;
	}
	public String toString(){
		return this.compy+" minPay:"+this.minPay+" maxPay:"+this.maxPay+" minExp:"+this.minWorkExp+" maxExp:"+this.maxWorkExp;
	}
	
}
