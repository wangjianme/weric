package com.myexam.teac.lostexam.dao;

import java.util.List;
import java.util.Map;

/**
 * 补考设置
 * @author wangjianme
 * @version 1.0,2010-11-22
 */
public interface ILostExamDao {
	/**
	 * 显示所有已经设置的补考，但还没有考试完成的学生
	 */
	public Map<String,Object> queryResitStuds();
	/**
	 * 查询应该参加的补考的学生
	 */
	public Map<String,Object> queryLostStud();
	/**
	 * 保存补考学生信息
	 */
	public Map<String,Object> saveLostStuds(List<Map<String,Object>> lostStuds);
	/**
	 * 删除已经安排的补考，首先要检查是否已经参加考试，如果没有才可以删除
	 */
	public Map<String,Object> deleteLostStud(Map<String,Object> param);
}
