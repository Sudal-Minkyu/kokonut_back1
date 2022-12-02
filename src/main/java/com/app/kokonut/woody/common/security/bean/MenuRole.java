package com.app.kokonut.woody.common.security.bean;

import org.apache.commons.codec.binary.StringUtils;

import java.util.HashMap;

public enum MenuRole 
{
	ROLE_ROOT(0, "ROLE_ROOT"),  
	ROLE_ENTER(1, "ROLE_ENTER"),
	ROLE_DEPT(2, "ROLE_DEPT"),
	ROLE_USER(3, "ROLE_USER"); 
	//ROLE_MON(3, "ROLE_MON");
	  
	private static final HashMap<Integer, MenuRole> map;
	private int roleType;
	private String roleName;
	  
	static {
		map = new HashMap<Integer, MenuRole>();
		for (MenuRole f : values()) {
			map.put(Integer.valueOf(f.getRoleType()), f);
		}
	}
  
	private MenuRole(int roleType, String roleName){
	    this.roleType = roleType;
	    this.roleName = roleName;
	}

	public int getRoleType() {
		return this.roleType;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public static MenuRole valueOf(int roleType) {
		return (MenuRole) map.get(Integer.valueOf(roleType));
	}

	public boolean equals(MenuRole role) {
		return StringUtils.equals(this.roleName, role.getRoleName());
	}
}
