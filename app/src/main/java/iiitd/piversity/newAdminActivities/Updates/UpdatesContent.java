package iiitd.piversity.newAdminActivities.Updates;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.PageUpdate;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class UpdatesContent {


    static Map updates = new HashMap<>();
    /**
     * An array of sample (dummy) items.
     */
    public static final List<UpdatesItem> ITEMS = new ArrayList<UpdatesItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, UpdatesItem> ITEM_MAP = new HashMap<String, UpdatesItem>();

    private static final int COUNT = 1;

    static {
//        ParseACL acl = new ParseACL();
//        acl.setPublicReadAccess(true);
//        p.setACL(acl);


//        List groupnames = new ArrayList<>();
//        List x = ParseInstallation.getCurrentInstallation().getList("channels");
//        for(Object a : x){
//            Log.d("###", a.toString());
//            groupnames.add(a.toString());
//        }
//        ParseQuery<GroupClubPage> query = ParseQuery.getQuery(GroupClubPage.class);
//        query.whereContainedIn("name", groupnames);
//        query.findInBackground(new FindCallback<GroupClubPage>() {
//            public void done(List<GroupClubPage> itemList, ParseException e) {
//                if (e == null) {
//                    if (!itemList.isEmpty()) {
//                        List y = new ArrayList<>();
//                        final Map namesidmap = new HashMap<>();
//                        for (int i = 0; i < itemList.size(); i++) {
//                            Log.d("####", itemList.get(i).getObjectId());
//                            y.add(itemList.get(i).getObjectId());
//                            namesidmap.put(itemList.get(i).getObjectId(), itemList.get(i).getName());
//                        }
//                        ParseQuery<PageUpdate> query2 = ParseQuery.getQuery(PageUpdate.class);
//                        query2.whereContainedIn("pageId", y);
//                        query2.findInBackground(new FindCallback<PageUpdate>() {
//                            public void done(List<PageUpdate> itemList, ParseException e) {
//                                if (e == null) {
//                                    if (!itemList.isEmpty()) {
//                                        Map z = new HashMap<>();
//                                        for (int i = 0; i < itemList.size(); i++) {
//                                            Log.d("######", itemList.get(i).getInfo());
//                                            z.put(itemList.get(i).getPageId(), itemList.get(i).getInfo());
//                                        }
//                                        for (Object a : z.keySet()) {
//                                            updates.put(namesidmap.get(a.toString()), z.get(a.toString()));
//                                        }
//                                        populateitems(updates);
//                                        Log.d("updates size 1", Integer.toString(updates.size()));
////                                        ParseQuery<PageUpdate> query = ParseQuery.getQuery(PageUpdate.class);
////                                        query.whereContainedIn("pageId", Arrays.asList(y));
//                                    }
//                                } else {
//                                    Log.d("item###", "Error: " + e.getMessage());
//                                }
//                            }
//                        });
//                    }
//                } else {
//                    Log.d("item###", "Error: " + e.getMessage());
//                }
//            }
//        });
        for (int i = 1; i <= COUNT; i++) {
            addItem(createUpdatesItem(i));
        }
    }

    private static void populateitems(Map updates){
        // Add some sample items.
        Integer position = 0;
        Log.d("updates size 2", Integer.toString(updates.size()));
        for(Object a: updates.keySet()){
            addItem(createUpdatesItem(position));
            position++;
        }

    }

    private static UpdatesItem createUpdatesItem(Integer position){
        return new UpdatesItem(String.valueOf(position), "No Notifications Yet :D", "Enjoy while you can");
    }

    private static void addItem(UpdatesItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static UpdatesItem createDummyItem(int position) {
        return new UpdatesItem(String.valueOf(position), "updates Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class UpdatesItem {
        public final String id;
        public final String content;
        public final String details;

        public UpdatesItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
