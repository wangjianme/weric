package com.topsen.financial.po;

import java.util.List;
/**
 * 
 * @author Administrator
 * 创建Emploee的类  用来充当集合的数据类型
 */
public class Emploee {
	private String empno;//员工的编号
	private String ename;//员工的姓名
	private String password;//员工的密码
	private String orgName;
	private String dname;
	private List<Power> powerList;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public List<Power> getPowerList() {
		return powerList;
	}
	public void setPowerList(List<Power> powerList) {
		this.powerList = powerList;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
