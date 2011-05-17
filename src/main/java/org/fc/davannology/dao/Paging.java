package org.fc.davannology.dao;

import org.springframework.util.StringUtils;

public class Paging {
	
	public static final Paging DEFAULT_NAME_PAGING = new Paging("name");
	
	private static int DEFAULT_ITEMS_PER_PAGE = 20;
	public enum SortDirection {
		ASC, DESC;
	}
	
	private int itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
	private int page;
	private String sortProperty;
	private SortDirection sortDirection;
	
	public Paging() {
		
	}
	
	public Paging(String sortProperty) {
		this.sortProperty = sortProperty;
	}
	
	public Paging(String sortProperty, SortDirection sortDirection) {
		this.sortProperty = sortProperty;
		this.sortDirection = sortDirection;
	}
	
	/**
	 * @return the itemsPerPage
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	/**
	 * @param itemsPerPage the itemsPerPage to set
	 */
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * @return the sortProperty
	 */
	public String getSortProperty() {
		return sortProperty;
	}
	
	/**
	 * @param sortProperty the sortProperty to set
	 */
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
	
	public int getOffset() {
		return (page - 1) * itemsPerPage;
	}

	/**
	 * @return the sortDirection
	 */
	public SortDirection getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection the sortDirection to set
	 */
	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSort() {
		if (StringUtils.hasLength(sortProperty)) {
			return (sortDirection == SortDirection.DESC ? "-" : "") + sortProperty;
		}
		return null;
	}
}
