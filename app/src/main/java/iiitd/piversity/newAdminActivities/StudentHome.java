package iiitd.piversity.newAdminActivities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import iiitd.piversity.adminActivities.StudentAdmin;
import iiitd.piversity.adminActivities.StudentEdit;
import iiitd.piversity.newAdminActivities.Clubs.ClubsContent;
import iiitd.piversity.newAdminActivities.Groups.GroupsContent;
import iiitd.piversity.newAdminActivities.Updates.UpdatesContent;
import iiitd.piversity.otherActivities.MainActivity;
import iiitd.piversity.parseModels.Student;

public class StudentHome extends AppCompatActivity implements updatesFragment.OnUpdatesListFragmentInteractionListener, GroupsFragment.OnGroupsListFragmentInteractionListener, ClubsFragment.OnClubsListFragmentInteractionListener {


    Boolean initialEdit = false;
    String name;
    String email;
    String ph;
    String groups;
    String clubs;
    String projects;
    String exp;
    String skills;
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
        setContentView(R.layout.activity_student_home);


        getData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_profile){
            Intent intent = new Intent(StudentHome.this, StudentAdmin.class);
            startActivity(intent);
        }

        if (id == R.id.action_logout) {
            ParseUser.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_edit_profile) {
            onEditProfileClicked();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdatesListFragmentInteraction(UpdatesContent.UpdatesItem item){
        //some code here.
        Toast.makeText(StudentHome.this, "List item Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGroupsListFragmentInteraction(GroupsContent.GroupsItem item){
        //some code here too.
        Toast.makeText(StudentHome.this, "List item Clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClubsListFragmentInteraction(ClubsContent.ClubsItem item){
        //some code here too please.
        Toast.makeText(StudentHome.this, "List item Clicked", Toast.LENGTH_SHORT).show();

    }

    private void getData() {
        ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Student>() {
            public void done(List<Student> itemList, ParseException e) {
                if (e == null) {
                    if (!itemList.isEmpty()) {
                        //Toast.makeText(StudentAdmin.this, "Data Received", Toast.LENGTH_SHORT).show();
                        updateProfile(itemList.get(0));
                    } else {
                        Student s = new Student();
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

    private void updateProfile(Student s) {
        name = s.getName();
        email = s.getEmail();
        ph = s.getPh();
        groups = s.getGroups();
        clubs = s.getClubs();
        projects = s.getProjects();
        exp = s.getExp();
        skills = s.getSkills();
    }

    private void onEditProfileClicked(){
        Intent intent = new Intent(this, StudentEdit.class);
        try{
            intent.putExtra("initialEdit", false);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("ph",ph);
            intent.putExtra("groups",groups);
            intent.putExtra("clubs",clubs);
            intent.putExtra("projects",projects);
            intent.putExtra("exp",exp);
            intent.putExtra("skills", skills);
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
            View rootView = inflater.inflate(R.layout.fragment_student_home, container, false);
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
            //return PlaceholderFragment.newInstance(position + 1);
            //
            switch (position){
                case 0:
                    return updatesFragment.newInstance(position + 1);

                case 1:
                    return GroupsFragment.newInstance(position + 1);

                case 2:
                    return ClubsFragment.newInstance(position + 1);

            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Updates";
                case 1:
                    return "Groups";
                case 2:
                    return "Clubs";
            }
            return null;
        }
    }
}
