package cn.grades.test.example.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.core.BaseDao;
import cn.grades.domain.Example;

public class ExampleDaoJdbc extends BaseDao implements IExampleDao {

	public Map<String, Object> query(int page, int rows) {
		// 1:查询出共多少行
		String sql = "select count(1) from example";
		int totalRows = run.query(sql, new ScalarHandler<Number>()).intValue();
		int start = (page - 1) * rows;
		sql = "select example_id as id,example_name as name," + "example_addr as addr,example_sex as sex"
				+ " from example limit " + start + "," + rows;
		List<Example> list = run.query(sql, new BeanListHandler<Example>(Example.class));
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalRows);
		map.put("rows", list);
		return map;
	}

	public Example save(Example exp) {
		exp.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into example values(?,?,?,?)";
		run.update(sql, exp.getId(), exp.getName(), exp.getAddr(), exp.getSex());
		return exp;
	}
}
