package com.echarts.service;

import java.util.List;

import com.echarts.dao.UserDao;
import com.echarts.utils.BasicFactory;

public class UserService {
	private UserDao dao=BasicFactory.getFactory().getInstance(UserDao.class);

	//年龄获取人数
	public Long getAgeCount(List<String> ageList) {
		return dao.getAgeCount(ageList);
	}
	//年龄获取人数
	public Long getAgeCountByBoy(List<String> ageList) {
		return dao.getAgeCountByBoy(ageList);
	}
	//根据学历获取人数
	public Long getCountBySchool(String schoolstr) {
		return dao.getCountBySchool(schoolstr);
	}
	//获取单身男女的比例
	public Long getCountByGender(String genderstr) {
		
		return dao.getCountByGender(genderstr);
	}//获取各个省份单身人士数量及比例
	public Long getCountByCity(String citystr) {
		
		return dao.getCountByCity(citystr);
	}
	//货物单身人士工资收入
	public Long getCountBySalary(String salarystr) {
		// TODO Auto-generated method stub
		return dao.getCountBySalary(salarystr);
	}
	//货物单身人士工资收入
	public Long getCountByBoySalary(String salarystr) {
		// TODO Auto-generated method stub
		return dao.getCountByBoySalary(salarystr);
	}
	/**
	 * 获得广州各市的人数
	 * @param string
	 * @return
	 */
	public Long getCountByGZ(String string) {
		// TODO Auto-generated method stub
		return dao.getCountByGZ(string);
	}
	/**
	 * 获得具体省的城市
	 * @param string
	 * @return
	 */
	public List<String> getGD(String string) {
		// TODO Auto-generated method stub
		return dao.getGD(string);
	}

}
