package spider.node;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import spider.bean.InfoContainer;
/**
 * 
 * @author 白宇
 * 用于进行正则匹配的节点类,将需要进行解析的页面转换成节点进行关联
 */
public abstract class Node {
	public void setExp(String exp) {
		this.exp = exp;
	}
	/**
	 * 子集节点集合
	 */
	private List<Node> children = new ArrayList<Node>();
	/**
	 * 当前节点解析后的结果，用于传递给子集进行下一步解析
	 */
	private String message;
	/**
	 * 父级节点对象
	 */
	private Node parent;
	/**
	 * 当前节点解析使用的正则表达式
	 */
	protected String exp;
	/**
	 * 当前节点定义的存储关键词，可以自由指定，指定后的关键词会在文件存储的时候用于标示当前值，同时也是解析以后获取当前值的key
	 */
	private String key;
	/**
	 * 当前节点进行存储时的信息集合
	 */
	private InfoContainer container;
	
	public InfoContainer getContainer() {
		return container;
	}

	public void setContainer(InfoContainer container) {
		this.container = container;
	}

	public String getKey() {
		return key;
	}

	public Node(String exp,String key){
		this.exp = exp;
		this.key = key;
	}
	
	public boolean hasChildren(){
		return this.children.size() > 0;
	}
	
	public List<Node> getChildren() {
		return children;
	}

	public void addChildren(Node node) {
		node.setParent(this);
		this.children.add(node);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	/**
	 * 当前节点的处理方法
	 * @return 处理以后的结果
	 */
	public abstract List<InfoContainer> process();
	/**
	 * 
	 * @param container 存储节点处理信息的容器
	 * @return 子集处理结果
	 */
	protected List<InfoContainer> processChildren(InfoContainer container){
		List<InfoContainer> result = new ArrayList<InfoContainer>();
		if (this.hasChildren()){
			for (Node node : this.getChildren()){
				node.setContainer(container);
				List<InfoContainer> childrenResults = node.process();
				result.addAll(childrenResults);
			}
		}
		return result;
	}
	/**
	 * 可以执行URL访问的方法，但是不会进行解析，只把处理结果进行返回 
	 * @param urlInfo 进行访问的URL地址
	 * @return 当前URL访问结果
	 */
	protected String executeHttp(String urlInfo){
		HttpClient httpClient = new DefaultHttpClient();
		String result = null;
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		System.out.println("爬取数据=====>url=" + urlInfo);
		HttpGet httpGet = new HttpGet(urlInfo);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int resStatu = httpResponse.getStatusLine().getStatusCode();
			if (resStatu == HttpStatus.SC_OK) {
				HttpEntity entity  = httpResponse.getEntity();
				result = EntityUtils.toString(entity);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
