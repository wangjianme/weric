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
 * 矩阵源，用于获取数据，创建矩阵信息的工作
 * 注意：本身数据来源不同需要提供不同的实现类，可参考{@link util.collaborative.matrix.impl.MatrixFromFile}
 * @author Administrator
 *
 */
public abstract class MatrixSource {
	private List<InfoBean> matrixValues = new ArrayList<InfoBean>();//为创建矩阵所使用的数据集合，可以从文件中获取，也可以从数据库中获取
	private Map<String,Integer> userMap = new TreeMap<String,Integer>();//存储用户名在数组中的索引坐标,和上面存储用户名和商品名的集合必须拥有相同的顺序结构
	private Map<String,Integer> itemMap = new TreeMap<String,Integer>();//存储商品名在数组中的索引坐标,和上面存储用户名和商品名的集合必须拥有相同的顺序结构
	private int userCount;
	private int itemCount;
	private int[][] matrix = null;
	private static Logger log = Logger.getLogger(CollaborativeFilter.class); 
	/**
	 * 负责矩阵源数据的初始化工作
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
	 * 初始化数据信息，每个信息包含一个用户名和一个商品名，从购买记录中生成，可以是从文件中读取，也可以从数据库中获取
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
	 * 负责矩阵数据的生成，根据来源不同需要编写不同的实现
	 * 一般需要实现三个基本信息的获取
	 * 购买记录的集合获取
	 * 购买记录中不重复用户信息的集合获取
	 * 购买记录中不重复商品信息的获取
	 */
	protected abstract void initCollection();
	/**
	 * 用于通过数据源获取用户购买信息
	 * @return 通过数据源获取的购买信息集合
	 */
	protected abstract List<InfoBean> initInfoList();
	/**
	 * 获取全部购买信息中不重复的用户
	 * @return 获取购买记录中全部不重复的用户
	 */
	protected abstract Collection<String> getUsers();
	/**
	 * 从购买记录中获取商品信息
	 * @return 获取购买记录中全部不重复的商品
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
