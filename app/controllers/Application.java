package controllers;

import static play.data.Form.form;
import models.Project;
import models.Task;
import models.UserDetails;
import play.Logger.ALogger;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import views.html.login;

public class Application extends Controller {

	
	@Security.Authenticated(Secured.class)
	public static Result index() {
		
		ALogger alLogger = play.Logger.of(Application.class);
		alLogger.info("username : " + request().username());
		return ok(index.render(
		        Project.findInvolving(request().username()), 
		        Task.findTodoInvolving(request().username()),
		        UserDetails.userFinder.byId(request().username())
		    )); 
		
	}

	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	public static class Login {
		public String email;
		public String password;

		ALogger aLogger = play.Logger.of(Application.class);
		
		public String validate() {
			
			aLogger.info("username : " + email + ", password: " + password);
			if (UserDetails.authenticate(email, password) == null) {
				aLogger.info("what value");
				return "Invalid user or password";
			}
			aLogger.info("is it null");
			return null;
		}
	}

	public static Result authenticate() {

		ALogger aLogger = play.Logger.of(Application.class);
		
		aLogger.info("In Authenticate method");
		
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		
		aLogger.info("loginform has errors" + loginForm.hasErrors());
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));

		} else {
			session().clear();
			session("email", loginForm.get().email);
			aLogger.info("Index page redirect");
			return redirect(routes.Application.index());
		}

	}
	
	public static Result logout() {
	    session().clear();
	    flash("success", "You've been logged out");
	    return redirect(
	        routes.Application.login()
	    );
	}
	
	public static Result javascriptRoutes() {
	    response().setContentType("text/javascript");
	    return ok(
	        Routes.javascriptRouter("jsRoutes",
	            controllers.routes.javascript.Projects.add(),
	            controllers.routes.javascript.Projects.delete(),
	            controllers.routes.javascript.Projects.rename(),
	            controllers.routes.javascript.Projects.addGroup(),
	            controllers.routes.javascript.Projects.addGroup(),
	            controllers.routes.javascript.Tasks.add())
	    );
	}

}
