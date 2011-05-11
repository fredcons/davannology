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
	
	public void save(T object) {
		Objectify objectify = objectifyFactory.begin();
		objectify.put(object);
	}
	
	public List<T> findAll() {
		Objectify objectify = objectifyFactory.begin();
		return objectify.query(getPersistentClass()).list();
	}
	
	public List<T> findByFilters(List<QueryFilter> filters) {
		Objectify objectify = objectifyFactory.begin();
		Query<T> query = objectify.query(getPersistentClass());
		for (QueryFilter filter : filters) {
			query.filter(filter.getCondition(), filter.getValue());
		}
		return query.list();
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
