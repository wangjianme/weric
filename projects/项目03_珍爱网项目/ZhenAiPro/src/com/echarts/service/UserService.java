package com.echarts.service;

import java.util.List;

import com.echarts.dao.UserDao;
import com.echarts.utils.BasicFactory;

public class UserService {
	private UserDao dao=BasicFactory.getFactory().getInstance(UserDao.class);

	//�����ȡ����
	public Long getAgeCount(List<String> ageList) {
		return dao.getAgeCount(ageList);
	}
	//�����ȡ����
	public Long getAgeCountByBoy(List<String> ageList) {
		return dao.getAgeCountByBoy(ageList);
	}
	//����ѧ����ȡ����
	public Long getCountBySchool(String schoolstr) {
		return dao.getCountBySchool(schoolstr);
	}
	//��ȡ������Ů�ı���
	public Long getCountByGender(String genderstr) {
		
		return dao.getCountByGender(genderstr);
	}//��ȡ����ʡ�ݵ�����ʿ����������
	public Long getCountByCity(String citystr) {
		
		return dao.getCountByCity(citystr);
	}
	//���ﵥ����ʿ��������
	public Long getCountBySalary(String salarystr) {
		// TODO Auto-generated method stub
		return dao.getCountBySalary(salarystr);
	}
	//���ﵥ����ʿ��������
	public Long getCountByBoySalary(String salarystr) {
		// TODO Auto-generated method stub
		return dao.getCountByBoySalary(salarystr);
	}
	/**
	 * ��ù��ݸ��е�����
	 * @param string
	 * @return
	 */
	public Long getCountByGZ(String string) {
		// TODO Auto-generated method stub
		return dao.getCountByGZ(string);
	}
	/**
	 * ��þ���ʡ�ĳ���
	 * @param string
	 * @return
	 */
	public List<String> getGD(String string) {
		// TODO Auto-generated method stub
		return dao.getGD(string);
	}

}
