package com.myexam.teac.manager.stud.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Stud;

/**
 * 学生管理
 * @author wangjianme
 * @version 1.0,2010-10-13
 */
public interface IStudManagerService {
	Map<String,Object> query(Map<String,Object> param);
	Map<String,Object> del(Map<String,Object> param);
	Map<String,Object> queryCls(Map<String,Object> param);
	public Map<String, Object> update(Map<String, Object> param);
}
