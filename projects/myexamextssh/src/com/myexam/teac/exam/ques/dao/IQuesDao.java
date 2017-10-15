package com.myexam.teac.exam.ques.dao;

import java.util.Map;

/**
 * 题库管理功能
 * @author wangjianme
 * @version 1.0,2010-10-25
 */
public interface IQuesDao {
	/**
	 * 查询试题
	 * @param param
	 * @return
	 */
	public Map<String,Object> query(Map<String,Object> param);
	/**
	 * 查询课程
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryCour(Map<String,Object> param);
	/**
	 * 保存试题
	 */
	public Map<String,Object> save(Map<String,Object> param);
	/**
	 * 查询确定的一条记录
	 */
	public Map<String,Object> queryOneQues(String quesId);
	/**
	 * 删除试题
	 */
	public Map<String,Object> del(String quesId);
}
