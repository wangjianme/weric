package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.topsen.financial.dao.inter.DeptDAO;
import com.topsen.financial.po.Deptment;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.dao.ibates.IBatesBaseDAO;
import com.topsen.financial.util.support.dao.page.PageModelFactory;

public class DeptDAOImpl  extends DeptDAO{
	
	public int delete(int deptno) {
		int i = 0;
		try {
			this.getMap().delete("dept_space.delete", deptno);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  i;
	}

	public int insert(Deptment deptment) {
		int i = 0;
		try {
			this.getMap().insert("dept_space.insertDept",deptment);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public Deptment load(int deptno) {
		Deptment dept = null;
		try {
			dept= (Deptment) this.getMap().queryForObject("dept_space.one", deptno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dept;
	}

	public int update(Deptment deptment) {
		int i = 0;
		try {
			this.getMap().update("dept_space.update", deptment);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public PageModel queryByPage(int curPage) {
		return PageModelFactory.getFactory().createPageModel("dept_space.findAllDept", "dept_space.countData", curPage);
	}

	public List<Deptment> queryAll() {
		List<Deptment> list = null;
		try {
			list = this.getMap().queryForList("dept_space.findAllDept");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
