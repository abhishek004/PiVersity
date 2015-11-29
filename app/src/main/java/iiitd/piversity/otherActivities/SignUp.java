package iiitd.piversity.otherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import iiitd.piversity.R;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton student,org;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.signupButton2).setOnClickListener(this);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        userType=null;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.studentRadioButton) {
                    userType = "student";
                } else if(checkedId == R.id.orgRadioButton) {
                    userType = "org";
                }
            }

        });
        //ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
        if(v.getId()==R.id.signupButton2){
            onSignupClicked();
        }
    }

    public void onSignInClicked(View v){
        Intent intent = new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
    }

    public void onSignupClicked(){
        EditText username = (EditText)findViewById(R.id.usernameEditText);
        EditText pass = (EditText)findViewById(R.id.passEditText);
        EditText cPass = (EditText)findViewById(R.id.cPassEditText);
        EditText email = (EditText)findViewById(R.id.emailEditText);
        boolean allChecksDone=true;
        if(!pass.getText().toString().equals(cPass.getText().toString()) && !pass.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            allChecksDone=false;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            Toast.makeText(getApplicationContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
            allChecksDone=false;
        }
        if(username.getText().equals("")){
            Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show();
            allChecksDone=false;
        }
        if(userType==null){
            Toast.makeText(getApplicationContext(), "Please select student/organisation", Toast.LENGTH_SHORT).show();
            allChecksDone=false;
        }

        if(allChecksDone){
            ParseUser user = new ParseUser();
            user.setUsername(String.valueOf(username.getText()));
            user.setPassword(String.valueOf(pass.getText()));
            user.setEmail(String.valueOf(email.getText()));
            user.put("type",userType);

            // other fields can be set just like with ParseObject
            //user.put("phone", "650-555-0000");

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        Toast.makeText(getApplicationContext(), "You successfully registered", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Log.e("Signup",e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
