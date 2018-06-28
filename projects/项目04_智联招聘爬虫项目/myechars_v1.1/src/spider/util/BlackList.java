package spider.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spider.node.UrlNode;

public class BlackList {
	private String fileName;
	private Set<String> result = new HashSet<String>();
	
	public BlackList(String fileName){
		this.fileName = fileName;
	}
	
	public BlackList(String fileName,List<Map<Object,String[]>> list){
		this.fileName = fileName;
		for (Map<Object,String[]> map : list){
			String[] values = map.get("compy");
			String[] url = map.get(UrlNode.URL);
			result.add(values[0]+" "+url[0]);
		}
	}
	public void saveToFile() throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName));
		for (String v : result){
			bw.write(v);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	public Map<String,String[]> getBlackList() throws IOException{
		Map<String,String[]> values = new HashMap<String,String[]>();
		BufferedReader br = new BufferedReader(new FileReader(this.fileName));
		String line = br.readLine();
		while(line != null){
			String[] v = line.split(" ");
			values.put(v[0],v);
			line = br.readLine();
		}
		return values;
	}
}
