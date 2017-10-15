package cn.grades.role.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.core.BaseDao;
import cn.grades.domain.Role;

public class RoleDaoJdbc extends BaseDao implements IRoleDao{
     
	public Map<String, Object> query(int page, int rows){
    	 //查询用多少行
    	 String sql ="select count(1) from roles";
    	 int totalRows =run.query(sql, new ScalarHandler<Number>()).intValue();
    	 int start =(page -1) * rows;
    	 sql ="select role_id as id,role_name as name from roles limit " +start +"," +rows;
    	 List<Role> list = run.query(sql, new BeanListHandler<Role>(Role.class));
    	 Map<String, Object> map =new HashMap<>();
    	 map.put("total", totalRows);
    	 map.put("rows", list);
		return map;
    	 
     }
    
  
    @Override
	public Role save(Role role){
    	role.setId(UUID.randomUUID().toString().replace("-", ""));
    	String sql ="insert into roles(role_id,role_name) value(?,?)";
    	run.update(sql, role.getId(),role.getName());
		return role;
		}
    
   
    @Override
	public int delete(String id) {
    	String sql ="select count(1) from role_menu where rm_roleid=?";
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
     * 修改角色
     * */

    	public int update(Role role) {
    		String sql = "update roles set role_name=? where role_id=?";
    		int rows = run.update(sql, role.getName(),role.getId());
    		System.err.println("id11 is ：　"+role.getId()+"name is : "+role.getName());
    		return rows;

    	}
    	/** 
    	 * 分配菜单
    	 * */
    	public void saveMenu(String[] menuIds,String roleId){
          //先删除，再写入    		
    		String sql ="delete from role_menu where rm_roleid=?";
    		int a =run.update(sql, roleId);
            sql ="insert into role_menu(rm_roleid,rm_menuid) values(?,?)";
            for(String menuId:menuIds){
            	run.update(sql, roleId,menuId);
            }
    	}
    
    	
    	
    	
}
