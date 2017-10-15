package cn.grades.domain;

public class Title {
     private String id;
     private String name;
     private String des;//desc为关键字 不能使用
	public String getId() {
		return id;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String desc) {
		this.des = desc;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return "Title [id=" + id + ", name=" + name + ", des=" + des + "]";
	}
	
     
     
}
