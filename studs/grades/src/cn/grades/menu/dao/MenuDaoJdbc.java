package cn.grades.menu.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.grades.core.BaseDao;
import cn.grades.domain.Menu;

public class MenuDaoJdbc extends BaseDao implements IMenuDao  {
	/* (non-Javadoc)
	 * @see cn.grades.menu.dao.IMenuDao#query(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Menu> query(String uid, String parent) {
		String sql = "select DISTINCT m.menu_id as id,m.menu_text as text,m.menu_url as url,"
				+ "m.menu_parent as parent,m.menu_state as state"
				+ " from menus m INNER JOIN role_menu rm ON m.menu_id=rm.rm_menuid"
				+ " INNER JOIN roles r ON r.role_id=rm.rm_roleid" + " INNER JOIN user_role ur ON r.role_id=ur.ur_roleid"
				+ " WHERE ur.ur_userid=?";
		System.err.println(sql);
		List<Object> param = new ArrayList<>();
		param.add(uid);
		if (parent == null || parent.trim().equals("")) {
			sql += " and m.menu_parent is null";
		} else {
			sql += " and m.menu_parent=?";
			param.add(parent);
		}

		List<Menu> list = run.query(sql, new BeanListHandler<Menu>(Menu.class), param.toArray());
		return list;
	}

	/* (non-Javadoc)
	 * @see cn.grades.menu.dao.IMenuDao#query(java.lang.String)
	 */

	@Override
	public List<Menu> query(String parentId) {
		String sql = "select menu_id as id,menu_text as text,menu_state as state from menus";
		if (parentId != null) {
			sql += " where menu_parent='" + parentId + "'";
		} else {
			sql += " where menu_parent is null";
		}
		List<Menu> list = run.query(sql, new BeanListHandler<Menu>(Menu.class));
		return list;
	}
	

	/* (non-Javadoc)
	 * @see cn.grades.menu.dao.IMenuDao#menuUser()
	 */
	@Override
	public Map<String, List<String>> menuUser() {
		String sql = "select menu_id,menu_url  from  menus where menu_state='open'";
		List<Map<String, Object>> menus = run.query(sql, new MapListHandler());
		// 查询
		sql = " SELECT  DISTINCT ur_userid FROM user_role ur INNER JOIN roles r ON r.role_id=ur.ur_roleid "
				+ " INNER JOIN role_menu rm ON rm.rm_roleid=ur.ur_roleid" + " WHERE rm.rm_menuid=?";
		Map<String, List<String>> map = new HashMap<>();
		for (Map<String, Object> m : menus) {
			String id = (String) m.get("menu_id");
			String url = (String) m.get("menu_url");
			List<String> list = 
					run.query(sql, new ColumnListHandler<String>(),id);
			map.put(url, list);
		}
		return map;
	}
}
