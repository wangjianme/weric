package spider.util.collaborative;

import java.util.Set;

import spider.util.collaborative.caculation.impl.ItemCFKNN;
import spider.util.collaborative.matrix.MatrixSource;
import spider.util.collaborative.matrix.impl.MatrixFromFile;

public class Test {

	public static void main(String[] args){
		MatrixSource dataSource = new MatrixFromFile("c://��������.txt");//���ļ�����ת�����û�����Ʒ�þ���
		CollaborativeFilter file = new CollaborativeFilter(dataSource,new ItemCFKNN());//������Ʒ����Ʒ��֮�����ƶȾ����
		Set<RecommandInfo> set = file.recommand("user1");//���û��Ƽ���Ʒ
		for (RecommandInfo v : set){
			System.out.print(v);
		}
		
	}
}
