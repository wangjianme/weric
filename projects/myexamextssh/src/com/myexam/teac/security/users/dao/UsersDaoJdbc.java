package com.myexam.teac.security.users.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.myexam.domain.Teac;
/**
 * 用户管理功能
 * @author wangjianme
 * @version 1.0,2010-10-3
 */
public class UsersDaoJdbc extends HibernateDaoSupport implements IUsersDao{
	@SuppressWarnings("unchecked")
	public Map<String,Object> query(String start,String limit) {
		String hql="select teac_id,teac_name,teac_sex,teac_state,"+
	               " teac_dept,dept_name,"+
	               " teac_email,teac_tel,teac_time"+
	               " from teac left join dept on teac_dept=dept_id";
		List<Map<String,Object>> list = getSession().createSQLQuery(hql)
					.setFirstResult(Integer.parseInt(start))
					.setMaxResults(Integer.parseInt(limit))
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)		//使用这样的方式,可以直接将原始查询封装成List<Map<..>>
					.list();
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("teacs",list);
		hql = "select count(*) from teac";
		Integer count = Integer.parseInt(""+getSession().createSQLQuery(hql).uniqueResult());
		result.put("count", count);
		return result;
	}
	/**
	 * 保存修改信息
	 */
	public Map<String, Object> agree(Teac teac) {
		Map<String,Object> result = new HashMap<String, Object>();
		System.err.println(">>参数："+result);
		result.put("success", false);
		String hql = "update teac set teac_dept=:dept,teac_state=:state where teac_id=:id";
		int a = getSession().createSQLQuery(hql).setString("dept", teac.getTeacDept())
									    .setString("state",teac.getTeacState())
									    .setString("id", teac.getTeacId())
									    .executeUpdate();
		System.err.println(">>:"+a);
		if(a>0){
			result.put("success", true);
		}
		return result;
	}
	/**
	 * 查询所有的角色
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryRoles(String teacId) {
		String sql = "select role_id,role_name,role_desc from role";
		List<Map<String,Object>> roles = getSession().createSQLQuery(sql)
										.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										.list();
		sql = "select count(*) from teacrole where tc_teac=:teac and tc_role=:role";
		for(Map role : roles){
			Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql)
									.setString("teac",teacId)
									.setString("role", (String)role.get("role_id"))
									.uniqueResult());
			System.err.println("cont>>>:"+count);
			if(count>0){
				role.put("flag", true);
			}else{
				role.put("flag",false);
			}
		}
		return roles;
	}
	/**
	 * 保存用户对应的角色
	 */
	public Map<String, Object> saveRole(String teacId, String roles) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", false);
		
		String sql = "delete from teacrole where tc_teac=:teac";
		getSession().createSQLQuery(sql)
					.setString("teac",teacId)
					.executeUpdate();
		if(!roles.trim().equals("")){
			String[] role = roles.split(",");
			sql = "insert into teacrole(tc_teac,tc_role) values(:teac,:role)";
			for(int i=0;i<role.length;i++){
				getSession().createSQLQuery(sql)
							.setString("teac", teacId)
							.setString("role",role[i])
							.executeUpdate();
			}
		}
		map.put("success", true);
		return map;
	}
	public Map<String, Object> delete(String teacId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",false);
		String sql = "delete from teacrole where tc_teac=:teac";
		getSession().createSQLQuery(sql)
					.setString("teac", teacId)
					.executeUpdate();
		sql = "delete from teac where teac_id=:id";
		getSession().createSQLQuery(sql)
					.setString("id", teacId)
					.executeUpdate();
		result.put("success", true);
		return result;
	}
	/**
	 * 修改密码
	 */
	public Map<String, Object> changePwd(String sid, String newpwd) {
		String sql  ="update teac set teac_pwd=:newpwd where teac_id=:id";
		getSession().createSQLQuery(sql)
					.setString("newpwd", newpwd)
					.setString("id",sid)
					.executeUpdate();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
}
