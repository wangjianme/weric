package com.myexam.teac.examadd.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.myexam.domain.Cate;
import com.myexam.domain.Exam;
import com.myexam.domain.Examcate;
import com.myexam.domain.Examques;
import com.myexam.domain.Ques;
import com.myexam.teac.examadd.domain.ExamAdd;
/**
 * 试卷增加
 * @author wangjianme
 * @version 1.0,2010-10-31
 */
public class ExamAddDaoJdbc extends HibernateDaoSupport implements IExamAddDao{
	/**
	 * 首先进行检查，
	 * 都检查通过以后，进行保存，主要检查题型的数量是否够多，如果不够则返回并提示
	 * 如果是自动生成，则试卷状态为可用，如果是手工生成，则试卷状态应该为设置中
	 */
	public Map<String, Object> save(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		ExamAdd examadd = (ExamAdd)param.get("examadd");
		String examCour = examadd.getExamCour();			//课程
		String[] ecCate = examadd.getEcCate();				//题型
		Integer[] ecQty = examadd.getEcQty();				//数量
		Map<String,Integer> map = new HashMap<String,Integer>();//用于存放题型和数量之后合并值
		for(int i=0;i<ecCate.length;i++){					//合并相同题型的题型数量
			if(map.containsKey(ecCate[i])){
				map.put(ecCate[i],map.get(ecCate[i])+ecQty[i]);
			}else{
				map.put(ecCate[i],ecQty[i]);
			}
		}
		Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,Integer> en = it.next();
			String sql = "select count(*) from ques where ques_cour=:cour and ques_cate=:cate";
			Integer count = Integer.parseInt(""+getSession().createSQLQuery(sql)
							.setString("cour",examCour)
							.setString("cate",en.getKey())
							.uniqueResult());
			if(count<en.getValue()){				//说明题目的数量不够
				Cate cate = (Cate)getSession().load(Cate.class,en.getKey());
				result.put("msg", "题型["+cate.getCateName()+"]共["+en.getValue()+"]道试题，题库有["+count+"]道，数量不够！");
				break;
			}
		}
		if(result.get("msg")==null){		//开始检索试题
			copy(param);
		}
		result.put("success", true);
		return result;
	}
	/**
	 * 从ques表向examques中导
	 * 首先要保存exam,注意状态的修改，从0到1
	 * 然后保存,examcate
	 * 最后保存examques
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> copy(Map<String,Object> param){
		ExamAdd examadd = (ExamAdd)param.get("examadd");
		String examCour = examadd.getExamCour();			//课程
		String[] ecCate = examadd.getEcCate();				//题型
		String[] ecCatename = examadd.getEcCateName();		//题型的别名
		Integer[] ecQty = examadd.getEcQty();				//某题型的数量
		Integer[] ecScore = examadd.getEcScore();			//每题型的分数
		Exam exam = new Exam();
		exam.setExamName(examadd.getExamName());
		exam.setExamCour(examadd.getExamCour());
		exam.setExamMktime(examadd.getExamMktime());
		exam.setExamMaker(examadd.getExamMaker());
		exam.setExamNote(examadd.getExamNote());
		exam.setExamScore(examadd.getExamScore());
		exam.setExamTime(examadd.getExamTime());
		exam.setExamState("1");								//状态：0为正在设计中，1：为设置完成
		exam.setExamPass(examadd.getExamPass());			//及格分数线
		
		String examId = (String)getSession().save(exam);
		System.err.println("返回的试题id为:"+examId);
		List<String> selectedQuesIds = new ArrayList<String>();		//保存所有已经用过的试题id
		selectedQuesIds.add("a");
		Random r = new Random();
		for(int i=0;i<ecCate.length;i++){
			Examcate ec = new Examcate();						//保存试卷的题型
			ec.setEcExam(examId);								//引用试卷id
			ec.setEcCatename(ecCatename[i]);					//题型别名
			ec.setEcCate(ecCate[i]);							//原始题型id
			ec.setEcQty(ecQty[i]);								//保存题目数量
			ec.setEcSeq(i+1);										//此题型的序号
			ec.setEcScore(ecScore[i]);
			ec.setEcOrder("0");										//是否按顺序显示，0：否。
			String examCateId = (String)getSession().save(ec);		//保存试卷题型对应信息
			System.err.println("试卷题型id为:"+examCateId);
			
			String sql = "select count(*) from ques where ques_cate=:cate " +
					     "and ques_cour=:cour and ques_id not in (:quesIds)";
			Object o =getSession().createSQLQuery(sql)
							.setString("cate",ecCate[i])
							.setString("cour",examCour)
							.setParameterList("quesIds", selectedQuesIds,Hibernate.STRING)
							.uniqueResult();
			Integer count = Integer.parseInt(""+o);
			System.err.println("题型："+ecCate[i]+",数量为："+count);
			for(int j=0;j<ecQty[i];j++){						//在qty内查询
				System.err.println("第:"+j+"个数.,题型别名为："+ecCatename[i]);
				int rd = r.nextInt(count);					//产生一个随机数
				System.err.println("生成的随机数为："+rd+",count的值为："+count);
				List<Ques> quess = getSession().createCriteria(Ques.class)
											   .add(Restrictions.eq("quesCate",ecCate[i]))
											   .add(Restrictions.eq("quesCour",examCour))
											   .add(Restrictions.not(Restrictions.in("quesId",selectedQuesIds)))	//将已经选出来的去掉
											   .setFirstResult(rd)
											   .setMaxResults(1)
											   .list();
				if(quess!=null && quess.size()>0){
					count--;					  //注意每次生成的随机数，必须 //随机数每次减1,越来越小
					Ques ques = quess.get(0);				  //因为只有一行数据
					System.err.println("随机选择的quesId:"+ques.getQuesId());
					selectedQuesIds.add(ques.getQuesId());			//一定要加入去，将已经选择的题去掉才可以
					
					Examques eq = new Examques();					//开始保存试卷试题表，直接copy
					eq.setEqQuesid(ques.getQuesId());
					eq.setEqEcid(examCateId);
					eq.setEqCate(ques.getQuesCate());
					eq.setEqSeq(j+1);
					eq.setEqTitle(ques.getQuesTitle());
					eq.setEqBody(ques.getQuesBody());
					eq.setEqSolu(ques.getQuesSolu());
					eq.setEqChoose(ques.getQuesChoose());
					eq.setEqNote(ques.getQuesNote());
					eq.setEqLevel(ques.getQuesLevel());
					eq.setEqMaker(ques.getQuesMaker());
					eq.setEqScore(ecScore[i]/ecQty[i]);				//这样才可以求出每个题目的分值
					String eqId = (String)getSession().save(eq);
					System.err.println("eqid为:"+eqId);
				}
				System.err.println("***************************************");
			}
		}
		return null;
	}
	
}
