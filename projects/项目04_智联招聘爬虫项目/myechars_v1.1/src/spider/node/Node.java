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
 * @author ����
 * ���ڽ�������ƥ��Ľڵ���,����Ҫ���н�����ҳ��ת���ɽڵ���й���
 */
public abstract class Node {
	public void setExp(String exp) {
		this.exp = exp;
	}
	/**
	 * �Ӽ��ڵ㼯��
	 */
	private List<Node> children = new ArrayList<Node>();
	/**
	 * ��ǰ�ڵ������Ľ�������ڴ��ݸ��Ӽ�������һ������
	 */
	private String message;
	/**
	 * �����ڵ����
	 */
	private Node parent;
	/**
	 * ��ǰ�ڵ����ʹ�õ�������ʽ
	 */
	protected String exp;
	/**
	 * ��ǰ�ڵ㶨��Ĵ洢�ؼ��ʣ���������ָ����ָ����Ĺؼ��ʻ����ļ��洢��ʱ�����ڱ�ʾ��ǰֵ��ͬʱҲ�ǽ����Ժ��ȡ��ǰֵ��key
	 */
	private String key;
	/**
	 * ��ǰ�ڵ���д洢ʱ����Ϣ����
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
	 * ��ǰ�ڵ�Ĵ�����
	 * @return �����Ժ�Ľ��
	 */
	public abstract List<InfoContainer> process();
	/**
	 * 
	 * @param container �洢�ڵ㴦����Ϣ������
	 * @return �Ӽ�������
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
	 * ����ִ��URL���ʵķ��������ǲ�����н�����ֻ�Ѵ��������з��� 
	 * @param urlInfo ���з��ʵ�URL��ַ
	 * @return ��ǰURL���ʽ��
	 */
	protected String executeHttp(String urlInfo){
		HttpClient httpClient = new DefaultHttpClient();
		String result = null;
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		System.out.println("��ȡ����=====>url=" + urlInfo);
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
