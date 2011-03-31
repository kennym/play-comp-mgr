package jobs;

import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() {
        // Check if the database is empty
        if((ApplicationRole.count() == 0) ||
            (User.count() == 0)){
            Fixtures.load("initial-data.yml");
        }
    }

}