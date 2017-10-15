package com.myexam.teac.security.func.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.SqlUpdate;
/**
 * 角色对应的菜单功能
 * @author wangjianme
 * @version 1.0,2010-10-3
 */
public class FuncDaoJdbc implements IFuncDao {
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * 查询,注意是如何设置checked属性的
	 */
	public List<Map<String, Object>> query(String id,String roleId) {
		String sql = "select menu_id as id,menu_text as text,menu_leaf as leaf from menu where menu_parent=?";
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		List<Map<String,Object>> menus = jt.queryForList(sql, id);
		SqlFunction<Integer> func = new SqlFunction<Integer>();
		func.setDataSource(getDataSource());
		sql = "select count(*) from func where func_menu=? and func_role=?";
		func.setSql(sql);
		func.declareParameter(new SqlParameter(Types.VARCHAR));
		func.declareParameter(new SqlParameter(Types.VARCHAR));
		func.compile();
		for(Map<String,Object> mm : menus){
			int count = func.run(mm.get("id"),roleId);
			if(count>0){
				mm.put("checked", true);
			}else{
				mm.put("checked", false);
			}
			if(mm.get("leaf").equals("1")){
				mm.put("leaf", true);
			}else{
				mm.put("leaf", false);
			}
		}
		System.err.println(">>::"+menus);
		return menus;
	}
	/**
	 * 保存功能
	 */
	public Map<String, Object> save(Map<String, Object> param) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("success", false);									//先说明有可能不成功.
		String roleId = (String)param.get("roleId");
		String menuIds = (String)param.get("menuIds");
		System.err.println("roleId:"+roleId+",menuIds:"+menuIds);
		
		String sql ="delete from func where func_role=?";
		SqlUpdate su = new SqlUpdate(getDataSource(),sql);					//删除以前与此角色相关的内容
		su.declareParameter(new SqlParameter(Types.VARCHAR));
		su.compile();
		su.update(roleId);
		if(menuIds!=null && !menuIds.trim().equals("")){					//如果用户选择了某些节点
			String[] menuId = menuIds.split(",");
			sql = "insert into func(func_role,func_menu) values(?,?)";			//然后再重新写入新的数据
			BatchSqlUpdate bsu = new BatchSqlUpdate(getDataSource(),sql);
			bsu.declareParameter(new SqlParameter(Types.VARCHAR));
			bsu.declareParameter(new SqlParameter(Types.VARCHAR));
			bsu.compile();
			for(int i=0;i<menuId.length;i++){
				bsu.update(roleId,menuId[i].trim());
			}
			bsu.flush();	
		}
		result.put("success", true);
		return result;
	}
}
