package com.myexam.teac.init.cour.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Cour;
import com.myexam.domain.Major;

/**
 * 课程管理
 * @author wangjianme
 * @version 1.0,2010-10-7
 */
public interface ICourService {
	Map<String,Object> query(Map<String,Object> param);
	Map<String,Object> save(Map<String,Object> param);
	List<Major> queryMajor();
	Map<String,Object> del(Cour cour);
}
