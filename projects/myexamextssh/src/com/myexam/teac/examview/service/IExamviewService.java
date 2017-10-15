package com.myexam.teac.examview.service;

import java.util.Map;

public interface IExamviewService {
	Map<String,Object> view(Map<String,Object> param);
	Map<String,Object> queryOneExamQues(String examQuesId);
}
