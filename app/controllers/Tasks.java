package controllers;

import models.Project;
import models.Task;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.tasks.item;
import static play.data.Form.*;

@Security.Authenticated(Secured.class)
public class Tasks extends Controller {

	public static Result index(Long project) {
		if (Secured.isMemberOf(project)) {
			return ok(views.html.tasks.index.render(
					Project.projeFinder.ref(project),
					Task.findByProject(project)));

		} else {
			return forbidden();
		}

	}

	public static Result add(Long project, String folder) {
		if (Secured.isMemberOf(project)) {
			Form<Task> taskForm = form(Task.class).bindFromRequest();
			if (taskForm.hasErrors()) {
				return badRequest();
			} else {
				return ok(item.render(Task.create(taskForm.get(), project,
						folder)));
			}
		} else {
			return forbidden();
		}
	}
}
