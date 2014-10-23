package org.jason.demo.repository;

import org.jason.demo.entity.Operation;

public interface OperationRepository {

	public void save(Operation o);

	public void add(Operation o);

	public void remove(String id);

}