package cn.stud.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.stud.domain.Stud;
import cn.stud.domain.User;

/**
 * 这个类，提供数据的存储服务用于维护用户和所有学生
 * 
 * @author wangjian
 */
public class DataBase {
	private String destFile;
	// --2:声明 保存学生信息的文件
	private String studFile;
	private String userHome = System.getProperty("user.home");
	private Map<String, User> users;

	// --1: 声明用于保存学生的信息 ，而TableView的数据从这里面获取的
	private List<Stud> list;

	// --6:保存一个学生
	public void saveStud(Stud stud) {
		list.add(stud);
		try {
			saveStudToFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// --7:保存学生持集合 - 注意你的Stud必须要实现接口：Serizalible
	private void saveStudToFile() throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(studFile));
		out.writeObject(list);
		out.close();
	}

	// --5:读取所有的学生信息，返回给界面
	public List<Stud> queryStuds() {
		return list;
	}

	// --8:删除一个学生从集合中 - 现在可以去做界面了
	public void removeStud(Stud stud) {
		list.remove(stud);
		// 保存
		try {
			saveStudToFile();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private DataBase() {
		destFile = userHome + "/.users";
		// --3:给studFile成员变量设置值
		studFile = userHome + "/.studs";
		File file = new File(destFile);
		try {
			if (file.exists()) {// 存在这个文件
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(destFile));
				users = (Map<String, User>) in.readObject();
				in.close();
			} else {// 如果文件不存在，则创建一个新的
				users = new HashMap<>();
			}

			// --4:读取studFile文件，如果存在，就从文件中读取数据，否则不创建一个新的list
			File file2 = new File(studFile);
			if (file2.exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file2));
				list = (List<Stud>) in.readObject();
				in.close();
			} else {
				list = new ArrayList<>();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static DataBase DATABASE;
	static {
		DATABASE = new DataBase();
	}

	public static DataBase getInstace() {
		return DATABASE;
	}

	// 注册用户
	public void reg(User user) {
		users.put(user.getName(), user);
		try {
			saveToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validateName(String name) {
		return users.containsKey(name);
	}

	// 开发一个登录的方法
	public boolean login(User user) {
		// 根据user.name获取User整个对象
		User _user = users.get(user.getName());
		if (_user == null) {
			return false;
		} else {
			// 再比较密码
			if (user.getPwd().equals(_user.getPwd())) {
				return true;
			} else {
				return false;
			}
		}
	}

	// 保存这个集合，这个集合中是有所有的用户的
	private void saveToFile() throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(destFile));
		out.writeObject(users);
		out.close();
	}

	/**
	 * 修改功能
	 * 
	 * @param index
	 * @param stud
	 */
	public void update(int index, Stud stud) {
		list.set(index, stud);// 修改下标位置的对象
		try {
			saveStudToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提供一个查询的方法 张 男
	 * 
	 */
	public List<Stud> queryWithStud(Stud stud) {
		// 声明一个List
		List<Stud> qList = new ArrayList<>();
		qList.addAll(list);
		Iterator<Stud> it = qList.iterator();
		if (stud.getName() != null && !stud.getName().equals("")) {
			while (it.hasNext()) {
				Stud s = it.next();
				if (!s.getName().contains(stud.getName())) {
					it.remove();
				}
			}
		}
		if (stud.getAge1() != null) {// 最小年龄
			it = qList.iterator();
			while (it.hasNext()) {
				Stud s = it.next();
				if (s.getAge() < stud.getAge1()) {
					it.remove();
				}
			}
		}
		if (stud.getAge2() != null) {// 最大年龄
			it = qList.iterator();
			while (it.hasNext()) {
				Stud s = it.next();
				if (s.getAge() > stud.getAge2()) {
					it.remove();
				}
			}
		}
		if (stud.getSex() != null && !stud.getSex().equals("不限")) {// 男
			it = qList.iterator();
			while (it.hasNext()) {
				Stud s = it.next();
				if (!s.getSex().equals(stud.getSex())) {
					it.remove();
				}
			}
		}
		return qList;
	}
}
