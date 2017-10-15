package com.myexam.teac.init.cate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Cate;

public class CateDaoJdbc extends HibernateDaoSupport implements ICateDao{
	@SuppressWarnings("unchecked")
	public Map<String, Object> query() {
		List<Cate> cates = getSession().createCriteria(Cate.class)
						   .addOrder(Order.asc("cateOrder"))
						   .list();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("cates", cates);
		return result;
	}
}
