import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tr {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws Exception {
		//<div style=\"width:683px;\" class=\"welfare-tab-box\">([\\s\\S]*?)</div>
		String exp = "<span>([\\s\\S]*?)</span>";
 		StringBuffer keyWordline = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c://info.txt"),"UTF-8"));
		String x = br.readLine();
		while(x != null){
			keyWordline.append(x+"\n");
			x = br.readLine();
		}
		String tline = keyWordline.toString();
		tline = "<span>����һ��</span><span>��Ч����</span><span>��н���</span><span>���Թ���</span><span>�������</span>";
		Pattern keyWordPat = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
		Matcher keyWordMatcher = keyWordPat.matcher(tline);
		while(keyWordMatcher.find()){
			String  keyWordMessage = keyWordMatcher.group(1);
			System.out.println(keyWordMessage);
		}
	}

}
