package spider.util.collaborative.matrix.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import spider.util.collaborative.CollaborativeFilter;
import spider.util.collaborative.InfoBean;
import spider.util.collaborative.matrix.MatrixSource;
/**
 * 从文件源获取矩阵信息的实现类，可以使用特定的文件格式进行处理，这里要求是商品购买信息结构必须是 第一列为用户名 第二列为商品名，分隔符可以自由指定
 * 默认为空格，如果指定了新的分隔符需要修改split变量
 * @author 白宇
 *
 */
public class MatrixFromFile extends MatrixSource{
	private File file;//用于读取的文件源
	private String split;//分隔符变量
	private Set<String> itemSet = new TreeSet<String>();//从源中获取的不重复商品信息
	private Set<String> userSet = new TreeSet<String>();//从源中获取的不重复用户信息
	private List<InfoBean> list = new ArrayList<InfoBean>();//全部的购买记录集合
	private static Logger log = Logger.getLogger(CollaborativeFilter.class); 
	public MatrixFromFile(String fileName,String split){
		this( new File(fileName),split);
	}
	public MatrixFromFile(String fileName){
		this( new File(fileName)," ");
	}
	public MatrixFromFile(File file){
		this(file," ");
	}
	public MatrixFromFile(File file,String split){
		this.file = file;
		this.split = split;
	}
	/**
	 * 从源中获取全部的数据信息
	 */
	protected void initCollection(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.file));
			String line = br.readLine();
			while(line != null){
				String[] values = line.split(this.split);
				userSet.add(values[0]);
				itemSet.add(values[1]);
				InfoBean infoBean = new InfoBean(values[0],values[1]);
				list.add(infoBean);
				line = br.readLine();
			}
			log.info("initCollection finish !!! datas:"+list.size());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Collection<String> getItems() {
		return this.itemSet;
	}

	@Override
	protected Collection<String> getUsers() {
		return this.userSet;
	}

	@Override
	protected List<InfoBean> initInfoList() {
		return this.list;
	}
	

}
