package spider.util.collaborative;


import java.util.Set;

import org.apache.log4j.Logger;

import spider.util.collaborative.caculation.SimilaryCalculation;
import spider.util.collaborative.matrix.MatrixSource;




/**
 * 
 * @author ����
 * ���ڽ���Эͬ�����Ƽ��ĺ����࣬��������Эͬ�����Ƽ����̵���
 *
 */
public class CollaborativeFilter {
	private int[][] matrix = null;//ԭʼ���󣬴洢�û�����Ʒ֮��Ĺ����ϵ
	private MatrixSource matrixSource = null;//����Դ�������ڿ��ƾ������ݵ���Դ�;��������
	private float[][] similaryMatrix = null;//�����Ծ���ͨ��ԭʼ�������õ��������㷨ʹ��SimilaryCalculation���ṩ��similaryCalculate����
	private SimilaryCalculation cal = null;//�㷨������������ʵ�ֶ����㷨
	private int[] baseMatrix = null;//�����������Ƽ�����Ļ������󣬿��Ի���ItemҲ���Ի���User
	private static Logger log = Logger.getLogger(CollaborativeFilter.class); 
	/**
	 * 
	 * @param matrixSource ����Դ����
	 * @param cal  ���Ƽ�����
	 */
	public CollaborativeFilter(MatrixSource matrixSource,SimilaryCalculation cal){
		this.cal = cal;
		this.matrixSource = matrixSource;
		this.matrixSource.init();
		this.matrix = this.matrixSource.getMatrix();
		log.info(StdoutObject.matrixToString(this.matrix));
	}
	/**
	 * ʹ��SimilaryCalculation�ṩ��similaryCalculate���������������Ծ���similaryMatrix���ں�������
	 */
	private void createSimilarityMatrix(){
		this.similaryMatrix = cal.similaryCalculate(this);
		log.info(StdoutObject.matrixToString(similaryMatrix));
	}
	/**
	 * 
	 * @param key �Ƽ����ؼ���
	 * @return �Ƽ���� ����������Խ������У��Ѿ��������Ʒ�Ѿ����ų�����
	 * key��ʾ��Ҫ�����Ƽ����û�Ҳ�����Ǵ�����в�Ʒ�������Ƽ���ĳ����Ʒ,
	 * ע��:��ΪCF�㷨������ǻ���Item�ļ����㷨����Ҫ������Ʒ֮��Ĺ�����ϵΪ�û��Ƽ�ĳ����Ʒ��������Ҫ֪�����û��Ĺ�����Ϣ���ʴ˻���ItemCF�㷨��keyӦ�����û���
	 *      ������User�㷨����Ҫ�����û�֮��Ĺ����������ҵ��͸��û�֮�����ƵĹ����û��Ӷ����Ͳ�Ʒ��������Ҫ�ҵ�ĳ���Ʒ���û������¼������key�ؼ���Ӧ������Ʒ��
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
