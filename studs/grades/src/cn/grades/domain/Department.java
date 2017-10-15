package cn.grades.domain;

public class Department {
	private String id;
	private String Text;
	private String state;
	private String parent;
	private Boolean isdept;
	private String addr;
	private String tel;
	private String iconCls;
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	private Integer person;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public Boolean getIsdept() {
		return isdept;
	}
	public void setIsdept(Boolean isdept) {
		this.isdept = isdept;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getPerson() {
		return person;
	}
	public void setPerson(Integer person) {
		this.person = person;
	}
	
}
