package cn.grades.domain;

public class CollScore {
	private String id;
	private String name;
	private String grade;
	private String score;
	private String majorid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getMajorid() {
		return majorid;
	}

	public void setMajorid(String majorid) {
		this.majorid = majorid;
	}

	@Override
	public String toString() {
		return "CollScore [id=" + id + ", name=" + name + ", grade=" + grade + ", score=" + score + ", majorid="
				+ majorid + "]";
	}
}
