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
		String locN = "北京";
		String jobN = "java";
		String folder = "zhiliantest";
		FileOperator operator = new FileOperator(locN,jobN);
		operator.setRoot("C");
		operator.setFolder(folder);
		operator.create();
		String loc = URLEncoder.encode(locN, "UTF-8");
		String job = URLEncoder.encode(jobN, "UTF-8");
//		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&jl="+loc+"&isadv=0";//计算机软件行业
//		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=4010200&jl="+loc+"&isadv=0"; //销售行业
//	    String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=5005000&jl="+loc+"&isadv=0";//服务行业
//	    String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=5002000&jl="+loc+"&isadv=0";//人力资源
//		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?bj=2071000&jl="+loc+"&isadv=0";
		String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=" + loc + "&kw=" + job + "&sm=0&isadv=0";
		//            https://sou.zhaopin.com/jobs/searchresult.ashx?in=160400%3B160000&jl=%E5%8C%97%E4%BA%AC&sm=0
		//			  https://sou.zhaopin.com/jobs/searchresult.ashx?in=160400&jl=%E5%8C%97%E4%BA%AC&p=1&isadv=0
		//            https://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&in=160400&jl=%E5%8C%97%E4%BA%AC&p=1&isadv=0
		//            https://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&jl=%E5%8C%97%E4%BA%AC&p=1&isadv=0
		Node node1 = new RootNode(url);//主路径
		Node node2 = new InfoNode("共<em>([\\d]+)");//统计当前共有多少招聘信息
		Node node3 = new UrlNode(";so=[\\d]+[\"][\\s+]href=\"(http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&*=]*))+\"","infoUrl");//招聘信息详情页URL地址
		Node node4 = new InfoNode("工作经验：</span><strong>(.*?)<","workExp");//工作经验
		Node node5 = new InfoNode("Str_CompName[\\s]*=[\\s]*\"([\\s\\S]*?)\"","compy").addFilter(new SpaceAdnEnterFilter());//公司名称
		Node node6 = new InfoNode("职位月薪：</span><strong>((.*?)(元/月)|(面议))","pay");//职位月薪
		//分析公司招聘信息详情，先提取关键词标签"<div class="tab-cont-box">
		Node node7 = new InfoNode("<div class=\"tab-cont-box\">([\\s\\S]*)<button id=\"applyVacButton1\"");
		//提取公司招聘信息详情，在node7数据基础上查找html标签中的信息,其中空格为例外情况,并且去除了空格和回车等,虽不是叶节点，但当前信息以message键写入文件
		Node node8 = new InfoNode(">([\\s\\S]*?)<","message",true).addFilter(new ExceptFilter("(^[\\s]*$)")).addFilter(new SpaceAdnEnterFilter());
		//提取公司技术关键词，在node8数据基础上提取招聘详情中的全部英文关键词,其中数字为例外情况
		Node node9 = new InfoNode("(\\w{2,})","keyWord").addFilter(new KeyWordFilter()).addFilter(new ExceptFilter("^[0-9]+$"));
		Node node11 = new InfoNode("<li><span>工作地点：</span><strong><a[\\s\\S]*?>([\\s\\S]*?)</strong></li>","city").addFilter(new SplitFilter());
		Node node12 = new InfoNode("<li><span>最低学历：</span><strong>([\\s\\S]*?)</strong></li>","xueli");
		Node node13 = new InfoNode("<li><span>职位类别：</span><strong><a target=\"_blank\" href=\"[\\s\\S]+?/\">([\\s\\S]+?)</a>","work");
		Node node14 = new InfoNode("<div style=\"width:683px;\" class=\"welfare-tab-box\">([\\s\\S]*?)</div>");
		Node node15 = new InfoNode("<span>([\\s\\S]*?)</span>","fuli");
		//节点关系串联，注意解析顺序,父子级关系
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
		//进行分页解析
		for (int i = 1;i <= 1;i++){
			String newurl = url + "&p=" + i;
			node1.setExp(newurl);
			List<InfoContainer> result = node1.process();
			operator.writeFile(result, new String[]{"compy","pay","workExp","keyWord",UrlNode.URL,"xueli","city","message","work","fuli"});
		}
		operator.flush();//存储数据
		//ETL行检查对象，处理写入行结果
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
