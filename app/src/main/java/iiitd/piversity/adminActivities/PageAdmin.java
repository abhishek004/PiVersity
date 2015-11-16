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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.parseModels.GroupClubPage;
import iiitd.piversity.parseModels.PageUpdate;

public class PageAdmin extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    TextView name;
    TextView des;
    String pageId;
    LinearLayout updateContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_admin);
        extras = getIntent().getExtras();
        name=(TextView)findViewById(R.id.namePageAdmin);
        des=(TextView)findViewById(R.id.desPageAdmin);
        getPage(extras.getString("name"));
        updateContainer = (LinearLayout)findViewById(R.id.updatesContainer);
        findViewById(R.id.addButtonPageAdmin).setOnClickListener(this);
    }

    private void getPage(String name){
        ParseQuery<GroupClubPage> query = ParseQuery.getQuery(GroupClubPage.class);
        query.whereEqualTo("name", name);
        query.findInBackground(new FindCallback<GroupClubPage>() {
            public void done(List<GroupClubPage> itemList, ParseException e) {
                if (e == null) {
                    if (!itemList.isEmpty()) {
                        GroupClubPage p = itemList.get(0);
                        setPage(p);
                        pageId = p.getObjectId();
                        getUpdates(pageId);
                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void setPage(GroupClubPage p){
        name.setText(p.getName());
        des.setText(p.getInfo());
    }

    private void getUpdates(String pageId){
        ParseQuery<PageUpdate> query = ParseQuery.getQuery(PageUpdate.class);
        query.whereEqualTo("pageId", pageId);
        query.findInBackground(new FindCallback<PageUpdate>() {
            public void done(List<PageUpdate> itemList, ParseException e) {
                if (e == null) {
                    if (!itemList.isEmpty()) {
                        setUpdates(itemList);
                    }
                    // Access the array of results here
                    //String firstItemId = itemList.get(0).getObjectId();

                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void setUpdates(List<PageUpdate> itemList){
        for (int i=0;i<itemList.size();i++){
            LayoutInflater layoutInflater =
                    (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.update_row, null);
            final TextView textOut = (TextView)addView.findViewById(R.id.textout);
            textOut.setText(itemList.get(i).getInfo().toString());
            updateContainer.addView(addView);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_admin, menu);
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
        if(v.getId()==R.id.addButtonPageAdmin){
            Intent intent = new Intent(PageAdmin.this, AddUpdate.class);
            intent.putExtra("pageId",pageId);
            intent.putExtra("pageName",extras.getString("name"));
            startActivity(intent);
        }
    }
}
