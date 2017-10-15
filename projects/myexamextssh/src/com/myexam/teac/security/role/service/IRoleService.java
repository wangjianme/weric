package com.myexam.teac.security.role.service;

import java.util.List;
import java.util.Map;

public interface IRoleService {
	List<Map<String,Object>> query();
	Map<String,Object> save(Map<String,Object> params);
	boolean del(String roleId);
}
