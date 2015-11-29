package iiitd.piversity.otherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import iiitd.piversity.R;
import iiitd.piversity.adminActivities.InstituteAdmin;
import iiitd.piversity.adminActivities.StudentAdmin;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById(R.id.signInButton).setOnClickListener(this);
        extras = getIntent().getExtras();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
        if(v.getId()==R.id.signInButton){
            onSigninClicked();
        }
    }

    public void onSignUpClicked(){
        Intent intent = new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
    }


    public void onSigninClicked(){
        EditText username = (EditText)findViewById(R.id.signinUserEdit);
        EditText pass = (EditText)findViewById(R.id.signinPassEdit);
        ParseUser.logInInBackground(String.valueOf(username.getText()), String.valueOf(pass.getText()), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                // Hooray! The user is logged in.
                if (user != null) {
                    if (extras != null) {
                        String value = extras.getString("type");
                        if (value.equals("student")) {
                            Intent intent = new Intent(SignIn.this, StudentAdmin.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SignIn.this, InstituteAdmin.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "You successfully signed in as an Organisation", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Signin Failed", Toast.LENGTH_LONG).show();
                        // Signup failed. Look at the ParseException to see what happened.
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
