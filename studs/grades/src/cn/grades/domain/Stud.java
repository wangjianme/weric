package cn.grades.domain;

public class Stud {
	private String addr;
	private String id;
	private String no;
	private String name;
	private String sex;
	private String idcard;
	private String age;
	private String pwd;
	private String email;
	private String tel;
	private String height;
	private String wight;
	private String qq;
	private String minzu;
	private String jibie;
	private String salt;
	
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getAddr() {
		return addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String emil) {
		this.email = emil;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWight() {
		return wight;
	}
	public void setWight(String wight) {
		this.wight = wight;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMinzu() {
		return minzu;
	}
	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}
	public String getJibie() {
		return jibie;
	}
	public void setJibie(String jibie) {
		this.jibie = jibie;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	private String clasid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getClasid() {
		return clasid;
	}
	public void setClasid(String clasid) {
		this.clasid = clasid;
	}
	@Override
	public String toString() {
		return "Stud [addr=" + addr + ", id=" + id + ", no=" + no + ", name=" + name + ", sex=" + sex + ", idcard="
				+ idcard + ", age=" + age + ", pwd=" + pwd + ", email=" + email + ", tel=" + tel + ", height=" + height
				+ ", wight=" + wight + ", qq=" + qq + ", minzu=" + minzu + ", jibie=" + jibie + ", clasid=" + clasid
				+ "]";
	}
	
	
	

}
