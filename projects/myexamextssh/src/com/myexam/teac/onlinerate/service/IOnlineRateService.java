package com.myexam.teac.onlinerate.service;

import java.util.Map;

public interface IOnlineRateService {
	public Map<String, Object> queryStuds(Map<String, Object> param);
	public Map<String,Object> queryAnsws(Map<String,Object> param);
	public Map<String,Object> updateScore(Map<String,Object> param);
}
