package spider;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import spider.bean.InfoContainer;
import spider.filter.ExceptFilter;
import spider.filter.KeyWordFilter;
import spider.filter.SpaceAdnEnterFilter;
import spider.filter.SplitFilter;
import spider.node.InfoNode;
import spider.node.Node;
import spider.node.RootNode;
import spider.node.UrlNode;
import spider.util.BlackList;

public class SpiderRunner {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String locN = "����";
		String jobN = "java";
		String folder = "zhiliantest";
		FileOperator operator = new FileOperator(locN,jobN);
		operator.setRoot("C");
		operator.setFolder(folder);
		operator.create();
		String loc = URLEncoder.encode(locN, "UTF-8");
		String job = URLEncoder.encode(jobN, "UTF-8");
//		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&jl="+loc+"&isadv=0";//����������ҵ
//		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=4010200&jl="+loc+"&isadv=0"; //������ҵ
//	    String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=5005000&jl="+loc+"&isadv=0";//������ҵ
//	    String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=5002000&jl="+loc+"&isadv=0";//������Դ
//		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=2071000&jl="+loc+"&isadv=0";
		String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=" + loc + "&kw=" + job + "&sm=0&isadv=0";
		//            https://sou.zhaopin.com/jobs/searchresult.ashx?in=160400%3B160000&jl=%E5%8C%97%E4%BA%AC&sm=0
		//			  https://sou.zhaopin.com/jobs/searchresult.ashx?in=160400&jl=%E5%8C%97%E4%BA%AC&p=1&isadv=0
		//            https://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&in=160400&jl=%E5%8C%97%E4%BA%AC&p=1&isadv=0
		//            https://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&jl=%E5%8C%97%E4%BA%AC&p=1&isadv=0
		Node node1 = new RootNode(url);//��·��
		Node node2 = new InfoNode("��<em>([\\d]+)");//ͳ�Ƶ�ǰ���ж�����Ƹ��Ϣ
		Node node3 = new UrlNode(";so=[\\d]+[\"][\\s+]href=\"(http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&*=]*))+\"","infoUrl");//��Ƹ��Ϣ����ҳURL��ַ
		Node node4 = new InfoNode("�������飺</span><strong>(.*?)<","workExp");//��������
		Node node5 = new InfoNode("Str_CompName[\\s]*=[\\s]*\"([\\s\\S]*?)\"","compy").addFilter(new SpaceAdnEnterFilter());//��˾����
		Node node6 = new InfoNode("ְλ��н��</span><strong>((.*?)(Ԫ/��)|(����))","pay");//ְλ��н
		//������˾��Ƹ��Ϣ���飬����ȡ�ؼ��ʱ�ǩ"<div class="tab-cont-box">
		Node node7 = new InfoNode("<div class=\"tab-cont-box\">([\\s\\S]*)<button id=\"applyVacButton1\"");
		//��ȡ��˾��Ƹ��Ϣ���飬��node7���ݻ����ϲ���html��ǩ�е���Ϣ,���пո�Ϊ�������,����ȥ���˿ո�ͻس���,�䲻��Ҷ�ڵ㣬����ǰ��Ϣ��message��д���ļ�
		Node node8 = new InfoNode(">([\\s\\S]*?)<","message",true).addFilter(new ExceptFilter("(^[\\s]*$)")).addFilter(new SpaceAdnEnterFilter());
		//��ȡ��˾�����ؼ��ʣ���node8���ݻ�������ȡ��Ƹ�����е�ȫ��Ӣ�Ĺؼ���,��������Ϊ�������
		Node node9 = new InfoNode("(\\w{2,})","keyWord").addFilter(new KeyWordFilter()).addFilter(new ExceptFilter("^[0-9]+$"));
		Node node11 = new InfoNode("<li><span>�����ص㣺</span><strong><a[\\s\\S]*?>([\\s\\S]*?)</strong></li>","city").addFilter(new SplitFilter());
		Node node12 = new InfoNode("<li><span>���ѧ����</span><strong>([\\s\\S]*?)</strong></li>","xueli");
		Node node13 = new InfoNode("<li><span>ְλ���</span><strong><a target=\"_blank\" href=\"[\\s\\S]+?/\">([\\s\\S]+?)</a>","work");
		Node node14 = new InfoNode("<div style=\"width:683px;\" class=\"welfare-tab-box\">([\\s\\S]*?)</div>");
		Node node15 = new InfoNode("<span>([\\s\\S]*?)</span>","fuli");
		//�ڵ��ϵ������ע�����˳��,���Ӽ���ϵ
		node1.addChildren(node2);
		node1.addChildren(node3);
		node3.addChildren(node4);
		node3.addChildren(node5); 
		node3.addChildren(node6);
		node3.addChildren(node7);
		node3.addChildren(node11);
		node3.addChildren(node12);
		node3.addChildren(node13);
		node14.addChildren(node15);
		node3.addChildren(node14);
		node7.addChildren(node8);
		node8.addChildren(node9);
		//���з�ҳ����
		for (int i = 1;i <= 1;i++){
			String newurl = url + "&p=" + i;
			node1.setExp(newurl);
			List<InfoContainer> result = node1.process();
			operator.writeFile(result, new String[]{"compy","pay","workExp","keyWord",UrlNode.URL,"xueli","city","message","work","fuli"});
		}
		operator.flush();//�洢����
		//ETL�м����󣬴���д���н��
		ContentChecker checker = new ContentChecker(){
			public String checkLine(String[] values,int[] requestIndex) {
				String writeLine = "";
				for (int index : requestIndex){
					String[] result = values[index].split("\\&\\^");
					if (result.length < 2){
						return null;
					}
					writeLine += values[index]+" ";
				}
				return writeLine;
			}
		};
		operator.ETL(10, new int[]{0,4,7},"C://"+folder+"//compyinfo_"+locN+"_"+jobN+".txt",checker);
		operator.ETL(10, new int[]{0,1,2,3,5,6,4,7,8,9},null,checker);
	}
}
