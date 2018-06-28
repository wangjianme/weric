package com.topsen.financial.util.support.dao.ibates;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.topsen.financial.config.SqlMap;
import com.topsen.financial.util.support.dao.page.PageDB;

public class IBatesPageDB extends PageDB{
	private SqlMapClient map;
	public IBatesPageDB(){
		map = SqlMap.getSqlMapInstance();
	}
	

	public List createResultList(String sql,int arg0,int arg1) {
		List list = null;
		try {
			list = this.map.queryForList(sql,arg0,arg1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int totalCount(String sql) {
		int totalCount = 0;
		try {
			totalCount = (Integer)this.map.queryForObject(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCount;
	}


	@Override
	public int createEndIndex(int curPage) {
		return this.getPerPage();
	}
	public int createBeginIndex(int curPage){
		return this.getPerPage()*(curPage-1);
	}


	@Override
	public List createResultList(String sql, Object value, int arg0, int arg1) {
		List list = null;
		try {
			list = this.map.queryForList(sql,value,arg0,arg1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public int totalCount(String countSql, Object value) {
		int totalCount = 0;
		try {
			totalCount = (Integer)this.map.queryForObject(countSql,value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCount;
	}


}
