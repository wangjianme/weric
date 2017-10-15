package cn.grades.grade.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Grade;

public interface IGradeService {

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeService.IGradeService#query(int, int)
	 */
	Map<String, Object> query(int rows, int page);

	List<Grade> selectGrade();

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeService.IGradeService#select(cn.grades.domain.Grade, int, int)
	 */
	Map<String, Object> select(Grade grade, int rows, int page);

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeService.IGradeService#update(cn.grades.domain.Grade)
	 */
	int update(Grade grade);

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeService.IGradeService#add(cn.grades.domain.Grade)
	 */
	int add(Grade grade);

	/* (non-Javadoc)
	 * @see cn.grades.manager.gradeService.IGradeService#delete(java.lang.String)
	 */
	int delete(String id);

}