package com.appsack.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.appsack.EMF;
import com.appsack.model.AuthResponse;
import com.appsack.model.User;
import com.appsack.utils.HashUtil;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "userendpoint", namespace = @ApiNamespace(ownerDomain = "appsack.com", ownerName = "appsack.com", packagePath = "androidappsack.model"))
public class UserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUser")
	public CollectionResponse<User> listUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from User as User");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<User>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (User obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */

	private User getUser(Long id) {
		EntityManager mgr = getEntityManager();
		User user = null;
		try {
			user = mgr.find(User.class, id);
		} finally {
			mgr.close();
		}
		return user;
	}

	@ApiMethod(name = "logInUser")
	public AuthResponse logInUser(@Named("email") String email,
			@Named("password") String password) {
		EntityManager mgr = getEntityManager();

		AuthResponse response = new AuthResponse();

		User tmpUser = getUserByEmail(email);

		if (tmpUser == null) {
			response.setInError(true);
			response.setError("Email and/or password does not match any of our results.  Please try again.");

			return response;
		}

		try {
			String sql = "SELECT u FROM " + User.class.getName()
					+ " u WHERE u.email = :email and u.password = :password";
			Query query = mgr.createQuery(sql);
			query.setParameter("email", email);
			query.setParameter(
					"password",
					HashUtil.getEncryptedPassword(password,
							tmpUser.getInsertedDate()));
			List<User> results = new ArrayList<User>(query.getResultList());

			if (results == null || results.size() == 0) {

				response.setInError(true);
				response.setError("Email and/or password does not match any of our results.  Please try again.");

				return response;
			} else {
				User user = results.get(0);

				response.setUser(user);

				// store the user in a cookie
				String apiKey = generateAPIKey(user.getKey().getId(), user.getPassword(), user.getInsertedDate().getTime());

				response.setApiKey(apiKey);

				return response;
			}
		} finally {
			mgr.close();
		}
	}

	private User getUserByEmail(String email) {

		EntityManager entityManager = EMF.get().createEntityManager();

		try {
			String sql = "SELECT u FROM " + User.class.getName()
					+ " u WHERE u.email = :email";
			Query query = entityManager.createQuery(sql);
			query.setParameter("email", email);
			List<User> results = new ArrayList<User>(query.getResultList());

			if (results != null && results.size() != 0) {

				return results.get(0);
			}
		} finally {
			entityManager.close();
		}

		return null;
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getCurrentUser")
	public User getCurrentUser(@Named("apiKey") String apiKey) {
		EntityManager mgr = getEntityManager();
		User user = null;

		try {
			user = mgr.find(User.class, getCurrentUserKey(apiKey).getId());
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param user
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertNewUser")
	public AuthResponse insertNewUser(@Named("email") String email,
			@Named("password") String password,
			@Named("firstName") String firstName,
			@Named("lastName") String lastName,
			@Named("displayName") String displayName) {
		EntityManager mgr = getEntityManager();

		AuthResponse response = new AuthResponse();

		User user = new User();

		Date insertedDate = new Date();

		user.setEmail(email);
		user.setPassword(HashUtil.getEncryptedPassword(password, insertedDate));
		user.setInsertedDate(insertedDate);
		user.setUpdatedDate(insertedDate);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDisplayName(displayName);

		try {
			// if (containsUser(user)) {
			// throw new EntityExistsException("Object already exists");
			// }
			mgr.persist(user);
		} finally {
			mgr.close();
		}

		response.setUser(user);
		response.setApiKey(generateAPIKey(user.getKey().getId(),
				user.getPassword(), insertedDate.getTime()));

		return response;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param user
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUser")
	public User insertUser(User user) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUser(user)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(user);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param user
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUser")
	public User updateUser(User user) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUser(user)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(user);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUser")
	public void removeUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			User user = mgr.find(User.class, id);
			mgr.remove(user);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUser(User user) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			// TODO check for user.getEmail() -- email should be unique too
			User item = mgr.find(User.class, user.getKey());
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

	private Key getCurrentUserKey(String apiKey) {

		if (apiKey == null)
			return null;

		String[] cookieElements = parseCookieElements(apiKey);

		if (cookieElements == null)
			return null;

		String userId = cookieElements[0];
		String insertedDate = cookieElements[1];
		String hash = cookieElements[2];

		// DateFormat dateFormat = new SimpleDateFormat();
		Date date;

		try {
			date = new Date(Long.parseLong(insertedDate));// dateFormat.parse(insertedDate);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		User user = getUser(Long.parseLong(userId));

		if (user == null) {
			return null;
		}

		String passwordFragment = user.getPassword().substring(3, 7);

		String key = HashUtil.generateHash(user.getKey().getId()
				+ passwordFragment + "|" + insertedDate, HashUtil.SALT_2);

		String generatedHash = HashUtil.generateHash(user.getKey().getId()
				+ "|" + insertedDate, key);

		if (!generatedHash.equals(hash))
			return null;

		return user.getKey();
	}

	private String[] parseCookieElements(String authCookie) {

		String[] cookieElements = StringUtils.split(
				authCookie.replace("%7C", "|"), "|");

		if (cookieElements.length != 3)
			return null;

		return cookieElements;
	}

	private String generateAPIKey(long id, String userPassword,
			long insertedDate) {

		String passwordFragment = userPassword.substring(3, 7); // wordpress
																// uses start at
																// 8 and length
																// 4
																// user.getPassword().substring(8,4);

		String hashKey = HashUtil.generateHash(id + passwordFragment + "|"
				+ insertedDate, HashUtil.SALT_2);

		String hash = HashUtil.generateHash(id + "|" + insertedDate, hashKey);

		String apiKey = id + "|" + insertedDate + "|" + hash;

		return apiKey;
	}
}
