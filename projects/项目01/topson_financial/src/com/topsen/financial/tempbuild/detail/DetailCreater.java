package com.topsen.financial.tempbuild.detail;

import java.util.ArrayList;
import java.util.List;

import com.topsen.financial.po.RebursementDetail;
import com.topsen.financial.po.TemplateItem;
import com.topsen.financial.util.template.TemplateCreater;
import com.topsen.financial.util.template.bean.TemplateBean;

public class DetailCreater extends TemplateCreater<TemplateBean>{
	@Override
	public List<TemplateBean> createItemList(Object... value) {
		List<TemplateBean> beanList = new ArrayList<TemplateBean>();
		List<RebursementDetail> detailList = (List<RebursementDetail>)value[0];
		TemplateBean bean = null;
		List<Object> body = null;
		int preTempId = 0;
		int preGroupId = 0;
		for (RebursementDetail detail : detailList){
			if (detail.getTemplate().getTempId() != preTempId){
				if (bean != null){
					bean.getFootList().addAll(bean.getBodyList().remove(bean.getBodyList().size()-1));
				}
				preTempId = detail.getTemplate().getTempId();
				bean = new TemplateBean();
				bean.setTitle(detail.getTemplate());
				beanList.add(bean);
				List<TemplateItem> itemList = detail.getTemplate().getItemList();
				bean.getHeadList().addAll(itemList);
				preGroupId = 0;
			}
			
			if (detail.getGroupId() != preGroupId){
				body = new ArrayList<Object>();
				preGroupId = detail.getGroupId();
				bean.addBody(body);
			}
			body.add(detail);
		}
		bean.getFootList().addAll(bean.getBodyList().remove(bean.getBodyList().size()-1));
		return beanList;
	}

}
