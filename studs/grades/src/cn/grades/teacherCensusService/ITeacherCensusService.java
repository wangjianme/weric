package cn.grades.teacherCensusService;

import java.util.List;
import java.util.Map;

import cn.grades.domain.TeacherCensus;

public interface ITeacherCensusService {

	List<TeacherCensus> selectDept();

	List<Map<String, Object>> titleSelect();

	List<Map<String, Object>> degreeSelect();

}