package spider.bean;


public class InfoBean {
	private String beanKey;
	private String beanValue;
	
	public InfoBean(){}
	
	public InfoBean(String beanKey,String beanValue){
		this.beanKey = beanKey;
		this.beanValue = beanValue;
	}
	public String getBeanKey() {
		return beanKey;
	}
	public void setBeanKey(String beanKey) {
		this.beanKey = beanKey;
	}
	public String getBeanValue() {
		return beanValue;
	}
	public void setBeanValue(String beanValue) {
		this.beanValue = beanValue;
	}
	public String toString(){
		return "["+beanKey+":"+beanValue+"]";
	}
	
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		if (obj instanceof InfoBean){
			InfoBean otherBean = (InfoBean)obj;
			return otherBean.beanValue.equals(this.beanValue);
		}
		return false;
	}
}
