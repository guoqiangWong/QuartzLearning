package com.wonders.quartzlearning.common.service;


import com.wonders.quartzlearning.common.dao.BaseDao;
import com.wonders.quartzlearning.common.dao.BaseDaoImpl;
import com.wonders.quartzlearning.common.dao.BaseNativeDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class CommonBeanService<T> {
	@PersistenceContext
	protected EntityManager outEm;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected BaseDao<T> getDao(Class clazz) {
		BaseDao<T> dao = new BaseDaoImpl(clazz) {
			{
				this.em = outEm;
			}
		};
		return dao;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected BaseDao<T> routeDao(String dbName,Class clazz) {
		BaseDao<T> dao = new BaseDaoImpl(clazz) {
			{
				this.em = outEm;
			}
		};
		return dao;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected BaseDao getNataveDao() {
		BaseDao dao = new BaseNativeDaoImpl() {
			{
				this.em = outEm;
			}
		};
		return dao;
	}
}
