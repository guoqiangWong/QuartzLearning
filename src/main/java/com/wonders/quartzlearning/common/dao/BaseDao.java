package com.wonders.quartzlearning.common.dao;

import java.io.Serializable;
import java.util.List;


/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs. extend
 * this interface if you want type safe DAO's for your domain objects.
 */
public interface BaseDao<T> {
	/**
	 * Generic method used to get all objects of a particular type. This is the same
	 * as lookup up all rows in a table.
	 * 
	 * @param startNoAndSize alternative parameter for startNo and size <code>
	 * findAll(); //return all objects
	 * findAll(0,10); //return 0-10 objects.
	 * </code>
	 * @return List of populated objects
	 */
	public List<T> getAll(final int... startNoAndSize);

	/**
	 * Generic method to get an object based on class and identifier. An Runtime
	 * Exception is thrown if nothing is found.
	 * 
	 * @param id the identifier (primary key) of the object to get
	 * @return a populated object
	 */
	public T findById(Serializable id);

	public T findById(Serializable id, Class<T> t);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object the object to save
	 * @return the persisted object
	 */
	public T save(T object);


	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param id the identifier (primary key) of the object to remove
	 */
	public void remove(Serializable id);

	/**
	 * get record count using specail sql and values
	 * 
	 * @param sql    the query language string
	 * @param values multi-property value
	 * @return the count of the record
	 */
	public long getCount(final String sql, final Object... values);

	/**
	 * Checks for existence of an object of type T using the id argument.
	 * 
	 * @param id the id of the entity
	 * @return - true if it exists, false if it doesn't
	 */
	public boolean exists(Serializable id);

	/**
	 * Generic method used to get objects of a particular type, with special
	 * property name, value and range
	 * 
	 * @param propertyName   the name of the property
	 * @param value          the value of the property
	 * @param startNoAndSize alternative parameter for startNo and size
	 * @return List of populated objects
	 */
	public List<T> findByProperty(final String propertyName, final Object value, final int... startNoAndSize);

	/**
	 * Generic method used to get objects of a particular type, with special
	 * property name, value and range, using like query
	 * 
	 * @param propertyName   the name of the property
	 * @param value          the value of the property
	 * @param startNoAndSize alternative parameter for startNo and size
	 * @return List of populated objects
	 * @return
	 */
	public List<T> findLikeProperty(final String propertyName, final Object value, final int... startNoAndSize);

	/**
	 * Used to get an unique object of a particular type, with special property name
	 * and value.
	 * 
	 * @param propertyName the name of the property
	 * @param value        the value of the property
	 * @return an unique populated object
	 */
	public T findUniqueByProperty(final String propertyName, final Object value);

	/**
	 * Used to get objects based on queryString with multi-property values.
	 * 
	 * @param sql    the query language string
	 * @param values multi-property value
	 * @return List of populated objects
	 */
	public List<T> findAllByQuery(final String sql, final Object... values);

	/**
	 * Used to get objects based on queryString with multi-property values and
	 * range.
	 * 
	 * @param sql     the query language string
	 * @param startNo the start no of the record
	 * @param size    the size of the record list
	 * @param values  multi-property value
	 * @return List of populated objects
	 */
	public List<T> findByQuery(String sql, Object[] values, int... startNoAndSize);

	public List<T> findByQueryll(String sql, Object[] values, Class cls, int... startNoAndSize);

	/**
	 * Used to get unique object based on queryString with multi-property values.
	 * 
	 * @param sql    the query language string
	 * @param values multi-property value
	 * @return an unique populated objects
	 */
	public T findUniqueByQuery(final String sql, final Object... values);

	/**
	 * Execute the update or delete statement.
	 * 
	 * @param sql a HQL update or delete statement
	 * @return The number of entities updated or deleted.
	 */
	public int executeUpdate(String sql);

	public int executeUpdatell(String sql);

	public int executeUpdate(String sql, Object... values);

	public List<T> save(List<T> list);

}
