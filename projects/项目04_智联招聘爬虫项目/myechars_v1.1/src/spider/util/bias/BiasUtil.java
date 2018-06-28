package spider.util.bias;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BiasUtil {
	/**
	 * ģ��������ϴ����
	 * @param src ԭʼ�ļ�
	 * @param etl ��ϴ�Ժ���ļ�
	 * @param values ��Ҫ��������ϴ�ֶ�
	 */
	public static void ETL(String src,String etl,int[] values){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(etl)));
			BufferedReader br = new BufferedReader(new FileReader(new File(src)));
			String line = br.readLine();
			while(line != null){
				String[] lines = line.split(" ");
				StringBuffer sb = new StringBuffer();
				for (int i : values){
					sb.append(lines[i]+" ");
				}
				String s = sb.substring(0, sb.length()-1);
				bw.write(s);
				bw.newLine();
				line = br.readLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ģ���ļ���������
	 * @param dataNumber ��������
	 */
	public static void fileCreater(int dataNumber){
		try{
			FileWriter fw = new FileWriter("c://pre.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			String[][] values = {{"˧","��˧"},{"��","����"},{"��","��"},{"�Ͻ�","���Ͻ�"},{"��","����"}};
			for (int i = 0;i < dataNumber;i++){
				StringBuffer sb = new StringBuffer();
				for (String[] value : values){
					int random = (int)(Math.random()*10);
					int index = 0;
					if (random > 3){
						index = 1;
					}
					String v = value[index];
					sb.append(v+" ");
				}
				String s = sb.substring(0, sb.length()-1);
				bw.write(s);
				bw.newLine();
				bw.flush();
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * �������ݾ����ģ����
	 * @param file �������ݾ����ԭʼ�ļ�
	 * @param kindIndex �����ֶ�����
	 * @return
	 * @throws Exception
	 */
	public static Map<String,List<String[]>> dataClust(String file,int kindIndex) throws Exception{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		Map<String,List<String[]>> valueMap = new HashMap<String,List<String[]>>();
		while(line != null){
			String[] values = line.split(" ");
			List<String[]> value = valueMap.get(values[kindIndex]);
			if (value == null){
				value = new ArrayList<String[]>();
			}
			value.add(values);
			valueMap.put(values[kindIndex], value);
			line = br.readLine();
		}
		return valueMap;
	}
	/**
	 * �������
	 * @param value ����������ʵķ���
	 * @param base ����������ʵķ�ĸ
	 * @return
	 */
	public static float probability(int value,int base){
		float d = new Integer(value).floatValue();
		DecimalFormat df = new DecimalFormat("#.000");
		return Float.parseFloat(df.format(d / base));
	}
	/**
	 * ͳ����ĳ������µ�ĳ���������ֵĴ���
	 * @param key ��������
	 * @param kindList ������� 
	 * @return ͳ�Ƴ����Ľ��
	 */
	public static int countAtKindType(String key,List<String[]> kindList){
		int count = 0;
		for (String[] value : kindList){
			if (contains(key, value)){
				count++;
			}
		}
		return count;
	}
	/**
	 * �жϷ������Ƿ���������
	 * @param key
	 * @param baseArray
	 * @return
	 */
	public static boolean contains(String key,String[] baseArray){
		for (String v : baseArray){
			if (v.equals(key)){
				return true;
			}
		}
		return false;
	}
	/**
	 * ͳ����ȫ������µ�ĳ���������ֵĴ���
	 * @param key ��������
	 * @param dataMap ���������ݼ�
	 * @return
	 */
	public static int countAtData(String key,Map<String,List<String[]>> dataMap){
		Set<String> keySet = dataMap.keySet();
		int count = 0;
		for (String dataKey : keySet){
			count+=countAtKindType(key,dataMap.get(dataKey));
		}
		return count;
	}
	/**
	 * ��ڷ���
	 * @param features ��������
	 * @param kindType ���
	 * @param dataMap  ȫ�����ݼ�
	 * @return ĳ������������ض�����µĸ���
	 */
	public static float kindProbability(String[] features,String kindType,Map<String,List<String[]>> dataMap){
		int totalCount = 0;
		Set<String> keySet = dataMap.keySet();
		for (String dataKey : keySet){
			totalCount+=dataMap.get(dataKey).size();
		}
		int kindCount = dataMap.get(kindType).size();
		List<Float> featuresProbabilityList1 = new ArrayList<Float>();
		List<Float> featuresProbabilityList2 = new ArrayList<Float>();
		float pKind = BiasUtil.probability(dataMap.get(kindType).size(), totalCount);
		featuresProbabilityList1.add(pKind);
		for (String f : features){
			float p = BiasUtil.probability(BiasUtil.countAtKindType(f, dataMap.get(kindType)),kindCount);
			featuresProbabilityList1.add(p);
			int count = BiasUtil.countAtData(f,dataMap);//ĳ��������ȫ������µļ���
			float p1 = BiasUtil.probability(count, totalCount);
			featuresProbabilityList2.add(p1);
		}
		float pKind1 = 1;
		float pKind2 = 1;
		for (float f : featuresProbabilityList1){
			pKind1*=f;
		}
		for (float f : featuresProbabilityList2){
			pKind2*=f;
		}
		return pKind1/pKind2;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,List<String[]>> map = BiasUtil.dataClust("c://file.txt",4);
		float px = BiasUtil.kindProbability(new String[]{"��˧","����","��","���Ͻ�"}, "��",  map);
		float px1 = BiasUtil.kindProbability(new String[]{"��˧","����","��","���Ͻ�"}, "����",  map);
		System.out.println(px);
		System.out.println(px1);
	}
	
}
