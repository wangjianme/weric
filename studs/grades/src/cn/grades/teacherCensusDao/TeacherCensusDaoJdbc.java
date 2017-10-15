package cn.grades.teacherCensusDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.core.BaseDao;
import cn.grades.domain.TeacherCensus;

public class TeacherCensusDaoJdbc extends BaseDao implements ITeacherCensusDao {
	/* (non-Javadoc)
	 * @see cn.grades.teacherCensusDao.ITeacherCensusDao#selectDept()
	 */
	@Override
	public List<TeacherCensus> selectDept() {
		String sql = "select dept_name as name from  departments where dept_isdept =1";
		List<TeacherCensus> list = run.query(sql, new BeanListHandler<TeacherCensus>(TeacherCensus.class));
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.grades.teacherCensusDao.ITeacherCensusDao#titleSelect()
	 */
	@Override
	public List<Map<String,Object>> titleSelect() {
		List<Map<String,Object>> list=new ArrayList<>();
		String  sql="select title_name as name from title";
		List<TeacherCensus> listTitle = run.query(sql, new BeanListHandler<TeacherCensus>(TeacherCensus.class));
		List<TeacherCensus> listDept = selectDept();
		for (TeacherCensus title:listTitle) {
			Map<String ,Object> map=new HashMap<>();
			map.put("name", title.getName());
			List<Integer> listCount=new ArrayList<>();
			for (TeacherCensus dept:listDept) {
				String sql2="select count(1) from teachers join title on teacher_titleid=title_id"
			    +" join departments on teacher_deptid = dept_id where dept_name=? and title_name=?";
				int count=run.query(sql2,new ScalarHandler<Number>(),dept.getName(),title.getName()).intValue();
				listCount.add(count);
			}
			map.put("data", listCount);
			list.add(map);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.grades.teacherCensusDao.ITeacherCensusDao#degreeSelect()
	 */
	@Override
	public List<Map<String,Object>> degreeSelect() {
		List<Map<String,Object>> list=new ArrayList<>();
		String  sql="select degrees_name as name from degrees";
		List<TeacherCensus> listDegrees = run.query(sql, new BeanListHandler<TeacherCensus>(TeacherCensus.class));
		List<TeacherCensus> listDept = selectDept();
		for (TeacherCensus title:listDegrees) {
			Map<String ,Object> map=new HashMap<>();
			map.put("name", title.getName());
			List<Integer> listCount=new ArrayList<>();
			for (TeacherCensus dept:listDept) {
				String sql2="select count(1) from teachers join degrees on teacher_degreesid=degrees_id"
						+" join departments on teacher_deptid = dept_id where dept_name=? and degrees_name=?";
				int count=run.query(sql2,new ScalarHandler<Number>(),dept.getName(),title.getName()).intValue();
				listCount.add(count);
			}
			map.put("data", listCount);
			list.add(map);
		}
		return list;
	}
}
