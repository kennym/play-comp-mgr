import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        // Check if the database is empty
        if((ApplicationRole.count() == 0) ||
            (Usuario.count() == 0)){
            Fixtures.load("initial-data.yml");
        }
    }

}