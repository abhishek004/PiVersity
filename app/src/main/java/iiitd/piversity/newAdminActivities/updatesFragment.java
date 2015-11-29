package iiitd.piversity.newAdminActivities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iiitd.piversity.R;
import iiitd.piversity.newAdminActivities.Updates.UpdatesContent;
import iiitd.piversity.newAdminActivities.Updates.UpdatesContent.UpdatesItem;
import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.PageUpdate;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnUpdatesListFragmentInteractionListener}
 * interface.
 */
public class updatesFragment extends Fragment{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnUpdatesListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public updatesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static updatesFragment newInstance(int columnCount) {
        updatesFragment fragment = new updatesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updates_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            List groupnames = new ArrayList<>();
            List x = ParseInstallation.getCurrentInstallation().getList("channels");
            for(Object a : x){
                Log.d("###", a.toString());
                groupnames.add(a.toString());
            }
            ParseQuery<GroupClubPage> query = ParseQuery.getQuery(GroupClubPage.class);
            query.whereContainedIn("name", groupnames);
            query.findInBackground(new FindCallback<GroupClubPage>() {
                public void done(List<GroupClubPage> itemList, ParseException e) {
                    if (e == null) {
                        if (!itemList.isEmpty()) {
                            List y = new ArrayList<>();
                            final Map namesidmap = new HashMap<>();
                            for (int i = 0; i < itemList.size(); i++) {
                                Log.d("####", itemList.get(i).getObjectId());
                                y.add(itemList.get(i).getObjectId());
                                namesidmap.put(itemList.get(i).getObjectId(), itemList.get(i).getName());
                            }
                            ParseQuery<PageUpdate> query2 = ParseQuery.getQuery(PageUpdate.class);
                            query2.whereContainedIn("pageId", y);
                            query2.findInBackground(new FindCallback<PageUpdate>() {
                                public void done(List<PageUpdate> itemList, ParseException e) {
                                    if (e == null) {
                                        if (!itemList.isEmpty()) {
                                            Map z = new HashMap<>();
                                            for (int i = 0; i < itemList.size(); i++) {
                                                Log.d("######", itemList.get(i).getInfo());
                                                z.put(itemList.get(i).getPageId(), itemList.get(i).getInfo());
                                            }
                                            Map updates = new HashMap<>();

                                            for (Object a : z.keySet()) {
                                                updates.put(namesidmap.get(a.toString()), z.get(a.toString()));
                                            }
                                            populateitems(updates, recyclerView);
                                            Log.d("updates size 1", Integer.toString(updates.size()));
//                                        ParseQuery<PageUpdate> query = ParseQuery.getQuery(PageUpdate.class);
//                                        query.whereContainedIn("pageId", Arrays.asList(y));
                                        }
                                    } else {
                                        Log.d("item###", "Error: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    } else {
                        Log.d("item###", "Error: " + e.getMessage());
                    }
                }
            });

            recyclerView.setAdapter(new MyupdatesRecyclerViewAdapter(UpdatesContent.ITEMS, mListener));

        }
        return view;
    }

    private void populateitems(Map updates, RecyclerView recyclerView){
        // Add some sample items.
        List<UpdatesItem> ITEMS = new ArrayList<UpdatesItem>();
        Integer position = 0;
        Log.d("updates size 2", Integer.toString(updates.size()));
        for(Object a: updates.keySet()){
            Log.d("@@@", a.toString());
            Log.d("@@@", updates.get(a).toString());
            ITEMS.add(new UpdatesItem(String.valueOf(position), updates.get(a).toString(), a.toString() ));
            position++;
        }
        recyclerView.setAdapter(new MyupdatesRecyclerViewAdapter(ITEMS, mListener));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpdatesListFragmentInteractionListener) {
            mListener = (OnUpdatesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGroupsListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUpdatesListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onUpdatesListFragmentInteraction(UpdatesItem item);
    }
}
