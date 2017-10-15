package cn.grades.course.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Course;
import cn.grades.domain.Major;

public interface ICourseService {

	int addCourse(Course course);

	int update(Course course);

	int add(Course course);

	Map<String, Object> query(int page, int rows);

	Map<String, Object> search(Course course);

	List<Major> selectMajor(String deptid);

	List<Major> selectGrade();

	String selectmg_id(Course course);

	int delete(String id);

	Map<String, String> selectparent_id(String id);

}