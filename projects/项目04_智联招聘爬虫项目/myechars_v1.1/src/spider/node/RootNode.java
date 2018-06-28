package spider.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * 爬虫的主入口实现类，用于指定爬虫程序的初识入口位置
 * @author 白宇
 *
 */
public class RootNode extends Node{
	

	public RootNode(String exp, String key) {
		super(exp, key);
	}
	public RootNode(String exp) {
		super(exp, "");
	}
	@Override
	public List<InfoContainer> process() {
		List<InfoContainer> result = new ArrayList<InfoContainer>();
		String urlInfo = this.exp;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		System.out.println("爬取数据=====>url=" + urlInfo);
		HttpGet httpGet = new HttpGet(urlInfo);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int resStatu = httpResponse.getStatusLine().getStatusCode();
			if (resStatu == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				this.setMessage(EntityUtils.toString(entity));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		InfoContainer container = new InfoContainer();
		List<InfoContainer> result1 = this.processChildren(container);
		if (result1.size() != 0){
			result.addAll(result1);
		}
		result.add(container);
		return result;
	}

}
