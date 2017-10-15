package com.myexam.teac.manager.cls.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.textui.ResultPrinter;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Cls;
import com.myexam.domain.Major;
import com.myexam.domain.Stud;
/**
 * 班级管理
 * @author wangjianme
 * @version 1.0,2010-10-9
 */
public class ClsDaoJdbc extends HibernateDaoSupport implements IClsDao{
	/**
	 * 查询指定条件的班级
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> query(Map<String, Object> map) {
		Map<String,Object> result = new HashMap<String, Object>();			//保存返回值
		Cls cls = (Cls)map.get("cls");
		Integer start = (Integer)map.get("start");
		Integer limit = (Integer)map.get("limit");
		String clsBtime2 = (String)map.get("clsBtime2");		//结束时间
		String clsEtime2 = (String)map.get("clsEtime2");		//结束时间
		String edusysName = (String)map.get("edusysName");		//
		StringBuffer sql = new StringBuffer();
		sql.append("select cls_id as clsId,cls_name as clsName,cls_btime as clsBtime,cls_etime as clsEtime,cls_state as clsState,");		//查询班级信息
		sql.append("cls_head as clsHead,teac_name as teacName,");
		sql.append("cls_major as clsMajor,major_name as majorName,");
		sql.append("edusys_id as edusysId,edusys_name as edusysName");
		sql.append(" from cls inner join teac on cls_head=teac_id");
		sql.append(" inner join major on cls_major=major_id");
		sql.append(" inner join edusys on major_edusys=edusys_id");
		sql.append(" where 1=1");
		StringBuffer sql2 =new StringBuffer();									//查询数量
		sql2.append("select count(*)");
		sql2.append(" from cls inner join teac on cls_head=teac_id");
		sql2.append(" inner join major on cls_major=major_id");
		sql2.append(" inner join edusys on major_edusys=edusys_id");
		sql2.append(" where 1=1");
		
		Map<String,Object> params =new HashMap<String,Object>();
		String tmpStr = "";
		if(cls.getClsName()!=null && !cls.getClsName().trim().equals("")){
			tmpStr = " and cls_name like :name";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("name","%"+cls.getClsName().trim()+"%");
		}
		if(cls.getClsHead()!=null && !cls.getClsHead().equals("")){
			tmpStr = " and teac_name like :teacName";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("teacName","%"+cls.getClsHead()+"%");
		}
		if(cls.getClsMajor()!=null && !cls.getClsMajor().equals("")){
			tmpStr = " and major_name like :major";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("major", "%"+cls.getClsMajor().trim()+"%");
		}
		if(cls.getClsState()!=null && !cls.getClsState().equals("")){
			tmpStr =" and cls_state=:state";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("state",cls.getClsState());
		}
		if(cls.getClsBtime()!=null && !cls.getClsBtime().equals("")){
			tmpStr = " and cls_btime>=:btime";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("btime",cls.getClsBtime().substring(0, 10));
		}
		if(cls.getClsEtime()!=null && !cls.getClsEtime().equals("")){
			tmpStr = " and cls_etime>=:etime";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("etime",cls.getClsEtime().substring(0, 10));
		}
		if(clsBtime2!=null && !clsBtime2.equals("")){
			tmpStr = " and cls_btime<=:btime2";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("btime2", clsBtime2.substring(0, 10));
		}
		if(clsEtime2!=null && !clsEtime2.equals("")){
			tmpStr = " and cls_etime<=:etime2";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("etime2",clsEtime2.substring(0,10));
		}
		if(edusysName!=null && !edusysName.trim().equals("")){
			tmpStr = " and edusys_name like :edusysName";
			sql.append(tmpStr);
			sql2.append(tmpStr);
			params.put("edusysName", "%"+edusysName.trim()+"%");
		}
		List<Map<String,Object>> classes = new ArrayList<Map<String,Object>>();
		Query query = getSession().createSQLQuery(sql.toString());
		Query query2 = getSession().createSQLQuery(sql2.toString());
		if(!params.isEmpty()){
			query = query.setProperties(params);
			query2 = query2.setProperties(params);
		}
		classes = query.setFirstResult(start)
						.setMaxResults(limit)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
		Integer count = Integer.parseInt(""+query2.uniqueResult());
		result.put("classes",classes);
		result.put("count", count);
		return result;
	}
	/**
	 * 可以根据名称进行查询，支持分页
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryTeac(String name,int start, int limit) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String,Object>> teacs= new ArrayList<Map<String,Object>>();
		Integer count = 0;
		if(name==null || name.trim().equals("")){
			String sql = "select teac_id,teac_name from teac";
			System.err.println(sql);
			teacs = getSession().createSQLQuery(sql)
											 .setFirstResult(start)
											 .setMaxResults(limit)
											 .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											 .list();
			sql = "select count(*) from teac";
			count = Integer.parseInt(""+getSession().createSQLQuery(sql)
			                    .uniqueResult());
		}else{
			String sql = "select teac_id,teac_name from teac where teac_name like :name";
			System.err.println(sql);
			teacs = getSession().createSQLQuery(sql)
								.setString("name", "%"+name.trim()+"%")
								.setFirstResult(start)
								.setMaxResults(limit)
								.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
								.list();
			sql = "select count(*) from teac where teac_name like :name";
			count = Integer.parseInt(""+getSession().createSQLQuery(sql)
										 .setString("name", "%"+name.trim()+"%")
										 .uniqueResult());
		}
		result.put("teacs", teacs);
		result.put("count", count);
		return result;
	}
	/**
	 * 查询专业
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryMajor() {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select major_id as majorId,major_name as majorName,"+
				     "major_dept as majorDept,dept_name as deptName,"+
				     "major_edusys as majorEdusys,edusys_name as edusysName,major_desc as majorDesc"+
				     " from major inner join dept on major_dept=dept_id"+
				     " inner join edusys on major_edusys=edusys_id where major_inuse='1'";
		System.err.println("查询专业:"+sql);
		List<Map<String,Object>> majors = getSession().createSQLQuery(sql)
										  .addScalar("majorId",Hibernate.STRING)
										  .addScalar("majorName",Hibernate.STRING)
										  .addScalar("majorDept",Hibernate.STRING)
										  .addScalar("deptName",Hibernate.STRING)
										  .addScalar("majorEdusys",Hibernate.STRING)
										  .addScalar("edusysName",Hibernate.STRING)
										  .addScalar("majorDesc",Hibernate.STRING)
										  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										  .list();
		result.put("majors",majors);
		System.err.println(">>结果："+majors);
		return result;
	}
	/**
	 * 保存或修改
	 */
	public Map<String, Object> save(Cls cls) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(cls.getClsId()==null || cls.getClsId().equals("")){
			getSession().save(cls);
			result.put("save", true);
		}else{
			result.put("save", false);
			getSession().update(cls);
		}
		result.put("cls", cls);
		result.put("success", true);
		//getSession().evict(cls);			//清除这个对像从Session中
		return result;
	}
	/**
	 * 删除
	 */
	public Map<String, Object> del(Cls cls) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "delete from stud where stud_cls=:cls";
		getSession().createSQLQuery(sql).setString("cls",cls.getClsId()).executeUpdate();
		getSession().delete(cls);
		result.put("success", true);
		return result;
	}
}
