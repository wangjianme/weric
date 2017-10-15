package cn.stud.domain;

import java.io.Serializable;

//JavaBean - 
public class User implements Serializable {
	private static final long serialVersionUID = 7395600558251780173L;
	private String name;
	private String pwd;

	public User() {
	}

	public User(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
