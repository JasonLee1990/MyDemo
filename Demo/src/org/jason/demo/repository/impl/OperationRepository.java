package org.jason.demo.repository.impl;

import org.jason.demo.entity.Operation;

public interface OperationRepository {

	public abstract void save(Operation o);

	public abstract void add(Operation o);

	public abstract void remove(String id);

}