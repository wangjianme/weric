package com.topsen.financial.action;

import java.util.List;

import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.Power;
import com.topsen.financial.po.Role;
import com.topsen.financial.service.PowerService;
import com.topsen.financial.service.RoleService;
import com.topsen.financial.util.support.struts2.BaseAction;

public class PowerAction extends BaseAction {
	private PowerService service = new PowerService();
	private RoleService roleService = new RoleService();
	private List<Power> powerList;
	private List<Power> roleList;
	private Role role; 
	private int[] powerId;
	public void setPowerId(int[] powerId) {
		this.powerId = powerId;
	}
	public Role getRole() {  
		return role;
	}
	public List<Power> getPowerList() {
		return powerList;
	}
	public List<Power> getRoleList() {
		return roleList;
	}
	public String queryAllPower(){
		String roleId = this.getRequest().getParameter("roleId");
	
		powerList = service.queryAll();
		roleList = service.queryByRoleId(Integer.parseInt(roleId));
		role = roleService.queryOne(Integer.parseInt(roleId));
		return "next";
	}
	public String insertNewPower(){
		String roleId = this.getRequest().getParameter("roleId");
		service.updatePowerAndRow(powerId, Integer.parseInt(roleId));
		return "go";
	}
	/**
	 * 
	 * �˷����Ǹ���ɫidѡ���ɫ  ��ϸ��������:
	 *    ����������ǰ�ķ�������֪��  ��ͬ���˵�¼�в�ͬ�Ľ�ɫ   ��ͬ�Ľ�ɫ  �в�ͬ��Ȩ��   Ҳ����˵��ɫ  ���˵Ľ�ɫ 
	 *    Ȩ�����˵�Ȩ��   �����Ѿ�����˸����û�ȥѡ���ɫ   ��������Ҫ���ݽ�ɫȥѡ��Ȩ��    �����û�ȥѡ���ɫʱ ����
	 *    ��ͨ���ڵ�¼�������ʺ�����   �����¼����̨�������ݿ����֤  �����¼�ɹ�  ��ʱ��¼�µĴ��û�����Ӧ���ʺ�
	 *    Ȼ����ø����û���ȥ��ѯ��ɫ�ķ������ ��ɫ�Ĳ�ѯ ������һ���������������  �����û������ҽ�ɫ    ��ɫ��ѯ����
	 *    �ѽ�ɫ����Ϣ  �洢����ɫ���͵ļ�����  Ȼ��Ѳ�ѯ���Ľ�ɫ�����������б����ʽ��ʾ��ҳ����  Ȼ���û�ȥѡ���ɫ
	 *    ���û��������б���ѡ���ɫ��ʱ��  ������û���Ӧ�Ľ�ɫ�Ѿ�������   ������Ҫ���������ɫȥѡ��Ȩ���� ��ʱ����
	 *    �û�ȥѡ��Ȩ�޵����ҳ��  �ں�̨��Ӧ�Ĵ���ҳ��Ϊindex.jspҳ��  �����û���ȥѡ���ɫ�Ժ�  ���̾�Ҫ���ݽ�ɫȥѡ��
	 *    Ȩ����  ������index.jspҳ���form����  �������Ĵ���<form action="power!queryEmpPowerByRoleId.action" method="post">
	 *   ����ɫѡ�����Ժ�Ҫ��ת�����ҳ��  ȥ�����û�ȥѡ���ɫ��  ���ҳ�����Ȩ�޵�һ��servletҳ��  ������淽����
	 *   ִ�еĹ���   �����û���ȥ��֤��¼��ʱ���̨�����ִ�еĹ�����һ����  ������������һ����̵ķ���   ҳ����ת�Ժ������
	 *   Ȩ�޵�sevletҳ��  Ȼ����ݷ����ļ���֪��ͼ��ԭ��  �ҵ����ݽ�ɫȥ����Ȩ�޵ķ���   ��������
	 *    queryEmpPowerByRoleId() ���ݴ˷����Ϳ�����ɸ��ݽ�ɫȥ����Ȩ��  ��סһ���������û�еĲ���   
	 *    ��Ϊ���ǿ�������request�Ѿ���we�ڵ�ǰ�����servlet�и�ȡ����  Ȼ����servlet���й���service��
	 *    ���service��ָ����PowerService���service��  Ȼ���������ĸ��ݽ�ɫȥ����Ȩ�޷���  �˷�����һ��
	 *    ����Ҫ��������   ��ΪҪ����sertvlet��ȡ�����Ľ�ɫid���ݵ�����ȥ����Ȩ�޵ķ�����  �������������
	 *    ����Ȩ�����ݷ��ʲ�ķ���   Ȼ��������ݷ��ʲ�ĸ��ݽ�ɫid����Ȩ�޵ķ���  Ȼ�󷽷��ĵ��õ�ԭ��
	 *    �ڳ����ִ�еĹ�������������ִ�з���  ����ִ������Ժ��ٻص��������ô�����ִ��   ������
	 *    ���ʲ�ķ�������Ҫ���ݴ��ݵĽ�ɫidȥ����Ȩ��  ��ѯ�Ľ��ΪȨ��  ��Ȼ��Ҫһ��Ȩ�����͵ļ���������
	 *    ��ѯ�����Ľ��  ��servlet��������Ȩ�����͵ļ��Ͻ���һ��   ��������ִ�е���Ϻ�  �ͻ�Ѹ��ݽ�ɫȥ��ѯȨ�޽��Ȼ��ѽ�����ص���һ�ε�
	 *    �����ĵ��ô�      Ȼ����ݽ�����е���Ӧ�Ĵ���   �������ǵľ����֪  Ҫ��֪��ĳ���û�����Ӧ�Ľ�ɫ  ��Ȼ���ҵ����û������  ��Ϊ���ҵ���
	 *    �û��Ľ�ɫ  ����û������ж�Ӧ�Ľ�ɫ  ������Ȼ��ѯ�����û���ͽ�ɫ����м��  ��ѯ������ ���ǵ��ڵ�¼���˻���Ϊ��¼�û���Ϊ���ҽ�ɫ������
	 *    ͬ���ĵ���   ��ɫ�����Ժ�Ҫѡ��Ȩ��   ����ڳ���ִ����ǰ����µ�ĳ���û�����Ӧ�Ľ�ɫ����Ӧ��Ȩ����˭  ������̾��൱��
	 *    ���ݽ�ɫȥ��ѯȨ��  ���������  ��ô�����ɫ�͵��ж�Ӧ��Ȩ��  ���û�ж�Ӧ��Ȩ��  ������ѯ������  ���ݵ����������� ��ɫ���Ȩ�ޱ���м����������ı�
	 *    sql����ѯ�����  �Խ�ɫ��Ϊ��ѯ������ �Ϳ��Բ�ѯ�� ��ɫ����Ӧ��Ȩ��   ���Ǵ�ʱ�û�����ѯ�Ľ�ɫ��Ӧ��Ȩ�޲���Ψһ�� 
	 *    ��һ��ϵ�����ڽ�ɫ���Ȩ�ޱ���м���п�����    
	 *    
	 *    ��ҳ��Ĵ��ݹ�����ֵ��ȡ����Ȼ�󴫵ݵ�services��  ��service�й���Ȩ�޵�dao�Ķ��� Ȼ��
	 *    ����dao���������õײ�����ݷ��ʵķ���  ���հѲ�ѯ��Ȩ�޵Ľ������ʾ��������
	 *
	 */
	public String queryEmpPowerByRoleId(){
	   /*
	    * ͨ���̳�BaseAction �����е�request �̳й���  Ȼ�����÷���
	    * getParameter("roleId")�ѽ�ɫ��ȡ����
	    */
		String roleId = this.getRequest().getParameter("roleId");
		//����roleId�Ƿ�ȡ��
		System.out.println("roleId="+roleId);
		/*
		 * ����roleIdȥ��ѯȨ�޵ķ���  �ѻ�ȡ����roleId���ݵ�queryUserPower()������  ����
		 * ��ʱ��roleId������String���͵�  queryUserPower()��������Ĳ��������͵�
		 * ���Դ�ʱҪ����Integer.parseInt(roleId)�����ַ�����roleIdת��Ϊint���͵�oleId
		 * 
		 * ����ִ������Ժ����һ��Ȩ�����͵ļ��������ܲ�ѯ������Ȩ�޵Ľ��
		 */
		powerList = service.queryUserPower(Integer.parseInt(roleId));
		//
		Emploee emp = (Emploee)getSession().getAttribute("emp");
		
		emp.setPowerList(powerList);
		return "sucess";
	}
	
}
