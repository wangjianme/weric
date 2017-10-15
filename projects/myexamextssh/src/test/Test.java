package test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.directwebremoting.servlet.DwrServlet;

public class Test {
	DwrServlet a1;
	public static void main(String[] args) {
		aa();
	}
	
	//将sql再组织成select count(*)
	public static void aa(){
		String sql = "select info_id,info_oe,"+
			 "oe_exam,exam_name,"+
			 "info_stud,stud_name,"+
			 "stud_cls,cls_name,"+
			 "exam_cour,cour_name,"+
			 "info_score,info_timein,"+
			 "info_type,info_rate,"+
			 "oe_teac,info_resit"+
			 " from info inner join openexam on info_oe=oe_id " +
			 "and info_stud=oe_stud"+
			 " inner join exam on oe_exam=exam_id"+
			 " inner join cour on exam_cour=cour_id"+
			 " inner join stud on info_stud=stud_id"+
			 " inner join cls on stud_cls=cls_id"+
			 " where info_state='1'";
		System.err.println(sql);
		String sql2 = sql.substring(sql.indexOf("from"),sql.length());
		System.err.println("select count(*) "+sql2);
	}
}
