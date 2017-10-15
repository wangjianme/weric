package cn.grades.domain;

public class Grade {
	private String id;
	private String name;
	private String dt;
	private String dsc;
	public String getId() {
		return id;
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
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	@Override
	public String toString() {
		return "Grade [id=" + id + ", name=" + name + ", dt=" + dt + ", dsc=" + dsc + "]";
	}
	
	

	

}
