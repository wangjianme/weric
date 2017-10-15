package cn.grades.score.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Course;
import cn.grades.domain.Department;
import cn.grades.domain.Grade;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;

public interface IScoreDao {

	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#query(int, int)
	 */
	Map<String, Object> query(int page, int rows);
	Map<String, Object> select(Score score);
	Score save(Score sc);

	List<Stud> query1();

	List<Course> query2();

	
	
	int delete(String id);

	int delete1(String studid);
	
	int delete2(String courseid);
	int update(Score sc);
}