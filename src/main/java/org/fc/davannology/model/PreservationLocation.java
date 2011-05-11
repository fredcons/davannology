package org.fc.davannology.model;

import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class PreservationLocation {
	
	@Id
	private Long id;
	@Length(min=1, max=500)
	private String name;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
