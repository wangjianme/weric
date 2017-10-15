package com.myexam.domain;
import java.util.HashSet;
import java.util.Set;

/**
 * Menu entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Menu implements java.io.Serializable {
	// Fields

	private String menuId;
	private String menuText;
	private String menuParent;
	private Boolean menuLeaf;
	public Boolean getMenuLeaf() {
		return menuLeaf;
	}

	public void setMenuLeaf(Boolean menuLeaf) {
		this.menuLeaf = menuLeaf;
	}

	private String menuUrl;
	private String menuTarget;
	private String menuIssys;
	private Integer menuOrder;
	//private Set funcs = new HashSet(0);

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** minimal constructor */
	public Menu(String menuId) {
		this.menuId = menuId;
	}

//	/** full constructor */
//	public Menu(String menuId, String menuText, String menuParent,
//			String menuLeaf, String menuUrl, String menuTarget,
//			String menuIssys, Integer menuOrder, Set funcs) {
//		this.menuId = menuId;
//		this.menuText = menuText;
//		this.menuParent = menuParent;
//		this.menuLeaf = menuLeaf;
//		this.menuUrl = menuUrl;
//		this.menuTarget = menuTarget;
//		this.menuIssys = menuIssys;
//		this.menuOrder = menuOrder;
//		this.funcs = funcs;
//	}

	// Property accessors

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuText() {
		return this.menuText;
	}

	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}

	public String getMenuParent() {
		return this.menuParent;
	}

	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}

//	public String getMenuLeaf() {
//		return this.menuLeaf;
//	}
//
//	public void setMenuLeaf(String menuLeaf) {
//		this.menuLeaf = menuLeaf;
//	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuTarget() {
		return this.menuTarget;
	}

	public void setMenuTarget(String menuTarget) {
		this.menuTarget = menuTarget;
	}

	public String getMenuIssys() {
		return this.menuIssys;
	}

	public void setMenuIssys(String menuIssys) {
		this.menuIssys = menuIssys;
	}

	public Integer getMenuOrder() {
		return this.menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

//	public Set getFuncs() {
//		return this.funcs;
//	}
//
//	public void setFuncs(Set funcs) {
//		this.funcs = funcs;
//	}

}