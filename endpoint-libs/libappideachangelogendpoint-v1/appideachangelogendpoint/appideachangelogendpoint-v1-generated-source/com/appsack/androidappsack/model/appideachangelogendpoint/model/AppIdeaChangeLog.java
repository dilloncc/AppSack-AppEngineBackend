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
 * on 2014-02-27 at 04:01:42 UTC 
 * Modify at your own risk.
 */

package com.appsack.model.appideachangelogendpoint.model;

/**
 * Model definition for AppIdeaChangeLog.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the appideachangelogendpoint. For a detailed explanation
 * see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class AppIdeaChangeLog extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long appIdeaId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime dateChanged;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fieldChanged;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String valueBeforeChange;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getAppIdeaId() {
    return appIdeaId;
  }

  /**
   * @param appIdeaId appIdeaId or {@code null} for none
   */
  public AppIdeaChangeLog setAppIdeaId(java.lang.Long appIdeaId) {
    this.appIdeaId = appIdeaId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDateChanged() {
    return dateChanged;
  }

  /**
   * @param dateChanged dateChanged or {@code null} for none
   */
  public AppIdeaChangeLog setDateChanged(com.google.api.client.util.DateTime dateChanged) {
    this.dateChanged = dateChanged;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFieldChanged() {
    return fieldChanged;
  }

  /**
   * @param fieldChanged fieldChanged or {@code null} for none
   */
  public AppIdeaChangeLog setFieldChanged(java.lang.String fieldChanged) {
    this.fieldChanged = fieldChanged;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public AppIdeaChangeLog setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getValueBeforeChange() {
    return valueBeforeChange;
  }

  /**
   * @param valueBeforeChange valueBeforeChange or {@code null} for none
   */
  public AppIdeaChangeLog setValueBeforeChange(java.lang.String valueBeforeChange) {
    this.valueBeforeChange = valueBeforeChange;
    return this;
  }

  @Override
  public AppIdeaChangeLog set(String fieldName, Object value) {
    return (AppIdeaChangeLog) super.set(fieldName, value);
  }

  @Override
  public AppIdeaChangeLog clone() {
    return (AppIdeaChangeLog) super.clone();
  }

}