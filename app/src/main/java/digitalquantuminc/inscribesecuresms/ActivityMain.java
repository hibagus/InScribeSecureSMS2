package digitalquantuminc.inscribesecuresms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.spongycastle.util.encoders.Base64;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivityContactsDetail;
import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivitySessionDetail;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
import digitalquantuminc.inscribesecuresms.Development.ContactDummyData;
import digitalquantuminc.inscribesecuresms.Development.ProfileDummyData;
import digitalquantuminc.inscribesecuresms.Development.SessionDummyData;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.contactListAdapter;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.sessionListAdapter;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.profileRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.QRCodeHandler;
import digitalquantuminc.inscribesecuresms.View.ViewCompose;
import digitalquantuminc.inscribesecuresms.View.ViewContactsList;
import digitalquantuminc.inscribesecuresms.View.ViewConversationList;
import digitalquantuminc.inscribesecuresms.View.ViewPagerAdapter;
import digitalquantuminc.inscribesecuresms.View.ViewProfile;
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
    private static final int DEPLOYMENT_MODE = 0;
    private static final int DEVELOPMENT_MODE = 1;

    private static final int RUNTIME_MODE = DEVELOPMENT_MODE;

    // Global Variable for UX Binding
    // Variable for ViewPager that has been modified to inflate standard activity layout (not fragment)
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;

    // Variable for Children Activity (i.e. inflated layout on each tab viewpager
    private ViewConversationList viewconversationlist;
    private ViewContactsList viewcontactslist;
    private ViewSessionList viewsessionlist;
    private ViewProfile viewprofile;
    private ViewCompose viewcompose;

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
        if (RUNTIME_MODE == DEVELOPMENT_MODE) {
            ContactDummyData.ClearDB(this);
            ContactDummyData.CreateDB(this);
            ContactDummyData.LoadDummyData(this);
            SessionDummyData.ClearDB(this);
            SessionDummyData.CreateDB(this);
            SessionDummyData.LoadDummyData(this);
            //ProfileDummyData.ClearDB(this);
            ProfileDummyData.CreateDB(this);
        }

        // UX Layout Setup
        setupUXLayout();

        // Contact List
        LoadContactList(viewcontactslist.getList_contacts());

        // Session List
        LoadSessionList(viewsessionlist.getList_session());

        // Profile
        LoadProfile();
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
        if (resultCode == Activity.RESULT_OK) {
            int code = data.getIntExtra(IntentString.MainFeedBackCode, IntentString.MainFeedbackCode_DoNothing);
            switch (code) {
                case IntentString.MainFeedbackCode_DoNothing: {
                    break;
                }
                case IntentString.MainFeedbackCode_RefreshContactList: {
                    LoadContactList(viewcontactslist.getList_contacts());
                    break;
                }
                case IntentString.MainFeedbackCode_RefreshBothContactandSessionList: {
                    LoadContactList(viewcontactslist.getList_contacts());
                    LoadSessionList(viewsessionlist.getList_session());
                    break;
                }
                default: {
                    break;
                }
            }
        } else {
            // Do Nothing
        }

    }

    //endregion
    //region UX Layout and Binding Method
    private void setupUXLayout() {

        // Instantiate Child View for ViewPager
        viewconversationlist = new ViewConversationList(this, findViewById(R.id.view_conversation_list));
        viewcompose = new ViewCompose(this, findViewById(R.id.view_compose));
        viewcontactslist = new ViewContactsList(this, findViewById(R.id.view_contacts_list));
        viewsessionlist = new ViewSessionList(this, findViewById(R.id.view_session_list));
        viewprofile = new ViewProfile(this, findViewById(R.id.view_profile));

        // Instantiate View Pager Adapter for the View Pager
        adapter = new ViewPagerAdapter();
        // Add Child View to the Adapter
        adapter.addView(viewconversationlist);
        adapter.addView(viewcompose);
        adapter.addView(viewcontactslist);
        adapter.addView(viewsessionlist);
        adapter.addView(viewprofile);

        // Instantiate View Pager for the Child View
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mViewPager_onPageChanged(position);
            }
        });

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

    public void LoadSessionList(ListView listsession) {
        // Access the database and load all of its content to ArrayList
        sessionRepository repo = new sessionRepository(this);
        ArrayList<HashMap<String, String>> sessionList = repo.getSessionListSorted();
        // If there is at least one element
        if (sessionList.size() != 0) {
            // Set adapter for the listsession
            listsession.setAdapter(new sessionListAdapter(ActivityMain.this, sessionList));
            // Set the Event Handler when the item in the List Session gets clicked.
            listsession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listview_sessionList_onItemClick(parent, view, position, id);
                }
            });
        } else {
            Toast.makeText(this, "No Session List", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadProfile()
    {
        profileRepository repo = new profileRepository(this);
        TypeProfile profile = repo.getProfile(TypeProfile.DEFAULTID);
        viewprofile.getText_ProfileName().setText(profile.getName_self());
        viewprofile.getText_ProfileNumber().setText(profile.getPhone_number());
        viewprofile.getText_ProfileLastUpdate().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(profile.getGenerated_date())));
        viewprofile.getText_ProfileRSAPubKey().setText(profile.getRsa_publickey());
        viewprofile.getText_ProfileRSAPubKey().setTextIsSelectable(true);
        viewprofile.getText_ProfileRSAPubKey().setKeyListener(null);
        viewprofile.getText_ProfileRSAPrivKey().setText(profile.getRsa_privatekey());
        viewprofile.getText_ProfileRSAPrivKey().setTextIsSelectable(true);
        viewprofile.getText_ProfileRSAPrivKey().setKeyListener(null);
        GenerateProfileQRCode();
        viewprofile.getBtn_RegenRSAKeyPair().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_RegenRSAKeyPair_onClick(v);
            }
        });
        viewprofile.getBtn_UpdateProfile().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_UpdateProfile_onClick(v);
            }
        });
    }

    public void RefreshProfile()
    {
        profileRepository repo = new profileRepository(this);
        TypeProfile profile = repo.getProfile(TypeProfile.DEFAULTID);
        viewprofile.getText_ProfileName().setText(profile.getName_self());
        viewprofile.getText_ProfileNumber().setText(profile.getPhone_number());
        viewprofile.getText_ProfileLastUpdate().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(profile.getGenerated_date())));
        viewprofile.getText_ProfileRSAPubKey().setText(profile.getRsa_publickey());
        viewprofile.getText_ProfileRSAPubKey().setTextIsSelectable(true);
        viewprofile.getText_ProfileRSAPubKey().setKeyListener(null);
        viewprofile.getText_ProfileRSAPrivKey().setText(profile.getRsa_privatekey());
        viewprofile.getText_ProfileRSAPrivKey().setTextIsSelectable(true);
        viewprofile.getText_ProfileRSAPrivKey().setKeyListener(null);
        GenerateProfileQRCode();
    }

    public void GenerateProfileQRCode()
    {
        String name = viewprofile.getText_ProfileName().getText().toString();
        String phonenum = viewprofile.getText_ProfileNumber().getText().toString();
        String rsapubkey = viewprofile.getText_ProfileRSAPubKey().getText().toString();
        int size = viewprofile.getImageView_ProfileQRCode().getLayoutParams().height;
        TypeProfile profile = new TypeProfile(name, phonenum, 0, rsapubkey, "",0);
        Bitmap bitmap = null;
        GenerateProfileQRCodeAsync asynctask = new GenerateProfileQRCodeAsync(this, bitmap, size);
        asynctask.execute(profile);
    }

    //endregion
    //region UX EventHandler Method
    private void listview_contactList_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Method that handle action when the item in ContactList listview is clicked.

        // First, we need to bind the UX Component into variable to access it easily.
        TextView textlist_ContactPhoneNumber = (TextView) view.findViewById(R.id.textlist_ContactPhoneNumber);
        ImageView imageView_ContactAccent = (ImageView) view.findViewById(R.id.imageView_ContactAccent);

        // Extract the Phone Number as a unique identity for database query.
        String ContactPhoneNumber = textlist_ContactPhoneNumber.getText().toString();

        // Preparing intent to be passed to ActivityContactsDetail as child activity
        Intent objIntent = new Intent(getApplicationContext(), ActivityContactsDetail.class);
        objIntent.putExtra(IntentString.MainToContactsDetails_PhoneNum, ContactPhoneNumber);
        objIntent.putExtra(IntentString.MainToContactsDetails_ColorTheme, ((ColorDrawable) imageView_ContactAccent.getBackground()).getColor());

        // Start the ActivityContactsDetail by passing the intent and the code for feedback request.
        startActivityForResult(objIntent, IntentString.MainFeedbackCode_RefreshContactList);
    }

    private void listview_sessionList_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Method that handle action when the item in SessionList listview is clicked.

        // First, we need to bind the UX Component into variable to access it easily.
        TextView textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);
        ImageView imageView_SessionStatus = (ImageView) view.findViewById(R.id.imageView_SessionStatus);

        // Extract the Phone Number as a unique identity for database query.
        String PartnerNumber = textlist_PartnerNumber.getText().toString();

        // Preparing intent to be passed to ActivitySessionDetail as child activity.
        Intent objIntent = new Intent(getApplicationContext(), ActivitySessionDetail.class);
        objIntent.putExtra(IntentString.MainToSessionDetails_PhoneNum, PartnerNumber);
        objIntent.putExtra(IntentString.MainToSessionDetails_ColorTheme, ((ColorDrawable) imageView_SessionStatus.getBackground()).getColor());

        // Start the ActivitySessionDetail by passing the intent and the code for feedback request.
        startActivityForResult(objIntent, IntentString.MainFeedbackCode_RefreshSessionList);
    }

    private void mViewPager_onPageChanged(int position)
    {
        // Method to be executed each time the ViewPager Tab is Changed
        switch(position)
        {
            case 0 :{
                break;
            }
            case 1 : {
                break;
            }
            case 2 : {
                break;
            }
            case 3 : {
                break;
            }
            case 4 : {
                break;
            }
            case 5 : {
                break;
            }
            default : {
                break;
            }
        }
    }

    private void btn_RegenRSAKeyPair_onClick (View v)
    {

        KeyPair RSAKeyPair = null;
        GenerateRSAKeyAsync asynctask = new GenerateRSAKeyAsync(this);
        asynctask.execute(RSAKeyPair, RSAKeyPair, RSAKeyPair);
    }

    private void btn_UpdateProfile_onClick (View v)
    {

        profileRepository profileRepository = new profileRepository(this);
        TypeProfile profileold = profileRepository.getProfile(TypeProfile.DEFAULTID);
        String name = viewprofile.getText_ProfileName().getText().toString();
        String phonenum = viewprofile.getText_ProfileNumber().getText().toString();
        long currentdate = System.currentTimeMillis();
        String rsapubkey = viewprofile.getText_ProfileRSAPubKey().getText().toString();
        String rsaprivkey = viewprofile.getText_ProfileRSAPrivKey().getText().toString();
        long lastsync = profileold.getLastsync();
        TypeProfile profilenew = new TypeProfile(phonenum, name, currentdate, rsapubkey, rsaprivkey, lastsync);
        profileRepository.update(profilenew);
        RefreshProfile();
    }
    //endregion

    private class GenerateRSAKeyAsync extends AsyncTask<KeyPair, KeyPair, KeyPair> {
        private Activity outer;
        public GenerateRSAKeyAsync(Activity outer)
        {
            this.outer = outer;
        }
        @Override
        protected void onPreExecute() {
            viewprofile.getText_ProfileRSAPubKey().setText(outer.getString(R.string.rsakeypairregenload));
            viewprofile.getText_ProfileRSAPrivKey().setText(outer.getString(R.string.rsakeypairregenload));
        }
        @Override
        protected KeyPair doInBackground(KeyPair... arg) {

            return Cryptography.GenerateRSAKeyPair(Cryptography.RSAKEYSIZE);
        }

        @Override
        protected void onPostExecute(KeyPair a) {
            viewprofile.getText_ProfileRSAPubKey().setText(Base64.toBase64String(a.getPublic().getEncoded()));
            viewprofile.getText_ProfileRSAPrivKey().setText(Base64.toBase64String(a.getPrivate().getEncoded()));
        }
    }

    private class GenerateProfileQRCodeAsync extends AsyncTask<TypeProfile, Bitmap, Bitmap>
    {
        private Activity outer;
        private Bitmap bitmap;
        private int size;
        public GenerateProfileQRCodeAsync(Activity outer, Bitmap bitmap, int size)
        {
            this.outer = outer;
            this.bitmap = bitmap;
            this.size = size;
        }
        @Override
        protected void onPreExecute() {
            viewprofile.getImageView_ProfileQRCode().setVisibility(View.GONE);
            viewprofile.getProgressBar_Refresh().setVisibility(View.VISIBLE);
        }
        @Override
        protected Bitmap doInBackground(TypeProfile... arg) {
            bitmap = QRCodeHandler.GenerateProfileQRCode(outer, arg[0].getName_self(), arg[0].getPhone_number(), arg[0].getRsa_publickey(), size);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap a) {
            viewprofile.getImageView_ProfileQRCode().setImageBitmap(bitmap);
            viewprofile.getImageView_ProfileQRCode().setVisibility(View.VISIBLE);
            viewprofile.getProgressBar_Refresh().setVisibility(View.GONE);
        }
    }
}
