package com.myexam.teac.reg.service;

import com.myexam.domain.Teac;

public interface ITeacRegService {
	boolean reg(Teac teac);
	boolean validate(Teac teac);
}
