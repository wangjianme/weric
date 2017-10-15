package cn.grades.score.dao;



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
import cn.grades.domain.Course;
import cn.grades.domain.Grade;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;


public class ScoreDaoJdbc extends BaseDao implements  IScoreDao {

	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#query(int, int)
	 */

	@Override
	public Map<String, Object> query(int page, int rows) {
		// 1:查询出共多少行
		String sql = "select count(1) from scores";
		int totalRows = run.query(sql, new ScalarHandler<Number>()).intValue();
		int start = (page - 1) * rows;
		sql = "select score_id as id,stud_name as studid,score_courseid as courseid,score_score as score,"
				+"score_term as term,score_type as type,score_datetime as datetime,score_teacher as teacher"
				+" from scores join studs on score_studid=stud_id limit " + start + "," + rows;
		List<Score> list = run.query(sql, new BeanListHandler<Score>(Score.class));
		
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalRows);
		map.put("rows", list);
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#save(cn.grades.domain.Score)
	 */
	
	
	

	@Override
	public Score save(Score sc) {
		sc.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into scores values(?,?,?,?,?,?,?,?)";
		System.out.println(sc.getId());
		System.out.println(sc.getStudid());
		System.out.println(sc.getCourseid());
		run.update(sql,sc.getId(),sc.getStudid(),sc.getCourseid(),sc.getScore(),sc.getTerm(),sc.getType(),sc.getDatetime(),sc.getTeacher() );
		return sc;
	}
	
	
	
	/*@Override
	public Object save(Object params) {
		//sc.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into scores values(?,?,?,?,?,?,?,?)";
		run.update(sql,params);
		return params;
	}*/
	
	
	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#query1()
	 */
	@Override
	public List<Stud> query1() {
		String sql = "select stud_name name,stud_id id from studs";
		List<Stud> list = run.query(sql, new BeanListHandler<Stud>(Stud.class));
		return list;
	}
	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#query2()
	 */
	@Override
	public List<Course> query2    () {
		String sql = "select  course_name name,course_id courseid from course";
		List<Course> list = run.query(sql, new BeanListHandler<Course>(Course.class));
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#select(cn.grades.domain.Score)
	 */


	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#delete(java.lang.String)
	 */
	@Override
	public int delete(String id) {
		String sql = "delete from scores where score_id=?";
	//Score sc=new Score();
		int rows = run.update(sql, id);
		return rows;
	}
	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#delete1(java.lang.String)
	 */
	@Override
	public int delete1(String studid){
		String sql = "delete from scores where score_studid='?'";
	
		int rows = run.update(sql, studid);
		return rows;
	}
	/* (non-Javadoc)
	 * @see cn.grades.score.dao.IScoreDao#delete2(java.lang.String)
	 */
	@Override
	public int delete2( String courseid){
		String sql = "delete from scores where score_courseid='?'";
	
		int rows = run.update(sql, courseid);
		return rows;
	}
	
	
	
	public int update(Score sc) {
		String sql = "update scores set score_score=?,"
				+ "score_term=?,score_type=?,score_datetime=?,score_teacher=? where score_id=?";
		
		int rows = run.update(sql,sc.getScore(),sc.getTerm(),sc.getType(),sc.getDatetime(),sc.getTeacher(),sc.getId() );
		return rows;
	}
	//score_studid=?,score_courseid=?,
	
	//sc.getStudid(),sc.getCourseid(),
	@Override
	public Map<String, Object> select(Score score) {
		Map<String , Object> map=new HashMap<String, Object>();
		//int start = (page - 1) * rows;
		//int end = start + rows;
		//int size = end - start;
		List<Object> params = new ArrayList<Object>();
		String where = " where 1=1";
		if (!StringUtils.isEmpty(score.getCourseid())) {
			where += " and score_courseid like ?";
			params.add("%" + score.getCourseid().trim() + "%");
		}
		if (StringUtils.isNotEmpty(score.getDatetime())) {
			where += " and score_datetime like ?";
			params.add("%" + score.getDatetime().trim() + "%");
		}
		if (StringUtils.isNotEmpty(score.getTerm())) {
			where += " and score_term like ?";
			params.add("%" + score.getTerm().trim() + "%");
		}
		System.out.println("===>"+params);
		// 1:查询数据表中的一共多少行
		String  sql = "select score_id as id,stud_name as studid,score_teacher as teacher,score_score as score,score_courseid as courseid," + "score_datetime as datetime,score_term as term from scores join studs on score_studid=stud_id " + where+" order by score_datetime DESC" ;
		String  sql2 = "select count(1) from scores " + where;
		System.out.println(sql);
		System.out.println(sql2);
		System.out.println(params);
		int count = run.query(sql2, new ScalarHandler<Number>(), params.toArray()).intValue();
		List<Score> list = run.query(sql, new BeanListHandler<Score>(Score.class),params.toArray());
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

}


