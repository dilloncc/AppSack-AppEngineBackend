package com.appsack.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuthResponse {
	
	@Id
	private User user;
	private String apiKey;
	private String error;
	private boolean isInError;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * @return the isInError
	 */
	public boolean isInError() {
		return isInError;
	}
	/**
	 * @param isInError the isInError to set
	 */
	public void setInError(boolean isInError) {
		this.isInError = isInError;
	}
}
