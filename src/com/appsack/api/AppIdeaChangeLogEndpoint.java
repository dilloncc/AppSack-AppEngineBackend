package com.appsack.api;

import com.appsack.EMF;
import com.appsack.model.AppIdeaChangeLog;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "appideachangelogendpoint", namespace = @ApiNamespace(ownerDomain = "appsack.com", ownerName = "appsack.com", packagePath = "androidappsack.model"))
public class AppIdeaChangeLogEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAppIdeaChangeLog")
	public CollectionResponse<AppIdeaChangeLog> listAppIdeaChangeLog(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<AppIdeaChangeLog> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from AppIdeaChangeLog as AppIdeaChangeLog");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<AppIdeaChangeLog>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (AppIdeaChangeLog obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<AppIdeaChangeLog> builder()
				.setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAppIdeaChangeLog")
	public AppIdeaChangeLog getAppIdeaChangeLog(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		AppIdeaChangeLog appideachangelog = null;
		try {
			appideachangelog = mgr.find(AppIdeaChangeLog.class, id);
		} finally {
			mgr.close();
		}
		return appideachangelog;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param appideachangelog the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAppIdeaChangeLog")
	public AppIdeaChangeLog insertAppIdeaChangeLog(
			AppIdeaChangeLog appideachangelog) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsAppIdeaChangeLog(appideachangelog)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(appideachangelog);
		} finally {
			mgr.close();
		}
		return appideachangelog;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param appideachangelog the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAppIdeaChangeLog")
	public AppIdeaChangeLog updateAppIdeaChangeLog(
			AppIdeaChangeLog appideachangelog) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsAppIdeaChangeLog(appideachangelog)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(appideachangelog);
		} finally {
			mgr.close();
		}
		return appideachangelog;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeAppIdeaChangeLog")
	public void removeAppIdeaChangeLog(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			AppIdeaChangeLog appideachangelog = mgr.find(
					AppIdeaChangeLog.class, id);
			mgr.remove(appideachangelog);
		} finally {
			mgr.close();
		}
	}

	private boolean containsAppIdeaChangeLog(AppIdeaChangeLog appideachangelog) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			AppIdeaChangeLog item = mgr.find(AppIdeaChangeLog.class,
					appideachangelog.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
