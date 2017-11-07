package backend.database;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import backend.datamodel.User;
import database.managers.DatabaseUserManager;

public class TestUserDatabase {
	static DatabaseUserManager databaseUserManager;
	static String createAndGetNewUserMail;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		databaseUserManager = new DatabaseUserManager();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		databaseUserManager.deleteUserByMail(createAndGetNewUserMail);
		databaseUserManager.closeConnection();
	}

	@Test
	public void createAndGetNewUser() {
		String userMail = "" + java.util.UUID.randomUUID() + "@web.com";
		User newUser = new User("Musterman", "password","Max",userMail);
		databaseUserManager.createUser(newUser);
		createAndGetNewUserMail = userMail;
		
		User getUser = databaseUserManager.getUserByMail(userMail);
		equals(userMail.equals(getUser.getMail()));
	}
	
	@Test
	public void deleteUser() {
		String userMail = "" + java.util.UUID.randomUUID() + "@web.com";
		User newUser = new User("Musterman", "password","Max",userMail);
		databaseUserManager.createUser(newUser);
		
		User getUser = databaseUserManager.getUserByMail(userMail);
		
		databaseUserManager.deleteUserByMail(getUser.getMail());
		 
		equals(databaseUserManager.getUserByMail(userMail)==null);
	}
	
	@Test
	public void userNotExist() {
		equals(databaseUserManager.getUserByMail("userNotExist")==null);
	}
	
	@Test
	public void mailIsEmpty() {
		equals(databaseUserManager.getUserByMail("userNotExist")==null);
	}
}