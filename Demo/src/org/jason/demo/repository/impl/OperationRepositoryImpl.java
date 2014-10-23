package org.jason.demo.repository.impl;

import javax.annotation.Resource;

import org.apache.shiro.session.mgt.SessionFactory;
import org.jason.demo.entity.Operation;
import org.jason.demo.repository.OperationRepository;

public class OperationRepositoryImpl implements OperationRepository {
	
	@Resource
	SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.jason.demo.repository.impl.OperationRepository#save(org.jason.demo.entity.Operation)
	 */
	@Override	
	public void save(Operation o){
		//TODO operations		
	}
	
	/* (non-Javadoc)
	 * @see org.jason.demo.repository.impl.OperationRepository#add(org.jason.demo.entity.Operation)
	 */
	@Override	
	public void add(Operation o){
		//TODO operations		
	}
	
	/* (non-Javadoc)
	 * @see org.jason.demo.repository.impl.OperationRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String id){
		//TODO operations		
	}
	
}
