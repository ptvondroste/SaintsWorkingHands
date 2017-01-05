package com.saints.working;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class DAO_ClientProfile {

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
		configuration.addResource("ClientProfile.hbm.xml");
		configuration.addResource("UserProfile.hbm.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}

	
	 public static int addClientProfile(ClientProfile b) {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		// save this specific record
		int i = (Integer) hibernateSession.save(b);
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		return i;
	} 
	 
	 public static ClientProfile deleteClientProfile(int clientIDClientProfile) {
		 if (factory == null)
			 setupFactory();
		Session hibernateSession = factory.openSession();
		ClientProfile deletedClientProfile = (hibernateSession.get(ClientProfile.class, clientIDClientProfile));
		hibernateSession.getTransaction().begin();
		hibernateSession.delete(deletedClientProfile);
	    hibernateSession.getTransaction().commit();
	    hibernateSession.close();
	    return deletedClientProfile;
	    }
	

	public static void confirmClientProfile(int id){
		if (factory == null)
            setupFactory();
        Session hibernateSession = factory.openSession();
        hibernateSession.getTransaction().begin();
		ClientProfile ClientProfile = (hibernateSession.get(ClientProfile.class, id));
		Date confirmationDate = Calendar.getInstance().getTime();
		//ClientProfile.setStatus("complete");
		//ClientProfile.setConfirmationDate(confirmationDate);
		hibernateSession.update(ClientProfile);
        //hibernateSession.createQuery("UPDATE ClientProfile set status = complete"
        		//+ " where idCompanyClientProfile = :id").list();

        hibernateSession.getTransaction().commit();
        hibernateSession.close();

        
	}
	
	public static void cancelClientProfile(int id){
		if (factory == null)
            setupFactory();
        Session hibernateSession = factory.openSession();
        hibernateSession.getTransaction().begin();
		ClientProfile ClientProfile = (hibernateSession.get(ClientProfile.class, id));
		//ClientProfile.setStatus("cancelled");
		hibernateSession.update(ClientProfile);
        //hibernateSession.createQuery("UPDATE ClientProfile set status = complete"
        		//+ " where idCompanyClientProfile = :id").list();

        hibernateSession.getTransaction().commit();
        hibernateSession.close();
	}
	
}
	/*
	public static List<User_Client_Case> getUser_Client_Case() {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		
		List<User_Client_Case> User_Client_Case = hibernateSession.createQuery("SELECT * from ClientProfile inner join "
				+ "CompanyProfile ON companyClientProfile.companyID =  " 
				

	}
	*/
