package iiitd.piversity.parseModels;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Abhishek on 01-11-2015.
 */
@ParseClassName("GroupClubPage")
public class GroupClubPage extends ParseObject{
    public GroupClubPage(){}

    public String getName() {
        return getString("name");
    }
    public void setName(String name) {
        put("name", name);
    }

    public ParseUser getAdmin() {
        return getParseUser("admin");
    }
    public void setAdmin(ParseUser admin) {
        put("admin", admin);
    }

    public String getInfo() {
        return getString("info");
    }
    public void setInfo(String info) {
        put("info", info);
    }

    public String getType() {
        return getString("type");
    }
    public void setType(String type) {
        put("type", type);
    }
}
