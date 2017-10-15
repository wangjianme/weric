package cn.sms.role.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.sms.core.BaseDao;
import cn.sms.domain.Role;

public class RoleDaoJdbc extends BaseDao {
	public Map<String, Object> query() {
		String sql = "select role_id as id,role_name as name from roles";
		List<Role> roles = run.query(sql, new BeanListHandler<Role>(Role.class));
		Map<String, Object> map = new HashMap<>();
		map.put("rows", roles);
		return map;
	}

	/**
	 * 保存角色
	 */
	public Role save(Role role) {
		role.setId(UUID.randomUUID().toString().replace("-", ""));
		String sql = "insert into roles(role_id,role_name) values(?,?)";
		run.update(sql, role.getId(), role.getName());
		return role;
	}

	/**
	 * 删除
	 */
	public int delete(String id) {
		String sql = "select count(1) from role_menu where rm_roleid=?";// 先检查是否有关联的菜单
		int a = Integer.parseInt("" + run.query(sql, new ScalarHandler<Object>(), id));
		if (a > 0) {// 有关联
			return -1;// 有菜单关联
		}
		sql = "select count(1) from user_role where ur_roleid=?";
		a = Integer.parseInt("" + run.query(sql, new ScalarHandler<Object>(), id));
		if (a > 0) {
			return -2;// 有用户关联
		}
		sql = "delete from roles where role_id=?";
		a = run.update(sql, id);
		return a;

	}
	
	/**
	 * 保存角色与菜单
	 */
	public void saveMenu(String[] menuIds,String roleId){
		//先删除所角色之前的菜单
		String sql = "delete from role_menu where rm_roleid=?";
		int a = run.update(sql,roleId);
		//再写入所有
		sql = "insert into role_menu(rm_roleid,rm_menuid) values(?,?)";
		for(String menuId:menuIds){
			run.update(sql,roleId,menuId);
		}
	}
}
