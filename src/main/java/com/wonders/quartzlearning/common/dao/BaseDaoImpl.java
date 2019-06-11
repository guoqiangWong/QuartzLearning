package com.wonders.quartzlearning.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;


public class BaseDaoImpl<T> implements BaseDao<T> {

	private Class<T> t;

	protected EntityManager em;

	public BaseDaoImpl(final Class<T> t) {
		this.t = t;
	}

	public List<T> getAll(int... startNoAndSize) {
		// TODO Auto-generated method stub
		return findByQuery("SELECT t FROM " + t.getSimpleName() + " t", startNoAndSize);
	}

	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return em.find(t, id);
	}

	public T findById(Serializable id, Class<T> s) {
		// TODO Auto-generated method stub
		return em.find(s, id);
	}

	public T save(T object) {
		// TODO Auto-generated method stub
		T obj = em.merge(object);
		return obj;
	}

	public List<T> save(List<T> list) {
		int batchSize = 1000;
		int entityCount = list.size();
		
		try {
		    for ( int i = 0; i < entityCount; ++i ) {
		        if ( i > 0 && i % batchSize == 0 ) {
		            em.flush();
		            em.clear();
		        }
		 
		        em.merge(list.get(i));
		    }
		} catch (RuntimeException e) {
		    e.printStackTrace();
		}
		return null;
	}

	public void remove(Serializable id) {
		// TODO Auto-generated method stub
		em.remove(em.getReference(t, id));
	}

	public long getCount(String sql, Object... values) {
		// TODO Auto-generated method stub
		return (Long) findUniqueByQuery(sql, values);
	}

	public boolean exists(Serializable id) {
		// TODO Auto-generated method stub
		return em.contains(id);
	}

	public List<T> findByProperty(String propertyName, Object value, int... startNoAndSize) {
		// TODO Auto-generated method stub
		final String sql = " from " + t.getSimpleName() + " obj where obj." + propertyName + "= ?";
		return findByQuery(sql, new Object[] { value }, startNoAndSize);
	}

	public List<T> findLikeProperty(String propertyName, Object value, int... startNoAndSize) {
		// TODO Auto-generated method stub
		final String sql = " from " + t.getSimpleName() + " obj where obj." + propertyName + "like '%?%'";
		return findByQuery(sql, new Object[] { value }, startNoAndSize);
	}

	public T findUniqueByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		final String sql = " from " + t.getSimpleName() + " obj where obj." + propertyName + "= ?";
		return findUniqueByQuery(sql, value);
	}

	public List<T> findAllByQuery(String sql, Object... values) {
		// TODO Auto-generated method stub
		return findByQuery(sql, values);
	}

	public List<T> findByQuery(String sql, int... startNoAndSize) {
		return findByQuery(sql, null, startNoAndSize);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByQuery(String sql, Object[] values, int... startNoAndSize) {
		// TODO Auto-generated method stub
		Query query = getQuery(sql);
		setPageInfo(query, startNoAndSize);
		setParameter(values, query);
		return query.getResultList();
	}

	private Query getQuery(String sql) {
		return em.createQuery(sql);
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

	public int executeUpdate(String sql) {
		// TODO Auto-generated method stub
		return getQuery(sql).executeUpdate();
	}

	@Override
	public int executeUpdate(String sql, Object... values) {
		Query query = getQuery(sql);
		setParameter(values, query);
		return query.executeUpdate();
	}

	@Override
	public int executeUpdatell(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> findByQueryll(String sql, Object[] values, Class cls, int... startNoAndSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
