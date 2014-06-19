package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

public class ModelTest extends WithApplication {

	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		start(fakeApplication(inMemoryDatabase()));

	}

	/*
	 * @Test public void createAndRetrieveUser() { new User("bob@gmail.com",
	 * "Bob", "secret").save(); User bob = User.userFinder.where().eq("email",
	 * "bob@gmail.com").findUnique(); assertNotNull(bob); assertEquals("Bob",
	 * bob.name);
	 * 
	 * 
	 * }
	 */@Test
	public void tryAuthenticateUser() {
		new User("bob@gmail.com", "Bob", "secret").save();

		assertNotNull(User.authenticate("bob@gmail.com", "secret"));
		assertNull(User.authenticate("bob@gmail.com", "badpassword"));
		assertNull(User.authenticate("tom@gmail.com", "secret"));
	}

	@Test
	public void findProjectsInvolving() {
		new User("bob@gmail.com", "Bob", "secret").save();
		new User("jane@gmail.com", "Jane", "secret").save();

		Project.create("Play 2", "play", "bob@gmail.com");
		Project.create("Play 1", "play", "jane@gmail.com");

		List<Project> results = Project.findInvolving("bob@gmail.com");
		assertEquals(2, results.size());
		assertEquals("Play 2", results.get(0).name);
	}
}
