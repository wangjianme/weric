package com.topsen.financial.po;

import java.util.List;

import com.topsen.financial.util.support.dao.BasePO;

public class Rebursement extends BasePO{
	private String rebId;
	private String rebDate;
	private String startDate;
	private String backDate;
	private String rebNumber;
	private String duty;
	private Emploee emp;
	private List<RebursementItem> rebItemList;
	private String moneyUpper;
	private float moneyLower;
	private float accordMoney;
	private float balanceMoney;
	private String dname;
	private String flag;
	private String orgName;
	private String proNumber;
	private int peopleNumber;
	private float koujian;
	
	public float getKoujian() {
		return koujian;
	}
	public void setKoujian(float koujian) {
		this.koujian = koujian;
	}
	public int getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getRebId() {
		return rebId;
	}
	public void setRebId(String rebId) {
		this.rebId = rebId;
	}
	public String getRebDate() {
		return rebDate;
	}
	public void setRebDate(String rebDate) {
		this.rebDate = rebDate;
	}
	public String getRebNumber() {
		return rebNumber;
	}
	public void setRebNumber(String rebNumber) {
		this.rebNumber = rebNumber;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Emploee getEmp() {
		return emp;
	}
	public void setEmp(Emploee emp) {
		this.emp = emp;
	}
	public List<RebursementItem> getRebItemList() {
		return rebItemList;
	}
	public void setRebItemList(List<RebursementItem> rebItemList) {
		this.rebItemList = rebItemList;
	}
	public String getMoneyUpper() {
		return moneyUpper;
	}
	public void setMoneyUpper(String moneyUpper) {
		this.moneyUpper = moneyUpper;
	}
	public float getMoneyLower() {
		return moneyLower;
	}
	public void setMoneyLower(float moneyLower) {
		this.moneyLower = moneyLower;
	}
	public float getAccordMoney() {
		return accordMoney;
	}
	public void setAccordMoney(float accordMoney) {
		this.accordMoney = accordMoney;
	}
	public float getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(float balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getProNumber() {
		return proNumber;
	}
	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}
	
}
