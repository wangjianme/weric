package com.myexam.teac.manager.cls.service;
import java.util.Map;

import com.myexam.domain.Cls;
/**
 * 班级管理
 * @author wangjianme
 * @version 1.0,2010-10-9
 */
public interface IClsService {
	Map<String, Object> query(Map<String, Object> params);
	Map<String,Object> queryTeac(String name,int start,int limit);
	Map<String, Object> queryMajor();
	Map<String, Object> save(Cls cls);
	Map<String, Object> del(Cls cls);
}
