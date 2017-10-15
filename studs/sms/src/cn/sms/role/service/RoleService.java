package cn.sms.role.service;

import java.util.Map;

import cn.sms.domain.Role;
import cn.sms.role.dao.RoleDaoJdbc;

public class RoleService {
	private RoleDaoJdbc dao = new RoleDaoJdbc();

	public Map<String, Object> query() {
		return dao.query();
	}
	public Role save(Role role){
		return dao.save(role);
	}
	public int delete(String id) {
		return dao.delete(id);
	}
	public void saveMenu(String[] menuIds,String roleId)
	{
		dao.saveMenu(menuIds, roleId);
	}
}
