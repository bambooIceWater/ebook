package com.sunshine.ebook.entity;

import java.io.Serializable;

public class Permission implements Serializable {

	private static final long serialVersionUID = -6788175584858358485L;
	
	private Integer permissionid;
	private String name;
	private String description;

	public Integer getPermissionid() {
		return permissionid;
	}

	public void setPermissionid(Integer permissionid) {
		this.permissionid = permissionid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
