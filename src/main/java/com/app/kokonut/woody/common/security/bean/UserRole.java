package com.app.kokonut.woody.common.security.bean;

import java.util.List;

public class UserRole {
	
	private int roleId;
	private int roleType;
	private String roleName;
	private String desc;
	private List<String> menuRole;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<String> getMenuRole() {
		return menuRole;
	}
	public void setMenuRole(List<String> menuRole) {
		this.menuRole = menuRole;
	}
	
	
	
}
