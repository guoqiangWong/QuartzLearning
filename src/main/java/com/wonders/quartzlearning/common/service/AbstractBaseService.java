package com.wonders.quartzlearning.common.service;

import com.wonders.quartzlearning.common.dao.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ExpanseWong
 */
@Service
public abstract class AbstractBaseService<T> extends CommonBeanService<T> implements BaseService<T> {
	public static Integer rows = 10, curRow, endRow, page, total, countPage, startRow;

	public List<T> load(String propName, Object propValue, final int... startNoAndSize) {
		// TODO Auto-generated method stub
		return getDao().findByProperty(propName, propValue, startNoAndSize);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDao<T> getDao() {
		
		try {
			return getDao(getModelClass());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 获取泛型实体类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getModelClass() {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return null;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (!(params[0] instanceof Class)) {
			return null;
		}
		return (Class<T>) params[0];
	}
	

	@Transactional
	public T save(T t) {
		// TODO Auto-generated method stub
		return getDao().save(t);
	}

	@Transactional
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		getDao().remove(id);
	}

	public T load(Serializable id) {
		// TODO Auto-generated method stub
		return getDao().findById(id);
	}

	public List<T> load(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return getDao().getAll(start, count);
	}

	public List<T> loadAll() {
		// TODO Auto-generated method stub
		return getDao().getAll();
	}

	public T loadFirstByUniqueProperty(String propName, Object propValue) {
		// TODO Auto-generated method stub
		return getDao().findUniqueByProperty(propName, propValue);
	}

	public T loadFirstByQuery(String sql, Object... values) {
		// TODO Auto-generated method stub
		List<T> list = getDao().findByQuery(sql, values, 0, 1);
		if (list == null || list.size() == 0)
			return null;
		return (T) list.get(0);
	}

	public void testDao() {
		// TODO Auto-generated method stub
		System.out.println(getDao());
	}

	public long getCount(String sql, Object... values) {
		// TODO Auto-generated method stub
		return getDao().getCount(sql, values);
	}

	/**
	 * 获取数据库分页数据
	 * 
	 * @param listSql
	 * @param countSql
	 * @param values
	 * @param pageMap
	 * @return
	 */
	public Map<String, Object> queryListAndCount(String listSql, String countSql, Object[] values,
			Map<String, String> pageMap) {
		LinkedHashMap<String, Object> returnData = new LinkedHashMap<String, Object>();
		List<T> listOrg = this.queryList(listSql, values, pageMap);
		Map<String, String> countMap = this.queryCount(countSql, values, pageMap);
		returnData.put("pageBean", countMap);
		returnData.put("datas", listOrg);
		return returnData;
	}

	/**
	 * 获取数据库分页数据
	 * 
	 * @param listSql
	 * @param values
	 * @param pageMap
	 * @return
	 */
	public List<T> queryList(String listSql, Object[] values, Map<String, String> pageMap) {
		page = pageMap.get("page") == null ? 1 : Integer.parseInt(pageMap.get("page").trim());
		rows = pageMap.get("rows") == null ? rows : Integer.parseInt(pageMap.get("rows").trim());
		startRow = ((page - 1) * rows);// 开始查询的行数
		endRow = page * rows;
		List<T> list = getDao().findByQuery(listSql, values, startRow, rows);
		return list;
	}

	/**
	 * 获取数据库总记录数
	 * 
	 * @param countSql
	 * @param values
	 * @param pageMap
	 * @return
	 */
	public Map<String, String> queryCount(String countSql, Object[] values, Map<String, String> pageMap) {
		total = Integer.parseInt((getDao().getCount(countSql, values)) + "");
		rows = pageMap.get("rows") == null ? rows : Integer.parseInt(pageMap.get("rows").trim());
		Map<String, String> countMap = new HashMap<String, String>();
		if (total > 0) {
			page = pageMap.get("page") == null ? 1 : Integer.parseInt(pageMap.get("page").trim());
			curRow = ((page - 1) * rows) + 1;// startNo 当前页起始行数
			countPage = ((total - 1) / rows) + 1;
			countMap.put("page", page.toString());
			countMap.put("rows", rows.toString());
			countMap.put("curRow", curRow.toString());
			countMap.put("total", total.toString());
			countMap.put("countPage", countPage.toString());
		}

		return countMap;

	}

	/**
	 * 获取数据库分页数据
	 * 
	 * @param listSql
	 * @param countSql
	 * @param values
	 * @param pageMap
	 * @return
	 */
	public Map<String, Object> nativeQueryListAndCount(String listSql, String countSql, Object[] values,
			Map<String, String> pageMap) {
		LinkedHashMap<String, Object> returnData = new LinkedHashMap<String, Object>();
		List<T> listOrg = this.nativeQueryList(listSql, values, pageMap);
		Map<String, String> countMap = this.nativeQueryCount(countSql, values, pageMap);
		returnData.put("pageBean", countMap);
		returnData.put("datas", listOrg);
		return returnData;
	}

	/**
	 * 获取数据库分页数据
	 * 
	 * @param listSql
	 * @param values
	 * @param pageMap
	 * @return
	 */
	private List<T> nativeQueryList(String listSql, Object[] values, Map<String, String> pageMap) {
		page = pageMap.get("page") == null ? 1 : Integer.parseInt(pageMap.get("page").trim());
		rows = pageMap.get("rows") == null ? rows : Integer.parseInt(pageMap.get("rows").trim());
		startRow = ((page - 1) * rows);// 开始查询的行数
		endRow = page * rows;
		List<T> list = getNataveDao().findByQuery(listSql, values, startRow, rows);
		return list;
	}

	/**
	 * 获取数据库总记录数
	 * 
	 * @param countSql
	 * @param values
	 * @param pageMap
	 * @return
	 */
	private Map<String, String> nativeQueryCount(String countSql, Object[] values, Map<String, String> pageMap) {
		total = Integer.parseInt((getNataveDao().getCount(countSql, values)) + "");
		rows = pageMap.get("rows") == null ? rows : Integer.parseInt(pageMap.get("rows").trim());
		Map<String, String> countMap = new HashMap<String, String>();
		if (total > 0) {
			page = pageMap.get("page") == null ? 1 : Integer.parseInt(pageMap.get("page").trim());
			curRow = ((page - 1) * rows) + 1;// startNo 当前页起始行数
			countPage = ((total - 1) / rows) + 1;
			countMap.put("page", page.toString());
			countMap.put("rows", rows.toString());
			countMap.put("curRow", curRow.toString());
			countMap.put("total", total.toString());
			countMap.put("countPage", countPage.toString());
		}

		return countMap;

	}
}
