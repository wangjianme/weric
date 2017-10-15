package cn.grades.spwd.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.grades.core.BaseDao;
import cn.grades.core.Md5Utils;
import cn.grades.domain.Stud;

public class PwdDaoJdbc extends BaseDao implements IPwdDao {
	@Override
	public int updatePwd(Stud stud,String newPwd){
		   //String newPwdMd5 =Md5Utils.md5(newPwd, stud.getSalt());
		   String sql ="update studs set stud_pwd=? where stud_id=?";
		   int rows = run.update(sql,newPwd,stud.getId());
		return rows;
		   
	   }
}
