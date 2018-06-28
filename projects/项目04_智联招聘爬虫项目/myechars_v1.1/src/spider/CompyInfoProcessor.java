package spider;

import java.util.Map;
import java.util.TreeMap;

public class CompyInfoProcessor implements LineProcessor{

	public Map<Object, String[]> processLine(String[] keys, String[] value) {
		Map<Object, String[]> map = new TreeMap<Object, String[]>();
		map.put(keys[0], new String[]{value[0]});
		map.put(keys[1], new String[]{value[1]});
		map.put(keys[2], value[2].split("-"));
		return map;
	}

}
