package spider.util.collaborative.matrix.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import spider.util.collaborative.CollaborativeFilter;
import spider.util.collaborative.InfoBean;
import spider.util.collaborative.matrix.MatrixSource;

public class MatrixFromDB extends MatrixSource{
	private List<String> userList = new ArrayList<String>();
	private List<String> itemList = new ArrayList<String>();
	private List<InfoBean> infoList = new ArrayList<InfoBean>();
	private static Logger log = Logger.getLogger(CollaborativeFilter.class); 
	protected Collection<String> getItems() {
		return this.itemList;
	}

	protected Collection<String> getUsers() {
		return this.userList;
	}

	protected void initCollection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			Statement stat = conn.createStatement();
			ResultSet rs1 = stat.executeQuery("select * from buyinfo");
			while(rs1.next()){
				InfoBean bean = new InfoBean();
				bean.setItemName(rs1.getString("itemname"));
				bean.setUserName(rs1.getString("username"));
				this.infoList.add(bean);
			}
			ResultSet rs2 = stat.executeQuery("select distinct username from buyinfo");
			while(rs2.next()){
				String username = rs2.getString("username");
				this.userList.add(username);
			}
			ResultSet rs3 = stat.executeQuery("select distinct itemname from buyinfo");
			while(rs3.next()){
				String itemname = rs3.getString("itemname");
				this.itemList.add(itemname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected List<InfoBean> initInfoList() {
		return this.infoList;
	}

}
