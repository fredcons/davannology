package org.fc.davannology.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Work {
	
	@Id
	private Long id;
	private String description;
	private Key<PositiveTechnique> positiveTechnique;
	private Key<NegativeTechnique> negativeTechnique;
	private Key<PreservationLocation> preservationLocation;
	
	private String dates;
	private int width;
	private int height;
	private String reference;
	private String comment;
	
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the positiveTechnique
	 */
	public Key<PositiveTechnique> getPositiveTechnique() {
		return positiveTechnique;
	}
	
	/**
	 * @param positiveTechnique the positiveTechnique to set
	 */
	public void setPositiveTechnique(Key<PositiveTechnique> positiveTechnique) {
		this.positiveTechnique = positiveTechnique;
	}
	
	/**
	 * @return the negativeTechnique
	 */
	public Key<NegativeTechnique> getNegativeTechnique() {
		return negativeTechnique;
	}
	
	/**
	 * @param negativeTechnique the negativeTechnique to set
	 */
	public void setNegativeTechnique(Key<NegativeTechnique> negativeTechnique) {
		this.negativeTechnique = negativeTechnique;
	}
	
	/**
	 * @return the preservationLocation
	 */
	public Key<PreservationLocation> getPreservationLocation() {
		return preservationLocation;
	}
	
	/**
	 * @param preservationLocation the preservationLocation to set
	 */
	public void setPreservationLocation(
			Key<PreservationLocation> preservationLocation) {
		this.preservationLocation = preservationLocation;
	}
	
	/**
	 * @return the dates
	 */
	public String getDates() {
		return dates;
	}
	
	/**
	 * @param dates the dates to set
	 */
	public void setDates(String dates) {
		this.dates = dates;
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	
	
}
