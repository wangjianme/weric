package spider;

import java.util.Map;

public interface LineProcessor {

	public Map<Object,String[]> processLine(String key[],String[] value);
}
