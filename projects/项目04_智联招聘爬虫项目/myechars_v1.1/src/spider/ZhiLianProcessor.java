package spider;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhiLianProcessor implements LineProcessor{

	public Map<Object, String[]> processLine(String[] keys,String[] value) {
		Map<Object, String[]> map = new TreeMap<Object, String[]>();
		String[] pay = new String[]{"0","0"};
		String[] wordExp = new String[]{"0","0"};
		Pattern pat = Pattern.compile("([0-9]+)", Pattern.CASE_INSENSITIVE);
 		Matcher m1 = pat.matcher(value[1]);
 		int i = 0;
 		while(m1.find()){
 			pay[i++] = m1.group(1);
 		}
 		m1 = pat.matcher(value[2]);
 		i = 0;
 		while(m1.find()){
 			wordExp[i++] = m1.group(1);
 		}
		map.put(keys[0], new String[]{value[0]});
		map.put(keys[1], pay);
		map.put(keys[2], wordExp);
		map.put(keys[3], value[3].split("-"));
		map.put(keys[4], new String[]{value[4]});
		map.put(keys[5], new String[]{value[5]});
		map.put(keys[6], new String[]{value[6]});
		map.put(keys[7], value[7].split("-"));
		map.put(keys[8], new String[]{value[8]});
		map.put(keys[9], value[9].split("-"));
		return map;
	}

}
