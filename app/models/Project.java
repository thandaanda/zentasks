package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

@Entity
public class Project extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String name;

	public String folder;

	@ManyToMany(cascade = CascadeType.REMOVE)
	public List<UserDetails> members = new ArrayList<UserDetails>();

	public Project(String name, String folder, UserDetails owner) {
		this.name = name;
		this.folder = folder;
		this.members.add(owner);
	}

	public static Model.Finder<Long, Project> projeFinder = new Model.Finder<>(
			Long.class, Project.class);

	public static Project create(String name, String folder, String owner) {
		Project project = new Project(name, folder, UserDetails.userFinder.ref(owner));
		project.save();
		project.saveManyToManyAssociations("members");
		return project;
	}

	public static List<Project> findInvolving(String email) {
		return projeFinder.where().eq("members.email", email).findList();

	}
	
	public static boolean isMember(Long project, String user) {
	    return projeFinder.where()
	        .eq("members.email", user)
	        .eq("id", project)
	        .findRowCount() > 0;
	}

	public static String rename(Long projectId, String newName) {
	    Project project = projeFinder.ref(projectId);
	    project.name = newName;
	    project.update();
	    return newName;
	}

}
