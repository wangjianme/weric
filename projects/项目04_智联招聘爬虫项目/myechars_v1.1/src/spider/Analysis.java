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
 * 文件解析类，负责对抓取的信息文件进行解析,解析文件时需要提供一个LineProcessor指定单行解析的方式。默认提供为ZhiLianProcessor，只针对智联招聘数据进行单行解析
 * 
 * @author 白宇
 *
 */
public class Analysis {
	private String fileName = "";// 解析的文件名
	private LineProcessor processor;// 单行解析器
	private List<Map<Object, String[]>> fileList = new ArrayList<Map<Object, String[]>>();// 解析后的存储集合

	public List<Map<Object, String[]>> getFileList() {
		return fileList;
	}

	private Map<Object, String[]> maxLine;// 某列数据max行数据集合,在使用过max方法后生效
	private Map<Object, String[]> minLine;// 某列数据min行数据集合,在使用过min方法后生效

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
	 * 文件解析类，根据制定的LineProcessor实现类进行解析
	 * 
	 * @throws IOException
	 *             当文件解析过程中出现异常
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
				keys[i] = values[0];// 当前key
				value[i] = values[1];// 当前value，不进行分割
				i++;
			}
			fileList.add(processor.processLine(keys, value));
			line = br.readLine();
		}
		fr.close();
		br.close();
	}

	/**
	 * 求数值列平均值方法
	 * 
	 * @param key
	 *            列标示
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param fileList
	 *            需要进行求值的目标集合
	 * @param exceptzero
	 *            是否对0值列进行例外统计 true不统计0值列
	 * @return 求得的平均值,结果小数点后保留2位
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
	 * 求数值列平均值方法,默认使用全部数据集
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param exceptzero
	 *            是否对0值列进行例外统计 true不统计0值列
	 * @return 求得的平均值,结果小数点后保留2位
	 */
	public float avg(Object key, int index, boolean exceptzero) {
		return this.avg(key, index, this.fileList, exceptzero);
	}

	/**
	 * 求数值列平均值方法，默认不带0值统计
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param fileList
	 *            需要进行求值的目标集合
	 * @return 求得的平均值,结果小数点后保留2位
	 */
	public float avg(Object key, int index, List<Map<Object, String[]>> fileList) {
		return this.avg(key, index, fileList, true);
	}

	/**
	 * 求数值列平均值方法，默认不带0值统计全部数据集
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @return 求得的平均值,结果小数点后保留2位
	 */
	public float avg(Object key, int index) {
		return this.avg(key, index, this.fileList, true);
	}

	/**
	 * 求数值列最大值方法,默认使用全部数据集，使用该方法后,maxLine属性生效
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param fileList
	 *            需要统计的数据集合
	 * @return 最大值数值
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
	 * 求数值列最大值方法,默认使用全部数据集，使用该方法后,maxLine属性生效
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @return 最大值数值
	 */
	public float max(Object key, int index) {
		return this.max(key, index, this.fileList);
	}

	/**
	 * 求数值列最小值方法，使用该方法后,minLine属性生效
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param fileList
	 *            需要统计的数据集合
	 * @return 最小值数值
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
	 * 求数值列最小值方法，默认使用全部数据集,使用该方法后,minLine属性生效
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @return 最小值数值
	 */
	public float min(Object key, int index) {
		return this.min(key, index, this.fileList);
	}

	/**
	 * 计数方法，支持正则匹配或范围查找,目前只支持大于或小于，不支持区间,如果是区间统计可先执行findListByInterval找到区间数据再进行统计数据结果
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param expre
	 *            表达式支持正则,或形如<5000,>1000等简单半区间判断
	 * @param fileList
	 *            需要统计的数据集合
	 * @param exceptzero
	 *            是否对0值列进行例外统计 true不统计0值列
	 * @return 统计计数结果
	 */
	public int count(Object key, int index, String expre, List<Map<Object, String[]>> fileList, boolean exceptzero) {
		int count = 0;
		String mark = expre.substring(0, 1);// 截取起始字符
		char c = mark.charAt(0);
		if (c < 65) {// 判断为< 或>号
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
	 * 计数方法，支持正则匹配或范围查找,默认为不记录0数值列
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param expre
	 *            表达式支持正则,或形如<5000,>1000等简单半区间判断
	 * @param fileList
	 *            需要统计的数据集合
	 * @return 统计计数结果
	 */
	public int count(Object key, int index, String expre, List<Map<Object, String[]>> fileList) {
		return this.count(key, index, expre, fileList, true);
	}

	/**
	 * 计数方法，支持正则匹配或范围查找,默认为使用全部数据集
	 * 
	 * @param key
	 *            key 列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param expre
	 *            表达式支持正则,或形如<5000,>1000等简单半区间判断
	 * @param fileList
	 *            需要统计的数据集合
	 * @return 统计计数结果
	 */
	public int count(Object key, int index, String expre, boolean exceptzero) {
		return this.count(key, index, expre, this.fileList, exceptzero);
	}

	/**
	 * 计数方法，支持正则匹配或范围查找,默认为使用全部数据集,并且不考虑0值统计
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param expre
	 *            表达式支持正则,或形如<5000,>1000等简单半区间判断
	 * @return 统计计数结果
	 */
	public int count(Object key, int index, String expre) {
		return this.count(key, index, expre, this.fileList, true);
	}

	public List<Map<Object, String[]>> findListByInterval(Object key, int index, String condition) {
		return this.findListByInterval(key, index, condition, this.fileList);
	}

	/**
	 * 支持数字区间的数据查找
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)
	 * @param condition
	 *            区间条件,比如查找3000到5000之前为3000-5000,如果查找数值列中包含为数字形式数据则为需根据LineProcessor解析时的情况进行操作
	 * @param fileList
	 *            进行解析的目标集合
	 * @return 符合数值据区间的数据
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
	 * 支持数字区间的数据查找,默认使用全部数据
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)，注:如果指定为index>=0的值则只查找指定的索引列值，如果指定为-1,则当前标示全部数据均进行查找
	 * @param condition
	 *            区间条件,比如查找3000到5000之前为3000-5000,如果查找数值列中包含为数字形式数据则为需根据LineProcessor解析时的情况进行操作
	 * @return 符合数值据区间的数据
	 */
	public List<Map<Object, String[]>> findListByExpression(Object key, int index, String condition) {
		return this.findListByExpression(key, index, condition, this.fileList);
	}

	/**
	 * 支持正则表达式的查找方法
	 * 
	 * @param key
	 *            列标示，构建Node时指定
	 * @param index
	 *            列值索引(针对多值列，如果单值列则为永远为0)，注:如果指定为index>=0的值则只查找指定的索引列值，如果指定为-1,则当前标示全部数据均进行查找
	 * @param condition
	 *            正则表达式
	 * @param fileList
	 *            进行解析的目标集合
	 * @return 符合结果的区间的数据
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
	 * 从全部数据中，查找某列的全部数据值，如需找部分数据则扩展该方法的重载
	 * 
	 * @param key
	 *            列标示
	 * @return 指定列的全部数据值
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
	 * 对某列值进行分类类别计数统计
	 * 
	 * @param key
	 *            列标示
	 * @param index
	 *            列索引(针对多值列，如果单值列则为永远为0)，注:如果指定为index>=0的值则只针对指定的索引列值进行计数统计，如果指定为-1,则当前标示全部数据值均进行分类统计计数
	 * @param fileList
	 *            进行统计的原始集合
	 * @return 列类别统计计数结果
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
	 * 对某列值进行分类类别计数统计,默认使用全部数据
	 * 
	 * @param key
	 *            列标示
	 * @param index
	 *            列索引(针对多值列，如果单值列则为永远为0)，注:如果指定为index>=0的值则只针对指定的索引列值进行计数统计，如果指定为-1,则当前标示全部数据值均进行分类统计计数
	 * @return 列类别统计计数结果
	 */
	public Map<String, Integer> countInClass(String key, int index) {
		return this.countInClass(key, index, this.fileList);
	}

	public List<Map<Object, String[]>> getMap(Object key, int index, Object valueName) {
		return this.getMap(key, index, valueName, this.fileList);
	}

	/**
	 * 指定列定值查找方法,可以使用findListByExpression方法替换
	 * 
	 * @param key
	 *            列标示
	 * @param index
	 *            列索引
	 * @param valueName
	 *            列值
	 * @param fileList
	 *            目标集合
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
		String locN = "北京";
		String jobN = "java";// 725软件行业 销售行业 服务行业 人力资源
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
		// "五险一金");
		// System.out.println(list.size());
		List<Map<Object, String[]>> list = an.findListByExpression("compy", -1, "北京可酷科技有限公司");
		System.out.println(list.size());
		// BlackList bla = new
		// BlackList("c://"+folder+"///black/black_"+locN+"_"+jobN+".txt",list);
		// bla.saveToFile();
		// List<Map<Object,String[]>> list = an.findListByExpression("message", -1,
		// "岗前|实训");
		// for (Map<Object,String[]> map : list){
		// String[] url = map.get(UrlNode.URL);
		// System.out.println(url[0]);
		// }
		// List<Map<Object,String[]>> listx = an.getMap("compy", 0, "北京可酷科技有限公司");
		// System.out.println(listx.size());
		// List<Map<Object,String[]>> list2 = an.findListByExpression("message", -1,
		// "岗前|实训", list1);
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
		// List<Map<Object,String[]>> list = an.findListByExpression("xueli", 0, "本科");
		// //统计本科中不要求工作经验的数据
		// List<Map<Object,String[]>> list1 = an.findListByExpression("workExp", 0,
		// "^0$", list);
		// for (Map<Object,String[]> v : list1){
		// String[] values = v.get("compy");
		// String[] pays = v.get("pay");
		// String[] wordExp = v.get("workExp");
		// System.out.println(values[0]+"--"+pays[0]+"--"+pays[1]+":"+wordExp[0]+"--"+wordExp[1]);
		// }
		// //统计本科工资上限平均工资
		// float money = an.avg("pay", 1, list);
		// System.out.println(money);
		// //统计本科工资下限平均工资
		// float money1 = an.avg("pay", 0, list);
		// System.out.println(money1);
		// //统计学历分类数据
		// Map<String,Integer> map = an.countInClass("xueli",0);
		// Set<String> keySet = map.keySet();
		// for (String key : keySet){
		// Integer in = map.get(key);
		// System.out.println(key+":"+in);
		// }
		// ------------------------------------------------------
		// float minLower = an.min("pay", 0);
		// float maxLower = an.max("pay", 0);
		// System.out.println("最低工资下限："+minLower);
		// System.out.println("最低工资上限："+maxLower);
		// float minUpper = an.min("pay", 1);
		// float maxUpper = an.max("pay", 1);
		// System.out.println("最高工资下限："+minUpper);
		// System.out.println("最高工资上限："+maxUpper);
		// float avgLower = an.avg("pay", 0);
		// float avgUpper = an.avg("pay", 1);
		// System.out.println("最低工资平均："+avgLower);
		// System.out.println("最高工资平均："+avgUpper);
		// Map<Object,String[]> maxLine = an.getMaxLine();
		// Map<Object,String[]> minLine = an.getMinLine();
		// System.out.println("最高工资公司:"+maxLine.get("compy")[0]+"
		// 工作经验要求:"+maxLine.get("workExp")[0]+"-"+maxLine.get("workExp")[1]);
		// System.out.println("最低工资公司:"+minLine.get("compy")[0]+"
		// 工作经验要求:"+minLine.get("workExp")[0]+"-"+minLine.get("workExp")[1]);
		// int count = an.count("pay", 0, "<10000",true);
		// System.out.println("工资区间小于10000的有"+count);
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
		// System.out.println("工资在0-10K之间共"+map1.size()+"家 平均工作经验:"+v1+"-"+v2);
		// float v3 = an.avg("workExp", 0, map2,false);
		// float v4 = an.avg("workExp", 1, map2,false);
		// System.out.println("工资在10K-20K之间共"+map2.size()+"家 的平均工作经验:"+v3+"-"+v4);
		// float v5 = an.avg("workExp", 0,map3,false);
		// float v6 = an.avg("workExp", 1,map3,false);
		// System.out.println("工资在20K-30K之间共"+map3.size()+"家 的平均工作经验:"+v5+"-"+v6);
		// float v7 = an.avg("workExp", 0,map4,false);
		// float v8 = an.avg("workExp", 1,map4,false);
		// System.out.println("工资在30K-50K之间共"+map4.size()+"家 的平均工作经验:"+v7+"-"+v8);
		// float v9 = an.avg("workExp", 0,map5,false);
		// float v10 = an.avg("workExp", 1,map5,false);
		// System.out.println("工资在50K以上的共"+map5.size()+"家 平均工作经验:"+v9+"-"+v10);
		// int count1 = an.count("workExp", 0, "^0$",false);
		// System.out.println("其中不需要考虑工作经验的:"+count1+"家");
		// float v11 = an.avg("pay",0,an.findListByInterval("workExp", 0, "0-0"));
		// float v12 = an.avg("pay",1,an.findListByInterval("workExp", 0, "0-0"));
		// System.out.println("平均工资:"+v11+"-"+v12);
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
