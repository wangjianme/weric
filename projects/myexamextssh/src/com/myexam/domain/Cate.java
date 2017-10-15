package com.myexam.domain;
//import java.util.HashSet;
//import java.util.Set;
@SuppressWarnings("serial")
public class Cate implements java.io.Serializable {
	private String cateId;
	private String cateName;
	private Integer cateOrder;
	private String cateKey;
	private String cateDesc;
	public Cate() {
	}

	/** minimal constructor */
	public Cate(String cateId) {
		this.cateId = cateId;
	}

	/** full constructor */
//	public Cate(String cateId, String cateName, Integer cateOrder,
//			String cateKey, String cateAtt, String cateDesc, Set queses,
//			Set examqueses, Set examcates) {
//		this.cateId = cateId;
//		this.cateName = cateName;
//		this.cateOrder = cateOrder;
//		this.cateKey = cateKey;
//		this.cateAtt = cateAtt;
//		this.cateDesc = cateDesc;
//		this.queses = queses;
//		this.examqueses = examqueses;
//		this.examcates = examcates;
//	}

	// Property accessors

	public String getCateId() {
		return this.cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return this.cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getCateOrder() {
		return this.cateOrder;
	}

	public void setCateOrder(Integer cateOrder) {
		this.cateOrder = cateOrder;
	}

	public String getCateKey() {
		return this.cateKey;
	}

	public void setCateKey(String cateKey) {
		this.cateKey = cateKey;
	}
	public String getCateDesc() {
		return this.cateDesc;
	}

	public void setCateDesc(String cateDesc) {
		this.cateDesc = cateDesc;
	}

//	public Set getQueses() {
//		return this.queses;
//	}
//
//	public void setQueses(Set queses) {
//		this.queses = queses;
//	}
//
//	public Set getExamqueses() {
//		return this.examqueses;
//	}
//
//	public void setExamqueses(Set examqueses) {
//		this.examqueses = examqueses;
//	}

//	public Set getExamcates() {
//		return this.examcates;
//	}
//
//	public void setExamcates(Set examcates) {
//		this.examcates = examcates;
//	}

}