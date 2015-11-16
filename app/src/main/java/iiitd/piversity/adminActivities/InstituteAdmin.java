package iiitd.piversity.adminActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.otherActivities.MainActivity;
import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.Institute;

public class InstituteAdmin extends AppCompatActivity implements View.OnClickListener {
    TextView name;
    TextView email;
    TextView ph;
    TextView info;
    boolean initialEdit;
    LinearLayout groupContainer;
    LinearLayout clubContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_admin);
        initialEdit=false;
        name = (TextView)findViewById(R.id.nameInstitute);
        email = (TextView)findViewById(R.id.emailInstitute);
        ph = (TextView)findViewById(R.id.phInstitute);
        info = (TextView)findViewById(R.id.infoIntitute);
        groupContainer = (LinearLayout)findViewById(R.id.groups);
        clubContainer = (LinearLayout)findViewById(R.id.clubs);
        findViewById(R.id.editProfileInstitute).setOnClickListener(this);
        findViewById(R.id.addClubButton).setOnClickListener(this);
        findViewById(R.id.addGroupButton).setOnClickListener(this);
        findViewById(R.id.logoutInstitute).setOnClickListener(this);
        getData();
        getPages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_institute_admin, menu);
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
        if(v.getId()==R.id.editProfileInstitute){
            onEditProfileClicked();
        }
        else if(v.getId()==R.id.logoutInstitute){
            ParseUser.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.addClubButton){
            Intent intent = new Intent(this, AddPage.class);
            intent.putExtra("type","club");
            startActivity(intent);
        }
        else if(v.getId()==R.id.addGroupButton){
            Intent intent = new Intent(this, AddPage.class);
            intent.putExtra("type","group");
            startActivity(intent);
        }
    }

    private void getData(){
        ParseQuery<Institute> query = ParseQuery.getQuery(Institute.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Institute>() {
            public void done(List<Institute> itemList, ParseException e) {
                if (e == null) {
                    if(!itemList.isEmpty()){
                        Toast.makeText(InstituteAdmin.this, "Data Received", Toast.LENGTH_SHORT).show();
                        updateProfile(itemList.get(0));
                    }
                    else{
                        Institute s = new Institute();
                        s.setUser(ParseUser.getCurrentUser());
                        initialEdit=true;
                        Toast.makeText(InstituteAdmin.this, "Please complete your profile", Toast.LENGTH_SHORT).show();
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

    private void updateProfile(Institute s){
        name.setText(s.getName());
        email.setText(s.getEmail());
        ph.setText(s.getPh());
        info.setText(s.getInfo());
    }

    private void getPages(){
        ParseQuery<GroupClubPage> query = ParseQuery.getQuery(GroupClubPage.class);
        query.whereEqualTo("admin", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<GroupClubPage>() {
            public void done(List<GroupClubPage> itemList, ParseException e) {
                if (e == null) {
                    if (!itemList.isEmpty()) {
                        // Toast.makeText(InstituteAdmin.this, "Data Received", Toast.LENGTH_SHORT).show();
                        addPages(itemList);
                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void addPages(List<GroupClubPage> itemList){
        for (int i=0;i<itemList.size();i++){
            LayoutInflater layoutInflater =
                    (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.update_row, null);
            final TextView textOut = (TextView)addView.findViewById(R.id.textout);
            textOut.setText(itemList.get(i).getName().toString());
            if(itemList.get(i).getType().toString().equals("club"))
                clubContainer.addView(addView);
            else
                groupContainer.addView(addView);

            textOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InstituteAdmin.this, PageAdmin.class);
                    intent.putExtra("name",textOut.getText().toString());
                    startActivity(intent);
                }
            });
        }
    }


    private void onEditProfileClicked(){
        Intent intent = new Intent(this, InstituteEdit.class);
        if(initialEdit==true){
            intent.putExtra("initialEdit", true);
            startActivity(intent);
        }
        else{
            intent.putExtra("initialEdit",false);
            intent.putExtra("name",name.getText());
            intent.putExtra("email",email.getText());
            intent.putExtra("ph",ph.getText());
            intent.putExtra("info",info.getText());
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
