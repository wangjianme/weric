package cn.grades.teacherCensusDao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.TeacherCensus;

public interface ITeacherCensusDao {

	List<TeacherCensus> selectDept();

	List<Map<String, Object>> titleSelect();

	List<Map<String, Object>> degreeSelect();

}