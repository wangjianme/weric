package com.myexam.teac.manager.stud.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Stud;
/**
 * 学生管理
 * @author wangjianme
 * @version 1.0,2010-10-13
 */
public class StudManagerDaoJdbc extends HibernateDaoSupport implements IStudManagerDao {
	@SuppressWarnings("unchecked")
	public Map<String,Object> query(Map<String,Object> param) {
		Stud stud = (Stud)param.get("stud");
		Integer start = (Integer)param.get("start");
		Integer limit = (Integer)param.get("limit");
		String studNo 		= stud.getStudNo();							//学号等，以下都用模糊查询
		String studName		= stud.getStudName();
		String studSid      = stud.getStudSid();
		String studSex      = stud.getStudSex();						//不能使用模糊查询
		String studCls      = stud.getStudCls();			
		String studMajor    = (String)param.get("studMajor");
		String studEdusys   = (String)param.get("studEdusys");
		String studTel      = stud.getStudTel();
		String studBirthFrom =stud.getStudBirth();						//不能使用模糊查询
		String studBirthTo   = (String)param.get("studBirth2");			//不能使用模糊查询
		String studState     = stud.getStudState();						//不能使用模糊查询
		String studAdd 		 = stud.getStudAddr();
		
		Map<String,Object> result = new HashMap<String, Object>();
		StringBuffer querySql = new StringBuffer
					("select stud_id as studId,stud_no as studNo,stud_name as studName,stud_sex as studSex,stud_sid as studSid,stud_birth as studBirth,"+
					 "stud_cls as studCls,cls_name as clsName,"+
				     "stud_rtime as studRtime,stud_pic as studPic,stud_addr as studAddr,stud_tel as studTel,"+
					 "stud_qq as studQq,stud_email as studEmail,stud_desc as studDesc,"+
					 "major_id as majorId,major_name as majorName,"+
					 "edusys_id as edusysId,edusys_name as edusysName"+
					 ",stud_state as studState"+
					 " from stud inner join cls on stud_cls=cls_id"+
				     " inner join major on cls_major=major_id"+
					 " inner join edusys on major_edusys=edusys_id");
		querySql.append(" where 1=1");
		
		StringBuffer countSql = new StringBuffer("select count(*)"+									//查询数量
				     " from stud inner join cls on stud_cls=cls_id"+
				     " inner join major on cls_major=major_id"+
				     " inner join edusys on major_edusys=edusys_id");
		countSql.append(" where 1=1");
		
		String tmp = "";
		Map<String,Object> props = new HashMap<String, Object>();
		if(isNotBlank(studNo)){
			tmp=" and stud_no like :no";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("no", "%"+studNo.trim()+"%");
		}
		if(isNotBlank(studName)){
			tmp = " and stud_name like :name";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("name","%"+studName.trim()+"%");
		}
		if(isNotBlank(studSid)){
			tmp = " and stud_sid like :sid";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("sid", "%"+studSid.trim()+"%");
		}
		if(isNotBlank(studSex)){				//性别
			tmp = " and stud_sex=:sex";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("sex",studSex);
		}
		if(isNotBlank(studCls)){
			tmp = " and cls_name like :cls";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("cls","%"+studCls.trim()+"%");
		}
		if(isNotBlank(studMajor)){
			tmp = " and major_name like :major";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("major", "%"+studMajor.trim()+"%");
		}
		if(isNotBlank(studEdusys)){
			tmp = " and edusys_name like :edusys";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("edusys", "%"+studEdusys.trim()+"%");
		}
		if(isNotBlank(studTel)){
			tmp = " and stud_tel like :tel";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("tel", "%"+studTel.trim()+"%");
		}
		if(isNotBlank(studBirthFrom)){
			tmp =" and stud_birth>=:birthFrom";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("birthFrom",studBirthFrom);
		}
		if(isNotBlank(studBirthTo)){
			tmp = " and stud_birth<=:birthTo";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("birthTo",studBirthTo);
		}
		if(isNotBlank(studState)){
			tmp = " and stud_state=:state";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("state", studState);
		}
		if(isNotBlank(studAdd)){
			tmp =" and stud_addr like :addr";
			querySql.append(tmp);
			countSql.append(tmp);
			props.put("addr","%"+studAdd.trim()+"%");
		}
		Query queryStud = getSession().createSQLQuery(querySql.toString());
		Query queryCount = getSession().createSQLQuery(countSql.toString());
		if(!props.isEmpty()){
			queryStud = queryStud.setProperties(props);
			queryCount = queryCount.setProperties(props);
		}
		List<Map<String,Object>> studs = queryStud.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										 .setFirstResult(start)
										 .setMaxResults(limit)
										.list();
		Integer count = Integer.parseInt(""+queryCount.uniqueResult());
		result.put("studs", studs);
		result.put("count", count);
		return result;
	}
	/**
	 * 修改学生信息，只可以修改学生的：学号，班级和状态
	 */
	public Map<String, Object> update(Map<String, Object> param) {
		Stud stud = (Stud)param.get("stud");
		String sql = "update stud set stud_no=:no,stud_cls=:cls,stud_state=:state where stud_id=:id";
		getSession().createSQLQuery(sql)
					.setString("no", stud.getStudNo().trim())
					.setString("cls",stud.getStudCls())
					.setString("state",stud.getStudState())
					.setString("id", stud.getStudId())
					.executeUpdate();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
	/**
	 * 删除，修改一个标记位，并不真删除
	 */
	public Map<String,Object> del(Map<String,Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		String studId = (String)param.get("studId");
		String sql = "delete from stud where stud_id=:id";
		getSession().createSQLQuery(sql)
					.setString("id",studId).executeUpdate();
		result.put("success", true);
		return result;
	}
	/**
	 * 查询班级
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCls(Map<String, Object> param) {
		
		String clsName = (String)param.get("clsName");
		Integer start  = (Integer)param.get("start");
		Integer limit  = (Integer)param.get("limit");
		String sql ="select cls_id as clsId,cls_name as clsName,"+
			        "cls_major as clsMajor,major_name as majorName,"+
			        "edusys_id as edusysId,edusys_name as edusysName"+
			        " from cls inner join major on cls_major=major_id"+
			        " inner join edusys on major_edusys=edusys_id"+
			        " where cls_state='1'";
		String sql2 = "select count(*)"+
					  " from cls inner join major on cls_major=major_id"+
					  " inner join edusys on major_edusys=edusys_id"+
					  " where cls_state='1'";
		Map<String,Object> props = new HashMap<String,Object>();
		if(clsName!=null && !clsName.trim().equals("")){
			sql = sql +" and cls_name like :clsName";
			sql2 = sql2+" and cls_name like :clsName";
			props.put("clsName","%"+clsName.trim()+"%");
		}
		Query query1 = getSession().createSQLQuery(sql);
		Query query2 = getSession().createSQLQuery(sql2);
		if(!props.isEmpty()){
			query1 = query1.setProperties(props);
			query2 = query2.setProperties(props);
		}
		List<Map<String,Object>> clses = query1.setFirstResult(start)
											   .setMaxResults(limit)
											   .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											   .list();
		Integer count = Integer.parseInt(""+query2.uniqueResult());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("classes",clses);
		result.put("count",count);
		return result;
	}
	/**
	 * 验证是否为空的方法
	 * @param param
	 * @return
	 */
	private boolean isNotBlank(String param){
		boolean boo = (param!=null && !param.trim().equals(""));
		return boo;
	}

}
