package com.vencent.jsoupnt.jsoup;

public class UserInfo {
	private Long id;
	private String userId;
	private String name;
	private String gender;
	private String age;
	private String height;
	private String salary;	
	private String marital;
	private String school;
	private String work;
	private String workAdd;
	private String address;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getMarital() {
		return marital;
	}
	public void setMarital(String marital) {
		this.marital = marital;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getWorkAdd() {
		return workAdd;
	}
	public void setWorkAdd(String workAdd) {
		this.workAdd = workAdd;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userId=" + userId + ", name=" + name + ", gender=" + gender + ", age=" + age
				+ ", height=" + height + ", salary=" + salary + ", marital=" + marital + ", school=" + school
				+ ", work=" + work + ", workAdd=" + workAdd + ", address=" + address + "]";
	}
	
}
