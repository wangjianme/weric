package com.vencent.jsoupnt.jsoup;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Demo2 {

	public static void findData(int start, int end) throws IOException {
		for (int j = start; j < end; j++) {
			String url = "http://album.zhenai.com/u/" + j + "?flag=s";
			try {
				// 获取连接，如果状态码为404，则进入异常处理块
				Document doc = Jsoup.connect(url).get();
				UserInfo userInfo = new UserInfo();
				// 名字
				Elements names = doc.getElementsByClass("name");
				String name = names.text();
				userInfo.setName(name);
				Elements idData = doc.getElementsByClass("brief-info");
				String ids = idData.text();
				// 临时id ID：28830025
				String _id = ids.split(" ")[0];
				System.out.println(_id);
				// 会员id
				String id = _id.substring(3, _id.length());
				userInfo.setUserId(id);
				// 性别
				Elements genderEles = doc.getElementsByClass("floor-table");
				Elements genders = genderEles.select("td");
				String gender = genders.get(0).text().substring(3);
				userInfo.setGender(gender);
				// String
				// _gender=doc.getElementsByClass("floor-table").select("td").get(0).text();
				// System.out.println(id);
				// 获取其他信息 年龄：31岁 身高：170CM 月收入：2001-3000元 婚况：未婚 学历：中专 工作地：广东佛山
				// 职业： 仓库管理员 星座：-- 籍贯：湖南益阳
				Elements infos = doc.getElementsByClass("brief-table");
				Elements eles = infos.select("td");
				List<String> userList = new ArrayList<String>();
				for (Element ele : eles) {
					int i = ele.select("span").text().length();
					String _info = ele.text();
					String info = _info.substring(i);

					userList.add(info);
				}
				userInfo.setAge(userList.get(0));
				userInfo.setHeight(userList.get(1));
				userInfo.setSalary(userList.get(2));
				userInfo.setMarital(userList.get(3));
				userInfo.setSchool(userList.get(4));
				userInfo.setWorkAdd(userList.get(5));
				userInfo.setWork(userList.get(6));
				userInfo.setAddress(userList.get(8));
				System.out.println(userInfo);
				addUserInfo(userInfo);
			} catch (Exception e) {
				// System.err.println("Url连接异常："+url+","+e.getMessage());
			}

		}

	}

	public static void addUserInfo(UserInfo userInfo) {
		String sql = "insert into users(id,user_id,name,gender,age," + "height,salary,marital,school,work,work_add,"
				+ "address) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			int rows = runner.update(sql, null, userInfo.getUserId(), userInfo.getName(), userInfo.getGender(),
					userInfo.getAge(), userInfo.getHeight(), userInfo.getSalary(), userInfo.getMarital(),
					userInfo.getSchool(), userInfo.getWork(), userInfo.getWorkAdd(), userInfo.getAddress());
			System.err.println("添加成功：" + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		/*
		 * addUserInfo(); System.out.println("成功了");
		 */
		int index = 10;
		for (int i = 1; i <= 180; i++) {
			threadStart(index++);
		}
	}

	public static void threadStart(int index) {
		final int num = index;
		// 定义ID的起始因子,以下为1千万
		final int idFator = 10000000;
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					// 如果index=1,则：id从100万-N00万
					findData(idFator * num, (num + 1) * idFator);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

}
