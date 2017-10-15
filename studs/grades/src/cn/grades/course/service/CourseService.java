package cn.grades.course.service;

import java.util.List;
import java.util.Map;
import cn.grades.course.dao.CourseDaoJdbc;
import cn.grades.course.dao.ICourseDao;
import cn.grades.domain.Course;
import cn.grades.domain.Major;

public class CourseService implements ICourseService  {
	private ICourseDao dao = new CourseDaoJdbc();

	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#addCourse(cn.grades.domain.Course)
	 */
	@Override
	public int addCourse( Course course ){
		return dao.addCourse(course);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#update(cn.grades.domain.Course)
	 */
	@Override
	public int update( Course course ){
		return dao.update(course);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#add(cn.grades.domain.Course)
	 */
	@Override
	public int add(Course course) {
		return dao.add(course);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#query(int, int)
	 */
	@Override
	public Map<String, Object> query(int page,int rows) {
		return dao.query(page,rows);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#search(cn.grades.domain.Course)
	 */
	@Override
	public Map<String, Object> search(Course course) {
		return dao.search(course);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#selectMajor(java.lang.String)
	 */
	@Override
	public List<Major> selectMajor(String deptid) {
		return dao.selectMajor(deptid);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#selectGrade()
	 */
	@Override
	public List<Major> selectGrade() {
		return dao.selectGrade();
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#selectmg_id(cn.grades.domain.Course)
	 */
	@Override
	public String  selectmg_id(Course course) {
		return dao.selectmg_id(course);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#delete(java.lang.String)
	 */
	@Override
	public int delete( String id ){
		return dao.delete(id);
	}
	/* (non-Javadoc)
	 * @see cn.grades.course.service.ICourseService#selectparent_id(java.lang.String)
	 */
	@Override
	public Map<String ,String >  selectparent_id(String  id) {
		return dao.selectparent_id(id);
	}
}
