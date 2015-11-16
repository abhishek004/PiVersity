package iiitd.piversity.parseModels;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Abhishek on 25-10-2015.
 */
@ParseClassName("Student")
public class Student extends ParseObject{
    public Student(){

    }
    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }
    public String getPh() {
        return getString("ph");
    }

    public void setPh(String ph) {
        put("ph", ph);
    }
    public String getClubs() {
        return getString("clubs");
    }

    public void setClubs(String clubs) {
        put("clubs", clubs);
    }

    public String getGroups() {
        return getString("groups");
    }

    public void setGroups(String Groups) {
        put("groups", Groups);
    }
    public String getProjects() {
        return getString("projects");
    }

    public void setProjects(String Projects) {
        put("projects", Projects);
    }

    public String getExp() {
        return getString("exp");
    }

    public void setExp(String Exp) {
        put("exp", Exp);
    }
    public String getSkills() {
        return getString("skills");
    }

    public void setSkills(String skills) {
        put("skills", skills);
    }



}
