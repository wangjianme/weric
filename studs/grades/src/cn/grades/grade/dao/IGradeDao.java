package cn.grades.grade.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Grade;

public interface IGradeDao {

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeDao.IGradeDao#query(int, int)
	 */
	Map<String, Object> query(int rows, int page);

	List<Grade> selectGrade();

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeDao.IGradeDao#select(cn.grades.domain.Grade, int, int)
	 */
	Map<String, Object> select(Grade grade, int rows, int page);

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeDao.IGradeDao#update(cn.grades.domain.Grade)
	 */
	int update(Grade grade);

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeDao.IGradeDao#add(cn.grades.domain.Grade)
	 */
	int add(Grade grade);

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeDao.IGradeDao#delete(java.lang.String)
	 */
	int delete(String id);

}