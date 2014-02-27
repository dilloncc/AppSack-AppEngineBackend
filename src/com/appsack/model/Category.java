package com.appsack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String androidCategoryName;
	private String iOSCategoryName;

	public Key getKey() {
		return key;
	}

	/**
	 * @return the androidCategoryName
	 */
	public String getAndroidCategoryName() {
		return androidCategoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setAndroidCategoryName(String androidCategoryName) {
		this.androidCategoryName = androidCategoryName;
	}

	/**
	 * @return the iOSCategoryName
	 */
	public String getiOSCategoryName() {
		return iOSCategoryName;
	}

	/**
	 * @param iOSCategoryName the iOSCategoryName to set
	 */
	public void setiOSCategoryName(String iOSCategoryName) {
		this.iOSCategoryName = iOSCategoryName;
	}
}
