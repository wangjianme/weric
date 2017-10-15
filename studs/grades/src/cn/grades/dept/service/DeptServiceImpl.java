package cn.grades.dept.service;

import java.util.List;
import java.util.Map;

import cn.grades.dept.dao.DeptDaoJdbc;
import cn.grades.dept.dao.IDeptDao;
import cn.grades.domain.Department;

public class DeptServiceImpl implements IDeptService {
	private IDeptDao dao = new DeptDaoJdbc();
	
	@Override
	public int delete(String id) {
		return dao.delete(id);
	}
	@Override
	public Department save(Department dept) {
		return dao.save(dept);
	}
	@Override
	public int update(Department dept) {
		return dao.update(dept);
	}
	@Override
	public String query(String parent, int page, int rows) {
		return dao.query(parent, page, rows);
	}
	@Override
	public List<Department> search(Department dept) {
		return dao.search(dept);
	}
	@Override
	public List<Department> combotree(String parent) {
		return dao.combotree(parent);
	}
	@Override
	public List<Department> combobox(String boo) {
		return dao.combobox(boo);
	}
}
