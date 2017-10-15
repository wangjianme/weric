package cn.grades.grade.service;

import java.util.List;
import java.util.Map;
import cn.grades.domain.Grade;
import cn.grades.grade.dao.GradeDaoJdbc;
import cn.grades.grade.dao.IGradeDao;

public class GradeService implements IGradeService  {
	private IGradeDao dao = new GradeDaoJdbc();
	
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.service.IGradeService#query(int, int)
	 */
	@Override
	public Map<String, Object> query(int rows,int page){
		return dao.query(rows, page);
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.service.IGradeService#selectGrade()
	 */
	@Override
	public List<Grade> selectGrade() {
		return dao.selectGrade();
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.service.IGradeService#select(cn.grades.domain.Grade, int, int)
	 */
	@Override
	public Map<String, Object> select(Grade grade,int rows,int page){
		return dao.select(grade,rows,page);
	}

	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.service.IGradeService#update(cn.grades.domain.Grade)
	 */
	@Override
	public int update(Grade grade){
		return dao.update(grade);
	}
	
	
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.service.IGradeService#add(cn.grades.domain.Grade)
	 */
	@Override
	public int add(Grade grade){
		return dao.add(grade);
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.service.IGradeService#delete(java.lang.String)
	 */
	@Override
	public int delete(String id){
		return dao.delete(id);
	}
	
	
}
