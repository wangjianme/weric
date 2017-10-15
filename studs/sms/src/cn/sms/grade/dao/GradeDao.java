package cn.sms.grade.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.sms.core.BaseDao;
import cn.sms.domain.Grade;

public class GradeDao extends BaseDao {
	public Map<String, Object> query(int rows, int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (page - 1) * rows;
		int end = start + rows;
		String sql = "select count(1) from grade";
		Object obj = run.query(sql, new ScalarHandler<Number>());
		map.put("total", obj);
		sql = "select * from(select rownum as n,grade_id as id,grade_name as name,"
				+ "grade_date as dt,grade_desc as dsc from grade) where n>" + start + " and n<=" + end;

		List<Grade> list = run.query(sql, new BeanListHandler<Grade>(Grade.class));
		map.put("rows", list);
		return map;
	}

	public int delete(String id) {
		String sql = "delete from grade where grade_id=?";
		int rows = run.update(sql, id);
		return rows;
	}

	public List<Grade> query() {
		String sql = "select grade_id as id," + "grade_name as name from grade";
		List<Grade> gs = run.query(sql, new BeanListHandler<Grade>(Grade.class));
		return gs;
	}
}
