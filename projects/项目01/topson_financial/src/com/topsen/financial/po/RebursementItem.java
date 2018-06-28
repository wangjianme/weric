package com.topsen.financial.po;

import com.topsen.financial.util.support.dao.BasePO;

public class RebursementItem extends BasePO{
	private int itemId;
	private String rebId;
	private String beginDate;
	private String beginPlace;
	private String endDate;
	private String endPlace;
	private float cityTraffic;
	private float others;
	private float zsMoney;
	private float butie;
	private float koujian;
	private float total;
	//ÐÂ¼Ó±¸×¢×Ö¶Î
	private String remark;

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public float getKoujian() {
		return koujian;
	}
	public void setKoujian(float koujian) {
		this.koujian = koujian;
	}
	public float getButie() {
		return butie;
	}
	public void setButie(float butie) {
		this.butie = butie;
	}
	public float getCityTraffic() {
		return cityTraffic;
	}
	public void setCityTraffic(float cityTraffic) {
		this.cityTraffic = cityTraffic;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getRebId() {
		return rebId;
	}
	public void setRebId(String rebId) {
		this.rebId = rebId;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getBeginPlace() {
		return beginPlace;
	}
	public void setBeginPlace(String beginPlace) {
		this.beginPlace = beginPlace;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndPlace() {
		return endPlace;
	}
	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}
	public float getOthers() {
		return others;
	}
	public void setOthers(float others) {
		this.others = others;
	}
	public float getZsMoney() {
		return zsMoney;
	}
	public void setZsMoney(float zsMoney) {
		this.zsMoney = zsMoney;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
}
