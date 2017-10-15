package cn.grades.slogin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;

import cn.grades.core.BaseDao;
import cn.grades.core.Md5Utils;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.domain.User;

public class LoginDaoJdbc extends BaseDao implements ILoginDao {

	public Stud login1(Stud stud){
		String sql = "select stud_id as id,stud_name as name,stud_salt as salt,stud_pwd as pwd"
				+ " from studs where stud_id=?";
		Stud loginStud= run.query(sql, new BeanHandler<Stud>(Stud.class),stud.getName());
		if(loginStud!=null){
			//比较密码
			//stud.setPwd(Md5Utils.md5(stud.getPwd(),loginStud.getSalt()));
			if(stud.getPwd().equals(loginStud.getPwd())){
				return loginStud;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	@Override
	public List<Stud> query(String id){
	
		String sql = "select stud_id as id,stud_name as name,stud_pwd as pwd,"
				+ "stud_no as no,stud_sex as sex,stud_idcard as idcard,stud_age as age,stud_email as email,stud_tel as tel,stud_height as height,stud_wight as wight,stud_qq as qq,stud_minzu as minzu,stud_jibie as jibie,stud_clasid as clasid,stud_addr as addr"
				+ " from studs "+
				" WHERE stud_id=?";
		List<Stud> list = 
				run.query(sql,new BeanListHandler<Stud>(Stud.class),id);
		return list;
	}
	@Override
	public List<Score> queryscore(String id,Score s){
		List<Object> params = new ArrayList<Object>();
		String and = " WHERE score_studid=?";
		params.add(id);
		if (!StringUtils.isEmpty(s.getType())) {
			and += " and score_id like ?";
			params.add("%" + s.getType().trim() + "%");
		}
		if (StringUtils.isNotEmpty(s.getTerm())) {
			and += " and score_term like ?";
			params.add("%" + s.getTerm().trim() + "%");
		}

		String sql = "select score_id as id,score_studid as studid,score_courseid as courseid,score_score as score,"
				+"score_term as term,score_type as type,score_datetime as datetime,score_teacher as teacher"
				+" from scores "+  and;	
		System.out.println("====");
		System.out.println(id);
		System.out.println(params.toString());
		List<Score> list = 
				run.query(sql,new BeanListHandler<Score>(Score.class),params.toArray());
		System.out.println(list);
		return list;
	}

}
