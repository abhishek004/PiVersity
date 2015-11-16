package iiitd.piversity.otherActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseAnalytics;

import iiitd.piversity.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.studentButton).setOnClickListener(this);
        findViewById(R.id.orgButton).setOnClickListener(this);
        findViewById(R.id.signupButton).setOnClickListener(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if(v.getId()==R.id.signupButton){
            onSignupClicked();
        }
        else if(v.getId()==R.id.studentButton){
            onStudentClicked();
        }
        else if(v.getId()==R.id.orgButton){
            onOrgClicked();
        }
    }

    public void onSignupClicked(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void onStudentClicked(){
        Intent intent = new Intent(this, SignIn.class);
        intent.putExtra("type","student");
        startActivity(intent);
    }
    public void onOrgClicked(){
        Intent intent = new Intent(this, SignIn.class);
        intent.putExtra("type","org");
        startActivity(intent);
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
