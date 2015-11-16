package iiitd.piversity.parseModels;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Abhishek on 02-11-2015.
 */
@ParseClassName("PageUpdate")
public class PageUpdate extends ParseObject{
    public PageUpdate(){}

    public ParseUser getOwner() {
        return getParseUser("owner");
    }
    public void setOwner(ParseUser owner) {
        put("owner", owner);
    }

    public String getPageId() {
        return getString("pageId");
    }
    public void setPageId(String pageId) {
        put("pageId", pageId);
    }

    public String getInfo() {
        return getString("info");
    }
    public void setInfo(String info) {
        put("info", info);
    }

}
