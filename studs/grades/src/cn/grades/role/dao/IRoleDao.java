package cn.grades.role.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Role;

public interface IRoleDao {
	Map<String, Object> query(int page, int rows);
	Role save(Role role);
	int delete(String id);
	int update(Role role);
	void saveMenu(String[] menuIds,String roleId);

}