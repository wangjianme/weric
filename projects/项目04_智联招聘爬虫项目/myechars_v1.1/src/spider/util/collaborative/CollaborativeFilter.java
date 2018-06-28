package spider.util.collaborative;


import java.util.Set;

import org.apache.log4j.Logger;

import spider.util.collaborative.caculation.SimilaryCalculation;
import spider.util.collaborative.matrix.MatrixSource;




/**
 * 
 * @author 白宇
 * 用于进行协同过滤推荐的核心类，负责整个协同过滤推荐流程的类
 *
 */
public class CollaborativeFilter {
	private int[][] matrix = null;//原始矩阵，存储用户和商品之间的购买关系
	private MatrixSource matrixSource = null;//矩阵源对象，用于控制矩阵数据的来源和矩阵的生成
	private float[][] similaryMatrix = null;//相似性矩阵，通过原始矩阵计算得到，计算算法使用SimilaryCalculation类提供的similaryCalculate方法
	private SimilaryCalculation cal = null;//算法计算器，可以实现多种算法
	private int[] baseMatrix = null;//用于最后进行推荐计算的基础矩阵，可以基于Item也可以基于User
	private static Logger log = Logger.getLogger(CollaborativeFilter.class); 
	/**
	 * 
	 * @param matrixSource 矩阵源对象
	 * @param cal  相似计算器
	 */
	public CollaborativeFilter(MatrixSource matrixSource,SimilaryCalculation cal){
		this.cal = cal;
		this.matrixSource = matrixSource;
		this.matrixSource.init();
		this.matrix = this.matrixSource.getMatrix();
		log.info(StdoutObject.matrixToString(this.matrix));
	}
	/**
	 * 使用SimilaryCalculation提供的similaryCalculate方法创建出相似性矩阵similaryMatrix用于后续计算
	 */
	private void createSimilarityMatrix(){
		this.similaryMatrix = cal.similaryCalculate(this);
		log.info(StdoutObject.matrixToString(similaryMatrix));
	}
	/**
	 * 
	 * @param key 推荐类别关键词
	 * @return 推荐结果 ，按照相关性降序排列，已经购买的商品已经被排除在外
	 * key表示需要进行推荐的用户也可以是打算进行产品相似性推荐的某款商品,
	 * 注意:因为CF算法中如果是基于Item的计算算法中需要根据商品之间的关联关系为用户推荐某款商品，所以需要知道该用户的购买信息，故此基础ItemCF算法中key应该是用户名
	 *      而基于User算法中需要根据用户之间的购买相似性找到和该用户之间相似的购买用户从而推送产品，所以需要找到某款产品的用户购买记录，所以key关键字应该是商品名
	 */
	public Set<RecommandInfo> recommand(String key){
		this.createSimilarityMatrix();
		this.baseMatrix = this.cal.getBaseMatrix(this, key);
		return this.cal.recommand(this);
	}
	
	
	public float[][] getSimilaryMatrix() {
		return similaryMatrix;
	}

	public int[] getBaseMatrix() {
		return baseMatrix;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	public MatrixSource getMatrixSource() {
		return matrixSource;
	}
	
	
	
	
	
}
