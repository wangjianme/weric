 package cn.grades.teacherCensusService;

import java.util.List;
import java.util.Map;

import cn.grades.domain.TeacherCensus;
import cn.grades.teacherCensusDao.TeacherCensusDaoJdbc;

public class TeacherCensusService implements ITeacherCensusService {
	/* (non-Javadoc)
	 * @see cn.grades.teacherCensusService.ITeacherCensusService#selectDept()
	 */
	@Override
	public List<TeacherCensus> selectDept() {
		return new TeacherCensusDaoJdbc().selectDept();
	}
	/* (non-Javadoc)
	 * @see cn.grades.teacherCensusService.ITeacherCensusService#titleSelect()
	 */
	@Override
	public List<Map<String,Object>> titleSelect() {
		return new  TeacherCensusDaoJdbc().titleSelect();
	}
	/* (non-Javadoc)
	 * @see cn.grades.teacherCensusService.ITeacherCensusService#degreeSelect()
	 */
	@Override
	public List<Map<String,Object>> degreeSelect() {
		return new  TeacherCensusDaoJdbc().degreeSelect();
	}
}
