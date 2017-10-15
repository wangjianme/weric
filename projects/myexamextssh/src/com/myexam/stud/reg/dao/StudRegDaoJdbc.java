package com.myexam.stud.reg.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Cls;
import com.myexam.domain.Stud;
/**
 * 学生注册功能
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
public class StudRegDaoJdbc extends HibernateDaoSupport implements IStudRegDao {
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryEdusys() {
		String sql = "select edusys_id as edusysId,edusys_name as edusysName from edusys";
		List<Map<String,Object>> edusyses = getSession().createSQLQuery(sql)
											.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											.list();
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("edusys",edusyses);
		return result;
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryMajor(String edusysId) {
		String sql = "select major_id as majorId,major_name as majorName"+
					 " from major where major_inuse='1'";
		if(edusysId!=null && !edusysId.trim().equals("")){
			sql = sql +" and major_edusys=:edusys";
		}
		Query query = getSession().createSQLQuery(sql);
		if(edusysId!=null && !edusysId.trim().equals("")){
			query = query.setString("edusys",edusysId);
		}
		List<Map<String,Object>> majors =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										  .list();
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("majors", majors);
		return result;
	}
	/**
	 * 查询出某些班级
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCls(Map<String,Object> param) {
		String edusysId = (String)param.get("edusysId");//学制id
		String majorId  = (String)param.get("majorId");//专业id
		String clsName  = (String)param.get("clsName");//班级名称，模糊查询
		StringBuilder sb = new StringBuilder("select cls_id as clsId,cls_name as clsName,teac_name as teacName"+
											 " from cls inner join major on cls_major=major_id"+
											 " inner join edusys on major_edusys=edusys_id"+
											 " inner join teac on cls_head=teac_id");
		sb.append(" where 1=1");
		Map<String,Object> prop = new HashMap<String, Object>();
		if(edusysId!=null && !edusysId.trim().equals("")){
			sb.append(" and edusys_id=:edusys");
			prop.put("edusys", edusysId);
		}
		if(majorId!=null && !majorId.trim().equals("")){
			sb.append(" and major_id=:major");
			prop.put("major",majorId);
		}
		if(clsName!=null && !clsName.trim().equals("")){
			sb.append(" and cls_name like :cls");
			prop.put("cls","%"+clsName.trim()+"%");
		}
		List<Map<String,Object>> clses = new ArrayList<Map<String,Object>>();
		Query query = getSession().createSQLQuery(sb.toString());
		if(!prop.isEmpty()){
			query = query.setProperties(prop);
		}
		clses = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					 .list();
		getSession().flush();
		getSession().clear();
		prop.clear();
		prop.put("cls", clses);
		return prop;
	}
	public Map<String, Object> save(Stud stud) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer count = validSid(stud.getStudSid().trim());
		result.put("count", count);
		if(count<=0){
			getSession().save(stud);
		}
		result.put("success", true);
		return result;
	}
	/**
	 * 验证身份证号是否存在
	 */
	public Integer validSid(String sid) {
		String sql = "select count(*) from stud where stud_sid=:sid";
		
		Object o = getSession().createSQLQuery(sql)
								.setString("sid",sid)
								.uniqueResult();
		Integer count = Integer.parseInt(o.toString());
		return count;
	}
}
