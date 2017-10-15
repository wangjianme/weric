package cn.sms.menu.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.sms.core.BaseDao;
import cn.sms.domain.Menu;

public class MenuDao extends BaseDao {
	public List<Menu> query(String userId, String parentId) {
		String sql = "select m.menu_id as id,m.menu_text as text,"
				+ "m.menu_state as state,m.menu_res as res,m.menu_parent as parent" + " from "
				+ "menus m inner join role_menu rm on m.menu_id=rm.rm_menuid"
				+ " inner join roles r on r.role_id = rm.rm_roleid"
				+ " inner join user_role ur on ur.ur_roleid=r.role_id" + " where ur.ur_userid=?";
		if (parentId != null) {
			sql += " and m.menu_parent='" + parentId + "'";
		} else {
			sql += " and m.menu_parent is null";
		}
		List<Menu> list = run.query(sql, new BeanListHandler<Menu>(Menu.class), userId);
		return list;
	}

	/**
	 * 查询菜单
	 */
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
}
