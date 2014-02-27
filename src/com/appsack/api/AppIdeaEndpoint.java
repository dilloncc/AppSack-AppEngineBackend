package com.appsack.api;

import com.appsack.EMF;
import com.appsack.model.AppIdea;
import com.appsack.model.AppIdeaVote;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "appideaendpoint", namespace = @ApiNamespace(ownerDomain = "appsack.com", ownerName = "appsack.com", packagePath = "androidappsack.model"))
public class AppIdeaEndpoint
{

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAppIdea")
	public CollectionResponse<AppIdea> listAppIdea(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit)
	{

		EntityManager mgr = null;
		Cursor cursor = null;
		List<AppIdea> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from AppIdea as AppIdea");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<AppIdea>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (AppIdea obj : execute)
				;
		}
		finally {
			mgr.close();
		}

		return CollectionResponse.<AppIdea> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAppIdeasByUserId")
	public CollectionResponse<AppIdea> listAppIdeasByUserId(@Named("userId") Long userId, @Named("vote") int vote,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit)
	{
		EntityManager mgr = null;
		Cursor cursor = null;
		List<AppIdea> execute = null;
		List<AppIdea> appIdeasList = new ArrayList<AppIdea>();

		try {
			mgr = getEntityManager();

			String sql = "SELECT a FROM " + AppIdea.class.getName() + " a WHERE a.userId = :userId";
			// Query query = mgr.createQuery("select from AppIdea as AppIdea where userId = :userId");
			Query query = mgr.createQuery(sql);

			query.setParameter("userId", userId);

			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<AppIdea>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (AppIdea obj : execute)
				;

			// for (AppIdea obj : execute) {
			//
			// // get the number of up votes
			// int numVotes = getNumVotesByAppIdea(obj.getKey().getId(), vote);
			//
			// obj.setNumVotes(numVotes);
			//
			// appIdeasList.add(obj);
			// }

		}
		finally {
			mgr.close();
		}

		return CollectionResponse.<AppIdea> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	private int getNumVotesByAppIdea(Long appIdeaId, int voteValue)
	{
		EntityManager mgr = null;
		List<AppIdeaVote> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("SELECT a FROM " + AppIdeaVote.class.getName()
					+ " a WHERE a.vote = :vote and a.appIdeaId = :id");

			query.setParameter("id", appIdeaId);
			query.setParameter("vote", voteValue);

			execute = (List<AppIdeaVote>) query.getResultList();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			// for (AppIdeaVote obj : execute)
			// ;
		}
		finally {
			mgr.close();
		}

		return execute.size();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAppIdea")
	public AppIdea getAppIdea(@Named("id") Long id)
	{
		EntityManager mgr = getEntityManager();
		AppIdea appidea = null;
		try {
			appidea = mgr.find(AppIdea.class, id);
		}
		finally {
			mgr.close();
		}
		return appidea;
	}

	private int getAppIdeaVote(@Named("id") Long id)
	{
		EntityManager mgr = getEntityManager();
		AppIdeaVote appideavote = null;
		try {
			appideavote = mgr.find(AppIdeaVote.class, id);
		}
		finally {
			mgr.close();
		}
		return 0;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already exists in the datastore, an exception
	 * is thrown. It uses HTTP POST method.
	 * 
	 * @param appidea
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAppIdea")
	public AppIdea insertAppIdea(AppIdea appidea)
	{

		EntityManager mgr = getEntityManager();
		try {
			// if (containsAppIdea(newAppIdea)) {
			// throw new EntityExistsException("Object already exists");
			// }
			mgr.persist(appidea);
		}
		finally {
			mgr.close();
		}
		return appidea;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not exist in the datastore, an exception
	 * is thrown. It uses HTTP PUT method.
	 * 
	 * @param appidea
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAppIdea")
	public AppIdea updateAppIdea(AppIdea appidea)
	{
		EntityManager mgr = getEntityManager();
		try {
			if (!containsAppIdea(appidea)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(appidea);
		}
		finally {
			mgr.close();
		}
		return appidea;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeAppIdea")
	public void removeAppIdea(@Named("id") Long id)
	{
		EntityManager mgr = getEntityManager();
		try {
			AppIdea appidea = mgr.find(AppIdea.class, id);
			mgr.remove(appidea);
		}
		finally {
			mgr.close();
		}
	}

	private boolean containsAppIdea(AppIdea appidea)
	{
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			AppIdea item = mgr.find(AppIdea.class, appidea.getKey());
			if (item == null) {
				contains = false;
			}
		}
		finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager()
	{
		return EMF.get().createEntityManager();
	}

}
