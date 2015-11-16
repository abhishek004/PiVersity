package iiitd.piversity.adminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.otherActivities.MainActivity;
import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.Student;

public class StudentAdmin extends AppCompatActivity implements View.OnClickListener {
    TextView name;
    TextView email;
    TextView ph;
    TextView clubs;
    TextView groups;
    TextView projects;
    TextView exp;
    TextView skills;
    boolean initialEdit;
    String org = "5sKnQxFI2y"; //hardcoding iiitd's objectId
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admin);
        initialEdit = false;
        name = (TextView)findViewById(R.id.nameTextView);
        email = (TextView)findViewById(R.id.emailTextView);
        ph = (TextView)findViewById(R.id.phTextView);
        clubs = (TextView)findViewById(R.id.clubsTextView);
        groups = (TextView)findViewById(R.id.groupsTextView);
        projects = (TextView)findViewById(R.id.projectsTextView);
        exp = (TextView)findViewById(R.id.expTextView);
        skills = (TextView)findViewById(R.id.skillsTextView);
        getData();
        findViewById(R.id.editProfileButton).setOnClickListener(this);
        findViewById(R.id.logoutButton).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_admin, menu);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.editProfileButton){
            onEditProfileClicked();
        }
        else if(v.getId()==R.id.logoutButton){
            ParseUser.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void getData(){
        ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Student>() {
            public void done(List<Student> itemList, ParseException e) {
                if (e == null) {
                    if (!itemList.isEmpty()) {
                        Toast.makeText(StudentAdmin.this, "Data Received", Toast.LENGTH_SHORT).show();
                        updateProfile(itemList.get(0));
                    } else {
                        Student s = new Student();
                        s.setUser(ParseUser.getCurrentUser());
                        initialEdit = true;
                        Toast.makeText(StudentAdmin.this, "Please complete your profile", Toast.LENGTH_SHORT).show();
                        s.saveInBackground();
                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });

        final ParseUser[] x = new ParseUser[1];
        ParseObject obj = ParseObject.createWithoutData("User", org);
        ParseUser user=new ParseUser();
        user.setObjectId(org);
        /*
        ParseQuery<Institute> query3 = ParseQuery.getQuery(Institute.class);
        query3.whereEqualTo("objectId", org);
        query3.findInBackground(new FindCallback<Institute>() {
            public void done(List<Institute> itemList, ParseException e) {
                if (e == null) {
                    if(!itemList.isEmpty()){
                        x[0] = itemList.get(0);
                        Toast.makeText(StudentAdmin.this, "Pages found", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(StudentAdmin.this, "Pages Not Found", Toast.LENGTH_SHORT).show();

                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });
*/
        ParseQuery innerQuery = ParseUser.getQuery();
        innerQuery.whereEqualTo("objectId", org);

        ParseQuery<GroupClubPage> query2 = ParseQuery.getQuery(GroupClubPage.class);
        query2.whereMatchesQuery("admin",innerQuery);
        //query2.whereEqualTo("admin", innerQuery);
        query2.findInBackground(new FindCallback<GroupClubPage>() {
            public void done(List<GroupClubPage> itemList, ParseException e) {
                if (e == null) {
                    if(!itemList.isEmpty()){
                        Toast.makeText(StudentAdmin.this, "Pages found", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(StudentAdmin.this, "Pages Not Found", Toast.LENGTH_SHORT).show();

                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });

    }

    private void updateProfile(Student s){
        name.setText(s.getName());
        email.setText(s.getEmail());
        ph.setText(s.getPh());
        groups.setText(s.getGroups());
        clubs.setText(s.getClubs());
        projects.setText(s.getProjects());
        exp.setText(s.getExp());
        skills.setText(s.getSkills());
    }
    private void onEditProfileClicked(){
        Intent intent = new Intent(this, StudentEdit.class);
        if(initialEdit==true){
            intent.putExtra("initialEdit", true);
            startActivity(intent);
        }
        else{
            intent.putExtra("initialEdit",false);
            intent.putExtra("name",name.getText());
            intent.putExtra("email",email.getText());
            intent.putExtra("ph",ph.getText());
            intent.putExtra("groups",groups.getText());
            intent.putExtra("clubs",clubs.getText());
            intent.putExtra("projects",projects.getText());
            intent.putExtra("exp",exp.getText());
            intent.putExtra("skills",skills.getText());
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
