package com.topsen.financial.util.support.dao.ibates;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.topsen.financial.config.SqlMap;
/**
 * 
 * @author Administrator
 * ����һ��IBatesBaseDAO��  ��������SqlMapClient map����  Ȼ���SqlMapClient map����
 * һ�����еķ�����  �ⲿͨ�����ø����еķ�������ȡ������SqlMapClient map����map
 */
public abstract class IBatesBaseDAO{
	//����һ��SqlMapClient map����
	private SqlMapClient map;
	//ͨ�����е�getMap()��������ȡ���ǵ�SqlMapClient map����
	public SqlMapClient getMap() {
		return map;
	}
   //ͨ�����췽��ΪSqlMapClient map��������
	public IBatesBaseDAO(){
		//ͨ��
		this.map = SqlMap.getSqlMapInstance();
	}
}
