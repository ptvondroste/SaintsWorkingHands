package com.saints.working;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class DAO_UserProfile {

	private static SessionFactory factory;

	private static void setupFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			;
		}

		Configuration configuration = new Configuration();
		// modify these to match your XML files
		configuration.configure("hibernate.cfg.xml");
		configuration.addResource("UserProfile.hbm.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static int addUserProfile(UserProfile cp) {

		// encrypts password of passed in UserProfile
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encrypted = passwordEncryptor.encryptPassword(cp.getPassword());
		cp.setPassword(encrypted);

		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();

		// checks for company profiles with existing signInNames

		Query<UserProfile> query = hibernateSession.createQuery("from UserProfile where signInName=?");
		UserProfile user = (UserProfile) query.setString(0, cp.getSignInName()).uniqueResult();

		// if signInName already taken, closes transaction and retuns 0
		if (user != null) {
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
			return 0;
		} else {
			// else if signInName doesn't exist, adds the profile to the database,
			// and returns id
			int id = (Integer) hibernateSession.save(cp);
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
			return id;
		}

	}

	public static UserProfile deleteUserProfile(int idUserProfile) {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		UserProfile deletedUserProfile = (hibernateSession.get(UserProfile.class, idUserProfile));
		hibernateSession.getTransaction().begin();
		hibernateSession.delete(deletedUserProfile);
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		return deletedUserProfile;
	}

	public static List<UserProfile> getAllProfiles() {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		List<UserProfile> profile = hibernateSession.createQuery("FROM UserProfile").list();
		hibernateSession.getTransaction().commit();
		hibernateSession.close();

		return profile;
	}

	public static UserProfile checkLogin(String signInName, String password) {
		// System.out.println("DEBUG: check password=" + password);

		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();

		// prepared statement to protect against injection
		// Returns a company proflie object where the signInName equals the passed
		// in signInName
		Query<UserProfile> sql = hibernateSession.createQuery("FROM UserProfile WHERE signInName=:signInName",
				UserProfile.class);

		// sets the '=:signInName' parameter, to the passed in parameter
		sql.setParameter("signInName", signInName);

		// initializes a null UserProfile object
		UserProfile UserProfile = null;
		try {
			// sets UserProfile and returns single object based on sql query
			// above
			UserProfile = sql.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

		// make sure we can close the session
		try {
			// hibernateSession.getTransaction().commit();
			hibernateSession.close();
		} catch (Exception e) {
			System.out.println("DEBUG: Error caught: " + e);
		}

		// System.out.println(UserProfile.getCompanyName());

		// makes non decryptable passwordEncryptor object
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		// checks if the given password is equal to the hashed password of the
		// company
		if (passwordEncryptor.checkPassword(password, UserProfile.getPassword())) {
			// System.out.println("DEBUG: Password passed");
			return UserProfile;
		} else {
			// System.out.println("DEBUG: Password failed against encrypted");
			return null;
		}
	}

	public static UserProfile getUserProfileById(int idUserProfile) {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		UserProfile UserProfile = (hibernateSession.get(UserProfile.class, idUserProfile));
		hibernateSession.getTransaction().begin();
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		return UserProfile;
	}

	public static void updateUserProfile(UserProfile cp) {

		// encrypts password of passed in UserProfile
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encrypted = passwordEncryptor.encryptPassword(cp.getPassword());
		cp.setPassword(encrypted);
		
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		hibernateSession.update(cp);
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		}

}


