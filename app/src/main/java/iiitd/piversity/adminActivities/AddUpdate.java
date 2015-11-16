package iiitd.piversity.adminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;

import iiitd.piversity.R;
import iiitd.piversity.parseModels.PageUpdate;

public class AddUpdate extends AppCompatActivity implements View.OnClickListener {
    String pageId;
    EditText info;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        extras = getIntent().getExtras();
        pageId=extras.getString("pageId");
        info=(EditText)findViewById(R.id.desAddUpdate);
        findViewById(R.id.addButtonAddUpdate).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_update, menu);
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
        if(v.getId()==R.id.addButtonAddUpdate){
            addUpdate();
        }
    }

    private void addUpdate(){
        PageUpdate p = new PageUpdate();
        p.setOwner(ParseUser.getCurrentUser());
        p.setPageId(pageId);
        p.setInfo(info.getText().toString());
        p.saveInBackground();
        Intent intent = new Intent(this,PageAdmin.class);
        intent.putExtra("name",extras.getString("pageName"));
        startActivity(intent);
    }
}
