package com.myexam.teac.init.edusys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Edusys;
/**
 * 学制管理
 * @author wangjianme
 * @version 1.0,2010-10-14
 */
public class EdusysDaoJdbc extends HibernateDaoSupport implements IEdusysDao {
	@SuppressWarnings("unchecked")
	public Map<String,Object> query() {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Object> edusys = getSession().createCriteria(Edusys.class).list();
		map.put("edusys", edusys);
		return map;
	}
	public Map<String, Object> save(List<Object> list) {
		Map<String, Object> mm = new HashMap<String, Object>();
		for(Object o : list){
			Edusys edusys = (Edusys)o;
			if(edusys.getEdusysId()==null || edusys.getEdusysId().equals("")){
				getSession().save(edusys);
			}else{
				getSession().update(edusys);
			}
		}
		mm.put("list", list);
		mm.put("success", true);
		return mm;
	}
	/**
	 * 删除方法
	 */
	public Map<String, Object> del(Object o) {
		String sql = "select count(*) from major where major_edusys=:edusys";
		Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql).setString("edusys",((Edusys)o).getEdusysId())
						.uniqueResult());
		Map<String, Object> m = new HashMap<String, Object>();
		if(count>0){
			m.put("success",false);
			m.put("msg", "不能删除的原因：此学制下尚有专业存在。");
		}else{
			getSession().delete(o);
			m.put("success", true);
		}
		return m;
	}
}
