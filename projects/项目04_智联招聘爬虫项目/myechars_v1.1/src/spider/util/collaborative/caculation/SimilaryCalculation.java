package spider.util.collaborative.caculation;

import java.util.Set;

import spider.util.collaborative.CollaborativeFilter;
import spider.util.collaborative.RecommandInfo;

public interface SimilaryCalculation {
	//相似性计算方法
	public float[][] similaryCalculate(CollaborativeFilter filter);
	
	public int[] getBaseMatrix(CollaborativeFilter filter,String key);
	
	public Set<RecommandInfo> recommand(CollaborativeFilter filter);
}
