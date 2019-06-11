package com.wonders.quartzlearning.common.dao;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;


public class BaseNativeDaoImpl<T> implements BaseDao<T> {

	protected EntityManager em;

	private Query getQuery(String sql) {
		return em.createNativeQuery(sql);
	}

	@Deprecated
	@Override
	public List getAll(int... startNoAndSize) {
		return null;
	}

	@Deprecated
	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	public Object save(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	public void remove(Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getCount(String sql, Object... values) {
		return Long.valueOf(findUniqueByQuery(sql, values).toString());
	}

	@Deprecated
	@Override
	public boolean exists(Serializable id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Deprecated
	@Override
	public List findByProperty(String propertyName, Object value, int... startNoAndSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	public List findLikeProperty(String propertyName, Object value, int... startNoAndSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	public T findUniqueByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findAllByQuery(String sql, Object... values) {
		return findByQuery(sql, values);
	}

	@Override
	public List findByQuery(String sql, Object[] values, int... startNoAndSize) {
		Query query = getQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		setPageInfo(query, startNoAndSize);
		setParameter(values, query);
		return query.getResultList();
	}

	@Override
	public List findByQueryll(String sql, Object[] values, Class cls, int... startNoAndSize) {
		Query query = getQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(cls));
		setPageInfo(query, startNoAndSize);
		setParameter(values, query);
		return query.getResultList();
	}

	@Override
	public int executeUpdate(String sql) {
		// TODO Auto-generated method stub
		return getQuery(sql).executeUpdate();
	}

	@Override
	public int executeUpdatell(String sql) {
		int ct = getQuery(sql).executeUpdate();
		em.flush();
		return ct;
	}

	@Override
	public int executeUpdate(String sql, Object... values) {
		Query query = getQuery(sql);
		setParameter(values, query);
		return query.executeUpdate();
	}
	
	
	@Override
	public List save(List list) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setParameter(Object[] values, Query query) {
		if (values == null) {
			return;
		}
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i + 1, values[i]);
		}
	}

	private void setPageInfo(Query query, int... startNoAndSize) {
		if (startNoAndSize != null && startNoAndSize.length > 1) {
			if (startNoAndSize[0] >= 0)
				query.setFirstResult(startNoAndSize[0]);
			if (startNoAndSize[1] > 0)
				query.setMaxResults(startNoAndSize[1]);
		}
	}

	@SuppressWarnings("unchecked")
	public T findUniqueByQuery(String sql, Object... values) {
		// TODO Auto-generated method stub
		Query query = getQuery(sql);
		if (values != null)
			setParameter(values, query);
		// Object result = null;
		try {
			// return (T)query.getSingleResult();
			List<T> list = query.getResultList();
			if (list.isEmpty()) {
				return null;
			}
			return list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		// return (T) result;
	}

	public T findById(Serializable id, Class<T> s) {
		// TODO Auto-generated method stub
		return null;
	}


}
