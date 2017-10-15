package cn.grades.spwd.service;

import cn.grades.domain.Stud;


public interface IPwdService {

	int updatePwd(Stud stud,String newPwd);

}