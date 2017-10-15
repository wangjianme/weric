package cn.grades.studs.dao;

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
import cn.grades.domain.Major;
import cn.grades.domain.Stud;

public class StudsDaoJbdc extends BaseDao implements IStudsDao  {
	public Map<String, Object> query(int page, int rows) {
		// 1:查询出共多少行
		String sql = "select count(1) from studs";
		int totalRows = run.query(sql, new ScalarHandler<Number>()).intValue();
		int start = (page - 1) * rows;
		sql = "select stud_id as id,stud_name as name,stud_sex as sex,"+"stud_no as no,stud_age as age,stud_idcard as idcard,stud_addr as addr"
				+ " from  studs limit " + start + "," + rows;
		List<Stud> list = run.query(sql, new BeanListHandler<Stud>(Stud.class));
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalRows);
		map.put("rows", list);
		return map;
	}
	
	public Stud save(Stud s) {
		//s.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into studs(stud_id,stud_name,stud_sex,stud_age,stud_idcard,stud_no,stud_pwd,stud_addr,stud_clasid) values(?,?,?,?,?,?,?,?,?)";
		run.update(sql, s.getId(), s.getName(),  s.getSex(),s.getAge(),s.getIdcard(),s.getNo(),s.getPwd(),s.getAddr(),s.getClasid());
		return s;
	}
	
	public int delete(String id) {
		String sql = "delete from studs where stud_id=?";
		int rows = run.update(sql, id);
		return rows;
	}
	public int update(Stud s){
		String sql="update studs set stud_name=?,stud_sex =?,stud_no =?,stud_idcard =?,stud_age =? ,stud_addr=? where stud_id=?";
		int rows=run.update(sql,s.getName(),s.getSex(),s.getNo(),s.getIdcard(),s.getAge(),s.getAddr(),s.getId());
		return rows;
				
	}
	@Override
	public List<Clas> selectClass() {
		String sql = "select clas_id as id,clas_name as name from clas";
		List<Clas> list = run.query(sql, new BeanListHandler<Clas>(Clas.class));
		return list;
	}
	//查询
	
	public Map<String, Object> select(Stud s) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> params = new ArrayList<Object>();
		String where = " where 1=1";
		//System.err.println(s.getClasid());
		
		if (!StringUtils.isEmpty(s.getClasid())) {
			where += " and stud_clasid= ?";
			params.add(s.getClasid());
		}
		System.err.println(s.getClasid());
		String sql0 = "select count(1) from studs s inner join  clas c  on  s.stud_clasid=c.clas_id"+where;
		//+where;
		int count = run.query(sql0, new ScalarHandler<Number>(),params.toArray()).intValue();
		map.put("total", count);
		String  sql = "select stud_id as id ,stud_name as name ,stud_sex as sex ,stud_no as no  ,stud_age as age ,stud_idcard as idcard ,"
		+"stud_addr as addr from studs s inner join  clas c  on  s.stud_clasid=c.clas_id"+where;
		List<Stud> list = run.query(sql, new BeanListHandler<Stud>(Stud.class),params.toArray());
		map.put("rows", list);
		return map;
	}

	
	

	
}
