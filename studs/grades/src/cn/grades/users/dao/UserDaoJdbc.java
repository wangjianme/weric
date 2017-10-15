package cn.grades.users.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.core.BaseDao;
import cn.grades.domain.Role;
import cn.grades.domain.Teacher;
import cn.grades.domain.User;

import cn.grades.core.Md5Utils;

public class UserDaoJdbc extends BaseDao implements IUserDao  {

	
	
	/* (non-Javadoc)
	 * @see cn.grades.users.dao.IUserDao#query(int, int)
	 */
	
	
	@Override
	public Map<String,Object> query(int page, int rows) {
		 String sql="select count(1) from users";
		 int totalRows =run.query(sql, new
		 ScalarHandler<Number>()).intValue();
		 int start=(page-1)*rows;
		sql = "select user_id as id,user_name as name from users";
		List<User> users = run.query(sql, new BeanListHandler<User>(User.class));
		for (User user : users) {
			sql = "select role_name as name,role_id as id from roles inner join user_role "
					+ "on role_id=ur_roleid where ur_userid=?";
			List<Role> roles = run.query(sql, new BeanListHandler<Role>(Role.class), user.getId());
			user.setRoles(roles);
		}
		Map<String,Object>map=new HashMap<>();
		 map.put("total", totalRows);
		 map.put("rows", users);
		return map;

	}
	
	public Map<String, Object> queryUser(int page, int rows) {
		 String sql ="select count(1) from users";
    	 int totalRows =run.query(sql, new ScalarHandler<Number>()).intValue();
    	 int start =(page -1) * rows;
    	 sql ="select user_id as id,user_name as name,user_state as state from users limit " +start +"," +rows;
    	 List<User> list = run.query(sql, new BeanListHandler<User>(User.class));
    	 Map<String, Object> map =new HashMap<>();
    	 map.put("total", totalRows);
    	 map.put("rows", list);
		return map;
    	 
	}
	
	public Map<String,Object> queryTeacher(int page,int rows){
		 String sql ="select count(1) from teachers";
    	 int totalRows =run.query(sql, new ScalarHandler<Number>()).intValue();
    	 int start =(page -1) * rows;
    	 sql ="select teacher_id as id,teacher_name as nm from teachers limit " +start +"," +rows;
    	 List<Teacher> list = run.query(sql, new BeanListHandler<Teacher>(Teacher.class));
    	 Map<String, Object> map =new HashMap<>();
    	 map.put("total", totalRows);
    	 map.put("rows", list);
    	 return map;
	}
	@Override
	public User save(User user) {
		user.setId(UUID.randomUUID().toString().replace("-", ""));
		user.setSalt(UUID.randomUUID().toString().replace("-", ""));
		user.setPwd(Md5Utils.md5(user.getPwd(), user.getSalt()));
		user.setState("0");
		System.out.println(user.getId() + "" + user.getName() + "" + user.getPwd() + "" + user.getSalt());
		String sql = "insert into users(user_id,user_name,user_pwd,user_salt,user_state) values(?,?,?,?,?)";
		run.update(sql, user.getId(), user.getName(), user.getPwd(), user.getSalt(), user.getState());
		return user;
	}
	//分配角色
	public void assignRole(String uuid,String[] ids){
		String sql="delete from user_role where ur_userid=?";
		run.update(sql,uuid);
		 sql="insert into user_role(ur_userid,ur_roleid) values(?,?)";
		 for(String id:ids){
			 run.update(sql,uuid,id);
		 }
	}
	@Override
	public int delete(String id) {
		String sql="select * from user_role where ur_userid=?";
		List<User> users = run.query(sql, new BeanListHandler<User>(User.class),id);
		int rows=0;
		if(!users.isEmpty()){
			sql="delete from user_role where ur_userid=?";
			 run.update(sql,id);
		}
		 sql = "delete from users where user_id=?";
		 rows = run.update(sql, id);
		return rows;
	}
	@Override
	public User saveTeacher(User u) {
		u.setSalt(UUID.randomUUID().toString().replace("-", ""));
		u.setPwd(Md5Utils.md5(u.getPwd(), u.getSalt()));
		u.setState("0");
		String sql = "insert into users(user_id,user_name,user_pwd,user_salt,user_state) values(?,?,?,?,?)";
		run.update(sql, u.getId(),u.getName(),u.getPwd(),u.getSalt(),u.getState());
		return u;
	}
	
	//查询角色状态
	//如果是0则为启用状态
	//初始化密码
	public String initialize(String id){
		//生成6位随机数
		String pwd="";
		Random random = new Random();
		for(int i=0;i<6;i++){
		pwd+=random.nextInt(10);
		}
		String salt=UUID.randomUUID().toString().replace("-", "");
		String npwd=(Md5Utils.md5(pwd, salt));
		System.out.println(pwd);
		System.out.println(npwd);
		String sql="update users set user_pwd=?,user_salt=? where user_id=?";
		run.update(sql,npwd,salt,id);
		return pwd;
	}
	
	//启用账户
	@Override
	public void statusOn(String id){
		String state="0";
		String sql="update users set user_state=? where user_id=?";
		run.update(sql,state,id);
	}	//停用账户
	@Override
	public void statusOff(String id){
		String state="1";
		String sql="update users set user_state=? where user_id=?";
		run.update(sql,state,id);
	}
//	public List<ComboBox> userList() {
//		String sql = "select user_id as id ,user_name as name from users";
//		List<ComboBox> list = run.query(sql, new BeanListHandler<ComboBox>(ComboBox.class));
//		return list;
//	}



}