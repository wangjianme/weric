package spider.util.collaborative;

public class InfoBean {
	private String userName;
	private String itemName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public InfoBean(String userName,String itemName){
		this.userName = userName;
		this.itemName = itemName;
	}
	
	public InfoBean(){}
}
