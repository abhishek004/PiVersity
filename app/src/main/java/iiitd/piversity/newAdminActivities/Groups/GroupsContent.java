package iiitd.piversity.newAdminActivities.Groups;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iiitd.piversity.parseModels.Student;

/**
 * Created by sahil on 11/29/15.
 */
public class GroupsContent {
    /* An array of sample (dummy) items.
            */
    public static final List<GroupsItem> ITEMS = new ArrayList<GroupsItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, GroupsItem> ITEM_MAP = new HashMap<String, GroupsItem>();

    private static final int COUNT = 1;

    static {
        // Add some sample items.
        //getData();
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }


    private static void addItem(GroupsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static GroupsItem createDummyItem(int position) {
        return new GroupsItem(String.valueOf(position), "No Groups Found!", "You haven't joined any groups yet.");
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
    public static class GroupsItem {
        public final String id;
        public final String content;
        public final String details;

        public GroupsItem(String id, String content, String details) {
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