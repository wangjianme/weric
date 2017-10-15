package cn.grades.users.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Teacher;
import cn.grades.domain.User;

public interface IUserService {

	User save(User user);

	int delete(String id);

	Map<String, Object> query(int page, int rows);
	
	Map<String, Object> queryUser(int page, int rows);

	Map<String,Object> queryTeacher(int page,int rows);
	
	User saveTeacher(User u);
	
	void statusOn(String id);
	
	public void statusOff(String id);
	
	String initialize(String id);

	void assignRole(String uuid,String[] ids);
	
//	List<ComboBox> userList();

}