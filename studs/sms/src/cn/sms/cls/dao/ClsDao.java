package cn.sms.cls.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.sms.core.BaseDao;
import cn.sms.domain.Cls;

public class ClsDao extends BaseDao {
	public Map<String, Object> query() {
		String sql = "select clazz_id as id,clazz_name as name,clazz_gradeid as gradeid,"
				+ "grade_name as grade,clazz_majorid as majorid,major_name as major"
				+ " from clazz inner join grade on clazz_gradeid=grade_id"
				+ " inner join major on major_id=clazz_majorid";
		List<Cls> list = run.query(sql, new BeanListHandler<Cls>(Cls.class));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	
	public Cls save(Cls cls){
		String sql = "insert into clazz(clazz_id,clazz_name,clazz_gradeid,clazz_majorid)"
				+ " values(?,?,?,?)";
		cls.setId(UUID.randomUUID().toString().replace("-", ""));
		run.update(sql,cls.getId(),cls.getName(),cls.getGradeid(),cls.getMajorid());
		return cls;
	}
}
