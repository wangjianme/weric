package com.myexam.stud.studexam.service;

import java.util.Map;

import com.myexam.stud.studexam.dao.IStudExamDao;

/**
 * @author wangjianme
 * @version 1.0,2010-11-28
 */
public class StudExamServiceImpl implements IStudExamService{
	private IStudExamDao dao;
	public IStudExamDao getDao() {
		return dao;
	}
	public void setDao(IStudExamDao dao) {
		this.dao = dao;
	}
	/**
	 * 查询此学生已经开通的所有考试
	 */
	public Map<String,Object> queryOpenedExam(Map<String,String> param){
		return getDao().queryOpenedExam(param);
	}
	public Map<String,Object> beginExaming(Map<String,Object> param){
		return getDao().beginExaming(param);
	}
	public Map<String, Object> queryOneExamQues(Map<String, Object> param) {
		return getDao().queryOneExamQues(param);
	}
	/**
	 * 保存学生考题信息
	 */
	public Map<String,Object> saveAnsw(Map<String,Object> param){
		return getDao().saveAnsw(param);
	}
	/**
	 * 计算总分
	 * @param param
	 * @return
	 */
	public Map<String,Object> calculateScore(Map<String,Object> param){
		return getDao().calculateScore(param);
	}
	/**
	 * 查询考试分数
	 */
	public Map<String,Object> examScore(Map<String,Object> param){
		return getDao().examScore(param);
	}
}
