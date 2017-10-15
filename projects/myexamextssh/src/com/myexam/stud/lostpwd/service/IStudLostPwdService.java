package com.myexam.stud.lostpwd.service;

import java.util.Map;

public interface IStudLostPwdService {
	Map<String,Object> queryQues(String studSid);
	void update(Map<String,Object> param);
}
