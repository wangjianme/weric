package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.topsen.financial.dao.inter.RoleDAO;
import com.topsen.financial.po.Role;
import com.topsen.financial.util.support.dao.bean.PageModel;

import com.topsen.financial.util.support.dao.page.PageModelFactory;

public class RoleDAOImpl extends RoleDAO{

	public int delete(int roleId) {
		int i = 0;
		try {
			this.getMap().delete("role_space.delete",roleId);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int insert(Role t) {
		int i = 0;
		try {
			this.getMap().insert("role_space.insert",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public Role load(int roleId) {
		Role role = null;
		try {
			role = (Role)this.getMap().queryForObject("role_space.one",roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	public int update(Role t) {
		int i = 0;
		try {
			this.getMap().update("role_space.update",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public PageModel queryByPage(int curPage) {
		return PageModelFactory.getFactory().createPageModel("role_space.queryAll", "role_space.count", curPage);
	}
    /**
     * ���ݷ��ʲ��еĸ����û���ȥ���ҽ�ɫ�ķ���
     */
	@Override
	public List<Role> queryEmpRole(String empno) {
		//����һ������ ��������ΪRole���͵�  �������ܲ�ѯ�Ľ�ɫ����ص���Ϣ
		List<Role> list = null;
		try {
			/**
			 * ����map������� �ײ��ѯ�ķ���  �˷����Ǹ����û���ȥ���ҽ�ɫ  ��Ҫ�����ǵײ�Ĳ�ѯ�ķ���
			 * ��Ҫ��һ���������û���   �û�����Ϊ�������ݵ���ѯ�ķ�����   ��Ϊsql����ѯ������
			 * ��Ϊ�����û�ȥ���ҽ�ɫ   ��ѯ���ô���  �������ǵķ�������֪��  sql���д��role_sqlmap.xml
			 * �������ļ���  Ȼ����dbconfig.xml�ļ��������sql������ڵ�·��  �����ڼ���dbconfig.xml�����ļ�ʱ  
			 * �Ż�Ѵ�sql�������ؽ���  Ȼ���Ա�ǩ��ʶ����ʽ���浽���map��   �õ�ʱ��ֱ�Ӹ��� ָ�����sql���ı�ǩ����
			 * 
			 * 
			 * �ش�������
			 *    queryForList("sql���ı�ʶ","where����ָ������")
			 *    sql���ı�ʶ��role_space.queryUserRole
			 *    ��һ������Ϊ:
			 *          ��Ҫ��ѯ��sql���
			 *    �ڶ�������:sql�������Ҫ�Ĳ�ѯ������
			 *          
			 *          
			 *    ����ִ������Ժ�  ����ѯ�Ľ�ɫ�Ľ�����浽Role���͵ļ�����
			 */
			list = this.getMap().queryForList("role_space.queryUserRole",empno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//�������յĽ�ɫ�Ľ��
		return list;
	}

}
