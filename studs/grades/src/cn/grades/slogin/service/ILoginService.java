package cn.grades.slogin.service;

import java.util.List;

import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.domain.User;

public interface ILoginService {

	Stud login1(Stud stud);
	List<Stud> query(String id);
	List<Score> queryscore(String id,Score s);
}