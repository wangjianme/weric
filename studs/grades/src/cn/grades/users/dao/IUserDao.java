package cn.grades.users.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Teacher;
import cn.grades.domain.User;

public interface IUserDao {

	Map<String, Object> query(int page, int rows);
	
	Map<String, Object> queryUser(int page, int rows);
	
	Map<String,Object> queryTeacher(int page,int rows);

	User save(User user);

	int delete(String id);
	//保存老师，用老师保存用户
	User saveTeacher(User u);
	//初始化密码
	String initialize(String id);
	//启用账户
	void statusOn(String id);
	//停用账户
	void statusOff(String id);
	//分配角色
	void assignRole(String uuid,String[] ids);
	
	
	//管理用户的查询用户
//	List<ComboBox> userList();
}