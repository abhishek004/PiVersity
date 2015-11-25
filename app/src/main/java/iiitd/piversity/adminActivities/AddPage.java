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
import iiitd.piversity.parseModels.GroupClubPage;

public class AddPage extends AppCompatActivity implements View.OnClickListener {
    Bundle extras;
    EditText name;
    EditText des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        name = (EditText)findViewById(R.id.nameAddPage);
        des = (EditText)findViewById(R.id.desAddPage);
        extras = getIntent().getExtras();
        findViewById(R.id.saveButtonAddPage).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_page, menu);
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
        if(v.getId()==R.id.saveButtonAddPage){
            onSaveClicked();
        }
    }

    private void onSaveClicked(){
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
                        p.setType(extras.getString("type"));
                        p.setOwner(ParseUser.getCurrentUser().getObjectId());
                        p.saveInBackground();
                        //Toast.makeText(StudentEdit.this, "Data Received", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPage.this, InstituteAdmin.class);
                        startActivity(intent);
                        //updateProfile(itemList.get(0));
                    }
                    else{
                        Toast.makeText(AddPage.this, "A "+extras.get("type")+" with the same name already exists.", Toast.LENGTH_SHORT).show();
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
