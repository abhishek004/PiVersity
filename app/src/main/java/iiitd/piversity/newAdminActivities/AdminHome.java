package iiitd.piversity.newAdminActivities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import iiitd.piversity.R;
import iiitd.piversity.adminActivities.AddPage;
import iiitd.piversity.adminActivities.InstituteAdmin;
import iiitd.piversity.adminActivities.InstituteEdit;
import iiitd.piversity.adminActivities.PageAdmin;
import iiitd.piversity.adminActivities.StudentEdit;
import iiitd.piversity.newAdminActivities.Clubs.ClubsContent;
import iiitd.piversity.newAdminActivities.Groups.GroupsContent;
import iiitd.piversity.otherActivities.MainActivity;
import iiitd.piversity.parseModels.Institute;
import iiitd.piversity.parseModels.Student;

public class AdminHome extends AppCompatActivity implements GroupsFragment.OnGroupsListFragmentInteractionListener{


    Boolean initialEdit = false;
    String name;
    String email;
    String ph;
    String info;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        getData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = mViewPager.getCurrentItem();
                Intent intent = new Intent(getBaseContext(), AddPage.class);
                intent.putExtra("email", ParseUser.getCurrentUser().getEmail());
                if(position == 0){
                    intent.putExtra("type","group");
                    startActivity(intent);
                } else{
                    intent.putExtra("type","club");
                    startActivity(intent);
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(AdminHome.this, InstituteAdmin.class);
            startActivity(intent);
        }

        if (id == R.id.action_logout){
            ParseUser.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_edit_profile){
            onEditProfileClicked();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupsListFragmentInteraction(GroupsContent.GroupsItem item){
        //some code here too.
       // Toast.makeText(StudentHome.this, "List item Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PageAdmin.class);
        intent.putExtra("name", item.content);
        startActivity(intent);
        Toast.makeText(AdminHome.this, "List item Clicked", Toast.LENGTH_SHORT).show();
    }


    private void getData() {
        ParseQuery<Institute> query = ParseQuery.getQuery(Institute.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Institute>() {
            public void done(List<Institute> itemList, ParseException e) {
                if (e == null) {
                    if (!itemList.isEmpty()) {
                        //Toast.makeText(StudentAdmin.this, "Data Received", Toast.LENGTH_SHORT).show();
                        updateProfile(itemList.get(0));
                    } else {
                        Institute s = new Institute();
                        s.setUser(ParseUser.getCurrentUser());
                        initialEdit = true;
//                        Toast.makeText(StudentAdmin.this, "Please complete your profile", Toast.LENGTH_SHORT).show();
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

    private void updateProfile(Institute s) {
        name = s.getName();
        email = s.getEmail();
        ph = s.getPh();
        info = s.getInfo();
    }


    private void onEditProfileClicked(){
        Intent intent = new Intent(this, InstituteEdit.class);
        try{
            intent.putExtra("initialEdit", false);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("ph",ph);
            intent.putExtra("info",info);
            startActivity(intent);
        } catch (Exception e) {
            intent.putExtra("initialEdit", true);
            ParseUser curruser = ParseUser.getCurrentUser();
            name = curruser.getUsername();
            email = curruser.getEmail();
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            startActivity(intent);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_admin_home, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return GroupsFragment.newInstance(position + 1);
                case 1:
                    return AddGroupFragment.newInstance();
                case 2:
                    return PlaceholderFragment.newInstance(position + 1);
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Groups/Clubs";
                case 1:
                    return "Create Group";
                case 2:
                    return "Create Update";
            }
            return null;
        }
    }
}
