package cn.grades.major.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import cn.grades.core.BaseDao;
import cn.grades.domain.Major;

public class MajorDaoJdbc extends BaseDao implements  IMajorDao {
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.dao.IMajorDao#query()
	 */
	@Override
	public Map<String, Object> query() {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(1) from major";
		int count = run.query(sql, new ScalarHandler<Number>()).intValue();
		map.put("total", count);
		System.out.println("totle:" + count);
		sql = "select m.major_id as id,m.major_desc as dsc,m.major_name as major,d.dept_name as groupName from major m join departments d on m.major_deptid=d.dept_id";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class));
		map.put("rows", list);
		return map;
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.dao.IMajorDao#select(cn.grades.domain.Major)
	 */
	@Override
	public Map<String, Object> select(Major major) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> params = new ArrayList<Object>();
		String where = " where 1=1";
		if (!StringUtils.isEmpty(major.getDeptVal())) {
			where += " and d.dept_id like ?";
			params.add("%" + major.getDeptVal().trim() + "%");
		}
		if (StringUtils.isNotEmpty(major.getMajorVal())) {
			where += " and m.major_id like ?";
			params.add("%" + major.getMajorVal().trim() + "%");
		}
		String sql0 = "select count(1) from major m join departments d on m.major_deptid=d.dept_id"+where;
		int count = run.query(sql0, new ScalarHandler<Number>(),params.toArray()).intValue();
		map.put("total", count);
		String  sql = "select m.major_id as id,m.major_desc as dsc,m.major_name as major,d.dept_name as groupName from major m join departments d on m.major_deptid=d.dept_id"+where;
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class),params.toArray());
		map.put("rows", list);
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.grades.manager.major.dao.IMajorDao#selectDept()
	 */
	@Override
	public List<Major> selectDept() {
		String sql = "select dept_id as id,dept_name as major from  departments where dept_isdept =1";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class));
		return list;
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.dao.IMajorDao#selectMajor()
	 */
	@Override
	public List<Major> selectMajor() {
		String sql = "select major_id as id,major_name as major from major";
		List<Major> list = run.query(sql, new BeanListHandler<Major>(Major.class));
		return list;
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.dao.IMajorDao#delete(java.lang.String)
	 */
	@Override
	public int delete(String id) {
		try {
			String sql = "delete from major where major_id=?";
			int rows = run.update(sql, id);
			return rows;
		} catch (Exception e) {
			return 0;
		}
//		String sql = "delete from major where major_id=?";
//		int rows = run.update(sql, id);
//		return rows;
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.dao.IMajorDao#save(cn.grades.domain.Major)
	 */
	@Override
	public int save(Major  major) {
		String groupName=major.getGroupName();
		System.out.println(groupName);
		System.out.println(major.getMajor());
		System.out.println(major.getDsc());
		String sql = "select dept_id as id from departments where dept_name=?";
		Major m = run.query(sql, new BeanHandler<Major>(Major.class),groupName);
		System.out.println(m.getId());
		if (major.getId()==null||major.getId().equals("")) {
			String sql2 = "insert into major values(?,?,?,?)";
			int rows = run.update(sql2, UUID.randomUUID().toString().replace("-", ""),major.getMajor(),major.getDsc(),m.getId());
			return rows;
		}else {
			String sql2 = "update major set major_name=?, major_desc=? where major_id=?";
			int rows = run.update(sql2, major.getMajor(),major.getDsc(),major.getId());
			return rows;
		}
	}
}