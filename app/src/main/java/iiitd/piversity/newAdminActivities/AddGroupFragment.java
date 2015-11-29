package iiitd.piversity.newAdminActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.adminActivities.InstituteAdmin;
import iiitd.piversity.parseModels.GroupClubPage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGroupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Bundle extras;
    EditText name;
    EditText des;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGroupFragment newInstance() {
        AddGroupFragment fragment = new AddGroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        name = (EditText)getView().findViewById(R.id.nameAddPage);
//        des = (EditText)getView().findViewById(R.id.desAddPage);
        View inputFragmentView = inflater.inflate(R.layout.fragment_add_group, container, false);
        Button saveButon = (Button) inputFragmentView.findViewById(R.id.saveButtonAddPage);
        name = (EditText)inputFragmentView.findViewById(R.id.nameAddPage);
        des = (EditText)inputFragmentView.findViewById(R.id.desAddPage);
        saveButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked(v);
            }
        });
        return inputFragmentView;
    }



    private void onSaveClicked(View v){
        ParseQuery<GroupClubPage> query = ParseQuery.getQuery(GroupClubPage.class);
        query.whereEqualTo("name", String.valueOf(name.getText()));
        query.findInBackground(new FindCallback<GroupClubPage>() {
            public void done(List<GroupClubPage> itemList, ParseException e) {
                if (e == null) {
                    if(itemList.isEmpty()){
                        GroupClubPage p = new GroupClubPage();
                        p.setName(String.valueOf(name.getText()));
                        p.setInfo(String.valueOf(des.getText()));
                        p.setAdmin(ParseUser.getCurrentUser());
                        p.setType("group");
                        p.setOwner(ParseUser.getCurrentUser().getObjectId());
                        ParseACL acl = new ParseACL();
                        acl.setPublicReadAccess(true);
                        p.setACL(acl);
                        p.saveInBackground();
                        Toast.makeText(getContext(), "New Group Created!!", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(StudentEdit.this, "Data Received", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), AdminHome.class);
                        startActivity(intent);
                        //updateProfile(itemList.get(0));
                    }
                    else{
                        Toast.makeText(getContext(), "A " + extras.get("type") + " with the same name already exists.", Toast.LENGTH_SHORT).show();
                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });
    }
}
