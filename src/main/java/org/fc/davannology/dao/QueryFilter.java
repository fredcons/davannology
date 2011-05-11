package org.fc.davannology.dao;

public class QueryFilter {

	private String condition;
	private Object value;
	
	public QueryFilter(String condition, Object value) {
		this.condition = condition;
		this.value = value;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	
}
