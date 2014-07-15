package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;

@Entity
public class Task extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Required
	public String title;

	public boolean done = false;

	@Formats.DateTime(pattern = "MM/dd/yyyy")
	public Date dueDate;

	@ManyToOne
	public UserDetails assignedTo;

	public String folder;

	@ManyToOne
	public Project project;

	public static Model.Finder<Long, Task> findTask = new Model.Finder<>(
			Long.class, Task.class);

	public static List<Task> findTodoInvolving(String user) {
		return findTask.fetch("project").where().eq("done", false)
				.eq("project.members.email", user).findList();
	}

	public static Task create(Task task, Long project, String folder) {
		task.project = Project.projeFinder.ref(project);
		task.folder = folder;
		task.save();
		return task;
	}
	public static List<Task> findByProject(Long project) {
	    return Task.findTask.where()
	        .eq("project.id", project)
	        .findList();
	}
	
	

}
