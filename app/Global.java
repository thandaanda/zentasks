import java.util.List;

import models.UserDetails;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        // Check if the database is empty
        if (UserDetails.userFinder.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("test-data.yml"));
        }
    }
}