package cn.grades.dept.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;

import cn.grades.core.BaseDao;
import cn.grades.domain.Department;

public class DeptDaoJdbc extends BaseDao implements IDeptDao {
	// 查询
	@Override
	public String query(String parent, int page, int rows) {
		Map<String, Object> map = new HashMap<>();
		int start = (page - 1) * rows;
		String sql = "select dept_id as id,dept_name as text,"
				+ "dept_parent as parent,dept_state as state,dept_isdept as isdept,dept_addr as addr,"
				+ "dept_tel as tel,dept_iconcls as iconCls,dept_person as person" + " from departments  WHERE 1=1";
		List<Object> param = new ArrayList<>();
		// 对父节点分页
		if (parent == null || parent.trim().equals("")) {
			sql += " and dept_parent is null limit " + start + "," + rows;
			String sql2 = "select count(1) from departments where dept_parent is null";
			int totalrows = run.query(sql2, new ScalarHandler<Number>()).intValue();

			List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class));
			map.put("total", totalrows);
			map.put("rows", list);
			String json = JSONArray.toJSONString(map);
			return json;
		} else {
			sql += " and dept_parent=?";
			param.add(parent);
			List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class), param.toArray());
			String json = JSONArray.toJSONString(list);
			return json;
		}
	}

	public List<Department> combotree(String parent) {
		String sql = "select dept_id as id,dept_name as text,"
				+ "dept_parent as parent,dept_state as state,dept_isdept as isdept,dept_addr as addr,"
				+ "dept_tel as tel,dept_iconcls as iconCls,dept_person as person" + " from departments  WHERE 1=1";
		List<Object> param = new ArrayList<>();
		if (parent == null || parent.trim().equals("")) {
			sql += " and dept_parent is null";
		} else {
			sql += " and dept_parent=?";
			param.add(parent);
		}

		List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class), param.toArray());
		return list;

	}
	
	@Override
	public List<Department> combobox(String boo) {
		String sql = "select dept_id as id,dept_name as text,dept_iconcls as iconCls" 
				+ " from departments  WHERE dept_parent is null";
		List<Object> param = new ArrayList<>();
		if (StringUtils.isNotEmpty(boo)) {
			sql += " and  dept_isdept = ?";
			param.add(boo);
		}
		List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class),param.toArray());
		return list;
	}

	// 删除
	@Override
	public int delete(String id) {
		String ss = "select dept_parent as parent from departments where dept_id = ?";
		Department d = run.query(ss, new BeanHandler<Department>(Department.class), id);
		String pid = d.getParent();
		// 父节点则删除父节点以及其子节点
		int rows = 0;
		if (pid == null) {// 父节点
			ss = "delete from departments where dept_parent = ?";
			rows = run.update(ss, id);
		}
		System.out.println(",,,,,,,,," + pid);
		String sql = "delete from departments where dept_id=?";
		int row = run.update(sql, id);
		row += rows;
		String s = "select count(1) from departments where dept_parent = '" + pid + "'";
		int len = run.query(s, new ScalarHandler<Number>()).intValue();
		System.out.println("len is" + len);
		if (len == 0) {
			// 最后一个子节点删除时，更新根节点的state
			s = "update departments set dept_state ='open' where dept_id= '" + pid + "'";
			try {
				run.update(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}

	// 保存添加
	@Override
	public Department save(Department dept) {
		dept.setId(UUID.randomUUID().toString().replace("-", ""));
		dept.setState("open");
		// select count(1) from departments where
		// parent=dept.getId()>0?closed:open //iconCls类似
		// 添加第一个子节点时更新父节点的state
		String sql2 = "update departments set dept_state='closed' where dept_id=?";
		if (dept.getParent() != null) {
			run.update(sql2, dept.getParent());
		}
		String sql = "insert into departments(dept_id,dept_name,dept_state,dept_isdept,"
				+ "dept_parent,dept_addr,dept_tel,dept_iconcls,dept_person) values(?,?,?,?,?,?,?,?,?)";
		run.update(sql, dept.getId(), dept.getText(), dept.getState(), dept.getIsdept(), dept.getParent(),
				dept.getAddr(), dept.getTel(), dept.getIconCls(), dept.getPerson());
		return dept;
	}

	@Override
	public int update(Department dept) {
		String sql = "update departments set dept_name=?,dept_addr=?,dept_tel=?,dept_person=? where dept_id=?";
		int rows = run.update(sql, dept.getText(), dept.getAddr(), dept.getTel(), dept.getPerson(), dept.getId());
		return rows;
	}

	@Override
	public List<Department> search(Department dept) {
		String sql = "select dept_id as id,dept_name as text,"
				+ "dept_parent as parent,dept_state as state,dept_isdept as isdept,dept_addr as addr,"
				+ "dept_tel as tel,dept_iconcls as iconCls,dept_person as person from departments";

		List<Object> param = new ArrayList<>();
		String where = " WHERE 1=1";
		System.out.println("parent is:" + dept.getParent());
		if (StringUtils.isNotEmpty(dept.getText())) {
			where += " and  dept_name like ?";
			param.add("%" + dept.getText().trim() + "%");
		}
		if (StringUtils.isNotEmpty(dept.getAddr())) {
			where += " and  dept_addr like ?";
			param.add("%" + dept.getAddr().trim() + "%");
		}
		if (StringUtils.isNotEmpty(dept.getTel())) {
			where += " and  dept_tel like ?";
			param.add("%" + dept.getTel().trim() + "%");
		}
		
		if(StringUtils.isNotEmpty(dept.getParent())){
			where += " and  dept_parent = ?";
			param.add(dept.getParent());
		}
		if (StringUtils.isEmpty(dept.getTel().trim()) &&StringUtils.isEmpty(dept.getText().trim()) && dept.getParent() == null
				&& StringUtils.isEmpty(dept.getAddr().trim())) {
			where += " and  dept_parent is null";
		}
		sql += where;
		System.out.println(sql);
		List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class), param.toArray());
		return list;
	}

}
