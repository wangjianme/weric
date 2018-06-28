package com.topsen.financial.tempbuild.rebursement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.topsen.financial.dao.impl.RefDAOImpl;
import com.topsen.financial.dao.inter.RefDAO;
import com.topsen.financial.po.RebursementDetail;
import com.topsen.financial.po.RebursementItem;
import com.topsen.financial.po.RebursementReference;
import com.topsen.financial.util.template.TemplateCreater;

public class Spilter extends TemplateCreater<RebursementItem>{
	static{
		RefDAO dao = new RefDAOImpl();
		List<RebursementReference> tempList = dao.queryAll();
		for (RebursementReference ref : tempList){
			referenceMap.put(ref.getRefColumnId(), ref.getRefName());
		}
	}
	public List<RebursementItem> createItemList(Object... value) {
		List<RebursementDetail> detailList = (List<RebursementDetail>)value[0];
		Map<Object,Object> map = TemplateCreater.referenceMap;
		Map<String,LinkedList<Object>> valueMap = new HashMap<String,LinkedList<Object>>();
		for (RebursementDetail detail : detailList){
			String tempName = (String)map.get(detail.getItem().getTempItemId());
			if (tempName != null){
				LinkedList<Object> list = valueMap.get(tempName);
				if (list == null){
					list = new LinkedList<Object>();
				}
				list.add(detail.getDetailValue());
				valueMap.put(tempName, list);
			}
		}
		value[0] = valueMap;
		return this.templateCreater.createItemList(value);
	}
	
}
