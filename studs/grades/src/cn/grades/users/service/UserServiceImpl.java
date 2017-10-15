package cn.grades.users.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Teacher;
import cn.grades.domain.User;
import cn.grades.users.dao.IUserDao;
import cn.grades.users.dao.UserDaoJdbc;


	public class UserServiceImpl implements IUserService{
		IUserDao dao= new UserDaoJdbc();
		/* (non-Javadoc)
		 * @see cn.grades.users.service.IUserService#save(cn.grades.domain.User)
		 */
		@Override
		public User save(User user) {
		
			return dao.save(user);
		}
		/* (non-Javadoc)
		 * @see cn.grades.users.service.IUserService#delete(java.lang.String)
		 */
		@Override
		public int delete(String id) {
			return dao.delete(id);
		}
		/* (non-Javadoc)
		 * @see cn.grades.users.service.IUserService#query(int, int)
		 */
		@Override
		public Map<String,Object> query(int page, int rows) {
			return dao.query(page, rows);
		}
		public Map<String,Object> queryUser(int page, int rows) {
			return dao.queryUser(page,rows);
		}
		public Map<String,Object> queryTeacher(int page,int rows){
			return dao.queryTeacher(page, rows);
		}
		@Override
		public User saveTeacher(User u) {
			return dao.saveTeacher(u);
		}
		@Override
		public void statusOn(String id){
			 dao.statusOn(id);
		}
		public void statusOff(String id){
			 dao.statusOff(id);
		}
		public String initialize(String id){
			 return  dao.initialize(id);
		}
		@Override
		public void assignRole(String uid,String[] ids) {
			 dao.assignRole(uid,ids);
		}
//		public List<ComboBox> userList(){
//			return dao.userList();
//		}
	}

