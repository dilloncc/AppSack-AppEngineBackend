package com.appsack.api;

import com.appsack.EMF;
import com.appsack.model.AppIdeaVote;
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

@Api(name = "appideavoteendpoint", namespace = @ApiNamespace(ownerDomain = "appsack.com", ownerName = "appsack.com", packagePath = "androidappsack.model"))
public class AppIdeaVoteEndpoint
{

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAppIdeaVote")
	public CollectionResponse<AppIdeaVote> listAppIdeaVote(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit)
	{

		EntityManager mgr = null;
		Cursor cursor = null;
		List<AppIdeaVote> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from AppIdeaVote as AppIdeaVote");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<AppIdeaVote>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (AppIdeaVote obj : execute)
				;
		}
		finally {
			mgr.close();
		}

		return CollectionResponse.<AppIdeaVote> builder().setItems(execute).setNextPageToken(cursorString).build();
	}

	/**
	 * This method returns the number of votes by appIdea and vote value. It uses HTTP GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "getVotesByAppIdea")
	public List<AppIdeaVote> getVotesByAppIdea(@Named("appIdeaId") Long appIdeaId, @Named("vote") int voteValue)
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

		return execute;
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAppIdeaVote")
	public AppIdeaVote getAppIdeaVote(@Named("id") Long id)
	{
		EntityManager mgr = getEntityManager();
		AppIdeaVote appideavote = null;
		try {
			appideavote = mgr.find(AppIdeaVote.class, id);
		}
		finally {
			mgr.close();
		}
		return appideavote;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already exists in the datastore, an exception
	 * is thrown. It uses HTTP POST method.
	 * 
	 * @param appideavote
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAppIdeaVote")
	public AppIdeaVote insertAppIdeaVote(AppIdeaVote appideavote)
	{
		EntityManager mgr = getEntityManager();
		try {
			// if (containsAppIdeaVote(appideavote)) {
			// throw new EntityExistsException("Object already exists");
			// }
			mgr.persist(appideavote);
		}
		finally {
			mgr.close();
		}
		return appideavote;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not exist in the datastore, an exception
	 * is thrown. It uses HTTP PUT method.
	 * 
	 * @param appideavote
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAppIdeaVote")
	public AppIdeaVote updateAppIdeaVote(AppIdeaVote appideavote)
	{
		EntityManager mgr = getEntityManager();
		try {
			if (!containsAppIdeaVote(appideavote)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(appideavote);
		}
		finally {
			mgr.close();
		}
		return appideavote;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeAppIdeaVote")
	public void removeAppIdeaVote(@Named("id") Long id)
	{
		EntityManager mgr = getEntityManager();
		try {
			AppIdeaVote appideavote = mgr.find(AppIdeaVote.class, id);
			mgr.remove(appideavote);
		}
		finally {
			mgr.close();
		}
	}

	private boolean containsAppIdeaVote(AppIdeaVote appideavote)
	{
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			AppIdeaVote item = mgr.find(AppIdeaVote.class, appideavote.getKey());
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
