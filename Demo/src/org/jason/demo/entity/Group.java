package org.jason.demo.entity;

import java.util.List;

import org.jason.demo.entity.base.BaseEntity;

public class Group extends BaseEntity{
	
	private String groupName;
	
	private List<Operation> operations;
	
	private List<User> users;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
