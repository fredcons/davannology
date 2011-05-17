package org.fc.davannology.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class AbstractObjectifyDAO<T> {
	
	@Autowired 
	private ObjectifyFactory objectifyFactory;
	
	public T findById(long id) {
		Objectify objectify = objectifyFactory.begin();
		return (T) objectify.find(getPersistentClass(), id);
	}
	
	public void delete(long id) {
		Objectify objectify = objectifyFactory.begin();
		objectify.delete(getPersistentClass(), id);
	}
	
	public void deleteAll() {
		Objectify objectify = objectifyFactory.begin();
		objectify.delete(findAll());
	}
	
	public void save(T object) {
		Objectify objectify = objectifyFactory.begin();
		objectify.put(object);
	}
	
	public List<T> findAll() {
		return findAll(null);
	}
	
	public List<T> findAll(Paging paging) {
		return findByFilters(null, paging);
	}
	
	public List<T> findByFilters(List<QueryFilter> filters) {
		return findByFilters(filters, null);
	}
	
	public List<T> findByFilters(List<QueryFilter> filters, Paging paging) {
		Objectify objectify = objectifyFactory.begin();
		Query<T> query = objectify.query(getPersistentClass());
		if (filters != null) {
			for (QueryFilter filter : filters) {
				query.filter(filter.getCondition(), filter.getValue());
			}
		}
		if (paging != null) {
			if (paging.getOffset() >= 0) {
				query.offset(paging.getOffset());
			}
			if (paging.getItemsPerPage() > 0) {
				query.limit(paging.getItemsPerPage());
			}	
			if (paging.getSort() != null) {
				System.out.println("Using sort : " + paging.getSort());
				query.order(paging.getSort());
			}
		}			
		return query.list();
	}
	
	public T findUniqueByFilters(List<QueryFilter> filters) {
		List<T> list = findByFilters(filters);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<T> getPersistentClass() {
		Class clazz = getClass();
		do {
			Type type = clazz.getGenericSuperclass();
			if (type instanceof ParameterizedType) {
		        Class<T> matchingClass = (Class<T>)((ParameterizedType) type).getActualTypeArguments()[0];
		        return matchingClass;
		    }
		    clazz = clazz.getSuperclass();
		} while (clazz != null);
		return null;
	}

}
