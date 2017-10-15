package cn.grades.dept.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Department;

public interface IDeptService {

	String query(String parent,int page,int rows);
	
	int delete(String id);
	
	Department save(Department dept);
	
	int update(Department dept);
	List<Department> search(Department dept);
	List<Department> combotree(String parent);
	List<Department> combobox(String boo);
}