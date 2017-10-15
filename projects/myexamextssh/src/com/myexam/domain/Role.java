package com.myexam.domain;
import java.util.HashSet;
import java.util.Set;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Role implements java.io.Serializable {
	// Fields

	private String roleId;
	private String roleName;
	private String roleCandel;
	private String roleDesc;
	//private Set funcs = new HashSet(0);
	//private Set teacroles = new HashSet(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
//	public Role(String roleId, String roleName, String roleCandel,
//			String roleDesc, Set funcs, Set teacroles) {
//		this.roleId = roleId;
//		this.roleName = roleName;
//		this.roleCandel = roleCandel;
//		this.roleDesc = roleDesc;
//		this.funcs = funcs;
//		this.teacroles = teacroles;
//	}

	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCandel() {
		return this.roleCandel;
	}

	public void setRoleCandel(String roleCandel) {
		this.roleCandel = roleCandel;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

//	public Set getFuncs() {
//		return this.funcs;
//	}
//
//	public void setFuncs(Set funcs) {
//		this.funcs = funcs;
//	}
//
//	public Set getTeacroles() {
//		return this.teacroles;
//	}
//
//	public void setTeacroles(Set teacroles) {
//		this.teacroles = teacroles;
//	}

}