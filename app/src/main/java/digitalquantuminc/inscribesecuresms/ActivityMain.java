package digitalquantuminc.inscribesecuresms;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
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

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.contactListAdapter;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.View.ViewContactsList;
import digitalquantuminc.inscribesecuresms.View.ViewConversationList;
import digitalquantuminc.inscribesecuresms.View.ViewPagerAdapter;
import digitalquantuminc.inscribesecuresms.View.ViewSessionList;

public class ActivityMain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    // Global Variable for UX Binding

    /**
     * The {@link ViewPager} that will host the section contents.
     */
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
            ClearDB();
            CreateDB();
            LoadDummyData();
        }

        // UX Layout Setup
        setupUXLayout();

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


    //public void list_conversation_OnItemClick(AdapterView<?> parent, View view, int position, long id)
    //{
    //    TextView text_ContactPhoneNumber = view.findViewById(R.id.text_ContactPhoneNumber)
    //}

    private void listview_contactList_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textlist_ContactPhoneNumber = (TextView) view.findViewById(R.id.textlist_ContactPhoneNumber);
        ImageView imageView_ContactAccent = (ImageView) view.findViewById(R.id.imageView_ContactAccent);
        String ContactPhoneNumber = textlist_ContactPhoneNumber.getText().toString();
        Intent objIntent = new Intent(getApplicationContext(), ActivityContactsDetail.class);
        objIntent.putExtra(IntentString.MainToContactsDetails_PhoneNum, ContactPhoneNumber);
        objIntent.putExtra(IntentString.MainToContactsDetails_ColorTheme, ((ColorDrawable) imageView_ContactAccent.getBackground()).getColor());
        startActivity(objIntent);
    }

    public void LoadDummyData() {
        TypeContact test1 = new TypeContact("081395141700", "Bagus Hanindhito", System.currentTimeMillis(), "00000000");
        TypeContact test2 = new TypeContact("081321132456", "Fulan Bin Abi Fulan", System.currentTimeMillis(), "00000000");
        TypeContact test3 = new TypeContact("085320456178", "Chobi Chocobi", System.currentTimeMillis(), "00000000");
        TypeContact test4 = new TypeContact("089203040120", "Oryza Sativa", System.currentTimeMillis(), "00000000");
        TypeContact test5 = new TypeContact("085320456179", "Cannabis Sativa", System.currentTimeMillis(), "00000000");
        TypeContact test6 = new TypeContact("087482813212", "Aurelia Auritania", System.currentTimeMillis(), "00000000");
        TypeContact test7 = new TypeContact("082145875521", "Fitria Ridayanti", System.currentTimeMillis(), "00000000");
        TypeContact test8 = new TypeContact("087545221214", "Wisnu Wijayanto", System.currentTimeMillis(), "00000000");
        TypeContact test9 = new TypeContact("025871422552", "Netizen Budiman", System.currentTimeMillis(), "00000000");
        TypeContact test10 = new TypeContact("025478525224", "Mantan Terindah", System.currentTimeMillis(), "00000000");
        TypeContact test11 = new TypeContact("025874568545", "Dia Yang Tersakiti", System.currentTimeMillis(), "00000000");
        TypeContact test12 = new TypeContact("036587855457", "Maung Bandung", System.currentTimeMillis(), "00000000");
        contactRepository contactRepo = new contactRepository(this);
        contactRepo.insert(test1);
        contactRepo.insert(test2);
        contactRepo.insert(test3);
        contactRepo.insert(test4);
        contactRepo.insert(test5);
        contactRepo.insert(test6);
        contactRepo.insert(test7);
        contactRepo.insert(test8);
        contactRepo.insert(test9);
        contactRepo.insert(test10);
        contactRepo.insert(test11);
        contactRepo.insert(test12);
    }

    public void ClearDB() {
        contactRepository contactRepo = new contactRepository(this);
        contactRepo.DropTable();
    }

    public void CreateDB() {
        contactRepository contactRepo = new contactRepository(this);
        contactRepo.CreateTable();
    }

}
