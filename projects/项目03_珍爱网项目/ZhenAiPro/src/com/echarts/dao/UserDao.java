package com.echarts.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.echarts.utils.DaoUtils;

public class UserDao {
	//获取各个年龄段的单身人数
	public Long getAgeCount(List<String> ageList) {
		String sql="select count(*) from users where age in (?,?,?,?,?,?,?,?,?,?)";
		
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long)runner.query(sql,new ScalarHandler(1),ageList.get(0),ageList.get(1),ageList.get(2),ageList.get(3),ageList.get(4),ageList.get(5),ageList.get(6),
					ageList.get(7),ageList.get(8),ageList.get(9));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	}
	public Long getAgeCountByBoy(List<String> ageList){
		String sql="select count(*) from (select * from users where gender='男') users where age in (?,?,?,?,?,?,?,?,?,?)";
		
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long)runner.query(sql,new ScalarHandler(1),ageList.get(0),ageList.get(1),ageList.get(2),ageList.get(3),ageList.get(4),ageList.get(5),ageList.get(6),
					ageList.get(7),ageList.get(8),ageList.get(9));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	//根据学历获取人数
	public Long getCountBySchool(String schoolstr) {
		String sql="select count(*) from users where school=?";	
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long) runner.query(sql, new ScalarHandler(1),schoolstr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public Long getCountByGender(String genderstr) {
		String sql="select count(*) from users where gender=?";	
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long) runner.query(sql, new ScalarHandler(1),genderstr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public Long getCountByCity(String citystr) {
		String sql="select count(*) from users where work_add like ?";	
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long) runner.query(sql, new ScalarHandler(1),citystr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public Long getCountBySalary(String salarystr) {
		String sql="select count(*) from users where salary=?";	
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long) runner.query(sql, new ScalarHandler(1),salarystr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	public Long getCountByBoySalary(String salarystr) {
		String sql="select count(*) from (select * from users where gender='男') users where salary=?";	
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long) runner.query(sql, new ScalarHandler(1),salarystr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	public Long getCountByGZ(String string) {
		String sql="select count(*) from users where work_add = ?";	
		try {
			QueryRunner runner=new QueryRunner(DaoUtils.getSource());
			return (Long) runner.query(sql, new ScalarHandler(1),string);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getGD(String myString) {
		String sql="SELECT work_add FROM (SELECT * FROM users WHERE work_add LIKE ?) _user GROUP BY work_add";	
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			List<Object[]> list = runner.query(sql, new ArrayListHandler(),myString);
			List<String> mylist = new ArrayList<String>();
			for(Object[] objs:list){
				 for(Object o:objs)
					 mylist.add((String) o);
			}
			return mylist;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		
		}
	}
	
	

}


