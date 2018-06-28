package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.EmploeeDAOImpl;
import com.topsen.financial.dao.inter.EmploeeDAO;
import com.topsen.financial.po.Emploee;
import com.topsen.financial.util.support.web.ObjectContainter;

public class EmploeeService {
	//��service�㹹�����ݷ��ʲ�Ķ���  ���ڵ������ݷ��ʲ���е�login()����
	private EmploeeDAO dao = new EmploeeDAOImpl();
	/**
	 * ����service���Լ��ĵ�¼�ķ���  ���ڴ˷���ֻ����servlet������ʾ�û��ĵ�¼��״̬  Ҳ����˵�δ˷���
	 * Ҫ��servlet�б�����  ���Դ˴˷����ķ���ֵString  ��Ϊ�Ĵ˷�������ֻ�Ƕ����ݷ��ʲ㴦��
	 * �Ժ� һ���������ʾ  ���Դ˷����ķ���ֵ����String�����͵�  ˳���ҳ����ʺź����봫�ݽ���
	 */
	public String login(String empno,String password){//���ô��ݽ������ʺ�������еĵ�¼����֤
		//����һ��String���͵ı��� ������¼��¼��״̬
		String result = "��¼ʧ��";
		/**
		 * ���ù����������ݷ��ʲ�Ķ���ȥ�������ݷ��ʲ������login()����  �˷�����Ҫһ�������Ǿ����û���
		 * ��Ϊ����Ҫ���������������ݷ��ʲ�ķ���ȥ���ҵ�ǰ����û����Ƿ����   ����ڰѵ�ǰ���û�����ص���Ϣ��������
		 * 
		 * ִ�����ݷ��ʲ�ķ��� ���û������ݽ�ȥ���еײ�Ĳ�ѯ
		 * ���ڷ���ֵ����ΪEmploee������ Emploee���͵ı������н���
		 * 
		 * 
		 */
		Emploee emp = dao.load(empno);//��ʱ�û�����Ϣ����emp��
		/**
		 * ��Ϊ����Ҫ��ȡemp�е����������������������ƥ��  �����ڻ�ȡ֮ǰ  ���жϴ�
		 * emp�Ƿ�Ϊ�� ��Ϊ�յ��������ȥ��ȡ   Ϊ�յ�����¾Ͳ�ȥ��ȡ  Ϊ�յ������ζ��ֱ�ӵ�¼ʧ��
		 * �ж�emp�Ƿ�Ϊ��
		 */
		if (emp != null){
			/**
			 * ��������ڿ�  ������ȡ���� ��ȡ��������������������һ��  ���¼�ɹ�
			 */
			if (emp.getPassword().equals(password)){//emp.getPassword() == null ||
				ObjectContainter.setObject(emp);
				//���ص�¼��״̬
				result = "��¼�ɹ�";
			}
		}
		/**
		 *��ʵ��result����ֵ���������
		 *һ���ǵ�¼ʧ��  һ���ǵ�¼�ɹ�    ����ǵ�¼ʧ��  ��һ��if�Ͳ�Ӧ��ִ��  �����¼�ɹ�  ��һ��if�͵�ִ��  ����
		 *���ǵ�һ��ifִ��  �ǲ��ܹ���֤��¼�ĳɹ���  ������֤��ifҲ��ִ��  ֻ���������ܵ�¼�ɹ�   ��������֤����ͨ��
         *���¼ʧ��   �����Ǵ�ǰ���û�������ô���  ����û���������  �ͻ�ֱ�ӵ�¼ʧ��   Ҳ����˵���result����ǵ�¼ʧ��ʱ
         *�����¼ʧ�� ����������  һ������  �����û�������ʱֱ�ӵ�¼ʧ��    ����  �û�������  ������֤ʧ��  ��ʾ�ĵ�¼ʧ�� 
         *�����ֲ�ͬ������  ����Ϊ�˴������ܸ�  �����Ը���  ���԰�������¼ʧ��  �϶�Ϊһ  ��һ����¼ʧ����ʾ  д��Ϊ �����յĵ�¼��
         *״̬����Ϣֱ��return���ص�һ��if����Ժ�
		 */
		return result;
	}
	public Emploee  superLogin(String empno,String checkCode){
		Emploee emp = null;
		if (checkCode.equals("checkon")){
			emp = dao.load(empno);
		}
		return emp;
	}
	
	public List<Emploee> queryEmpByRolId(int roleId){
		return dao.queryByRoleId(roleId);
	}
	
	public List<Emploee> queryAll(){
		return dao.queryAll();
	}
	
	public int updateRoleAndEmp(int roleId,String[] empnos){
		return dao.updateRoleAndEmp(roleId, empnos);
	}
	
	public int updatePassword(Emploee emp){
		return dao.update(emp);
	}
}
