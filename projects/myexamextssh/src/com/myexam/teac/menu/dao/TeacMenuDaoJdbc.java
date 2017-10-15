package com.myexam.teac.menu.dao;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
public class TeacMenuDaoJdbc extends HibernateDaoSupport implements ITeacMenuDao{
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> query(String parent,String uid) {
		String sql = "select distinct menu_id as id,menu_text as text,menu_leaf as leaf,menu_url as url"+
				     " from menu inner join func on menu_id=func_menu"+
				     " inner join role on role_id=func_role"+
				     " inner join teacrole on tc_role=role_id"+
				     " inner join teac on teac_id=tc_teac"+
				     " where menu_parent=:parent and teac_id=:uid";
		List<Map<String,Object>> menus = getSession().createSQLQuery(sql)
										.addScalar("id",Hibernate.STRING)
										.addScalar("text",Hibernate.STRING)
										.addScalar("leaf",Hibernate.BOOLEAN)
										.addScalar("url",Hibernate.STRING)
										.setString("parent", parent)
										.setString("uid",uid)
										.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
										.list();
		return menus;
	}
}
