package cn.grades.teacher.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Teacher;
import cn.grades.domain.Title;

public interface ITeacherService {

	Teacher save(Teacher teacher);

	Map<String, Object> query(int page, int rows);

	List<Title> rank();

	List<Department> ins();

	List<Degrees> edu();

	int delete(String id);

	int update(Teacher teacher);
   Map<String , Object> select(Teacher teacher);
}