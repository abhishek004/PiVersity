package iiitd.piversity.parseModels;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Abhishek on 01-11-2015.
 */

@ParseClassName("Institute")
public class Institute extends ParseObject{
    public Institute(){}
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
    public String getInfo() {
        return getString("info");
    }

    public void setInfo(String info) {
        put("info", info);
    }
}
