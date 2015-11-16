package iiitd.piversity.adminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.parseModels.Institute;

public class InstituteEdit extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText email;
    EditText ph;
    EditText info;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_edit);
        extras=getIntent().getExtras();
        name = (EditText)findViewById(R.id.nameInstituteEdit);
        email = (EditText)findViewById(R.id.emailInstituteEdit);
        ph = (EditText)findViewById(R.id.phInstituteEdit);
        info = (EditText)findViewById(R.id.infoInstituteEdit);
        setFields();
        findViewById(R.id.saveInstituteEdit).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_institute_edit, menu);
        return true;
    }

    private void setFields(){
        if(!extras.getBoolean("initialEdit")){
            name.setText(extras.getString("name"));
            email.setText(extras.getString("email"));
            ph.setText(extras.getString("ph"));
            info.setText(extras.getString("info"));
        }
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
        if(v.getId()==R.id.saveInstituteEdit){
            onSaveClicked();
        }
    }

    private void onSaveClicked(){
        ParseQuery<Institute> query = ParseQuery.getQuery(Institute.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Institute>() {
            public void done(List<Institute> itemList, ParseException e) {
                if (e == null) {
                    if(!itemList.isEmpty()){
                        Institute s = itemList.get(0);
                        s.setName(String.valueOf(name.getText()));
                        s.setEmail(String.valueOf(email.getText()));
                        s.setInfo(String.valueOf(info.getText()));
                        s.setPh(String.valueOf(ph.getText()));
                        s.setUser(ParseUser.getCurrentUser());
                        s.saveInBackground();
                        //Toast.makeText(StudentEdit.this, "Data Received", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InstituteEdit.this, InstituteAdmin.class);
                        startActivity(intent);
                        //updateProfile(itemList.get(0));
                    }
                    else{
                        Institute s = new Institute();
                        s.setUser(ParseUser.getCurrentUser());
                        Toast.makeText(InstituteEdit.this, "Please complete your profile", Toast.LENGTH_SHORT).show();
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
