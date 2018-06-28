package spider.util.collaborative.matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import spider.util.collaborative.CollaborativeFilter;
import spider.util.collaborative.InfoBean;
/**
 * ����Դ�����ڻ�ȡ���ݣ�����������Ϣ�Ĺ���
 * ע�⣺����������Դ��ͬ��Ҫ�ṩ��ͬ��ʵ���࣬�ɲο�{@link util.collaborative.matrix.impl.MatrixFromFile}
 * @author Administrator
 *
 */
public abstract class MatrixSource {
	private List<InfoBean> matrixValues = new ArrayList<InfoBean>();//Ϊ����������ʹ�õ����ݼ��ϣ����Դ��ļ��л�ȡ��Ҳ���Դ����ݿ��л�ȡ
	private Map<String,Integer> userMap = new TreeMap<String,Integer>();//�洢�û����������е���������,������洢�û�������Ʒ���ļ��ϱ���ӵ����ͬ��˳��ṹ
	private Map<String,Integer> itemMap = new TreeMap<String,Integer>();//�洢��Ʒ���������е���������,������洢�û�������Ʒ���ļ��ϱ���ӵ����ͬ��˳��ṹ
	private int userCount;
	private int itemCount;
	private int[][] matrix = null;
	private static Logger log = Logger.getLogger(CollaborativeFilter.class); 
	/**
	 * �������Դ���ݵĳ�ʼ������
	 */
	public void init(){
		this.initCollection();
		this.matrixValues = this.initInfoList();
		Collection<String> userCollection = this.getUsers();
		Collection<String> itemCollection = this.getItems();
		for (String user : userCollection){
			userMap.put(user, userCount++);
		}
		for (String item : itemCollection){
			itemMap.put(item, itemCount++);
		}
		log.info(userMap);
		log.info(itemMap);
		this.matrix = new int[this.userCount][this.itemCount];
		this.initMatrix();
	}
	/**
	 * ��ʼ��������Ϣ��ÿ����Ϣ����һ���û�����һ����Ʒ�����ӹ����¼�����ɣ������Ǵ��ļ��ж�ȡ��Ҳ���Դ����ݿ��л�ȡ
	 */
	private void initMatrix(){
		List<InfoBean> matrixValues = this.getMatrixValues();
		for (InfoBean bean : matrixValues){
			Integer userIndex = this.getUserMap().get(bean.getUserName());
			Integer itemIndex = this.getItemMap().get(bean.getItemName());
			this.matrix[userIndex][itemIndex] = this.matrix[userIndex][itemIndex]+1;
		}
	}
	/**
	 * ����������ݵ����ɣ�������Դ��ͬ��Ҫ��д��ͬ��ʵ��
	 * һ����Ҫʵ������������Ϣ�Ļ�ȡ
	 * �����¼�ļ��ϻ�ȡ
	 * �����¼�в��ظ��û���Ϣ�ļ��ϻ�ȡ
	 * �����¼�в��ظ���Ʒ��Ϣ�Ļ�ȡ
	 */
	protected abstract void initCollection();
	/**
	 * ����ͨ������Դ��ȡ�û�������Ϣ
	 * @return ͨ������Դ��ȡ�Ĺ�����Ϣ����
	 */
	protected abstract List<InfoBean> initInfoList();
	/**
	 * ��ȡȫ��������Ϣ�в��ظ����û�
	 * @return ��ȡ�����¼��ȫ�����ظ����û�
	 */
	protected abstract Collection<String> getUsers();
	/**
	 * �ӹ����¼�л�ȡ��Ʒ��Ϣ
	 * @return ��ȡ�����¼��ȫ�����ظ�����Ʒ
	 */
	protected abstract Collection<String> getItems();
	
	public Map<String, Integer> getUserMap() {
		return userMap;
	}
	public Map<String, Integer> getItemMap() {
		return itemMap;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	public List<InfoBean> getMatrixValues() {
		return matrixValues;
	}
	public int getUserCount() {
		return userCount;
	}
	public int getItemCount() {
		return itemCount;
	}
	
}
