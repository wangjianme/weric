package spider.util.collaborative.caculation.impl;

import java.text.DecimalFormat;
import java.util.Set;
import java.util.TreeSet;

import spider.util.collaborative.CollaborativeFilter;
import spider.util.collaborative.RecommandInfo;
import spider.util.collaborative.caculation.SimilaryCalculation;
public class ItemCFKNN implements SimilaryCalculation{

	public float[][] similaryCalculate(CollaborativeFilter filter) {
		float[][] similaryMatrix = new float[filter.getMatrixSource().getItemCount()][filter.getMatrixSource().getItemCount()];
		int[][] matrix = filter.getMatrixSource().getMatrix();
		for (int i = 0;i < similaryMatrix.length;i++){
			for (int j = 0; j < similaryMatrix[i].length;j++){
				double v = 0;
				//欧式距离  
				for (int w = 0;w < matrix.length;w++){
					v += Math.pow((matrix[w][i] - matrix[w][j]),2);
				}
				v = Math.sqrt(v);
				similaryMatrix[i][j] = Float.parseFloat(new DecimalFormat("#.000000").format(1/(1+v)));
			}
		}
		return similaryMatrix;
	}
	public int[] getBaseMatrix(CollaborativeFilter filter,String key) {
		int[] userItem = new int[filter.getMatrixSource().getItemCount()];
		int[][] matrix =  filter.getMatrixSource().getMatrix();
		int userIndex = filter.getMatrixSource().getUserMap().get(key);
		for (int i = 0;i < userItem.length;i++){
			userItem[i] = matrix[userIndex][i];
		}
		return userItem;
	}
	public Set<RecommandInfo> recommand(CollaborativeFilter filter) {
		Set<RecommandInfo> infoSet = new TreeSet<RecommandInfo>();
		Object[] itemNameArray = filter.getMatrixSource().getItemMap().keySet().toArray();
		int[] baseMatrix = filter.getBaseMatrix();
		float[][] similaryMatrix = filter.getSimilaryMatrix();
		for (int i = 0;i < baseMatrix.length;i++){
			double d = 0;
			for (int j = 0;j < filter.getMatrixSource().getItemCount();j++){
				d+=similaryMatrix[i][j]*baseMatrix[j];
			}
			//只将用户没有购买过的商品加入集合，并且降序排列推荐前几名
			if (baseMatrix[i] == 0){
				RecommandInfo info = new RecommandInfo(itemNameArray[i].toString(),Double.parseDouble(new DecimalFormat("#.00").format(d)));
				infoSet.add(info);
			}
		}
		return infoSet;
	}

}
