package com.myexam.teac.init.major.dao;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.AnyType;
import org.hibernate.type.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.myexam.domain.Major;
/**
 * 专业
 * @author wangjianme
 * @version 1.0,2010-10-6
 */
public class MajorDaoJdbc extends HibernateDaoSupport implements IMajorDao{
	/**
	 * 根据示例查询
	 * 同时使用getSession来操作动态的参数
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> query(Major major) {
		String sql = "select major_id,major_name,"+
					 "major_dept,dept_name,"+
					 "major_edusys,edusys_name,"+
					 "major_desc,major_inuse"+
					 " from major inner join dept on major_dept=dept_id"+
					 " inner join edusys on major_edusys=edusys_id where 1=1";
		Map<String,Object> param = new HashMap<String, Object>();	//声明用于封装参数和值的map
		if(major.getMajorName()!=null && !major.getMajorName().trim().equals("")){
			sql =sql + " and major_name like :name";
			param.put("name", "%"+major.getMajorName().trim()+"%");		//注意将参数名和值直接保存到map中
		}
		if(major.getMajorDept()!=null && !major.getMajorDept().trim().equals("")){
			sql = sql +" and major_dept=:dept";
			param.put("dept", major.getMajorDept().trim());
		}
		if(major.getMajorInuse()!=null && !major.getMajorInuse().trim().equals("")){
			sql = sql + " and major_inuse=:inuse";
			param.put("inuse", major.getMajorInuse().trim());
		}
		if(major.getMajorEdusys()!=null && !major.getMajorEdusys().equals("")){
			sql = sql + " and major_edusys=:edusys";
			param.put("edusys", major.getMajorEdusys());
		}
		System.err.println(">>>>>>>>:");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();		//先声明一个返回的空集合
		Query query = getSession().createSQLQuery(sql);								//根据原生sql创建Query
		if(!param.isEmpty()){
			query = query.setProperties(param);
		}
		list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();//将查询的结果直接封装到map中
		System.err.println(">>:"+list);
		return list;
	}
	public Map<String, Object> save(Major major) {
		getSession().save(major);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("major", major);
		m.put("success", true);
		m.put("save", true);
		return m;
	}
	public Map<String, Object> del(String majorId) {
		String sql = "delete from courmajor where cm_major=:major";	//先删除关联表中的内容。其实，最好是不删除给一个不能删除的原因提示
		getSession().createSQLQuery(sql).setString("major",majorId).executeUpdate();
		sql = "delete from major where major_id=:id";
		getSession().createSQLQuery(sql).setString("id",majorId).executeUpdate();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success",true);
		return m;
	}
	public Map<String, Object> update(Major major) {
		getSession().update(major);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("major", major);
		m.put("success", true);
		m.put("save", false);
		return m;
	}
}
