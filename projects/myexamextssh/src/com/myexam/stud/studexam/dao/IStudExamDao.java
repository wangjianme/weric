package com.myexam.stud.studexam.dao;

import java.util.Map;

/**
 * 学生参加考试的dao
 * @author wangjianme
 * @version 1.0,2010-11-28
 */
public interface IStudExamDao {
	/**
	 * 查询此学生已经开通的所有考试
	 */
	Map<String,Object> queryOpenedExam(Map<String,String> param);
	/**
	 * 开始考试
	 */
	Map<String,Object> beginExaming(Map<String,Object> param);
	/**
	 * 根据infoId,quesId查询一个试题
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryOneExamQues(Map<String,Object> param);
	/**
	 * 保存学生考题信息
	 */
	Map<String,Object> saveAnsw(Map<String,Object> param);
	/**
	 * 计算总分
	 * @param param
	 * @return
	 */
	public Map<String,Object> calculateScore(Map<String,Object> param);
	/**
	 * 查询考试分数
	 */
	public Map<String,Object> examScore(Map<String,Object> param);
}
