package com.topsen.financial.po;

import java.util.List;
/**
 * 
 * @author Administrator
 * ����Emploee����  �����䵱���ϵ���������
 */
public class Emploee {
	private String empno;//Ա���ı��
	private String ename;//Ա��������
	private String password;//Ա��������
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
