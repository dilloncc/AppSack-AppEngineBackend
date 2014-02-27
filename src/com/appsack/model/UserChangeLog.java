package com.appsack.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class UserChangeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private long userId;
	private String fieldChanged;
	private String valueBeforeChange;
	private Date dateChanged;
	
	public Key getKey() {
		return key;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the fieldChanged
	 */
	public String getFieldChanged() {
		return fieldChanged;
	}

	/**
	 * @param fieldChanged the fieldChanged to set
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
	 * @param valueBeforeChange the valueBeforeChange to set
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
	 * @param dateChanged the dateChanged to set
	 */
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
}
