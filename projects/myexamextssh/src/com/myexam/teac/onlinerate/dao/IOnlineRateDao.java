package com.myexam.teac.onlinerate.dao;

import java.util.Map;

/**
 * 在线阅卷功能
 * @author wangjianme
 * @version 1.0,2010-11-18
 */
public interface IOnlineRateDao {
	/**
	 * 查询出所有已经考试完成的试卷。
	 * 条件如下：
	 * --在线阅卷功能
	  --查询已经做完提交了的试卷即oe_state='2',info_state='1'
      --和阅卷人为空的记录，阅卷人为空表明还没有阅卷，
      --同时要查询阅卷人为自己的名称的才可以阅卷，即：oe_rate='王健'
      --按试卷名称进行分类
	 * 
	 */
	public Map<String,Object> queryStuds(Map<String,Object> param);
	/**
	 * 根据试卷id查询答案
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryAnsws(Map<String,Object> param);
	/**
	 * 修改答案
	 * @param param
	 * @return
	 */
	public Map<String,Object> updateScore(Map<String,Object> param);
}
