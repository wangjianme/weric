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
				// ��ȡ���ӣ����״̬��Ϊ404��������쳣�����
				Document doc = Jsoup.connect(url).get();
				UserInfo userInfo = new UserInfo();
				// ����
				Elements names = doc.getElementsByClass("name");
				String name = names.text();
				userInfo.setName(name);
				Elements idData = doc.getElementsByClass("brief-info");
				String ids = idData.text();
				// ��ʱid ID��28830025
				String _id = ids.split(" ")[0];
				System.out.println(_id);
				// ��Աid
				String id = _id.substring(3, _id.length());
				userInfo.setUserId(id);
				// �Ա�
				Elements genderEles = doc.getElementsByClass("floor-table");
				Elements genders = genderEles.select("td");
				String gender = genders.get(0).text().substring(3);
				userInfo.setGender(gender);
				// String
				// _gender=doc.getElementsByClass("floor-table").select("td").get(0).text();
				// System.out.println(id);
				// ��ȡ������Ϣ ���䣺31�� ��ߣ�170CM �����룺2001-3000Ԫ �����δ�� ѧ������ר �����أ��㶫��ɽ
				// ְҵ�� �ֿ����Ա ������-- ���᣺��������
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
				// System.err.println("Url�����쳣��"+url+","+e.getMessage());
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
			System.err.println("��ӳɹ���" + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		/*
		 * addUserInfo(); System.out.println("�ɹ���");
		 */
		int index = 10;
		for (int i = 1; i <= 180; i++) {
			threadStart(index++);
		}
	}

	public static void threadStart(int index) {
		final int num = index;
		// ����ID����ʼ����,����Ϊ1ǧ��
		final int idFator = 10000000;
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					// ���index=1,��id��100��-N00��
					findData(idFator * num, (num + 1) * idFator);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

}
