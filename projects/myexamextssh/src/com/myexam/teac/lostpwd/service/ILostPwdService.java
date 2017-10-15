package com.myexam.teac.lostpwd.service;

import java.util.Map;

/**
 * 忘记密码
 * @author wangjianme
 * @version 1.0,2010-12-7
 */
public interface ILostPwdService {
	public Map<String, Object> queryQues(String name);
	public void update(Map<String, Object> param);
}
