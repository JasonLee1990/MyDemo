package com.lee.core.business.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class Query<T> {
	private DetachedCriteria detachedCriteria;

	private List<Order> orders;

	private HashMap<String, String> aliases;

	private Pagination pagination;

	public Query(Class<T> clazz) {
		this.detachedCriteria = DetachedCriteria.forClass(clazz);
		this.orders = new ArrayList<Order>();
		this.aliases = new HashMap<String, String>();
	}

	private String generateKey(String key) {
		String result = new String(key);
		String[] keys = result.split("\\.");
		if (keys.length > 1) {
			String temp = "";
			for (int i = 0; i < keys.length - 1; i++) {
				String k = i > 0 ? (temp + "." + keys[i]) : keys[i];
				String alias = aliases.get(k);
				if (alias != null) {
					temp = alias;
				} else {
					// 用随机数做别名会使每次sql都发生变化，无法有效使用查询缓存
					// alias = "x" +
					// UUID.randomUUID().toString().replaceAll("-", "");
					alias = k.replaceAll("\\.", "") + "_";
					aliases.put(k, alias);
					detachedCriteria.createAlias(k, alias);
					temp = alias;
				}
			}
			return temp + "." + keys[keys.length - 1];
		} else {
			return result.trim();
		}
	}

	/******************* 约束 *******************/
	public Query<T> add(Criterion criterion) {
		if (criterion != null) {
			detachedCriteria.add(criterion);
		}
		return this;
	}

	public Disjunction or(Criterion... criterions) {
		Disjunction disjunction = Restrictions.or();
		for (Criterion criterion : criterions) {
			if (criterion != null) {
				disjunction.add(criterion);
			}
		}
		return disjunction;
	}

	public Query<T> add(Order... orders) {
		for (Order order : orders) {
			this.orders.add(order);
		}
		return this;
	}

	/******************* 聚合、投影 *******************/
	public Query<T> setProjection(Projection projection) {
		detachedCriteria.setProjection(projection);
		return this;
	}

	public ProjectionList projectionList(Projection... projections) {
		ProjectionList projectionList = Projections.projectionList();
		for (Projection projection : projections) {
			projectionList.add(projection);
		}
		return projectionList;
	}

	public AggregateProjection min(String propertyName) {
		return Projections.min(propertyName);
	}

	public AggregateProjection max(String propertyName) {
		return Projections.max(propertyName);
	}

	public AggregateProjection sum(String propertyName) {
		return Projections.sum(propertyName);
	}

	public AggregateProjection avg(String propertyName) {
		return Projections.avg(propertyName);
	}

	public Projection rowCount() {
		return Projections.rowCount();
	}

	public PropertyProjection groupProperty(String propertyName) {
		return Projections.groupProperty(generateKey(propertyName));
	}

	/******************* 执行查询 *******************/
	@SuppressWarnings("unchecked")
	public T single(SessionFactory sessionFactory) {
		try {
			return (T) detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).setCacheable(true).uniqueResult();
		} catch (ObjectNotFoundException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <W> W single(SessionFactory sessionFactory, TransformerAdapter<W> transformer) {
		try {
			return (W) detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).setCacheable(true).setResultTransformer(transformer).uniqueResult();
		} catch (ObjectNotFoundException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> list(SessionFactory sessionFactory) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).setCacheable(true);
		if (pagination != null) {
			pagination.setTotal(count(sessionFactory));
			criteria.setFirstResult(pagination.calculateOffset()).setMaxResults(pagination.getPageSize());
		}
		for (Order order : orders) {
			detachedCriteria.addOrder(order.isAsc() ? org.hibernate.criterion.Order.asc(generateKey(order.getPropertyName())) : org.hibernate.criterion.Order.desc(generateKey(order.getPropertyName())));
		}
		return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <W> List<W> list(SessionFactory sessionFactory, TransformerAdapter<W> transformer) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).setCacheable(true);
		if (pagination != null) {
			pagination.setTotal(count(sessionFactory));
			criteria.setFirstResult(pagination.calculateOffset()).setMaxResults(pagination.getPageSize());
		}
		for (Order order : orders) {
			detachedCriteria.addOrder(order.isAsc() ? org.hibernate.criterion.Order.asc(generateKey(order.getPropertyName())) : org.hibernate.criterion.Order.desc(generateKey(order.getPropertyName())));
		}
		return (List<W>) criteria.setResultTransformer(transformer).list();
	}

	public int count(SessionFactory sessionFactory) {
		Criteria c = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).setCacheable(true);
		int total = ((Long) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		c.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
		return total;
	}

	/******************* 约束条件生成 *******************/

	public Criterion not(Criterion criterion) {
		return criterion == null ? null : Restrictions.not(criterion);
	}

	public Criterion isNull(String propertyName) {
		return Restrictions.isNull(generateKey(propertyName));
	}

	public Criterion isNotNull(String propertyName) {
		return Restrictions.isNotNull(generateKey(propertyName));
	}

	public Criterion isEmpty(String propertyName) {
		return Restrictions.isEmpty(generateKey(propertyName));
	}

	public Criterion isNotEmpty(String propertyName) {
		return Restrictions.isNotEmpty(generateKey(propertyName));
	}

	public Criterion sizeEq(String propertyName, int size) {
		return Restrictions.sizeEq(generateKey(propertyName), size);
	}

	public Criterion sizeNe(String propertyName, int size) {
		return Restrictions.sizeNe(generateKey(propertyName), size);
	}

	public Criterion sizeLt(String propertyName, int size) {
		return Restrictions.sizeLt(generateKey(propertyName), size);
	}

	public Criterion sizeLe(String propertyName, int size) {
		return Restrictions.sizeLe(generateKey(propertyName), size);
	}

	public Criterion sizeGt(String propertyName, int size) {
		return Restrictions.sizeGt(generateKey(propertyName), size);
	}

	public Criterion sizeGe(String propertyName, int size) {
		return Restrictions.sizeGe(generateKey(propertyName), size);
	}

	public SimpleExpression eq(String propertyName, Object value) {
		return (value == null || value.equals("")) ? null : Restrictions.eq(generateKey(propertyName), value);
	}

	public SimpleExpression ne(String propertyName, Object value) {
		return (value == null || value.equals("")) ? null : Restrictions.ne(generateKey(propertyName), value);
	}

	public SimpleExpression lt(String propertyName, Object value) {
		return (value == null || value.equals("")) ? null : Restrictions.lt(generateKey(propertyName), value);
	}

	public SimpleExpression le(String propertyName, Object value) {
		return (value == null || value.equals("")) ? null : Restrictions.le(generateKey(propertyName), value);
	}

	public SimpleExpression gt(String propertyName, Object value) {
		return (value == null || value.equals("")) ? null : Restrictions.gt(generateKey(propertyName), value);
	}

	public SimpleExpression ge(String propertyName, Object value) {
		return (value == null || value.equals("")) ? null : Restrictions.ge(generateKey(propertyName), value);
	}

	public Criterion between(String propertyName, Object lo, Object hi) {
		return (lo == null || lo.equals("") || hi == null || hi.equals("")) ? null : Restrictions.between(generateKey(propertyName), lo, hi);
	}

	public SimpleExpression like(String propertyName, String value) {
		return (value == null || value.equals("")) ? null : Restrictions.like(generateKey(propertyName), value, MatchMode.ANYWHERE).ignoreCase();
	}

	public Criterion in(String propertyName, Object[] values) {
		return (values == null || values.length == 0) ? null : Restrictions.in(generateKey(propertyName), values);
	}

	public Criterion in(String propertyName, Collection<?> values) {
		return (values == null || values.size() == 0) ? null : Restrictions.in(generateKey(propertyName), values);
	}
}
