package com.myexam.teac.lostexam.service;

import java.util.List;
import java.util.Map;

import com.myexam.teac.lostexam.dao.ILostExamDao;

public class LostExamServiceImpl implements ILostExamService{
	private ILostExamDao dao;

	public ILostExamDao getDao() {
		return dao;
	}

	public void setDao(ILostExamDao dao) {
		this.dao = dao;
	}
	/**
	 * 显示所有已经设置的补考，但还没有考试完成的学生
	 */
	public Map<String,Object> queryResitStuds(){
		return getDao().queryResitStuds();
	}
	/**
	 * 查询应该参加的补考的学生
	 */
	public Map<String,Object> queryLostStud(){
		return getDao().queryLostStud();
	}
	/**
	 * 保存补考学生信息
	 */
	public Map<String,Object> saveLostStuds(List<Map<String,Object>> lostStuds){
		return getDao().saveLostStuds(lostStuds);
	}
	/**
	 * 删除已经安排的补考，首先要检查是否已经参加考试，如果没有才可以删除
	 */
	public Map<String,Object> deleteLostStud(Map<String,Object> param){
		return getDao().deleteLostStud(param);
	}
}
