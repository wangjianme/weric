package com.topsen.financial.tempbuild.rebursement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.topsen.financial.po.RebursementItem;
import com.topsen.financial.util.template.TemplateCreater;

public class Creater extends TemplateCreater<RebursementItem>{

	@Override
	public List<RebursementItem> createItemList(Object... value) {
		List<RebursementItem> itemList = new ArrayList<RebursementItem>();
		Map<String,LinkedList<Object>> valueMap = (Map<String,LinkedList<Object>>)value[0];
		LinkedList<Object> beginDates = valueMap.get("��������");
		LinkedList<Object> beginPlaces = valueMap.get("������");
		LinkedList<Object> endDates = valueMap.get("��������");
		LinkedList<Object> endPlaces = valueMap.get("�����");
		LinkedList<Object> cityTraffics = valueMap.get("�Ǽʽ�ͨ��");
		LinkedList<Object> zsMoneys = valueMap.get("ס�޷�");
		LinkedList<Object> otherses = valueMap.get("��������");
		LinkedList<Object> buties = valueMap.get("����");
		int peopleNumbers = Integer.parseInt((String)value[2]);
		beginDates.getFirst();
		int endIndex = beginDates.size();
		if (endIndex > 2){
			endIndex = 2;
		}
		for (int i = 0;i < endIndex;i++){
			RebursementItem item = new RebursementItem();
			item.setBeginDate((String)getObject(i,beginDates));
			item.setBeginPlace((String)getObject(i,beginPlaces));
			item.setEndDate((String)getObject(i,endDates));
			item.setEndPlace((String)getObject(i,endPlaces));
			float cityTraffic = this.firstCreat(i, cityTraffics);
			item.setCityTraffic(cityTraffic);
			float zsMoney = this.firstCreat(i,zsMoneys);
			item.setZsMoney(zsMoney);
			float others = this.firstCreat(i,otherses);
			item.setOthers(others);
			float butieMoney = 0;
			if (i == 0){
				butieMoney = (Float)value[1]*peopleNumbers*50;
				int peopleSum = 0;
				if (buties != null){
					for (Object people : buties){
						peopleSum+=Integer.parseInt((String)people);
					}
				}
				butieMoney-=peopleSum*15;
			}
			item.setButie(butieMoney);
			item.setTotal(zsMoney+item.getCityTraffic()+others+butieMoney);
			itemList.add(item);
		}
		return itemList;
	}
	
	public float firstCreat(int index,List<Object> zsMoneys){
		BigDecimal rtvMoney = new BigDecimal("0");
		if (index == 0 && zsMoneys != null){
			for (Object money : zsMoneys){
				if (money == null || money.equals("")){
					money = "0";
				}
				BigDecimal bd = new BigDecimal((String)money);
				rtvMoney = rtvMoney.add(bd);
			}
		}
		return rtvMoney.floatValue();
	}
	
	public Object getObject(int i ,LinkedList<Object> value){
		if (i == 0){
			return value.getFirst();
		}
		return value.getLast();
	}
	
	

}
