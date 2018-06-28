package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.DeptDAOImpl;
import com.topsen.financial.dao.inter.DeptDAO;
import com.topsen.financial.po.Deptment;
import com.topsen.financial.util.support.dao.bean.PageModel;

public class DeptService {
	private DeptDAO dao = new DeptDAOImpl();
	
	public PageModel queryByPage(int curPage){
		return dao.queryByPage(curPage);
	}
	public List<Deptment> queryAll(){
		return dao.queryAll();
	}
	
	public void insertDept(Deptment dept){
		dao.insert(dept);
	}
	public Deptment queryOne(int deptno){
		return dao.load(deptno);
	}
	public void deleteOne(int deptno){
		Deptment dept = dao.load(deptno);
		dept.setFlag("1");
		dao.update(dept);
	}
	public void updateOne(Deptment deptment){
		dao.update(deptment);
	}
	public void delete(String[] deptnos){
		for(String deptno:deptnos){
			Deptment dept = dao.load(Integer.parseInt(deptno));
			dept.setFlag("1");
			dao.update(dept);
		}
	}
}
