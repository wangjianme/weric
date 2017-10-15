package com.myexam.teac.init.dept.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Dept;
/**
 * 组织架构
 * @author wangjianme
 * @version 1.0,2010-9-30
 */
public class DeptDaoJdbc extends HibernateDaoSupport implements IDeptDao {
	@SuppressWarnings("unchecked")
	public List<Dept> query(String id) {
		List<Dept> list = getSession().createCriteria(Dept.class)
						  .add(Restrictions.eq("deptParent", id))
						  .list();
		return list;
	}
	/**
	 * 保存或修改功能
	 */
	public Map<String, Object> save(Dept dept) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(dept.getDeptId()==null || dept.getDeptId().trim().equals("")){			//如果没有ID刚为增加
			getSession().save(dept);
			if(!dept.getDeptParent().equals("ROOT")){
				Dept parent = (Dept)getSession().load(Dept.class, dept.getDeptParent());
				if(parent!=null && parent.isDeptIsleaf()){
					parent.setDeptIsleaf(false);
					getSession().update(parent);
				}
			}
			result.put("save",true);												//设为保存标记
		}else{
			Dept tmp = (Dept)getSession().load(Dept.class, dept.getDeptId());      //先获取这个对像
			if(dept.getDeptName()!=null){
				tmp.setDeptName(dept.getDeptName());
			}
			if(dept.getDeptDesc()!=null){
				tmp.setDeptDesc(dept.getDeptDesc());
			}
			dept = tmp;
			getSession().update(dept);												//修改这个对像
			result.put("save", false);												//设置修改标记
		}
		result.put("success", true);
		result.put("deptId", dept.getDeptId());
		result.put("text", dept.getDeptName());
		result.put("desc", dept.getDeptDesc());
		return result;
	}
	public Map<String, Object> del(String deptId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String sql2 = "select count(*) from major where major_dept=:dept";//先查询一下此部门或是院下是否包含专业，如果有则不能删除
		Integer count2 = Integer.parseInt(""+getSession().createSQLQuery(sql2)
								 .setString("dept",deptId)
								 .uniqueResult());
		if(count2>0){
			result.put("success",false);
			result.put("msg", "不能删除，因为此部门/院系下尚存在一些专业！");
			return result;
		}
		Dept dept = (Dept)getSession().load(Dept.class, deptId);
		getSession().delete(dept);
		String sql = "select count(*) from dept where dept_parent=:parent";
		Integer count =
				Integer.parseInt(""+getSession().createSQLQuery(sql)
				.setString("parent", dept.getDeptParent())
				.uniqueResult());
		if(count<=1){
			sql = "update dept set dept_isleaf='1' where dept_id=:id";
			getSession().createSQLQuery(sql)
			            .setString("id",dept.getDeptParent())
			            .executeUpdate();
			result.put("leaf", true);
		}
		
		sql = "update teac set teac_dept=:newDept where teac_dept=:oldDept";			//删除这个部门时,自动将这个部门的员工转成它的父部门
		getSession().createSQLQuery(sql)
					.setString("newDept",dept.getDeptParent())
					.setString("oldDept", dept.getDeptId())
					.executeUpdate();
		result.put("success", true);
		return result;
	}
}
