package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class User extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5854422586239724109L;

	@Id
	public String email;

	public String name;

	public String password;

	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public static Finder<String, User> userFinder = new Finder<>(String.class,
			User.class);
	
	public static User authenticate(String email, String password) {
        return userFinder.where().eq("email", email)
            .eq("password", password).findUnique();
    }

}
