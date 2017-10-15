package cn.grades.manager.dept.degrees.dao;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.grades.core.BaseDao;
import cn.grades.domain.Degrees;

public class DegreesDaoJdbc extends BaseDao implements IDegreesDao {
	public List<Degrees> query() {
		String sql = "select * from degrees";
		List<Degrees> list = run.query(sql, new BeanListHandler<Degrees>(Degrees.class));

		return list;
	}

	public Degrees save(Degrees degrees) {
		String sql = "insert into degrees values(?,?,?)";
		degrees.setDegrees_id(UUID.randomUUID().toString().replace("-", ""));
		run.update(sql, degrees.getDegrees_id(), degrees.getDegrees_name(), degrees.getDegrees_desc());
		return degrees;
	}

	public int delete(String id) {
		String sql = "delete from degrees where degrees_id=?";
		int rows = run.update(sql,id);
		return rows;
	}
	public int update(Degrees degrees){
		String sql ="update degrees set degrees_name=?,degrees_desc=? where degrees_id=?";
		int rows = run.update(sql, degrees.getDegrees_name(), degrees.getDegrees_desc(), degrees.getDegrees_id());
		return rows;
	}	
}
