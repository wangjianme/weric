package spider;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import spider.bean.InfoBean;
import spider.bean.InfoContainer;
/**
 * 文件生成器，将爬取的数据转换成文件信息，也提供进行清洗的方法
 * @author Administrator
 *
 */
public class FileOperator {
	private BufferedWriter bw = null;
	private BufferedReader br = null;
	private String bakFileName = "";//备份文件名，用于存储不符合规格的数据信息
	private String root = "C";//存储文件的跟路径，默认为c盘
	private String folder = "";//存储文件的目录结构
	private String fileFolder = "";//文件最后进行存储的完成路径
	private String job;//搜索的工作-用于生成文件名的一部分 
	private String loc;//搜索的地区-用于生成文件名的一部分
	private String srcFileName = "";//原始文件名称
	private String ETLFileName = "";//清洗文件的文件名
	public String getRoot() {
		return root;
	}
	public String getFolder() {
		return folder;
	}
	public String getFileFolder() {
		return fileFolder;
	}
	public String getJob() {
		return job;
	}
	public String getLoc() {
		return loc;
	}
	public String getSrcFileName() {
		return srcFileName;
	}
	public String getETLFileName() {
		return ETLFileName;
	}
	private boolean append;
	
	public void setRoot(String root) {
		this.root = root;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public FileOperator(String job,String loc,boolean append){
		this(job,loc,"c","",append);
		
	}
	public FileOperator(String job,String loc,String root,String folder,boolean append){
		this.job = job;
		this.loc = loc;
		this.append = append;
		this.root = root;
		this.folder = folder;
	}
	public void create() throws IOException{
		this.create(true);
		
	}
	public void create(boolean isCreateNew) throws IOException{
		this.fileFolder = root+":/"+folder;
		this.srcFileName = fileFolder+"/result_"+ job + "_" + loc + ".txt";
		this.ETLFileName = fileFolder+"/result_"+ job + "_" + loc + "_ETL.txt";
		this.bakFileName = fileFolder+"/result_"+ job + "_" + loc + "_ETL_BAK.txt";
		File file = new File(fileFolder);
		if (!file.exists()){
			file.mkdirs();
		}
		if (isCreateNew){
			this.bw = new BufferedWriter(new FileWriter(srcFileName,append));
		}
		
		
	}
	public FileOperator(String job,String loc) throws IOException{
		this(job,loc,false);
	}
	public  void writeFile(List<InfoContainer> result,String[] keys) throws IOException{
		try {
			for (InfoContainer cons  :  result){
				StringBuffer values = new StringBuffer();
				for (String key : keys){
					List<InfoBean> list = cons.getMap().get(key);
					if (list != null){
						StringBuffer sb = new StringBuffer(key+"&^");
						for (InfoBean bean : list){
							if (list.size() == 1){
								sb.append(bean.getBeanValue());
								continue;
							}
							sb.append(bean.getBeanValue()+"-");
						}
						values.append(sb.toString()+" ");
					}
					
				}
				bw.write(values.toString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void flush() throws IOException{
		this.bw.flush();
	}
	public void close() throws IOException{
		if (this.br != null){
			this.br.close();
		}
		if (this.bw != null){
			this.bw.close();
		}
	}
	/**
	 * ETL文件写入方法
	 * @param checkLength 进行检测的行长度，即当前行数据应该符合多少个列的组合才可写入
	 * @param requestIndex 需要写入ETL文件的列索引
	 * @param fileName ETL文件名
	 * @param checker 行检查
	 * @throws IOException
	 */
	public void ETL(int checkLength,int[] requestIndex,String fileName,ContentChecker checker) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(srcFileName));
		String ETLFileName = this.ETLFileName;
		if (fileName != null){
			ETLFileName = fileName;
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(ETLFileName));
		BufferedWriter bakBw = new BufferedWriter(new FileWriter(bakFileName));
		String line = br.readLine();
		while(line != null){
			String[] values = line.split(" ");
			if (values.length == checkLength && !values[0].equals("")){
				String  writeLine = checker.checkLine(values,requestIndex);
				if (writeLine != null){
					bw.write(writeLine);
					bw.newLine();
				}
				line = br.readLine();
				continue;
			}
			if (!line.equals("")){
				bakBw.write(line);
				bakBw.newLine();
			}
			line = br.readLine();
		}
		bw.flush();
		bakBw.flush();
	}
	public void ETL(int checkLength,int[] requestIndex) throws IOException{
		this.ETL(checkLength, requestIndex, null,new ContentChecker());
	}
	public void ETL(int checkLength,int[] requestIndex,String fileName) throws IOException{
		this.ETL(checkLength, requestIndex, fileName,new ContentChecker());
	}
	
}
