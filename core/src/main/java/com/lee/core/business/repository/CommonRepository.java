package com.lee.core.business.repository;


import java.util.List;

import com.lee.core.business.entity.Entity;
import com.lee.core.business.entity.Order;
import com.lee.core.business.entity.Query;
import com.lee.core.business.entity.TransformerAdapter;


/**
 *It's a common operation interface for query,remove,get by id and save entity
 * @author Jason Lee
 *
 */
public interface CommonRepository {

	/**
	 * delete/remove entity
	 * @param t
	 * @return
	 */
	public <T extends Entity> T remove(T t);
	
	/**
	 * get entity by the primary key of it 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T extends Entity> T get(Class<T> entityClass, String id);
	
	/**
	 * save entity 
	 * @param t
	 * @return
	 */
	public <T extends Entity> T save(T t);
	
	/**
	 * use query search to get one result
	 * @param query
	 * @return
	 */
	public <T extends Entity> T single(Query<T> query);
	
	/**
	 *  use query search to get  one result
	 * @param query
	 * @param transformer
	 * @return
	 */
	public <T extends Entity, W> T single(Query<T> query, TransformerAdapter<W> transformer);
	
	/**
	 * get all results in a table
	 * @param entityClass
	 * @param orders
	 * @return
	 */
	public <T extends Entity> List<T> getAll(Class<T> entityClass, Order... orders);
	
	/**
	 * get results by query and orders
	 * @param query
	 * @param orders
	 * @return
	 */
	public <T extends Entity> List<T> list(Query<T> query, Order... orders);
	
	/**
	 * get results by query and orders
	 * @param query
	 * @param transformer
	 * @param orders
	 * @return
	 */
	public <T extends Entity, W> List<T> list(Query<T> query, TransformerAdapter<W> transformer, Order... orders);
	
}
