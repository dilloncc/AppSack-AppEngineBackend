package com.appsack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * An Entity for AppSack AppIdeas.
 * 
 * Its associated, AppIdeaEndpoint.java, provides an api in which we can modify
 * app ideas from multiple platforms (iOS, Android, Web).
 * 
 * @author Cody
 * 
 */
@Entity
public class AppIdea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	private String title;
	private String summary;
	private String details;
	private long timeSubmitted;
	private long timeEdited;
	private long userId;
	private long categoryId;
	
	private int numVotes;
	
	public AppIdea(AppIdea appIdea)
	{
		setTitle(appIdea.getTitle());
		setSummary(appIdea.getSummary());
		setDetails(appIdea.getDetails());
		setTimeSubmitted(appIdea.getTimeSubmitted());
		setTimeEdited(appIdea.getTimeEdited());
		setUserId(appIdea.getUserId());
		setCategoryId(appIdea.getCategoryId());
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the timeSubmitted
	 */
	public long getTimeSubmitted() {
		return timeSubmitted;
	}

	/**
	 * @param timeSubmitted
	 *            the timeSubmitted to set
	 */
	public void setTimeSubmitted(long timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	/**
	 * @return the timeEdited
	 */
	public long getTimeEdited() {
		return timeEdited;
	}

	/**
	 * @param timeEdited
	 *            the timeEdited to set
	 */
	public void setTimeEdited(long timeEdited) {
		this.timeEdited = timeEdited;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the categoryId
	 */
	public long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the numVotes
	 */
	public int getNumVotes()
	{
		return numVotes;
	}

	/**
	 * @param numVotes the numVotes to set
	 */
	public void setNumVotes(int numVotes)
	{
		this.numVotes = numVotes;
	}
}
