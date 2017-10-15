package cn.grades.manager.coll.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.grades.core.BaseDao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.domain.CollScore;
import cn.grades.domain.Course;
import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Major;

public class CollScoreDao extends BaseDao implements  ICollScoreDao {
	@Override
	public List<CollScore> query() {
		String sql = "select s.stud_name as name,g.grade_name as grade,m.cm_majorid as majorid,t.score_score as score,"
				+ "from studs s,grades g,course_major m,scores t";

		List<CollScore> list = run.query(sql, new BeanListHandler<CollScore>(CollScore.class));
		for (CollScore collScore : list) {
			System.out.println(collScore.toString());
		}
		return list;
	}
	//联动
	public List<Major> selectMajor(String deptid) {
		String sql = "select major_id as id,major_name as major from  major where major_deptid=?";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class), deptid);
		return list;
	}

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#selectGrade()
	 */
	@Override
	public List<Major> selectGrade() {
		String sql = "select grade_id as id,grade_name as major from  grades ";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class));
		return list;
	}
//以上联动
	@Override
	public List<Department> ins() {
		String sql = "SELECT dept_id AS id,dept_name  AS text FROM departments WHERE dept_isdept=1";
		List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class));
		return list;
	}
	@Override
	public List<Major> majorVal() {
		String sql = "SELECT major_id as id,major_name as majorVal from major";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class));
		return list;
	}
	@Override
	public List<Degrees> edu() {
		String sql = "SELECT degrees_id as degrees_id,degrees_name as degrees_name from degrees";
		List<Degrees> list = run.query(sql, new BeanListHandler<Degrees>(Degrees.class));
		return list;
	}
// 下面是保表数据
	public List<Major> selectDept(String deptid) {
		//String sql = "select dept_name as name from  departments where dept_isdept =1";
		//List<CollScore> list = run.query(sql, new BeanListHandler<CollScore>(CollScore.class));
		//return list;
		String sql = "select major_id as id,major_name as major from  major where major_deptid=?";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class), deptid);
		return list;
	}
	
	
	public List<Map<String,Object>> titleSelect(String m_id) {
		List<Map<String,Object>> list=new ArrayList<>();
		String  sql="SELECT mg_id _id FROM major_grade WHERE mg_majorid = ?";
		List<Course> listTitle = run.query(sql, new BeanListHandler<Course>(Course.class),m_id);
		for (Course course:listTitle) {
			String sql2="SELECT cg_courseid id FROM course_grade WHERE cg_mgid = ?";
			Course listTitle2 = run.query(sql2, new BeanHandler<Course>(Course.class),course.get_id());
			
			String sql3="SELECT course_name name,course_id id FROM course WHERE course_id =?";
			Course listTitle3 = run.query(sql3, new BeanHandler<Course>(Course.class),listTitle2.getId());

			String sql4 ="SELECT AVG(score_score) score FROM scores WHERE score_courseid =?";
			Course listTitle4 = run.query(sql4, new BeanHandler<Course>(Course.class),listTitle3.getId());
			
			Map<String ,Object> map=new HashMap<>();
			map.put("name", listTitle3.getName());
			
			List<Integer> listCount=new ArrayList<>();
			listCount.add(listTitle4.getScore());
			
			map.put("data", listCount);
			list.add(map);
		}
		return list;
	}
	

}
