package com.topsen.financial.action;


import java.util.List;

import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.LogCreater;
import com.topsen.financial.po.OperationLog;
import com.topsen.financial.po.Role;
import com.topsen.financial.service.EmploeeService;
import com.topsen.financial.service.RoleService;
import com.topsen.financial.util.support.struts2.BaseAction;
import com.topsen.financial.util.support.web.ObjectContainter;

public class EmploeeAction extends BaseAction{
	private EmploeeService service = new EmploeeService();
	private RoleService roleService = new RoleService();
	private String empno;
	private String password;
	private String[] empnos;
	private String operation;
	private List<Emploee> empList;
	private List<Emploee> allList;
	private String result;
	private Role role;
	private List<Role> roleList;
	
	public String getResult() {
		return result;
	}

	public void setEmpnos(String[] empnos) {
		this.empnos = empnos;
	}
	
	public Role getRole() {
		return role;
	}
	
	public List<Emploee> getEmpList() {
		return empList;
	}
	
	public List<Emploee> getAllList() {
		return allList;
	}
	
	public String getOperation() {
		return operation;
	}
	

	public List<Role> getRoleList() {
		return roleList;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	 * 
	 */
	public String updatePassword(){
		String password = this.getRequest().getParameter("password");
		Emploee emp = (Emploee)getSession().getAttribute("emp");
		if (password == null || password.equals("")){
			emp = new Emploee();
			emp.setEmpno(empno);
			password="000000";
		}
		emp.setPassword(password);
		service.updatePassword(emp);
		return "upconfirm";
	}
	/**
	 * �˷���Ϊ��¼�ķ��� ����֤����������Ϊ����֤��¼��״̬ Ҳ���ǵ�¼�ɹ�����˵�ǵ�¼ʧ��  Ҳ����˵�����¼���Ե���
	 * ������֤����֤��ͨ��  ���仰˵��Ϊ��¼����֤������ֻ����֤����ɹ��� �������������֤��������������֤�������֤�ɹ�  ֻ��
	 * ��������ǰ�������ǲſ��Ե�¼ Ҳ����˵ ��֤�����ŵ�¼  ֻ����֤ͨ���˲ſ��Ե�¼  ��˵ֱ�׵� ֻ�� 
	 * ��¼�Ĵ����д����֤����֤�ɹ�������
	 */
	public String login(){		
		//String checkCode = (String)getSession().getAttribute("check_code");
		//String requestCode = getRequest().getParameter("checkcode");
		//��ȡ��������֤��
		String checkCode = (String)getSession().getAttribute("certCode");
		//����������֤��������
		System.out.println("checkCode:"+checkCode);
		//��ȡ���������
		String requestCode = getRequest().getParameter("yz");
		//����ȡ���뵽����֤����д�ӡ���
		System.out.println("requestCode:"+requestCode); 
		//�ж������������֤��������������֤���Ƿ�һ��  ��һ�¿��Խ��������ĵ�¼  ����һ����ʾ��֤����֤ʧ��
		if (requestCode == null||checkCode==null||!checkCode.equals(requestCode)){
			//��һ����ʾ��֤����֤����
			result = "��֤�����";
			//result = "��¼�ɹ�";
			//�����֤�ɹ�����������ĵĵ�¼����֤
		}else{
			/**
			 * ��֤�ɹ���ǰ���¿�ʼ��¼��֤ 
			 * ��ҳ����൱��һ��servlet  �������ǵľ������֪�� ��servlet���湹��services��Ķ���Ȼ����������
			 * ���� �������ڵ���service�������湹��dao��Ķ���  Ȼ�����ù��������Ķ������dao����ķ���
			 * ����dao����ķ�����Ҫ�������ĵĵ�¼����֤��   ���Դ�ʱ��dao��¼�ķ�������͵ò���  ��������������  һ�����ʺ�
			 * һ��������  ���ݴ��ݽ������ʺŽ��в�ѯ�����˺��Ƿ����  �����ڰѴ��˺����������ȡ����  �ʹ�ʱ���ǵ�������������ƥ��  ��ƥ��
			 * �ɹ��򷵻ص�¼�ɹ�����ʾ��Ϣ ����¼ʧ���򷵻صĵ�¼ʧ�ܵ���ʾ��Ϣ  ���ڴ�ʱ��dao���еĵ�¼�ط�������service���¼�еķ������õ�
			 * ���Դ�ʱdao��ķ������صĵ�¼��״̬����ʾ��ͣ����services���еĵ�¼�ķ�����  ���ҵ�����������Ķ�������servlet�д�����
			 * ��������Ҫ��servlet��������services����  Ŀ�ؾ��ǰѵĵ�¼��״̬��������servlet��Ҳ����ҵ���  ����service������ķ���
			 * �����String  ��Ϊ����Ҫ��ҵ��㽫��¼��״̬������  Ȼ����ݷ��ص�״̬�ж����յĵ�¼�ɹ����ǵ�¼ʧ��  ��ʵ��������һ������
			 * �Ǿ���dao������ĵ�¼�ķ��� ��������˭���ݽ�����  Ҫ��֪������������˭���ݽ�����  ����֪�������������Ǹ�ʲô�õ�  ����dao��¼����������
			 * �������Ǵ����û���������ʺź�����  ���������������������û����ݽ�����  �������Ǿ����֪ �û�����
			 * �����Ĳ������ǿ�����servlet���ȡ �ɴ˿ɼ���������϶��Ǵ�servlet�㴫�ݽ�����  servlet��
			 * ������Ĺ�������ȡ����   ͨ����صķ�����ҳ�洫�ݽ�����ֵ��ȡ����  Ȼ�󴫵ݵ�servlet�е�service
			 * ��  �ڴ��ݵ�service���е�dao�ĵ�¼�ķ�����
			 * 
			 * �˷����ľ���Ľ����
			 *         �Ѵ�ҳ��Ļ�ȡ����ֵ���ݵ�service���е�login����  ��service�������ù���������dao
			 *         �Ķ���ȥ����dao���еķ���  Ȼ��Ѳ������ݵ�dao��ķ�����   ʵ�ֵ����ݿ����صĲ���
			 *         ����������ѧ������������֪ �ڳ���ִ�еĸ�������  ������������ͻ�ִ�з���   ����ִ������Ժ�
			 *         �ٻص��������ô����ų���ִ��˳�����ִ��
			 */
			result = service.login(empno, password);
		    /**
		     * �ѵõ���¼��״̬���бȽ�  ���result����洢�ľ��ǵ�¼�ɹ�  ��ô���ǵ�¼�ɹ�
		     * ����洢�ǵ�¼��ʧ��  ˵����¼ʧ��
		     * 
		     * 
		     * ��֤ͨ����ʾ��¼�ɹ�
		     */
			if (result.equals("��¼�ɹ�")){
				/**
				 * ��������������5�ű��Ȩ�޵ĵ�¼   ��ǰ�ĵ�¼���ڵ�¼�ɹ��� ֱ��������ҳ����й��ܵĲ���
				 * �������ڵĵ�¼���ڵ�¼������û���ѡ���ɫ   �ٸ���ѡ��Ľ�ɫȥѡ��Ȩ�� ˵������5
				 * �ű��Ȩ�޵ĵ�¼Ŀ�ľ��Ǹ��û�����Ȩ��  
				 * ��ʱ���Ǿͻ��¼�ɹ�  ��¼�ɹ������̼�ס��ʱ��¼���û���  Ȼ������û���ȥ���ҽ�ɫ
				 * 
				 * �����û���ȥ���ҽ�ɫ  ����û����Ѿ�������   ���ҵ�һ������ ������������ṩ���û���ȥ���ҽ�ɫ
				 * 	���roleList = roleService.queryEmpRole(empno);�������Ǹ����û������ҽ�ɫ
				 *  �������ǵľ����֪  �漰������Ĳ�ѯ��  �ǰ����ݴ��ݵ�servlet��  ��servlet�㹹��service
				 *  �Ķ���   Ȼ����service�й������ݷ��ʲ�Ķ���  ���õײ�ķ���ִ����Ӧ�Ĳ���  ��ô��ʱ
				 *  Ҳ��һ����   ��ֵ������  �͵���Ҫ������  ����services�Ķ����dao�Ķ�����   �������
				 *  services��ĵô��� ������������û������ҽ�ɫ�ķ���   ��ô��Ӧ��dao���ݷ��ʲ�Ҳ�ô���
				 *  ����ķ���Ҳ�Ǹ����û������ҽ�ɫ�ķ���   ���Եĵ���֮���ô�����service���dao��
				 *  
				 *  
				 *  �����û���ȥ���ҽ�ɫ  һ��Ҫ���û������ݽ�ȥ ֻ���������еķ������ܸ����û���ȥ����
				 *  ����Ļ��ǲ��ܵ�  
				 *  
				 *  ����ѯ���û���Ӧ�Ľ�ɫ���浽roleList������
				 */
				roleList = roleService.queryEmpRole(empno);
				//�Ѵ�ֵ������setAttribute()����������  Ȼ����Ȩ�޵�servlet�������getSession.getAttribute()��ֵȡ����
				getSession().setAttribute("emp",ObjectContainter.getObject());
				getSession().setAttribute("empno", empno);
				LogCreater.saveLog(OperationLog.getNewLog(LogCreater.TYPE_SYSTEM, "emploee", "����ϵͳ", "��¼�ɹ�"));
				//getSession().setAttribute("operation", "role");
				//�ѽ�ɫ��ֵ��operation  
				operation="role";
			
			
			}else{
				LogCreater.saveLog(OperationLog.getNewLog(LogCreater.TYPE_SYSTEM, "emploee", "����ϵͳ", "��¼ʧ��"));
			}
		}
		//��ʱ����תҳ����ݿ�ܵİ�ȥ��  ���ݰ�ȥ�ҵ���Ӧ��struts���    
		return "next";
	}
	public String queryEmpByRoleId(){
		String roleId = this.getRequest().getParameter("roleId");
		empList = service.queryEmpByRolId(Integer.parseInt(roleId));
		allList = service.queryAll();
		role = roleService.queryOne(Integer.parseInt(roleId));
		return "go";
	}
	
	public String updateGroup(){
		String roleId = this.getRequest().getParameter("roleId");
		int i = service.updateRoleAndEmp(Integer.parseInt(roleId),empnos);
		return "update";
	}
}
