package com.appsack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class AppIdeaVote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private int vote;
	private long appIdeaId;
	private long userId;

	public enum Vote {
		LIKE, DISLIKE, NEUTRAL
	}
	
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @return the vote
	 */
	public int getVote() {
		return vote;
	}

	/**
	 * @param vote the vote to set
	 */
	public void setVote(int vote) {
		this.vote = vote;
	}

	/**
	 * @return the appIdeaId
	 */
	public long getAppIdeaId() {
		return appIdeaId;
	}

	/**
	 * @param appIdeaId the appIdeaId to set
	 */
	public void setAppIdeaId(long appIdeaId) {
		this.appIdeaId = appIdeaId;
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
	
	
}
