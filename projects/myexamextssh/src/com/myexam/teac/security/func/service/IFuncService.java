package com.myexam.teac.security.func.service;

import java.util.List;
import java.util.Map;

public interface IFuncService {
	List<Map<String,Object>> query(String parentId,String roleId);
	Map<String,Object> save(Map<String,Object> param);
}
