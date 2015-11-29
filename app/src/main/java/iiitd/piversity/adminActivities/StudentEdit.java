package iiitd.piversity.adminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.newAdminActivities.StudentHome;
import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.Student;

public class StudentEdit extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText email;
    EditText ph;
    TextView clubs;
    TextView groups;
    EditText projects;
    EditText exp;
    EditText skills;
    Spinner groupsSpinner,clubsSpinner;

    ArrayList<String> availableGroups;
    ArrayList<String> availableClubs;
    HashMap<String,String> availablePages;

    ArrayList<String> selectedPages;
    ArrayList<String> listGroups;
    ArrayList<String> listClubs;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);
        extras=getIntent().getExtras();
        name = (EditText)findViewById(R.id.nameStudentEdit);
        email = (EditText)findViewById(R.id.emailStudentEdit);
        ph = (EditText)findViewById(R.id.phoneStudentEdit);
        clubs = (TextView)findViewById(R.id.clubsStudentEdit);
        groups = (TextView)findViewById(R.id.groupsStudentEdit);
        projects = (EditText)findViewById(R.id.projectsStudentEdit);
        exp = (EditText)findViewById(R.id.expStudentEdit);
        skills = (EditText)findViewById(R.id.skillsStudentEdit);
        listClubs = new ArrayList<>();
        listGroups = new ArrayList<>();
        selectedPages = new ArrayList<>();
        availablePages = new HashMap<>();
        availableClubs = new ArrayList<>();
        availableGroups = new ArrayList<>();
        getPages();
        /*setGroupsSpinner();
        setClubsSpinner();
        setFields();*/
        findViewById(R.id.saveButton).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPages(){
        ParseQuery<ParseUser> innerQuery = ParseUser.getQuery();
        innerQuery.whereEqualTo("username", "iiitd");

        ParseQuery<GroupClubPage> query2 = ParseQuery.getQuery(GroupClubPage.class);
        query2.whereMatchesQuery("admin", innerQuery);
        query2.findInBackground(new FindCallback<GroupClubPage>() {
            public void done(List<GroupClubPage> itemList2, ParseException e) {
                if (e == null) {
                    if (!itemList2.isEmpty()) {
                        availableGroups.add("Select Group");
                        availableClubs.add("Select Club");
                        Log.i("XXX", String.valueOf(itemList2.size()));
                        for (int i=0;i<itemList2.size();i++){
                            availablePages.put(itemList2.get(i).getName(),itemList2.get(i).getObjectId());
                            if(itemList2.get(i).getType().equals("group")){
                                availableGroups.add(itemList2.get(i).getName());
                            }
                            else if(itemList2.get(i).getType().equals("club")){
                                availableClubs.add(itemList2.get(i).getName());
                            }
                        }
                        availableGroups.add("Clear all selections");
                        availableClubs.add("Clear all selections");
                        setGroupsSpinner();
                        setClubsSpinner();
                        setFields();
                        Toast.makeText(StudentEdit.this, "Data Received" + itemList2.get(0).getName(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentEdit.this, "No pages found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void setFields(){
        if(!extras.getBoolean("initialEdit")){
            name.setText(extras.getString("name"));
            email.setText(extras.getString("email"));
            ph.setText(extras.getString("ph"));
            groups.setText(extras.getString("groups"));
            clubs.setText(extras.getString("clubs"));
            projects.setText(extras.getString("projects"));
            exp.setText(extras.getString("exp"));
            skills.setText(extras.getString("skills"));
        } else{
            name.setText(extras.getString("name"));
            email.setText(extras.getString("email"));
        }
    }

    private void setGroupsSpinner(){
        groupsSpinner = (Spinner) findViewById(R.id.groupsSpinner);
        groupsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = groupsSpinner.getSelectedItem().toString();
                if(val.equals("Clear all selections")){
                    listGroups.clear();
                    updateGroups();
                }
                else if(!listGroups.contains(val) && !val.equals("Select Group")){
                    listGroups.add(val);
                    updateGroups();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //addGroups();
        /*List<String> list = new ArrayList<String>();
        list.add("Select Group");
        list.add("Administration");
        list.add("BTech-2013");*/
        //availableGroups.add("Clear all selections");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, availableGroups);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupsSpinner.setAdapter(dataAdapter);
    }

    private void updateGroups(){
        String g = TextUtils.join(",", listGroups);
        groups.setText(g);
    }

    private void setClubsSpinner(){
        clubsSpinner = (Spinner) findViewById(R.id.clubsSpinner);
        clubsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = clubsSpinner.getSelectedItem().toString();
                Log.i("spinnerXX",clubsSpinner.getSelectedItem().toString());
                if(val.equals("Clear all selections")){
                    listClubs.clear();
                    updateClubs();
                }
                else if(!listClubs.contains(val) && !val.equals("Select Club")){
                    listClubs.add(val);
                    Log.i("spinnerXX", "added");
                    updateClubs();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //addGroups();
        /*List<String> list = new ArrayList<String>();
        list.add("Select Club");
        list.add("FooBar");
        list.add("Byld");
        list.add("Design");
        list.add("Photography");
        list.add("Astronomy");*/
        //list.add("Clear all selections");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, availableClubs);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clubsSpinner.setAdapter(dataAdapter);
    }

    private void updateClubs(){
        String g = TextUtils.join(",", listClubs);
        clubs.setText(g);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.saveButton){
            onSaveClicked();
        }
    }

    private void onSaveClicked(){
        selectedPages.clear();
        LinkedList<String> channels = new LinkedList<String>();

        //LinkedList<String> temp = new LinkedList<String>();
        List<String> temp = new ArrayList<String>();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        int j=0;
        /*installation.put("channels", selectedPages);
        installation.saveInBackground();*/
        for(int i=0;i<listClubs.size();i++){
            selectedPages.add(availablePages.get(listClubs.get(i)));
            //ParsePush.subscribeInBackground(availablePages.get(listClubs.get(i)));
            temp.add(listClubs.get(i));
        }
        for(int i=0;i<listGroups.size();i++){
            selectedPages.add(availablePages.get(listGroups.get(i)));
            //ParsePush.subscribeInBackground(availablePages.get(listGroups.get(i)));
            temp.add(listGroups.get(i));
        }
        //ParsePush.subscribeInBackground(String.valueOf(temp));
        Log.i("selectedPages", String.valueOf(selectedPages));
        installation.put("channels", temp);
        installation.saveInBackground();

        /*ParsePush.subscribeInBackground(String.valueOf(availableClubs), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                //Log.e("pNotify", "Successfully subscribed to Parse!");
            }
        });*/
        ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Student>() {
            public void done(List<Student> itemList, ParseException e) {
                if (e == null) {
                    if(!itemList.isEmpty()){
                        Student s = itemList.get(0);
                        s.setName(String.valueOf(name.getText()));
                        s.setEmail(String.valueOf(email.getText()));
                        s.setClubs(String.valueOf(clubs.getText()));
                        s.setExp(String.valueOf(exp.getText()));
                        s.setGroups(String.valueOf(groups.getText()));
                        s.setPh(String.valueOf(ph.getText()));
                        s.setProjects(String.valueOf(projects.getText()));
                        s.setSkills(String.valueOf(skills.getText()));
                        s.setUser(ParseUser.getCurrentUser());
                        s.saveInBackground();
                        //Toast.makeText(StudentEdit.this, "Data Received", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentEdit.this, StudentHome.class);
                        startActivity(intent);
                        //updateProfile(itemList.get(0));
                    }
                    else{
                        Student s = new Student();
                        s.setUser(ParseUser.getCurrentUser());
                        Toast.makeText(StudentEdit.this, "Please complete your profile", Toast.LENGTH_SHORT).show();
                        s.saveInBackground();
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
