package org.fc.davannology.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Work {
	
	@Id
	private Long id;
	private String description;
	private Key<PositiveTechnique> positiveTechniqueKey;
	private Key<NegativeTechnique> negativeTechniqueKey;
	private Key<PreservationLocation> preservationLocationKey;
	
	private String dates;
	private Integer width;
	private Integer height;
	private String reference;
	private String comment;
	
	@Transient
	private PositiveTechnique positiveTechnique;
	@Transient
    private NegativeTechnique negativeTechnique;
	@Transient
    private PreservationLocation preservationLocation;
	
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
	public Key<PositiveTechnique> getPositiveTechniqueKey() {
		return positiveTechniqueKey;
	}
	
	/**
	 * @param positiveTechnique the positiveTechnique to set
	 */
	public void setPositiveTechniqueKey(Key<PositiveTechnique> positiveTechniqueKey) {
		this.positiveTechniqueKey = positiveTechniqueKey;
	}
	
	/**
     * @return the positiveTechnique
     */
    public Long getPositiveTechniqueKeyAsLong() {
        return positiveTechniqueKey != null ? positiveTechniqueKey.getId() : null;
    }
    
    /**
     * @param positiveTechnique the positiveTechnique to set
     */
    public void setPositiveTechniqueKeyAsLong(Long positiveTechniqueKeyAsLong) {
        this.positiveTechniqueKey = positiveTechniqueKeyAsLong != null ? new Key<PositiveTechnique>(PositiveTechnique.class, positiveTechniqueKeyAsLong) : null;
    }
	
	/**
	 * @return the negativeTechnique
	 */
	public Key<NegativeTechnique> getNegativeTechniqueKey() {
		return negativeTechniqueKey;
	}
	
	/**
	 * @param negativeTechnique the negativeTechnique to set
	 */
	public void setNegativeTechniqueKey(Key<NegativeTechnique> negativeTechniqueKey) {
		this.negativeTechniqueKey = negativeTechniqueKey;
	}
	
	/**
     * @return the negativeTechnique
     */
    public Long getNegativeTechniqueKeyAsLong() {
        return negativeTechniqueKey != null ? negativeTechniqueKey.getId() : null;
    }
    
    /**
     * @param negativeTechnique the negativeTechnique to set
     */
    public void setNegativeTechniqueKeyAsLong(Long negativeTechniqueKeyAsLong) {
        this.negativeTechniqueKey = negativeTechniqueKeyAsLong != null ? new Key<NegativeTechnique>(NegativeTechnique.class, negativeTechniqueKeyAsLong) : null;
    }
	
	/**
	 * @return the preservationLocation
	 */
	public Key<PreservationLocation> getPreservationLocationKey() {
		return preservationLocationKey;
	}
	
	/**
	 * @param preservationLocation the preservationLocation to set
	 */
	public void setPreservationLocationKey(Key<PreservationLocation> preservationLocationKey) {
		this.preservationLocationKey = preservationLocationKey;
	}
	
	/**
     * @return the preservationLocation
     */
    public Long getPreservationLocationKeyAsLong() {
        return preservationLocationKey != null ? preservationLocationKey.getId() : null;
    }
    
    /**
     * @param preservationLocation the preservationLocation to set
     */
    public void setPreservationLocationKeyAsLong(Long preservationLocationKeyAsLong) {
        this.preservationLocationKey = preservationLocationKeyAsLong != null ? new Key<PreservationLocation>(PreservationLocation.class, preservationLocationKeyAsLong) : null;
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
	public Integer getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
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

    /**
     * @return the positiveTechnique
     */
    public PositiveTechnique getPositiveTechnique() {
        return positiveTechnique;
    }

    /**
     * @param positiveTechnique the positiveTechnique to set
     */
    public void setPositiveTechnique(PositiveTechnique positiveTechnique) {
        this.positiveTechnique = positiveTechnique;
    }

    /**
     * @return the negativeTechnique
     */
    public NegativeTechnique getNegativeTechnique() {
        return negativeTechnique;
    }

    /**
     * @param negativeTechnique the negativeTechnique to set
     */
    public void setNegativeTechnique(NegativeTechnique negativeTechnique) {
        this.negativeTechnique = negativeTechnique;
    }

    /**
     * @return the preservationLocation
     */
    public PreservationLocation getPreservationLocation() {
        return preservationLocation;
    }

    /**
     * @param preservationLocation the preservationLocation to set
     */
    public void setPreservationLocation(PreservationLocation preservationLocation) {
        this.preservationLocation = preservationLocation;
    }

	
	
}
