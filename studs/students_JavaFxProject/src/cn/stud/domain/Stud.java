package cn.stud.domain;

import java.io.Serializable;

public class Stud implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String sex;
	private Integer age;
	private String addr;
	
	//可以与你的查询进行匹配
	private Integer age1;
	private Integer age2;
	public Integer getAge1() {
		return age1;
	}

	public void setAge1(Integer age1) {
		this.age1 = age1;
	}

	public Integer getAge2() {
		return age2;
	}

	public void setAge2(Integer age2) {
		this.age2 = age2;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "Stud [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", addr=" + addr + "]";
	}

}
