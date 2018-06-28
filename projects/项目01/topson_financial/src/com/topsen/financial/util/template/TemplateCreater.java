package com.topsen.financial.util.template;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topsen.financial.dao.impl.RefDAOImpl;
import com.topsen.financial.dao.inter.RefDAO;
import com.topsen.financial.po.RebursementReference;
import com.topsen.financial.po.Template;


public abstract class TemplateCreater<T> {
	protected TemplateCreater<T> templateCreater;
	protected static  Map<Object,Object> referenceMap = new HashMap<Object,Object>();
	public void addTemplateCreater(TemplateCreater<T> templateCreater) {
		this.templateCreater = templateCreater;
	}
	abstract public  List<T> createItemList(Object... value);
}
