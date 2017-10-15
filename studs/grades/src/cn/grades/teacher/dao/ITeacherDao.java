package cn.grades.teacher.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Teacher;
import cn.grades.domain.Title;

public interface ITeacherDao {



	Map<String, Object> query(int page, int rows);


	Teacher save(Teacher teacher);


	int delete(String id);


	int update(Teacher teacher);


	List<Department> ins();


	List<Degrees> edu();


	List<Title> rank();


	Map<String, Object> select(Teacher teacher);




}