package spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �ļ������࣬�����ץȡ����Ϣ�ļ����н���,�����ļ�ʱ��Ҫ�ṩһ��LineProcessorָ�����н����ķ�ʽ��Ĭ���ṩΪZhiLianProcessor��ֻ���������Ƹ���ݽ��е��н���
 * 
 * @author ����
 *
 */
public class Analysis {
	private String fileName = "";// �������ļ���
	private LineProcessor processor;// ���н�����
	private List<Map<Object, String[]>> fileList = new ArrayList<Map<Object, String[]>>();// ������Ĵ洢����

	public List<Map<Object, String[]>> getFileList() {
		return fileList;
	}

	private Map<Object, String[]> maxLine;// ĳ������max�����ݼ���,��ʹ�ù�max��������Ч
	private Map<Object, String[]> minLine;// ĳ������min�����ݼ���,��ʹ�ù�min��������Ч

	public Map<Object, String[]> getMaxLine() {
		return maxLine;
	}

	public Map<Object, String[]> getMinLine() {
		return minLine;
	}

	public Analysis(String fileName, LineProcessor processor) {
		this.fileName = fileName;
		this.processor = processor;
		try {
			this.processFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Analysis(String fileName) {
		this.fileName = fileName;
		try {
			this.processFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ļ������࣬�����ƶ���LineProcessorʵ������н���
	 * 
	 * @throws IOException
	 *             ���ļ����������г����쳣
	 */
	public void processFile() throws IOException {
		FileReader fr = new FileReader(new File(fileName));
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (line != null) {
			String[] value = line.split(" ");
			String[] keys = new String[value.length];
			int i = 0;
			for (String v : value) {
				String[] values = v.split("\\&\\^");
				keys[i] = values[0];// ��ǰkey
				value[i] = values[1];// ��ǰvalue�������зָ�
				i++;
			}
			fileList.add(processor.processLine(keys, value));
			line = br.readLine();
		}
		fr.close();
		br.close();
	}

	/**
	 * ����ֵ��ƽ��ֵ����
	 * 
	 * @param key
	 *            �б�ʾ
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param fileList
	 *            ��Ҫ������ֵ��Ŀ�꼯��
	 * @param exceptzero
	 *            �Ƿ��0ֵ�н�������ͳ�� true��ͳ��0ֵ��
	 * @return ��õ�ƽ��ֵ,���С�������2λ
	 */
	public float avg(Object key, int index, List<Map<Object, String[]>> fileList, boolean exceptzero) {
		float sum = 0;
		int count = 0;
		float avg = 0;
		DecimalFormat df = new DecimalFormat("#.00");
		if (fileList.size() > 0) {
			for (Map<Object, String[]> map : fileList) {
				String[] values = map.get(key);
				if (exceptzero) {
					if (!values[index].equals("0")) {
						sum += Float.parseFloat(values[index]);
						count++;
					}
				} else {
					sum += Float.parseFloat(values[index]);
					count++;
				}

			}
			avg = sum / count;
		}
		return Float.parseFloat(df.format(avg));
	}

	/**
	 * ����ֵ��ƽ��ֵ����,Ĭ��ʹ��ȫ�����ݼ�
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param exceptzero
	 *            �Ƿ��0ֵ�н�������ͳ�� true��ͳ��0ֵ��
	 * @return ��õ�ƽ��ֵ,���С�������2λ
	 */
	public float avg(Object key, int index, boolean exceptzero) {
		return this.avg(key, index, this.fileList, exceptzero);
	}

	/**
	 * ����ֵ��ƽ��ֵ������Ĭ�ϲ���0ֵͳ��
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param fileList
	 *            ��Ҫ������ֵ��Ŀ�꼯��
	 * @return ��õ�ƽ��ֵ,���С�������2λ
	 */
	public float avg(Object key, int index, List<Map<Object, String[]>> fileList) {
		return this.avg(key, index, fileList, true);
	}

	/**
	 * ����ֵ��ƽ��ֵ������Ĭ�ϲ���0ֵͳ��ȫ�����ݼ�
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @return ��õ�ƽ��ֵ,���С�������2λ
	 */
	public float avg(Object key, int index) {
		return this.avg(key, index, this.fileList, true);
	}

	/**
	 * ����ֵ�����ֵ����,Ĭ��ʹ��ȫ�����ݼ���ʹ�ø÷�����,maxLine������Ч
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param fileList
	 *            ��Ҫͳ�Ƶ����ݼ���
	 * @return ���ֵ��ֵ
	 */
	public float max(Object key, int index, List<Map<Object, String[]>> fileList) {
		float max = Float.MIN_VALUE;
		for (Map<Object, String[]> map : fileList) {
			String[] values = map.get(key);
			if (!values[index].equals("0") && Float.parseFloat(values[index]) > max) {
				max = Float.parseFloat(values[index]);
				maxLine = map;
			}
		}
		return max;
	}

	/**
	 * ����ֵ�����ֵ����,Ĭ��ʹ��ȫ�����ݼ���ʹ�ø÷�����,maxLine������Ч
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @return ���ֵ��ֵ
	 */
	public float max(Object key, int index) {
		return this.max(key, index, this.fileList);
	}

	/**
	 * ����ֵ����Сֵ������ʹ�ø÷�����,minLine������Ч
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param fileList
	 *            ��Ҫͳ�Ƶ����ݼ���
	 * @return ��Сֵ��ֵ
	 */
	public float min(Object key, int index, List<Map<Object, String[]>> fileList) {
		float min = Float.MAX_VALUE;
		for (Map<Object, String[]> map : this.fileList) {
			String[] values = map.get(key);
			if (!values[index].equals("0") && Float.parseFloat(values[index]) < min) {
				min = Float.parseFloat(values[index]);
				minLine = map;
			}
		}
		return min;
	}

	/**
	 * ����ֵ����Сֵ������Ĭ��ʹ��ȫ�����ݼ�,ʹ�ø÷�����,minLine������Ч
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @return ��Сֵ��ֵ
	 */
	public float min(Object key, int index) {
		return this.min(key, index, this.fileList);
	}

	/**
	 * ����������֧������ƥ���Χ����,Ŀǰֻ֧�ִ��ڻ�С�ڣ���֧������,���������ͳ�ƿ���ִ��findListByInterval�ҵ����������ٽ���ͳ�����ݽ��
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param expre
	 *            ���ʽ֧������,������<5000,>1000�ȼ򵥰������ж�
	 * @param fileList
	 *            ��Ҫͳ�Ƶ����ݼ���
	 * @param exceptzero
	 *            �Ƿ��0ֵ�н�������ͳ�� true��ͳ��0ֵ��
	 * @return ͳ�Ƽ������
	 */
	public int count(Object key, int index, String expre, List<Map<Object, String[]>> fileList, boolean exceptzero) {
		int count = 0;
		String mark = expre.substring(0, 1);// ��ȡ��ʼ�ַ�
		char c = mark.charAt(0);
		if (c < 65) {// �ж�Ϊ< ��>��
			String value = expre.substring(1);
			switch (c) {
			case 62:// < "5[0-9]{4}|^\\d{6}"
				expre = value.substring(0, 1) + "[0-9]{" + value.substring(1).length() + "}|^\\d{"
						+ (value.length() + 1) + "}";
				break;
			case 60:// > "[1-5][1-4][1-5][1-5][1-5]|^\\d{1,4}$";
				char[] numbers = value.toCharArray();
				StringBuffer expreBuffer = new StringBuffer("[1-");
				for (char num : numbers) {
					expreBuffer.append(num + "][0-");
				}
				expre = expreBuffer.substring(0, expreBuffer.length() - 3).toString();
				if (value.length() - 1 >= 1) {
					expre += "|^\\d{1," + (value.length() - 1) + "}$";
				}
				break;
			}
		}
		Pattern pat = Pattern.compile(expre, Pattern.CASE_INSENSITIVE);
		for (Map<Object, String[]> map : fileList) {
			String[] values = map.get(key);
			Matcher m1 = pat.matcher(values[index]);
			if (exceptzero) {
				if (!values[index].equals("0")) {
					if (m1.find()) {
						count++;
					}
				}
			} else {
				if (m1.find()) {
					count++;
				}
			}

		}
		return count;
	}

	/**
	 * ����������֧������ƥ���Χ����,Ĭ��Ϊ����¼0��ֵ��
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param expre
	 *            ���ʽ֧������,������<5000,>1000�ȼ򵥰������ж�
	 * @param fileList
	 *            ��Ҫͳ�Ƶ����ݼ���
	 * @return ͳ�Ƽ������
	 */
	public int count(Object key, int index, String expre, List<Map<Object, String[]>> fileList) {
		return this.count(key, index, expre, fileList, true);
	}

	/**
	 * ����������֧������ƥ���Χ����,Ĭ��Ϊʹ��ȫ�����ݼ�
	 * 
	 * @param key
	 *            key �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param expre
	 *            ���ʽ֧������,������<5000,>1000�ȼ򵥰������ж�
	 * @param fileList
	 *            ��Ҫͳ�Ƶ����ݼ���
	 * @return ͳ�Ƽ������
	 */
	public int count(Object key, int index, String expre, boolean exceptzero) {
		return this.count(key, index, expre, this.fileList, exceptzero);
	}

	/**
	 * ����������֧������ƥ���Χ����,Ĭ��Ϊʹ��ȫ�����ݼ�,���Ҳ�����0ֵͳ��
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param expre
	 *            ���ʽ֧������,������<5000,>1000�ȼ򵥰������ж�
	 * @return ͳ�Ƽ������
	 */
	public int count(Object key, int index, String expre) {
		return this.count(key, index, expre, this.fileList, true);
	}

	public List<Map<Object, String[]>> findListByInterval(Object key, int index, String condition) {
		return this.findListByInterval(key, index, condition, this.fileList);
	}

	/**
	 * ֧��������������ݲ���
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)
	 * @param condition
	 *            ��������,�������3000��5000֮ǰΪ3000-5000,���������ֵ���а���Ϊ������ʽ������Ϊ�����LineProcessor����ʱ��������в���
	 * @param fileList
	 *            ���н�����Ŀ�꼯��
	 * @return ������ֵ�����������
	 */
	public List<Map<Object, String[]>> findListByInterval(Object key, int index, String condition,
			List<Map<Object, String[]>> fileList) {
		List<Map<Object, String[]>> newList = new ArrayList<Map<Object, String[]>>();
		String[] conditionArray = condition.split("-");
		for (Map<Object, String[]> map : fileList) {
			String[] values = map.get(key);
			float value = Float.parseFloat(values[index]);
			if (conditionArray.length == 2) {
				if (value >= Float.parseFloat(conditionArray[0]) && value <= Float.parseFloat(conditionArray[1])) {
					newList.add(map);
				}
			} else {
				if (value >= Float.parseFloat(conditionArray[0])) {
					newList.add(map);
				}
			}
		}
		return newList;
	}

	/**
	 * ֧��������������ݲ���,Ĭ��ʹ��ȫ������
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)��ע:���ָ��Ϊindex>=0��ֵ��ֻ����ָ����������ֵ�����ָ��Ϊ-1,��ǰ��ʾȫ�����ݾ����в���
	 * @param condition
	 *            ��������,�������3000��5000֮ǰΪ3000-5000,���������ֵ���а���Ϊ������ʽ������Ϊ�����LineProcessor����ʱ��������в���
	 * @return ������ֵ�����������
	 */
	public List<Map<Object, String[]>> findListByExpression(Object key, int index, String condition) {
		return this.findListByExpression(key, index, condition, this.fileList);
	}

	/**
	 * ֧��������ʽ�Ĳ��ҷ���
	 * 
	 * @param key
	 *            �б�ʾ������Nodeʱָ��
	 * @param index
	 *            ��ֵ����(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)��ע:���ָ��Ϊindex>=0��ֵ��ֻ����ָ����������ֵ�����ָ��Ϊ-1,��ǰ��ʾȫ�����ݾ����в���
	 * @param condition
	 *            ������ʽ
	 * @param fileList
	 *            ���н�����Ŀ�꼯��
	 * @return ���Ͻ�������������
	 */
	public List<Map<Object, String[]>> findListByExpression(Object key, int index, String condition,
			List<Map<Object, String[]>> fileList) {
		List<Map<Object, String[]>> newList = new ArrayList<Map<Object, String[]>>();
		Pattern pat = Pattern.compile(condition, Pattern.CASE_INSENSITIVE);
		for (Map<Object, String[]> map : fileList) {
			String[] values = map.get(key);
			if (index > 0) {
				Matcher m1 = pat.matcher(values[index]);
				if (m1.find()) {
					newList.add(map);
				}
			} else {
				for (String v : values) {
					Matcher m1 = pat.matcher(v);
					if (m1.find()) {
						newList.add(map);
						break;
					}
				}
			}

		}
		return newList;
	}

	/**
	 * ��ȫ�������У�����ĳ�е�ȫ������ֵ�������Ҳ�����������չ�÷���������
	 * 
	 * @param key
	 *            �б�ʾ
	 * @return ָ���е�ȫ������ֵ
	 */
	public List<String[]> getValueArray(Object key) {
		List<String[]> result = new ArrayList<String[]>();
		for (Map<Object, String[]> map : this.fileList) {
			String[] values = map.get(key);
			result.add(values);
		}
		return result;
	}

	/**
	 * ��ĳ��ֵ���з���������ͳ��
	 * 
	 * @param key
	 *            �б�ʾ
	 * @param index
	 *            ������(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)��ע:���ָ��Ϊindex>=0��ֵ��ֻ���ָ����������ֵ���м���ͳ�ƣ����ָ��Ϊ-1,��ǰ��ʾȫ������ֵ�����з���ͳ�Ƽ���
	 * @param fileList
	 *            ����ͳ�Ƶ�ԭʼ����
	 * @return �����ͳ�Ƽ������
	 */
	public Map<String, Integer> countInClass(String key, int index, List<Map<Object, String[]>> fileList) {
		Map<String, Integer> rtvMap = new HashMap<String, Integer>();
		for (Map<Object, String[]> map : fileList) {
			String[] values = map.get(key);
			if (index > 0) {
				String value = values[index];
				Integer count = rtvMap.get(value);
				if (count == null) {
					count = 0;
				}
				rtvMap.put(value, ++count);
			} else {
				for (String v : values) {
					Integer count = rtvMap.get(v);
					if (count == null) {
						count = 0;
					}
					rtvMap.put(v, ++count);
				}
			}

		}
		return rtvMap;
	}

	/**
	 * ��ĳ��ֵ���з���������ͳ��,Ĭ��ʹ��ȫ������
	 * 
	 * @param key
	 *            �б�ʾ
	 * @param index
	 *            ������(��Զ�ֵ�У������ֵ����Ϊ��ԶΪ0)��ע:���ָ��Ϊindex>=0��ֵ��ֻ���ָ����������ֵ���м���ͳ�ƣ����ָ��Ϊ-1,��ǰ��ʾȫ������ֵ�����з���ͳ�Ƽ���
	 * @return �����ͳ�Ƽ������
	 */
	public Map<String, Integer> countInClass(String key, int index) {
		return this.countInClass(key, index, this.fileList);
	}

	public List<Map<Object, String[]>> getMap(Object key, int index, Object valueName) {
		return this.getMap(key, index, valueName, this.fileList);
	}

	/**
	 * ָ���ж�ֵ���ҷ���,����ʹ��findListByExpression�����滻
	 * 
	 * @param key
	 *            �б�ʾ
	 * @param index
	 *            ������
	 * @param valueName
	 *            ��ֵ
	 * @param fileList
	 *            Ŀ�꼯��
	 * @return
	 */
	public List<Map<Object, String[]>> getMap(Object key, int index, Object valueName,
			List<Map<Object, String[]>> fileList) {
		List<Map<Object, String[]>> list = new ArrayList<Map<Object, String[]>>();
		for (Map<Object, String[]> map : fileList) {
			String[] values = map.get(key);
			if (values[index].equals(valueName)) {
				list.add(map);
			}
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		String locN = "����";
		String jobN = "java";// 725�����ҵ ������ҵ ������ҵ ������Դ
		String folder = "zhilian";
		Analysis an = new Analysis("C://" + folder + "//result_" + locN + "_" + jobN + "_ETL.txt",
				new ZhiLianProcessor());
		// float max = an.max("pay", 1);
		// System.out.println(max);
		// List<Map<Object,String[]>> list1 = an.findListByInterval("workExp", 1,
		// "0-0");
		// List<Map<Object,String[]>> list2 = an.findListByInterval("workExp", 1,
		// "1-3");
		// List<Map<Object,String[]>> list3 = an.findListByInterval("workExp", 1,
		// "3-5");
		// List<Map<Object,String[]>> list4 = an.findListByInterval("workExp", 1,
		// "5-10");
		// List<Map<Object,String[]>> list5 = an.findListByInterval("workExp", 1, "10");
		// float f1 = an.avg("pay", 1, list1);
		// System.out.println(list1.size()+"-"+f1);
		// float f2 = an.avg("pay", 1, list2);
		// System.out.println(list2.size()+"-"+f2);
		// float f3 = an.avg("pay", 1, list3);
		// System.out.println(list3.size()+"-"+f3);
		// float f4 = an.avg("pay", 1, list4);
		// System.out.println(list4.size()+"-"+f4);
		// float f5 = an.avg("pay", 1, list5);
		// System.out.println(list5.size()+"-"+f5);
		// Map<String,Integer> map = an.countInClass("fuli", -1);
		// System.out.println(map);
		// List<Map<Object,String[]>> list = an.findListByExpression("fuli", -1,
		// "����һ��");
		// System.out.println(list.size());
		List<Map<Object, String[]>> list = an.findListByExpression("compy", -1, "�����ɿ�Ƽ����޹�˾");
		System.out.println(list.size());
		// BlackList bla = new
		// BlackList("c://"+folder+"///black/black_"+locN+"_"+jobN+".txt",list);
		// bla.saveToFile();
		// List<Map<Object,String[]>> list = an.findListByExpression("message", -1,
		// "��ǰ|ʵѵ");
		// for (Map<Object,String[]> map : list){
		// String[] url = map.get(UrlNode.URL);
		// System.out.println(url[0]);
		// }
		// List<Map<Object,String[]>> listx = an.getMap("compy", 0, "�����ɿ�Ƽ����޹�˾");
		// System.out.println(listx.size());
		// List<Map<Object,String[]>> list2 = an.findListByExpression("message", -1,
		// "��ǰ|ʵѵ", list1);
		// System.out.println(list2.size());
		// list = an.getFileList();
		// for (Map<Object,String[]> map : list){
		// String[] url = map.get("work");
		// System.out.println(url[0]);
		// }
		// List<Map<Object,String[]>> list = an.findListByExpression("city", 0, "-");
		// System.out.println(list.size());
		// for (Map<Object,String[]> v : list){
		// String[] values = v.get("compy");
		// String[] pays = v.get("pay");
		// String[] wordExp = v.get("workExp");
		// String[] city = v.get("city");
		// System.out.println(values[0]+"--"+city[0]);
		// }
		// Map<String,Integer> map = an.countInClass("city",0,list);
		// Set<String> keySet = map.keySet();
		// for (String key : keySet){
		// Integer in = map.get(key);
		// System.out.println(key+":"+in);
		// }
		// -----------------------------------------------------
		// List<Map<Object,String[]>> list = an.findListByExpression("xueli", 0, "����");
		// //ͳ�Ʊ����в�Ҫ�������������
		// List<Map<Object,String[]>> list1 = an.findListByExpression("workExp", 0,
		// "^0$", list);
		// for (Map<Object,String[]> v : list1){
		// String[] values = v.get("compy");
		// String[] pays = v.get("pay");
		// String[] wordExp = v.get("workExp");
		// System.out.println(values[0]+"--"+pays[0]+"--"+pays[1]+":"+wordExp[0]+"--"+wordExp[1]);
		// }
		// //ͳ�Ʊ��ƹ�������ƽ������
		// float money = an.avg("pay", 1, list);
		// System.out.println(money);
		// //ͳ�Ʊ��ƹ�������ƽ������
		// float money1 = an.avg("pay", 0, list);
		// System.out.println(money1);
		// //ͳ��ѧ����������
		// Map<String,Integer> map = an.countInClass("xueli",0);
		// Set<String> keySet = map.keySet();
		// for (String key : keySet){
		// Integer in = map.get(key);
		// System.out.println(key+":"+in);
		// }
		// ------------------------------------------------------
		// float minLower = an.min("pay", 0);
		// float maxLower = an.max("pay", 0);
		// System.out.println("��͹������ޣ�"+minLower);
		// System.out.println("��͹������ޣ�"+maxLower);
		// float minUpper = an.min("pay", 1);
		// float maxUpper = an.max("pay", 1);
		// System.out.println("��߹������ޣ�"+minUpper);
		// System.out.println("��߹������ޣ�"+maxUpper);
		// float avgLower = an.avg("pay", 0);
		// float avgUpper = an.avg("pay", 1);
		// System.out.println("��͹���ƽ����"+avgLower);
		// System.out.println("��߹���ƽ����"+avgUpper);
		// Map<Object,String[]> maxLine = an.getMaxLine();
		// Map<Object,String[]> minLine = an.getMinLine();
		// System.out.println("��߹��ʹ�˾:"+maxLine.get("compy")[0]+"
		// ��������Ҫ��:"+maxLine.get("workExp")[0]+"-"+maxLine.get("workExp")[1]);
		// System.out.println("��͹��ʹ�˾:"+minLine.get("compy")[0]+"
		// ��������Ҫ��:"+minLine.get("workExp")[0]+"-"+minLine.get("workExp")[1]);
		// int count = an.count("pay", 0, "<10000",true);
		// System.out.println("��������С��10000����"+count);
		// List<Map<Object,String[]>> map1 = an.findListByInterval("pay", 1, "0-10000");
		// List<Map<Object,String[]>> map2 = an.findListByInterval("pay", 1,
		// "10000-20000");
		// List<Map<Object,String[]>> map3 = an.findListByInterval("pay", 1,
		// "20000-30000");
		// List<Map<Object,String[]>> map4 = an.findListByInterval("pay", 1,
		// "30000-50000");
		// List<Map<Object,String[]>> map5 = an.findListByInterval("pay", 1, "50000");
		// float v1 = an.avg("workExp", 0,map1,false);
		// float v2 = an.avg("workExp", 1,map1,false);
		// System.out.println("������0-10K֮�乲"+map1.size()+"�� ƽ����������:"+v1+"-"+v2);
		// float v3 = an.avg("workExp", 0, map2,false);
		// float v4 = an.avg("workExp", 1, map2,false);
		// System.out.println("������10K-20K֮�乲"+map2.size()+"�� ��ƽ����������:"+v3+"-"+v4);
		// float v5 = an.avg("workExp", 0,map3,false);
		// float v6 = an.avg("workExp", 1,map3,false);
		// System.out.println("������20K-30K֮�乲"+map3.size()+"�� ��ƽ����������:"+v5+"-"+v6);
		// float v7 = an.avg("workExp", 0,map4,false);
		// float v8 = an.avg("workExp", 1,map4,false);
		// System.out.println("������30K-50K֮�乲"+map4.size()+"�� ��ƽ����������:"+v7+"-"+v8);
		// float v9 = an.avg("workExp", 0,map5,false);
		// float v10 = an.avg("workExp", 1,map5,false);
		// System.out.println("������50K���ϵĹ�"+map5.size()+"�� ƽ����������:"+v9+"-"+v10);
		// int count1 = an.count("workExp", 0, "^0$",false);
		// System.out.println("���в���Ҫ���ǹ��������:"+count1+"��");
		// float v11 = an.avg("pay",0,an.findListByInterval("workExp", 0, "0-0"));
		// float v12 = an.avg("pay",1,an.findListByInterval("workExp", 0, "0-0"));
		// System.out.println("ƽ������:"+v11+"-"+v12);
		// List<String[]> values = an.getValueArray("keyWord");
		// Map<String,Integer> keyMap = new HashMap<String,Integer>();
		// for (String[] keyWords : values){
		// for (String keyWord : keyWords){
		// Integer keyCount = keyMap.get(keyWord);
		// if (keyCount == null){
		// keyCount = 0;
		// }
		// keyMap.put(keyWord, ++keyCount);
		// }
		// }
		// Set<KeyBean> set = new TreeSet<KeyBean>();
		// Set<String> keySet = keyMap.keySet();
		// for (String key : keySet){
		// KeyBean bean = new KeyBean(key,keyMap.get(key));
		// set.add(bean);
		// }
		// System.out.println(set);
	}

}
