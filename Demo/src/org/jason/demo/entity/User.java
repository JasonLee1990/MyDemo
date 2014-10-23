package org.jason.demo.entity;

import java.util.List;

import org.jason.demo.entity.base.BaseEntity;

public class User extends BaseEntity{
	
	private String userName;
	
	private String password;
	
	private List<Group> groups;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
}
