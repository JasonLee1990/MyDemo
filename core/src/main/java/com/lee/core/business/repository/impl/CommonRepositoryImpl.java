package com.lee.core.business.repository.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;

import com.lee.core.business.entity.Entity;
import com.lee.core.business.entity.Order;
import com.lee.core.business.entity.Query;
import com.lee.core.business.entity.TransformerAdapter;
import com.lee.core.business.repository.CommonRepository;

@Resource
public class CommonRepositoryImpl implements CommonRepository {

	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public <T extends Entity> T remove(T t) {
		sessionFactory.getCurrentSession().delete(t);
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T get(Class<T> entityClass, String id) {
		return StringUtils.isBlank(id) ? null : (T) sessionFactory.getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T save(T t) {
		if (t == null)
			return null;
		return (T) sessionFactory.getCurrentSession().merge(t);
	}

	public <T extends Entity> T single(Query<T> query) {
		return query.single(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity, W> T single(Query<T> query, TransformerAdapter<W> transformer) {
		return (T) query.single(sessionFactory, transformer);
	}

	public <T extends Entity> List<T> getAll(Class<T> entityClass, Order... orders) {
		return new Query<T>(entityClass).add(orders).list(sessionFactory);
	}

	public <T extends Entity> List<T> list(Query<T> query, Order... orders) {
		return query.add(orders).list(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity, W> List<T> list(Query<T> query, TransformerAdapter<W> transformer, Order... orders) {
		return (List<T>) query.add(orders).list(sessionFactory, transformer);
	}

}
