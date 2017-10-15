package cn.grades.domain;

public class Score {
	private String id;
	private String studid;
	private String courseid;
	private String score;
	private String term;
	private String type;
	private String datetime;
	private String teacher;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudid() {
		return studid;
	}
	public void setStudid(String studid) {
		this.studid = studid;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	@Override
	public String toString() {
		return "Score [id=" + id + ", studid=" + studid + ", courseid=" + courseid + ", score=" + score + ", term="
				+ term + ", type=" + type + ", datetime=" + datetime + ", teacher=" + teacher + "]";
	}
	
}
