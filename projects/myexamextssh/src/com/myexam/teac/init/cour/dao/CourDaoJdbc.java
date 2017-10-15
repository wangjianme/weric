package com.myexam.teac.init.cour.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.myexam.domain.Cour;
import com.myexam.domain.Major;
/**
 * 课程设置
 * @author wangjianme
 * @version 1.0,2010-10-7
 */
public class CourDaoJdbc extends HibernateDaoSupport implements ICourDao {
	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> query(Map<String,Object> param) {
		Cour cour = (Cour)param.get("cour");
		String major = (String)param.get("majors");		//如果选择了专业，只能是一个专业
		Integer start = (Integer)param.get("start");
		Integer limit = (Integer)param.get("limit");
		Integer courHour2 = (Integer)param.get("courHour2");
		StringBuffer sql=null;							//查询sql
		StringBuffer countSql = null;					//数量sql
		if(major==null || major.equals("")){			//有专业的查询和没有专业的查询sql是不一样的
			sql = new StringBuffer("select cour_id,cour_name,cour_desc,cour_hour,cour_ispubs,cour_inuse");
			sql.append(" from cour");
			countSql=new StringBuffer("select count(*) from cour");
		}else{
			sql = new StringBuffer("select cour_id,cour_name,cour_desc,cour_hour,cour_ispubs,cour_inuse");
			sql.append(" from cour left join courmajor on cour_id=cm_cour");
			sql.append(" left join major on cm_major=major_id");
			
			countSql=new StringBuffer("select count(*)");
			countSql.append(" from cour left join courmajor on cour_id=cm_cour");
			countSql.append(" left join major on cm_major=major_id");
		}
		sql.append(" where 1=1");
		countSql.append(" where 1=1");
		
		Map<String,Object> mm = new HashMap<String, Object>();
		String tmpStr = "";
		if(cour.getCourName()!=null && !cour.getCourName().trim().equals("")){
			tmpStr = " and cour_name like :name";
			sql.append(tmpStr);
			countSql.append(tmpStr);
			mm.put("name", "%"+cour.getCourName().trim()+"%");
		}
		if(cour.getCourIspubs()!=null && !cour.getCourIspubs().equals("")){
			tmpStr=" and cour_ispubs=:ispubs";
			sql.append(tmpStr);
			countSql.append(tmpStr);
			mm.put("ispubs", cour.getCourIspubs());
		}
		if(cour.getCourInuse()!=null && !cour.getCourInuse().equals("")){
			tmpStr=" and cour_inuse=:inuse";
			sql.append(tmpStr);
			countSql.append(tmpStr);
			mm.put("inuse", cour.getCourInuse());
		}
		if(major!=null && !major.equals("")){
			tmpStr=" and major_id =:major";
			sql.append(tmpStr);
			countSql.append(tmpStr);
			mm.put("major",major);
		}
		if(cour.getCourHour()!=null && cour.getCourHour()>0){
			tmpStr = " and cour_hour>=:hour1";
			sql.append(tmpStr);
			countSql.append(tmpStr);
			mm.put("hour1", cour.getCourHour());
		}
		if(courHour2!=null && courHour2>0){
			tmpStr = " and cour_hour<=:hour2";
			sql.append(tmpStr);
			countSql.append(tmpStr);
			mm.put("hour2", courHour2);
		}
		
		Query query = getSession().createSQLQuery(sql.toString());
		Query countQuery = getSession().createSQLQuery(countSql.toString());
		if(!mm.isEmpty()){
			query = query.setProperties(mm);//直接将map参数传组query对像
			countQuery.setProperties(mm);
		}
		
		Map<String,Object> result = new HashMap<String, Object>();			//返回的结果集
		List<Map<String,Object>> cours = query
										.setFirstResult(start)
										.setMaxResults(limit)
										.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)//指定封装成map
										.list();
		String tmpSql="";
		for(Map<String,Object> tmpCour : cours){
			tmpSql = "select major_id,major_name " +
							"from major inner join courmajor on major_id=cm_major " +
							"where cm_cour=:cour";
			List<Map<String,String>> majorList = getSession().createSQLQuery(tmpSql)
											  .setString("cour",(String)tmpCour.get("cour_id"))
											  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
											  .list();
			String majorIds="";
			String majorNames="";
			for(Map<String,String> mjr : majorList){
				if(majorIds.equals("")){
					majorIds=mjr.get("major_id");
					majorNames=mjr.get("major_name");
				}else{
					majorIds=majorIds+","+mjr.get("major_id");
					majorNames=majorNames+","+mjr.get("major_name");
				}
			}
			tmpCour.put("majorIds",majorIds);
			tmpCour.put("majorNames", majorNames);
		}
		result.put("cours", cours);
		Integer count = Integer.parseInt(""+countQuery.uniqueResult());
		result.put("count", count);
		return result;
	}
	public Map<String, Object> save(Map<String,Object> param) {
		Cour c = (Cour)param.get("cour");
		String majors = (String)param.get("majors");
		Map<String,Object> m = new HashMap<String, Object>();
		System.err.println(">>>>>>>:"+JSONObject.fromObject(c).toString());
		if(c.getCourId()==null || c.getCourId().trim().equals("")){			//增加新内容
			getSession().save(c);
			m.put("save", true);
		}else{
			String sql = "delete from courmajor where cm_cour=:cour";//删除已经有的全部关联
			getSession().createSQLQuery(sql)
						.setString("cour",c.getCourId())
						.executeUpdate();
			getSession().update(c);
			m.put("save",false);
		}
		System.err.println(">>>>>>>>:"+m);
		getSession().flush();
		getSession().clear();
		System.err.println("Hello...................................");
		String sql ="insert into courmajor(cm_cour,cm_major) values(:cour,:major)";//保存
		String[] major = majors.split(",");
		for(int i=0;i<major.length;i++){
			System.err.println("Courmajor.....");
			getSession().createSQLQuery(sql)
						.setString("cour",c.getCourId())
						.setString("major",major[i])
						.executeUpdate();
		}
		m.put("success", true);
		m.put("cour", c);
		return m;
	}
	@SuppressWarnings("unchecked")
	public List<Major> queryMajor() {
		List<Major> list = getSession().createCriteria(Major.class)
										.add(Restrictions.eq("majorInuse", "1"))
										.list();
		return list;
	}
	public Map<String, Object> del(Cour cour) {
		Map<String, Object> m = new HashMap<String, Object>();
		String sql = "delete from courmajor where cm_cour=:cour";
		getSession().createSQLQuery(sql).setString("cour",cour.getCourId()).executeUpdate();
		getSession().delete(cour);
		m.put("success", true);
		return m;
	}
}
