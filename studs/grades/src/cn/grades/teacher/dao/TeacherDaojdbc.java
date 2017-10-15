package cn.grades.teacher.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import cn.grades.core.BaseDao;

import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Teacher;
import cn.grades.domain.Title;

public class TeacherDaojdbc extends BaseDao implements ITeacherDao {
	@Override
	public Map<String, Object> query(int page, int rows) {
		String sql = "select count(1) from teachers";
		int totalRows = run.query(sql, new ScalarHandler<Number>()).intValue();
		int start = (page - 1) * rows;
		sql = "SELECT teacher_id AS id,teacher_name AS nm,teacher_sex AS sex,teacher_tel AS tel,"
				+ "departments.dept_name AS institute,degrees.degrees_name AS edu,title.title_name AS rank,"
				+ "teacher_major AS sub,teacher_date AS date FROM teachers INNER JOIN departments ON teachers"
				+ ".teacher_deptid=departments.dept_id INNER JOIN degrees ON teachers.teacher_degreesid"
				+ "=degrees.degrees_id INNER JOIN title ON teachers.teacher_titleid=title.title_id limit " + start + ","
				+ rows;
		List<Teacher> list = run.query(sql, new BeanListHandler<Teacher>(Teacher.class));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", totalRows);
		map.put("rows", list);
		return map;
	}

	@Override
	public Teacher save(Teacher teacher) {
		teacher.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into teachers(teacher_id,teacher_name,teacher_sex,teacher_tel,teacher_deptid"
				+ ",teacher_degreesid,teacher_titleid,teacher_major,teacher_date)" + " values(?,?,?,?,?,?,?,?,?)";
		System.out.println("---------------------------------------保存来了啊");
		run.update(sql, teacher.getId(), teacher.getNm(), teacher.getSex(), teacher.getTel(), teacher.getInstitute(),
				teacher.getEdu(), teacher.getRank(), teacher.getSub(), teacher.getDate());
		return teacher;
	}

	@Override
	public int delete(String id) {
		String sql = "delete from teachers where teacher_id=?";
		int rows = run.update(sql, id);
		return rows;
	}

	/**
	 * 修改
	 */
	@Override
	public int update(Teacher teacher) {
		String sql = "UPDATE teachers SET teacher_name=?,teacher_sex=?,teacher_tel=?,teacher_deptid=(SELECT dept_id FROM departments WHERE dept_name="+"?),teacher_degreesid=(SELECT degrees_id FROM DEGREES WHERE degrees_name="+"?),teacher_titleid=(SELECT title_id FROM title WHERE title_name="+"?),teacher_major=?,teacher_date=? WHERE teacher_id="+"?";
		int rows = run.update(sql, teacher.getNm(), teacher.getSex(), teacher.getTel(), teacher.getInstitute(),
				teacher.getEdu(), teacher.getRank(), teacher.getSub(), teacher.getDate(), teacher.getId());
		return rows;
	}

	@Override
	public List<Department> ins() {
		String sql = "SELECT dept_id AS id,dept_name  AS text FROM departments WHERE dept_isdept=1";
		List<Department> list = run.query(sql, new BeanListHandler<Department>(Department.class));
		return list;
	}

	@Override
	public List<Degrees> edu() {
		String sql = "SELECT degrees_id as degrees_id,degrees_name as degrees_name from degrees";
		List<Degrees> list = run.query(sql, new BeanListHandler<Degrees>(Degrees.class));
		return list;
	}

	@Override
	public List<Title> rank() {
		String sql = "SELECT title_id as id,title_name as name from title";
		List<Title> list = run.query(sql, new BeanListHandler<Title>(Title.class));

		return list;
	}
	
	/*以下是查询*/

	public Map<String, Object> select(Teacher teacher) {
		
		Map<String , Object> map=new HashMap<String, Object>();
		//int start = (page - 1) * rows;
		List<Object> params = new ArrayList<Object>();
		String where = " where 1=1";
		if (!StringUtils.isEmpty(teacher.getNm())) {
			where += " and teacher_name like ?";
			params.add("%" + teacher.getNm() + "%");
		}
		if (StringUtils.isNotEmpty(teacher.getInstitute())) {
			where += " and  teacher_deptid = ? ";
			params.add(  teacher.getInstitute().trim() );
		}
	
		String sql = "SELECT teacher_id AS id,teacher_name AS nm,teacher_sex AS sex,teacher_tel AS tel,departments.dept_name AS institute,degrees.degrees_name AS edu,title.title_name AS rank,teacher_major AS sub,teacher_date AS DATE FROM teachers INNER JOIN departments ON teachers.teacher_deptid=departments.dept_id  INNER JOIN degrees ON teachers.teacher_degreesid=degrees.degrees_id INNER JOIN title ON teachers.teacher_titleid=title.title_id "+where;
		String  sql2 = "select count(1) from teachers " + where;
		int count = run.query(sql2, new ScalarHandler<Number>(), params.toArray()).intValue();
		List<Teacher> list = run.query(sql, new BeanListHandler<Teacher>(Teacher.class),params.toArray());
		map.put("total", count);
		map.put("rows", list);
		System.out.println("map:"+map);
		return map;
	}

}
