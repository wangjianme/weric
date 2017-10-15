package cn.grades.dept.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Department;

public interface IDeptDao {

	//查询
	String query(String parent,int page,int rows);

	//删除
	int delete(String id);
	
	Department save(Department college);
	
	int update(Department college);
	
	List<Department> search(Department dept);
	
	List<Department> combotree(String parent);
	
	List<Department> combobox(String boo);
	
}