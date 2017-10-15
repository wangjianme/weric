package com.myexam.teac.login.service;

import java.util.Map;

import com.myexam.domain.Teac;

public interface ILoginService {
	public Map<String,Object> login(Teac teac);
}
