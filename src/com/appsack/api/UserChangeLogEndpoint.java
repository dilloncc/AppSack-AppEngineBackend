package com.appsack.api;

import com.appsack.EMF;
import com.appsack.model.UserChangeLog;
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

@Api(name = "userchangelogendpoint", namespace = @ApiNamespace(ownerDomain = "appsack.com", ownerName = "appsack.com", packagePath = "androidappsack.model"))
public class UserChangeLogEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUserChangeLog")
	public CollectionResponse<UserChangeLog> listUserChangeLog(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<UserChangeLog> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from UserChangeLog as UserChangeLog");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<UserChangeLog>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (UserChangeLog obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<UserChangeLog> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUserChangeLog")
	public UserChangeLog getUserChangeLog(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		UserChangeLog userchangelog = null;
		try {
			userchangelog = mgr.find(UserChangeLog.class, id);
		} finally {
			mgr.close();
		}
		return userchangelog;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param userchangelog the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUserChangeLog")
	public UserChangeLog insertUserChangeLog(UserChangeLog userchangelog) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUserChangeLog(userchangelog)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(userchangelog);
		} finally {
			mgr.close();
		}
		return userchangelog;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param userchangelog the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUserChangeLog")
	public UserChangeLog updateUserChangeLog(UserChangeLog userchangelog) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUserChangeLog(userchangelog)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(userchangelog);
		} finally {
			mgr.close();
		}
		return userchangelog;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUserChangeLog")
	public void removeUserChangeLog(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			UserChangeLog userchangelog = mgr.find(UserChangeLog.class, id);
			mgr.remove(userchangelog);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUserChangeLog(UserChangeLog userchangelog) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			UserChangeLog item = mgr.find(UserChangeLog.class,
					userchangelog.getKey());
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
