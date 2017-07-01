package digitalquantuminc.inscribesecuresms;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import digitalquantuminc.inscribesecuresms.Development.ContactDummyData;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.contactListAdapter;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.View.ViewContactsList;
import digitalquantuminc.inscribesecuresms.View.ViewConversationList;
import digitalquantuminc.inscribesecuresms.View.ViewPagerAdapter;
import digitalquantuminc.inscribesecuresms.View.ViewSessionList;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 * This is class for ActivityMain which is the launcher activity for this program.
 * There is only small things to do in this class, such as instantiating the child view to be displayed in ViewPager,
 * initialize the ViewPager, TabLayout, and ViewPager Adapter, handle the feedback code request from the child activity,
 * send appropriate intent to each child activity before launched.
 */

public class ActivityMain extends AppCompatActivity {

    //region Global Variable
    // Development Mode Switch
    // Put 1 to enable development mode otherwise 0
    // Development mode will automatically populates SQLite Database with dummy data for easy development.
    // It will destroy the database, recreate database, and load dummy data each runtime.
    private static final int DEVELOPMENT_MODE = 1;

    // Global Variable for UX Binding
    // Variable for ViewPager that has been modified to inflate standard activity layout (not fragment)
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;

    // Variable for Children Activity (i.e. inflated layout on each tab viewpager
    private ViewConversationList viewconversationlist;
    private ViewContactsList viewcontactslist;
    private ViewSessionList viewsessionlist;

    // Variable for Toolbar
    private Toolbar toolbar;

    // Variable for Tablayout
    private TabLayout tabLayout;

    //endregion
    //region Override Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dummy data, only for development only.
        if (DEVELOPMENT_MODE == 1) {
            ContactDummyData.ClearDB(this);
            ContactDummyData.CreateDB(this);
            ContactDummyData.LoadDummyData(this);
        }

        // UX Layout Setup
        setupUXLayout();

        // Contact List
        LoadContactList(viewcontactslist.getList_contacts());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Plan what kind of menu that should be included in MenuItem and Their respective action
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle all feedbackcode request from child activity to be executed in parent activity
        super.onActivityResult(requestCode, resultCode, data);
        // Check request code send by child activity before it closes itself.
        switch (requestCode) {
            case IntentString.MainFeedbackCode_DoNothing: {
                break;
            }
            case IntentString.MainFeedbackCode_RefreshContactList: {
                LoadContactList(viewcontactslist.getList_contacts());
                break;
            }
            default: {
                break;
            }
        }
    }

    //endregion
    //region UX Layout and Binding Method
    private void setupUXLayout() {

        // Instantiate Child View for ViewPager
        viewconversationlist = new ViewConversationList(this, findViewById(R.id.view_conversation_list));
        viewcontactslist = new ViewContactsList(this, findViewById(R.id.view_contacts_list));
        viewsessionlist = new ViewSessionList(this, findViewById(R.id.view_session_list));

        // Instantiate View Pager Adapter for the View Pager
        adapter = new ViewPagerAdapter();
        // Add Child View to the Adapter
        adapter.addView(viewconversationlist);
        adapter.addView(viewcontactslist);
        adapter.addView(viewsessionlist);

        // Instantiate View Pager for the Child View
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);

        // Setup Tab Layout to use the View Pager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Setup Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //endregion
    //region Method
    public void LoadContactList(ListView listcontact) {
        // Access the database and load all of its content to ArrayList
        contactRepository repo = new contactRepository(this);
        ArrayList<HashMap<String, String>> contactList = repo.getContactListSorted();
        // If there is at least one element
        if (contactList.size() != 0) {
            // Set adapter for the listcontact
            listcontact.setAdapter(new contactListAdapter(ActivityMain.this, contactList));
            // Set the eventhandler when the item in the listcontact gets clicked.
            listcontact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listview_contactList_onItemClick(parent, view, position, id);
                }
            });
        } else {
            Toast.makeText(this, "No Contact List", Toast.LENGTH_SHORT).show();
        }
    }

    //endregion
    //region UX EventHandler Method
    private void listview_contactList_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Method that handle action when the item in ContactList listview is clicked.

        // First, we need to bind the UX Component into variable to access it easily.
        TextView textlist_ContactPhoneNumber = view.findViewById(R.id.textlist_ContactPhoneNumber);
        ImageView imageView_ContactAccent = view.findViewById(R.id.imageView_ContactAccent);

        // Extract the Phone Number as a unique identity for database query.
        String ContactPhoneNumber = textlist_ContactPhoneNumber.getText().toString();

        // Preparing intent to be passed to ActivityContactsDetail as child activity
        Intent objIntent = new Intent(getApplicationContext(), ActivityContactsDetail.class);
        objIntent.putExtra(IntentString.MainToContactsDetails_PhoneNum, ContactPhoneNumber);
        objIntent.putExtra(IntentString.MainToContactsDetails_ColorTheme, ((ColorDrawable) imageView_ContactAccent.getBackground()).getColor());

        // Start the ActivityContactsDetail by passing the intent and the code for feedback request.
        startActivityForResult(objIntent, IntentString.MainFeedbackCode_RefreshContactList);
    }
    //endregion
}
