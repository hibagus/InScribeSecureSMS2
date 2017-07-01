package digitalquantuminc.inscribesecuresms;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
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

public class ActivityMain extends AppCompatActivity {

    // Global Variable for UX Binding
    private static final int DEVELOPMENT_MODE = 1;

    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;

    private ViewConversationList viewconversationlist;
    private ViewContactsList viewcontactslist;
    private ViewSessionList viewsessionlist;

    private Toolbar toolbar;

    private TabLayout tabLayout;

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
        //setButtonOnClickListener();

        // UI Component Binding
        //UIComponentBinding();

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

    public void LoadContactList(ListView listcontact) {
        contactRepository repo = new contactRepository(this);
        ArrayList<HashMap<String, String>> contactList = repo.getContactListSorted();
        if (contactList.size() != 0) {
            listcontact.setAdapter(new contactListAdapter(ActivityMain.this, contactList));
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


    private void listview_contactList_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textlist_ContactPhoneNumber = (TextView) view.findViewById(R.id.textlist_ContactPhoneNumber);
        ImageView imageView_ContactAccent = (ImageView) view.findViewById(R.id.imageView_ContactAccent);
        String ContactPhoneNumber = textlist_ContactPhoneNumber.getText().toString();
        Intent objIntent = new Intent(getApplicationContext(), ActivityContactsDetail.class);
        objIntent.putExtra(IntentString.MainToContactsDetails_PhoneNum, ContactPhoneNumber);
        objIntent.putExtra(IntentString.MainToContactsDetails_ColorTheme, ((ColorDrawable) imageView_ContactAccent.getBackground()).getColor());
        startActivityForResult(objIntent, IntentString.MainFeedbackCode_RefreshContactList);
    }



}
