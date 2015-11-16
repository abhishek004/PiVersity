package iiitd.piversity.application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.Institute;
import iiitd.piversity.parseModels.PageUpdate;
import iiitd.piversity.parseModels.Student;

/**
 * Created by Abhishek on 24-10-2015.
 */
public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Student.class);
        ParseObject.registerSubclass(Institute.class);
        ParseObject.registerSubclass(GroupClubPage.class);
        ParseObject.registerSubclass(PageUpdate.class);
        // Add your initialization code here
        Parse.initialize(this, "fvqPYOyzptieL3fbQqre6QAwLzB0JhkY2nZxtaUl", "g8w9ursbuhaTDbHggRibJ0vxH03uHtB7kNq5DIau");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
