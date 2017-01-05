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

import com.saints.working.UserProfile;
import com.saints.working.ClientCaseFile;
import com.saints.working.User_Client_Case;

public class DAO_ClientCaseFile {

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
			configuration.addResource("CaseFile.hbm.xml");
			configuration.addResource("ClientProfile.hbm.xml");
			configuration.addResource("UserProfile.hbm.xml");
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			factory = configuration.buildSessionFactory(serviceRegistry);
		}

		
		 public static int addClientCaseFile(ClientCaseFile b) {
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
		 
		 public static ClientCaseFile deleteClientCaseFile(int clientIDClientCaseFile) {
			 if (factory == null)
				 setupFactory();
			Session hibernateSession = factory.openSession();
			ClientCaseFile deletedClientCaseFile = (hibernateSession.get(ClientCaseFile.class, clientIDClientCaseFile));
			hibernateSession.getTransaction().begin();
			hibernateSession.delete(deletedClientCaseFile);
		    hibernateSession.getTransaction().commit();
		    hibernateSession.close();
		    return deletedClientCaseFile;
		    }
		

		public static List<User_Client_Case> getAllUser_Client_Case() {
			if (factory == null)
				setupFactory();
			Session hibernateSession = factory.openSession();
			hibernateSession.getTransaction().begin();
			//initializes case list
			ArrayList<User_Client_Case> cases = new ArrayList<User_Client_Case>();
			//gets list of ClientCaseFiles ordered by date
			List<ClientCaseFile> ClientCaseFiles = hibernateSession.createQuery("FROM ClientCaseFile").list();
				for (ClientCaseFile d : ClientCaseFiles) {
					//gets the company associated with the ClientCaseFile ID
					ClientProfile clientprofile = hibernateSession.get(ClientProfile.class, d.getClientID());
					
					//adds itemForPickUp to case list, which contains a company object and a ClientCaseFile object
					cases.add(new User_Client_Case(clientprofile, d));
				}
				
			hibernateSession.getTransaction().commit();
			hibernateSession.close();

			return cases;
		}
		
		public static List<ClientCaseFile> getAllClientCaseFiles() {
            if (factory == null)
                setupFactory();
            Session hibernateSession = factory.openSession();
            hibernateSession.getTransaction().begin();

            List<ClientCaseFile> ClientCaseFiles = hibernateSession.createQuery("FROM ClientCaseFile").list();
            
            hibernateSession.getTransaction().commit();
            hibernateSession.close();

            return ClientCaseFiles;
		}
		
		public static void confirmClientCaseFile(int id){
			if (factory == null)
                setupFactory();
            Session hibernateSession = factory.openSession();
            hibernateSession.getTransaction().begin();
			ClientCaseFile ClientCaseFile = (hibernateSession.get(ClientCaseFile.class, id));
			Date confirmationDate = Calendar.getInstance().getTime();
			//ClientCaseFile.setStatus("complete");
			//ClientCaseFile.setConfirmationDate(confirmationDate);
			hibernateSession.update(ClientCaseFile);
            //hibernateSession.createQuery("UPDATE ClientCaseFile set status = complete"
            		//+ " where idCompanyClientCaseFile = :id").list();

            hibernateSession.getTransaction().commit();
            hibernateSession.close();

            
		}
		
		public static void cancelClientCaseFile(int id){
			if (factory == null)
                setupFactory();
            Session hibernateSession = factory.openSession();
            hibernateSession.getTransaction().begin();
			ClientCaseFile ClientCaseFile = (hibernateSession.get(ClientCaseFile.class, id));
			//ClientCaseFile.setStatus("cancelled");
			hibernateSession.update(ClientCaseFile);
            //hibernateSession.createQuery("UPDATE ClientCaseFile set status = complete"
            		//+ " where idCompanyClientCaseFile = :id").list();

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
			
			List<User_Client_Case> User_Client_Case = hibernateSession.createQuery("SELECT * from ClientCaseFile inner join "
					+ "CompanyProfile ON companyClientCaseFile.companyID =  " 
					

		}
		*/
	