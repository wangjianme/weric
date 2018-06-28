package spider.util.collaborative;

import java.util.Set;

import spider.util.collaborative.caculation.impl.ItemCFKNN;
import spider.util.collaborative.matrix.MatrixSource;
import spider.util.collaborative.matrix.impl.MatrixFromFile;

public class Test {

	public static void main(String[] args){
		MatrixSource dataSource = new MatrixFromFile("c://测试数据.txt");//将文件数据转换成用户和商品得矩阵
		CollaborativeFilter file = new CollaborativeFilter(dataSource,new ItemCFKNN());//计算商品和商品得之间相似度矩阵得
		Set<RecommandInfo> set = file.recommand("user1");//给用户推荐商品
		for (RecommandInfo v : set){
			System.out.print(v);
		}
		
	}
}
