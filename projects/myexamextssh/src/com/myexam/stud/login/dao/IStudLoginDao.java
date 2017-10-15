package com.myexam.stud.login.dao;

import java.util.Map;

/**
 * 学生登录
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
public interface IStudLoginDao {
	Map<String,Object> login(Map<String,Object> param);
}