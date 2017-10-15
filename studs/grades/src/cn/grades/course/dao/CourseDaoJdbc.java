package cn.grades.course.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import cn.grades.core.BaseDao;
import cn.grades.domain.Course;
import cn.grades.domain.Major;

public class CourseDaoJdbc extends BaseDao implements ICourseDao {
  
	
	
	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#search(cn.grades.domain.Course)
	 */
	@Override
	public Map<String, Object> search(Course course) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		System.out.println("sign<>"+course.getSign());
		if (course.getSign().equals("2")) {
			//公开课
			String sqlPub = "select course_id id,course_pub pub,course_name as name,course_must as must,course_score as score,"
					+ " course_desc as dsc   from  course where course_pub=1 ";
			List<Course> listc = run.query(sqlPub, new BeanListHandler<Course>(Course.class));
			for (Course c : listc) {
				Map<String, Object> mapC = new HashMap<>();
				mapC.put("id", c.getId());
				mapC.put("dmg", "公开课");
				mapC.put("name", c.getName());
				mapC.put("must", c.getMust());
				mapC.put("pub", c.getPub());
				mapC.put("score", c.getScore());
				mapC.put("dsc", c.getDsc());
				mapC.put("iconCls", "icon-book_open");
				list.add(mapC);
			}
			
			
		}else if (course.getSign().equals("0")) {
			//必修课
			
			
			String sqlmgid = "select mg_id as id where mg_majorid=? and mg_gradeid=? ";
			Course mgid = run.query(sqlmgid, new BeanHandler<Course>(Course.class),course.getMajorid(),course.getGradeid());
			String sqlM = "select cg_courseid as courseid where cg_gradeid=? and cg_mgid=? ";
			List<Course> listc = run.query(sqlM, new BeanListHandler<Course>(Course.class),course.getGradeid(),mgid.getId());
			for (Course courseid:listc) {
				String sqlCourse = "select course_id id,course_pub pub,course_name as name,course_must as must,course_score as score,"
						+ " course_desc as dsc   from   course  where course_id=?";
				Course cou = run.query(sqlCourse, new BeanHandler<Course>(Course.class),courseid.getCourseid());
				Map<String, Object> mapCourse = new HashMap<>();
				mapCourse.put("id", cou.getId());
				mapCourse.put("dmg", "");
				mapCourse.put("name", cou.getName());
				mapCourse.put("must", cou.getMust());
				mapCourse.put("pub", cou.getPub());
				mapCourse.put("score", cou.getScore());
				mapCourse.put("dsc", cou.getDsc());
				mapCourse.put("_parentId", mgid.getId());
				mapCourse.put("iconCls", "icon-book_open");
				list.add(mapCourse);
				
				
			}
			
			
		}else {
			//选修课
			
		}
		
		
		map.put("rows", list);
		return map;
	}
	
	
	
	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#add(cn.grades.domain.Course)
	 */
	@Override
	public int add(Course course) {
		String sql = "insert into course values(?,?,?,?,?,?)";
		int row = run.update(sql, course.getCourseid(), course.getName(), course.getScore(), null, 1, course.getDsc());
		return row;
	}

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#selectmg_id(cn.grades.domain.Course)
	 */
	@Override
	public String selectmg_id(Course course) {
		String sql = "select mg_id id from major_grade where mg_majorid=? and mg_gradeid=?";
		Course c = run.query(sql, new BeanHandler<Course>(Course.class), course.getMajorid(), course.getGradeid());
		if (c != null) {
			System.out.println("mg_id:" + c.getId());
			return c.getId();
		} else {
			System.out.println("没有mg_id:");
			return "0";
		}
	}

	// 获得删除节点的上上级节点,即专业id
	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#selectparent_id(java.lang.String)
	 */
	@Override
	public Map<String, String> selectparent_id(String id) {
		Map<String, String> map = new HashMap<>();
		String sql = "select cg_mgid id from course_grade where cg_courseid=? ";
		Course c = run.query(sql, new BeanHandler<Course>(Course.class), id);
		if (c != null) {
			System.out.println("mg_id:" + c.getId());
			String sql2 = "select mg_majorid id from major_grade where mg_id=?";
			Course c2 = run.query(sql2, new BeanHandler<Course>(Course.class), c.getId());
			if (c2 != null) {
				map.put("majorid", c2.getId());
				String sql3 = "select major_deptid id from major where major_id=?";
				Course c3 = run.query(sql3, new BeanHandler<Course>(Course.class), c2.getId());
				if (c3 != null) {
					map.put("deptid", c3.getId());
				}
			}
		}

		return map;
	}

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#delete(java.lang.String)
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from course_grade where cg_courseid=?";
		int row = 0;
		try {
			row = run.update(sql, id);
			System.out.println(000);
			
				System.out.println(111);
				String sql2 = "delete from course where course_id=?";
				row = run.update(sql2, id);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return row;
	}

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#update(cn.grades.domain.Course)
	 */
	@Override
	public int update(Course course) {
		String sql = "update course set course_name=?, course_score=?, course_must=?, course_desc=? where course_id=?";
		String must;
		if (course.getMust().equals("null")) {
			 must="";
		}else{
			must=course.getMust();
		}
		int row = run.update(sql, course.getName(), course.getScore(), must, course.getDsc(),
				course.getCourseid());
		return row;
	}

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#addCourse(cn.grades.domain.Course)
	 */
	@Override
	public int addCourse(Course course) {
		String sqlselmg = "select mg_id id from major_grade where mg_majorid=? and mg_gradeid=?";
		System.out.println("<>" + course.getScore());
		if (course.getMajorid().equals("") || course.getGradeid().equals("") || course.getScore() == 0
				|| course.getName().equals("") || course.getMust().equals("")) {
			return 0;
		}
		Course listselmg = run.query(sqlselmg, new BeanHandler<Course>(Course.class), course.getMajorid(),
				course.getGradeid());
		if (listselmg == null || listselmg.equals("")) {
			String sqlCourse = "insert into course values(?,?,?,?,?,?)";
			String courseId = UUID.randomUUID().toString().replace("-", "");
			int rowc = run.update(sqlCourse, courseId, course.getName(), course.getScore(), course.getMust(), 0,
					course.getDsc());

			String sqlmg = "insert into major_grade values(?,?,?)";
			String mgId = UUID.randomUUID().toString().replace("-", "");
			int rowmg = run.update(sqlmg, mgId, course.getMajorid(), course.getGradeid());

			String sqlcg = "insert into course_grade values(?,?,?)";
			int rowcg = run.update(sqlcg, courseId, course.getGradeid(), mgId);
			if (rowc != 0 & rowmg != 0 & rowcg != 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			String sqlCourse = "insert into course values(?,?,?,?,?,?)";
			String courseId = UUID.randomUUID().toString().replace("-", "");
			int rowc = run.update(sqlCourse, courseId, course.getName(), course.getScore(), course.getMust(), 0,
					course.getDsc());

			String sqlcg = "insert into course_grade values(?,?,?)";
			int rowcg = run.update(sqlcg, courseId, course.getGradeid(), listselmg.getId());
			if (rowc != 0 & rowcg != 0) {
				return 1;
			} else {
				return 0;
			}
		}

	}

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#selectMajor(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see cn.grades.course.dao.ICourseDao#query(int, int)
	 */
	@Override
	public Map<String, Object> query(int page, int rows) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();

		String sqlDept = "select dept_id as id, dept_name as name from  departments where dept_isdept=1";
		List<Course> listDept = run.query(sqlDept, new BeanListHandler<Course>(Course.class));
		for (Course dept : listDept) {
			Map<String, Object> mapDept = new HashMap<>();
			mapDept.put("id", dept.getId());
			mapDept.put("dmg", dept.getName());
			mapDept.put("state", "closed");
			list.add(mapDept);
			String sqlMajor = "select major_id as id ,major_name as name from   major  where major_deptid=?";
			List<Course> listMajor = run.query(sqlMajor, new BeanListHandler<Course>(Course.class), dept.getId());
			System.out.println("Major:" + listMajor.toArray().length);
			for (Course major : listMajor) {
				System.out.println(1);
				Map<String, Object> mapMajor = new HashMap<>();
				mapMajor.put("id", major.getId());
				mapMajor.put("dmg", major.getName());
				mapMajor.put("_parentId", dept.getId());
				mapMajor.put("state", "closed");
				list.add(mapMajor);

				String sqlGrade = "select mg_id _id, mg_gradeid id,grade_name name  "
						+ "from major_grade join grades on mg_gradeid = grade_id where mg_majorid=?";
				List<Course> listGrade = run.query(sqlGrade, new BeanListHandler<Course>(Course.class), major.getId());
				for (Course grade : listGrade) {
					System.out.println(2);
					Map<String, Object> mapGrade = new HashMap<>();
					mapGrade.put("id", grade.get_id());
					mapGrade.put("dmg", grade.getName());
					mapGrade.put("_parentId", major.getId());
					mapGrade.put("state", "closed");
					list.add(mapGrade);
					String sqlCourse = "select course_id id,course_pub pub,course_name as name,course_must as must,course_score as score,"
							+ " course_desc as dsc   from  course_grade join course on course_id=cg_courseid where cg_mgid=?";
					List<Course> listCourse = run.query(sqlCourse, new BeanListHandler<Course>(Course.class),
							grade.get_id());
					for (Course course : listCourse) {
						System.out.println(3);
						Map<String, Object> mapCourse = new HashMap<>();
						mapCourse.put("id", course.getId());
						mapCourse.put("dmg", "");
						mapCourse.put("name", course.getName());
						mapCourse.put("must", course.getMust());
						mapCourse.put("pub", course.getPub());
						mapCourse.put("score", course.getScore());
						mapCourse.put("dsc", course.getDsc());
						mapCourse.put("_parentId", grade.get_id());
						mapCourse.put("iconCls", "icon-book_open");
						list.add(mapCourse);
					}

				}

			}
		}
		String sqlc = "select course_id id,course_pub pub,course_name as name,course_must as must,course_score as score,"
				+ " course_desc as dsc   from  course where course_pub=1 ";
		List<Course> listc = run.query(sqlc, new BeanListHandler<Course>(Course.class));
		for (Course c : listc) {
			Map<String, Object> mapC = new HashMap<>();
			mapC.put("id", c.getId());
			mapC.put("dmg", "公开课");
			mapC.put("name", c.getName());
			mapC.put("must", "null");
			mapC.put("pub", c.getPub());
			mapC.put("score", c.getScore());
			mapC.put("dsc", c.getDsc());
			mapC.put("iconCls", "icon-book_open");
			list.add(mapC);
		}
		map.put("rows", list);
		return map;
	}
}
