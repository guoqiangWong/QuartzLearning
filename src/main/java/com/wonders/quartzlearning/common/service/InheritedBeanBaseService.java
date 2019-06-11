package com.wonders.quartzlearning.common.service;

import com.wonders.quartzlearning.common.dao.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Richard
 * 
 */
@Service
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class InheritedBeanBaseService<T> extends CommonBeanService<T>{

	public T save(T bean) {

		BaseDao dao = getDao(bean.getClass());

		Object obj = dao.save(bean);

		if (obj != null)
			return (T) obj;
		else
			throw new RuntimeException(bean.getClass().getSimpleName()
					+ " saving failed!");
	}

	public void remove(Class clazz, Serializable id) {
		BaseDao dao = getDao(clazz);
		dao.remove(id);
	}

	public List<T> findContentList(Class clazz, int... startNoAndSize) {
		BaseDao dao = getDao(clazz);
		return dao.getAll(startNoAndSize);
	}

	public long getCount(Class clazz) {
		BaseDao dao = getDao(clazz);
		return dao.getCount("SELECT count(*) FROM " + clazz.getSimpleName());
	}

	public T load(Class clazz, Serializable id) {
		BaseDao dao = getDao(clazz);
		Object obj = dao.findById(id);
		if (obj != null)
			return (T) obj;
		else
			return null;
	}
}
