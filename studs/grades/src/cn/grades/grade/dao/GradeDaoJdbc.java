package cn.grades.grade.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import cn.grades.core.BaseDao;
import cn.grades.domain.Grade;

public class GradeDaoJdbc extends BaseDao implements IGradeDao  {

	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.dao.IGradeDao#query(int, int)
	 */
	@Override
	public Map<String, Object> query(int rows, int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (page - 1) * rows;
		int end = start + rows;
		int size = end - start;
		System.out.println("start:" + start);
		System.out.println("end:" + end);
		String sql = "select count(1) from grades";
		Object obj = run.query(sql, new ScalarHandler<Number>());
		map.put("total", obj);
		sql = "select grade_id as id,grade_name as name," + "grade_date as dt,grade_desc as dsc from grades " +" order by grade_date DESC"+ " limit "
				+ start + "," + size;
		List<Grade> list = run.query(sql, new BeanListHandler<Grade>(Grade.class));
		map.put("rows", list);
		return map;
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.dao.IGradeDao#selectGrade()
	 */
	@Override
	public List<Grade> selectGrade() {
		String sql = "select grade_id as id,grade_name as name from  grades ";
		List<Grade> list = run.query(sql, new BeanListHandler<Grade>(Grade.class));
		return list;
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.dao.IGradeDao#select(cn.grades.domain.Grade, int, int)
	 */
	@Override
	public Map<String, Object> select(Grade grade,int rows, int page) {
		Map<String , Object> map=new HashMap<String, Object>();
		int start = (page - 1) * rows;
		int end = start + rows;
		int size = end - start;
		List<Object> params = new ArrayList<Object>();
		String where = " where 1=1";
		if (!StringUtils.isEmpty(grade.getName())) {
			where += " and grade_name like ?";
			params.add("%" + grade.getName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(grade.getDt())) {
			where += " and grade_date like ?";
			params.add("%" + grade.getDt().trim() + "%");
		}
		if (StringUtils.isNotEmpty(grade.getDsc())) {
			where += " and grade_desc like ?";
			params.add("%" + grade.getDsc().trim() + "%");
		}
		// 1:查询数据表中的一共多少行
		String  sql = "select grade_id as id,grade_name as name," + "grade_date as dt,grade_desc as dsc from grades " + where+" order by grade_date DESC"+ " limit "
				+ start + "," + size;
		String  sql2 = "select count(1) from grades " + where;
		int count = run.query(sql2, new ScalarHandler<Number>(), params.toArray()).intValue();
		List<Grade> list = run.query(sql, new BeanListHandler<Grade>(Grade.class),params.toArray());
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.dao.IGradeDao#update(cn.grades.domain.Grade)
	 */
	@Override
	public int update(Grade grade) {
		String sql = "update grades set grade_name=?, grade_date=?,grade_desc=? where grade_id=?";
		System.out.println(grade.getDt() + grade.getDsc() + grade.getId());
		int rows = run.update(sql, grade.getName(),grade.getDt(), grade.getDsc(), grade.getId());
		return rows;
	}

	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.dao.IGradeDao#add(cn.grades.domain.Grade)
	 */
	@Override
	public int add(Grade grade) {
		String sql = "select * from grades where grade_name=?";
		Object o[] = run.query(sql, new ArrayHandler(), grade.getName());
		System.out.println("---" + grade.getName());
		if (o != null && o.length != 0) {
			return 2;
		}
		sql = "insert into grades values(?,?,?,?)";
		int rows = run.update(sql, UUID.randomUUID().toString().replace("-", ""), grade.getName(), grade.getDt(), grade.getDsc());
		return rows;
	}

	
	/* (non-Javadoc)
	 * @see cn.grades.manager.grade.dao.IGradeDao#delete(java.lang.String)
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from grades where grade_id=?";
		int rows = run.update(sql, id);
		return rows;
	}
	

	
	
}
