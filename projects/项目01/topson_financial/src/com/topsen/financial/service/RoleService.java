package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.RoleDAOImpl;
import com.topsen.financial.dao.inter.RoleDAO;
import com.topsen.financial.po.Role;
import com.topsen.financial.util.support.dao.bean.PageModel;

public class RoleService {
	//�������ݷ��ʲ�Ķ���  �����������ݷ��ʲ��еĸ����û���ȥ���ҽ�ɫ�ķ���  
	private RoleDAO dao = new RoleDAOImpl();
	public PageModel queryByPage(int curPage){
		return dao.queryByPage(curPage);
	}
	public Role queryOne(int roleId){
		return dao.load(roleId);
	}
	public void update(Role role){
		dao.update(role);
	}
	
	public void delete(int roleId){
		dao.delete(roleId);
	}
	public void insert(Role role){
		dao.insert(role);
	}
	/**
	 * 
	 * �����û���ȥ���ҽ�ɫ�ķ���
	 * 
	 * �˷�����services�� �����Ǹ����û���ȥ���ҽ�ɫ��   �������ݷ��ʲ�Ķ���  Ȼ�����ô˶���� 
	 * ���ݷ��ʲ�ĵײ�ĸ����û���ȥ���ҽ�ɫ�ķ������ó���  Ȼ�������Ϊ��servlet��Ĵ��������û�������
	 * ��ȥ  Ȼ������û���ȥ��ѯ���û��Ľ�ɫ  ֻ�������Ĵ���ſ���ʵ�ַ����Ĵ��ݽ�����
	 * 
	 *
	 */
	public List<Role> queryEmpRole(String empno){
		return dao.queryEmpRole(empno);
	}
}
