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
 * ���ļ�Դ��ȡ������Ϣ��ʵ���࣬����ʹ���ض����ļ���ʽ���д�������Ҫ������Ʒ������Ϣ�ṹ������ ��һ��Ϊ�û��� �ڶ���Ϊ��Ʒ�����ָ�����������ָ��
 * Ĭ��Ϊ�ո����ָ�����µķָ�����Ҫ�޸�split����
 * @author ����
 *
 */
public class MatrixFromFile extends MatrixSource{
	private File file;//���ڶ�ȡ���ļ�Դ
	private String split;//�ָ�������
	private Set<String> itemSet = new TreeSet<String>();//��Դ�л�ȡ�Ĳ��ظ���Ʒ��Ϣ
	private Set<String> userSet = new TreeSet<String>();//��Դ�л�ȡ�Ĳ��ظ��û���Ϣ
	private List<InfoBean> list = new ArrayList<InfoBean>();//ȫ���Ĺ����¼����
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
	 * ��Դ�л�ȡȫ����������Ϣ
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
