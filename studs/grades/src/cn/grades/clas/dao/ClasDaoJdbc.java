package cn.grades.clas.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import cn.grades.core.BaseDao;
import cn.grades.domain.Clas;
import cn.grades.domain.Department;
import cn.grades.domain.Grade;
import cn.grades.domain.Major;

public class ClasDaoJdbc extends BaseDao implements IClasDao {
	@Override
	public Map<String, Object> query(int page, int rows) {
		String sql = "select count(1) from clas";
		int totalRows = run.query(sql, new ScalarHandler<Number>()).intValue();
		int start = (page - 1) * rows;
		sql = "select clas_id as id,clas_name as name," + "clas_des as des,grade_name as gradeid"
				+ ",major_name as majorid, dept_name as deptid"
				+ " from clas join grades on clas_gradeid=grade_id join major on clas_majorid=major_id join departments on clas_deptid=dept_id limit "
				+ start + "," + rows;
		List<Clas> list = run.query(sql, new BeanListHandler<Clas>(Clas.class));
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalRows);
		map.put("rows", list);
		return map;
	}

	@Override
	public Clas save(Clas clas) {
		clas.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into clas(clas_id,clas_name,clas_des,clas_gradeid,clas_majorid,clas_deptid)"
				+ " values(?,?,?,?,?,?)";

		run.update(sql, clas.getId(), clas.getName(), clas.getDes(), clas.getGradeid(), clas.getMajorid(),
				clas.getDeptid());
		return clas;
	}

	@Override
	public List<Department> text() {
		String sql = "SELECT dept_id as id,dept_name as text from departments where dept_isdept=1";
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
	public List<Grade> name() {
		String sql = "SELECT grade_id as id,grade_name as name from grades";
		List<Grade> list = run.query(sql, new BeanListHandler<Grade>(Grade.class));
		return list;
	}

	/* 删除 */
	public int delete(String id) {
		String sql = "delete from clas where clas_id=?";
		int rows = run.update(sql, id);
		return rows;
	}

	/* 修改 */
	@Override
	public int update(Clas clas) {
		String sql = "UPDATE clas SET clas_name=?,clas_des=?,clas_gradeid=(SELECT grade_id FROM grades WHERE grade_name="+"?),clas_majorid=(SELECT major_id FROM major WHERE major_name="+"?),clas_deptid=(SELECT dept_id FROM departments WHERE dept_name="+"?) WHERE clas_id=?";
		int rows = run.update(sql, clas.getName(), clas.getDes(), clas.getGradeid(), clas.getMajorid(),
				clas.getDeptid(),clas.getId());
		return rows;
	}

	/*
	 * 以下是查询
	 */


	public Map<String, Object> select(Clas clas, int rows, int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		String where = " where 1=1";
		if (!StringUtils.isEmpty(clas.getName())) {
			where += " and clas_name like ?";
			params.add("%" + clas.getName() + "%");
		}
		if (StringUtils.isNotEmpty(clas.getDeptid())) {
			where += " and  clas_deptid = ? ";
			params.add(clas.getDeptid());
		}
	    String sql = "SELECT clas_id as id ,clas_name as name,clas_des as des,grades.grade_name as gradeid,major.major_name as majorid,departments.dept_name as deptid from clas inner join grades on clas.clas_gradeid=grades.grade_id inner join major on clas.clas_majorid=major.major_id inner join departments on clas.clas_deptid=departments.dept_id"+where;
		String sql2 = "select count(1) from clas " + where;
		int count = run.query(sql2, new ScalarHandler<Number>(), params.toArray()).intValue();
		List<Clas> list = run.query(sql, new BeanListHandler<Clas>(Clas.class), params.toArray());
		map.put("total", count);
		map.put("rows", list);
		System.out.println("map:" + map);
		return map;
	}
}
