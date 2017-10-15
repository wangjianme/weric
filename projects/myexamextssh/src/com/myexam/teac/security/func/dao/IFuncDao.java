package com.myexam.teac.security.func.dao;

import java.util.List;
import java.util.Map;

public interface IFuncDao {
	List<Map<String,Object>> query(String id,String roleId);
	Map<String,Object> save(Map<String,Object> param);
}
