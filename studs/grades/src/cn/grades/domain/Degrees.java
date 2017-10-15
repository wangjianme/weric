package cn.grades.domain;

public class Degrees {

	private String degrees_id;
	private String degrees_name;
	private String degrees_desc;

	public String getDegrees_id() {
		return degrees_id;
	}

	public void setDegrees_id(String degrees_id) {
		this.degrees_id = degrees_id;
	}

	public String getDegrees_name() {
		return degrees_name;
	}

	public void setDegrees_name(String degrees_name) {
		this.degrees_name = degrees_name;
	}

	public String getDegrees_desc() {
		return degrees_desc;
	}

	public void setDegrees_desc(String degrees_desc) {
		this.degrees_desc = degrees_desc;
	}

	@Override
	public String toString() {
		return "Meeting [degrees_id=" + degrees_id + ", degerees_name=" + degrees_name + ", degrees_desc="
				+ degrees_desc + "]";
	}
}
