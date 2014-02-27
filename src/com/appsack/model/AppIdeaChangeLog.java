package com.appsack.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class AppIdeaChangeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private long appIdeaId;
	private String fieldChanged;
	private String valueBeforeChange;
	private Date dateChanged;

	public Key getKey() {
		return key;
	}

	/**
	 * @return the appIdeaId
	 */
	public long getAppIdeaId() {
		return appIdeaId;
	}

	/**
	 * @param appIdeaId
	 *            the appIdeaId to set
	 */
	public void setAppIdeaId(long userId) {
		this.appIdeaId = userId;
	}

	/**
	 * @return the fieldChanged
	 */
	public String getFieldChanged() {
		return fieldChanged;
	}

	/**
	 * @param fieldChanged
	 *            the fieldChanged to set
	 */
	public void setFieldChanged(String fieldChanged) {
		this.fieldChanged = fieldChanged;
	}

	/**
	 * @return the valueBeforeChange
	 */
	public String getValueBeforeChange() {
		return valueBeforeChange;
	}

	/**
	 * @param valueBeforeChange
	 *            the valueBeforeChange to set
	 */
	public void setValueBeforeChange(String valueBeforeChange) {
		this.valueBeforeChange = valueBeforeChange;
	}

	/**
	 * @return the dateChanged
	 */
	public Date getDateChanged() {
		return dateChanged;
	}

	/**
	 * @param dateChanged
	 *            the dateChanged to set
	 */
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
}
