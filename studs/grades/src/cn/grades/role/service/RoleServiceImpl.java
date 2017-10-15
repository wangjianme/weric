package cn.grades.role.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Role;
import cn.grades.role.dao.IRoleDao;
import cn.grades.role.dao.RoleDaoJdbc;

public class RoleServiceImpl implements IRoleService {
   private IRoleDao dao = new RoleDaoJdbc();
   
@Override
public Map<String, Object> query(int page,int rows){
	return dao.query(page, rows);
	   
   }
public Role save(Role role){
	return dao.save(role);
   }
public int delete(String id) {
	return dao.delete(id);
}

public  int update(Role role){
	return dao.update(role);
   }

public void saveMenu(String[] menuIds,String roleId){
	dao.saveMenu(menuIds, roleId);
}

}
