package com.myexam.teac.security.role.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import com.myexam.pub.MyUUID;

public class RoleDaoJdbc implements IRoleDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public List<Map<String, Object>> query() {
		String sql = "select role_id,role_name,role_desc,role_candel from role";
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		List<Map<String,Object>> roles = jt.queryForList(sql);
		return roles;
	}
	/**
	 * 保存或修改
	 */
	public Map<String, Object> save(Map<String, Object> params) {
		Map<String,Object> result = new HashMap<String, Object>();
		String roleId = (String)params.get("roleId");
		System.err.println("roleId:"+roleId);
		if(roleId==null || roleId.trim().equals("")){
			result.put("save", true);						//标记这是保存
			String sql  = "insert into role(role_id,role_name,role_desc,role_candel) values(:roleId,:roleName,:roleDesc,:roleCanDel)";
			SqlUpdate update = new SqlUpdate(getDataSource(),sql);
			update.declareParameter(new SqlParameter(Types.VARCHAR));
			update.declareParameter(new SqlParameter(Types.VARCHAR));
			update.declareParameter(new SqlParameter(Types.VARCHAR));
			update.declareParameter(new SqlParameter(Types.CHAR));
			update.compile();
			params.put("roleId", MyUUID.getUUID());		//获取新的uuid
			params.put("roleCanDel", "1");
			update.updateByNamedParam(params);			//直接使用map作为参数
		}else{
			result.put("save",false);							//标记这不是保存是这更新
			String sql = "update role set role_name=:roleName,role_desc=:roleDesc where role_id=:roleId";
			SqlUpdate update = new SqlUpdate(getDataSource(),sql);
			update.declareParameter(new SqlParameter(Types.VARCHAR));
			update.declareParameter(new SqlParameter(Types.VARCHAR));
			update.declareParameter(new SqlParameter(Types.VARCHAR));
			update.compile();
			update.updateByNamedParam(params);
		}
		result.put("success",true);
		result.putAll(params);
		return result;
	}
	public boolean del(String roleId) {
		String sql = "delete from func where func_role=?";				//删除与之相关的菜单关联
		SqlUpdate update = new SqlUpdate(getDataSource(), sql);
		update.declareParameter(new SqlParameter(Types.VARCHAR));
		update.compile();
		int a = update.update(roleId);
		
		sql = "delete from teacrole where tc_role=?";					//删除与关联的用户
		update = new SqlUpdate(getDataSource(),sql);
		update.declareParameter(new SqlParameter(Types.VARCHAR));
		update.compile();
		a = update.update(roleId);
		
		sql ="delete from role where role_id=?";						//删除角色本身
		update = new SqlUpdate(getDataSource(),sql);
		update.declareParameter(new SqlParameter(Types.VARCHAR));
		update.compile();
		a = update.update(roleId);
		if(a>0){
			return true;
		}
		return false;
	}
}
