package cn.grades.course.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Course;
import cn.grades.domain.Major;

public interface ICourseDao {

	Map<String, Object> search(Course course);

	int add(Course course);

	String selectmg_id(Course course);

	// 获得删除节点的上上级节点,即专业id
	Map<String, String> selectparent_id(String id);

	int delete(String id);

	int update(Course course);

	int addCourse(Course course);

	List<Major> selectMajor(String deptid);

	List<Major> selectGrade();

	Map<String, Object> query(int page, int rows);

}