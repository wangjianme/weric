package cn.grades.manager.coll.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.CollScore;
import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Major;
import cn.grades.manager.coll.CollScoreServlet;
import cn.grades.manager.coll.dao.CollScoreDao;
import cn.grades.manager.coll.dao.ICollScoreDao;


public class CollScoreService implements ICollScoreService {
	private ICollScoreDao dao = new CollScoreDao();

	@Override
	public List<CollScore> query() {
		return new CollScoreDao().query();
	}

	@Override
	public List<Department> ins() {

		return dao.ins();
	}

	@Override
	public List<Degrees> edu() {
		return dao.edu();
	}
	public List<Major> selectDept(String deptid1) {
		return dao.selectDept(deptid1);
	}
	public List<Map<String,Object>> titleSelect(String k) {
		return new  CollScoreDao().titleSelect(k);
	}
	
	public List<Major>majorVal (){
		return dao.majorVal();
	}
	
	//联动
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
}
//联动以上
