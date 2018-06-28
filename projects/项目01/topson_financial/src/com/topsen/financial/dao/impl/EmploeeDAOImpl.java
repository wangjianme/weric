package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.topsen.financial.dao.inter.EmploeeDAO;
import com.topsen.financial.po.Emploee;
/**
 * 
 * @author Administrator
 * �������ݷ��ʲ����
 */
public class EmploeeDAOImpl extends EmploeeDAO {
	/**
	 * ���ݷ��ʲ��еĵ�¼�ķ���() �˷��������б�Ϊһ������ �����û���  ͨ���˲������û������ݽ����������ݵĵײ��
	 * ����
	 * 
	 */
	public Emploee load(String empno) {
		//�����û����Ƿ񴫵ݽ���
		System.out.println("�û���(empno)="+empno);
		//����һ��Emploee���������û�����ص���Ϣ
		Emploee emp = null;
		try {
			/**
			 * ��sql����������ݿ�����ִ��  
			 * ���queryForObject("emp_space.one",empno);�������൱��������ǰ��װ�Ķ����ݿ�
			 * ��ѯ�ķ���  ֻ������������ǿ�ܸ������ṩ�õ�  �ù���ֱ���ü���  ��Ҫע�����������������Ķ���
			 * ������SqlMapClient map�����Ķ���  ���ǲ�ȡ�Ĵ�ʩ���ǰѴ������������������Ϊ˽�е�
			 * Ȼ������˽�е�SqlMapClient map������ڷ���ֵΪqlMapClient map���еķ����м��� Ȼ����
			 * �ⲿֱ�ӵ��� ���˽�е�SqlMapClient map���ڵĴ��з���ֵ����ΪSqlMapClient map�Ĺ��е�
			 * �����м���   ����ִ������Ժ�������SqlMapClient map��������������
			 * 
			 * (Emploee) this.getMap().queryForObject("emp_space.one",empno);���������Ҫ��
			 * ���ݴ��ݹ������ʺŽ��в�ѯ  Ҳ����˵�����ǲ�ѯ�������ʺŵ���sql����ѯ������  
			 * ���ܲ�ؽ��˵�����ǵ��ʺ��Ǵ��ڵ�  �����ǵĽ����������    
			 * ������������ǵ�sql�����д��queryForObject("emp_space.one",empno);�����ڲ���
			 * ���Ǵ�ʱ����������ǿ���ṩ�õ� �������sql��������޷���Ϊ�ظ�д���ڲ�   ����ֻ��д���ⲿ
			 * Ҳ����˵��Ȼ�����������  ��������ܰ������������ɾ�Ĳ�Ĳ���  ˵����������һ����sql���  ��������
			 * ������Ϊ�Ŀ��Ʋ���   ����sql���ֻ��ͨ�� �˷����Բ�������ʽ���ݽ�ȥ  �˷�����ʱ����������  һ��
			 * �ʺ� ��һ�����ǲ�ѯ������ִ����Ҫ�Ĳ�ѯ��sql���  ��ʵ����emp_space.one�����������ľ���sql���
			 * ����������Ҫ��ѯ��sql���   ���emp_space.oneҲ���൱�ڴ������map�����е� ��Ϊ���map����
			 * ��ԭ�����������������ļ�dbconfig.xml��  ��������ļ��а��������ݿ���  �õ����еĶ���  �����������ݿ�
			 * �Ĵ���   ����Ĳ�����sql��� ֻ�����������sql���û��ֱ��д�ڴ������ļ���  ����д����һ��emploee_sqlmap.xml
			 * �ļ���  ���ڵ�ǰ���ǲ����ľ��Ƕ��û����в���  ���û��Ƿ����  Ҳ���Ƕ�emplooe���в��� ����
		     * ���ǵ���������һ��emploee_sqlmap.xml�ļ�  �����еĶ�empolee��������ص�sql���д�����
		     * �����ļ���
		     * 
		     * 
		     * ִ�в�ѯ�Ĳ���  �Ѳ�ѯ�Ľ��  ����Emploee�Ķ���ȥ���ղ�ѯ�����ĸ��û�����Ϣ
		     *
		     *
		     * ע�ⷽ���ķ���ֵ����һ����Object����  ��Ϊ��������� �ײ���ṩ�õ�    ���ڶ�����ʹ��   ����
		     * ������ĳһ����������  ������������һ��Ҫע���������͵�ת��  ת����������Ҫ����������
			 */
			emp = (Emploee) this.getMap().queryForObject("emp_space.one",empno);
			System.out.println("emp="+emp);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//���û����յ���Ϣ������
		return emp;
	}

	public List<Emploee> queryByRoleId(int roleId) {
		List<Emploee> list = null;
		try {
			list = this.getMap().queryForList("emp_space.queryByRoleId",roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Emploee> queryAll() {
		List<Emploee> list = null;
		try {
			list = this.getMap().queryForList("emp_space.queryAll");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int updateRoleAndEmp(int roleId, String[] empnos) {
		int i = 0;
		try {
			this.getMap().startTransaction();
			this.getMap().startBatch();
			this.getMap().delete("emp_space.delete", roleId);
			if (empnos != null){
				for (String empno : empnos){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("empno", empno);
					map.put("roleId", roleId);
					this.getMap().insert("emp_space.insert", map);
				}
			}
			this.getMap().executeBatch();
			this.getMap().commitTransaction();
			i = 1;
		} catch (SQLException e) {
			try {
				this.getMap().getCurrentConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				this.getMap().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;

	}

	public int update(Emploee t) {
		int i = 0;
		try {
			i = this.getMap().update("emp_space.update",t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
//	public static void main(String[] args) {
//     byte a = 1;
//     byte b = 127;
//     System.out.println((byte)(a+b)); 128  -128
//	}
}
