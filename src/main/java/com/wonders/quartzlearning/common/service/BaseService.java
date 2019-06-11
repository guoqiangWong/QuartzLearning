package com.wonders.quartzlearning.common.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
	public T save(T t);
	public void delete(Serializable id);
	public T load(Serializable id);
	public List<T> load(Integer start, Integer count);
	public List<T> loadAll();
	public List<T> load(String propName, Object propValue, final int... startNoAndSize);
	public T loadFirstByUniqueProperty(String propName, Object propValue);
	public T loadFirstByQuery(String sql, Object... values);
	public long getCount(String sql, Object... values);
	public void testDao();
}
