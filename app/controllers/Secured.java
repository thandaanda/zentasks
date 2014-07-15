package controllers;

import models.Project;
import play.Logger.ALogger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

	ALogger alLogger = play.Logger.of(Secured.class);
	
	public String getUserName(Context ctx) {
		alLogger.info("Name of the user: " + ctx.session().get("email"));
		return ctx.session().get("email");
	}

	public Result onUnauthorized(Context ctx) {
		alLogger.info("Name of the user: " + ctx.session().get("email"));
		return redirect(routes.Application.login());
	}

	public static boolean isMemberOf(Long project) {
		return Project
				.isMember(project, Context.current().request().username());
	}

}
