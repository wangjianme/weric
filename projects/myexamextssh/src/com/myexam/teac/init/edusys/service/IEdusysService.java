package com.myexam.teac.init.edusys.service;

import java.util.List;
import java.util.Map;

/**
 * 学制管理
 * @author wangjianme
 * @version 1.0,2010-10-14
 */
public interface IEdusysService {
	Map<String,Object> query();
	Map<String,Object> save(List<Object> list);
	Map<String, Object> del(Object o);
}
