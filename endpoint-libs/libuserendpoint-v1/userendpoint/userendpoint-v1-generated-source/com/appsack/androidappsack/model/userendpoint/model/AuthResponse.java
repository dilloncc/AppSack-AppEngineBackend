/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-02-14 18:40:25 UTC)
 * on 2014-02-27 at 04:01:57 UTC 
 * Modify at your own risk.
 */

package com.appsack.model.userendpoint.model;

/**
 * Model definition for AuthResponse.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class AuthResponse extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String apiKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String error;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean inError;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private User user;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getApiKey() {
    return apiKey;
  }

  /**
   * @param apiKey apiKey or {@code null} for none
   */
  public AuthResponse setApiKey(java.lang.String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getError() {
    return error;
  }

  /**
   * @param error error or {@code null} for none
   */
  public AuthResponse setError(java.lang.String error) {
    this.error = error;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getInError() {
    return inError;
  }

  /**
   * @param inError inError or {@code null} for none
   */
  public AuthResponse setInError(java.lang.Boolean inError) {
    this.inError = inError;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user user or {@code null} for none
   */
  public AuthResponse setUser(User user) {
    this.user = user;
    return this;
  }

  @Override
  public AuthResponse set(String fieldName, Object value) {
    return (AuthResponse) super.set(fieldName, value);
  }

  @Override
  public AuthResponse clone() {
    return (AuthResponse) super.clone();
  }

}
