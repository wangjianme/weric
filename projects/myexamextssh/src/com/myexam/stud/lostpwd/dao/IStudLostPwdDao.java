package com.myexam.stud.lostpwd.dao;

import java.util.Map;

/**
 * 学生忘记密码以后的找回
 * @author wangjianme
 * @version 1.0,2010-12-8
 */
public interface IStudLostPwdDao {
	Map<String,Object> queryQues(String studSid);
	
	void update(Map<String,Object> param);
}