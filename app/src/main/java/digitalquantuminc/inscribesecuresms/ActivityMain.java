package digitalquantuminc.inscribesecuresms;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivityContactsDetail;
import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivityConversationListDetail;
import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivitySessionDetail;
import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivityValidSessionList;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.Development.ContactDummyData;
import digitalquantuminc.inscribesecuresms.Development.MessageDummyData;
import digitalquantuminc.inscribesecuresms.Development.ProfileDummyData;
import digitalquantuminc.inscribesecuresms.Development.SessionDummyData;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.contactListAdapter;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.conversationListAdapter;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.sessionListAdapter;
import digitalquantuminc.inscribesecuresms.Message.MessageSender;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.messageRepository;
import digitalquantuminc.inscribesecuresms.Repository.profileRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.CompressionDecompression;
import digitalquantuminc.inscribesecuresms.UserInterface.Cryptography;
import digitalquantuminc.inscribesecuresms.UserInterface.GSMEncoderDecoder;
import digitalquantuminc.inscribesecuresms.UserInterface.QRCodeHandler;
import digitalquantuminc.inscribesecuresms.UserInterface.UserInterfaceColor;
import digitalquantuminc.inscribesecuresms.View.ViewAbout;
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
    private static ActivityMain inst;
    private static boolean active = false;
    private static final int DEPLOYMENT_MODE = 0;
    private static final int DEVELOPMENT_MODE = 1;
    private static final int RUNTIME_MODE = DEVELOPMENT_MODE;
    private static final String SMS_URI_INBOX = "content://sms/inbox";
    private static final String SMS_URI_SENT = "content://sms/sent";
    private static int CurrentViewPagerPosition;
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
    private ViewAbout viewabout;

    // Variable for Toolbar
    private Toolbar toolbar;

    // Variable for Tablayout
    private TabLayout tabLayout;

    // QR Code Scanner
    private IntentIntegrator qrScan;

    //endregion

    public static ActivityMain instance() {
        return inst;
    }

    public static boolean active() {
        return active;
    }


    //region Override Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dummy data, only for development only.
        if (RUNTIME_MODE == DEVELOPMENT_MODE) {
            //ContactDummyData.ClearDB(this);
            ContactDummyData.CreateDB(this);
            //ContactDummyData.LoadDummyData(this);
            //SessionDummyData.ClearDB(this);
            SessionDummyData.CreateDB(this);
            //SessionDummyData.LoadDummyData(this);
            //MessageDummyData.ClearDB(this);
            MessageDummyData.CreateDB(this);
            //MessageDummyData.LoadDummyData(this);
            //ProfileDummyData.ClearDB(this);
            ProfileDummyData.CreateDB(this);
        }

        // UX Layout Setup
        setupUXLayout();

        LoadAbout();

        // Contact List
        LoadContactList(viewcontactslist.getList_contacts());

        // Session List
        LoadSessionList(viewsessionlist.getList_session());

        // Profile
        LoadProfile();

        SyncMessage();
        UpdateSMSLastSync();
        // Conversation List
        LoadConversationList(viewconversationlist.getList_conversation());

        // Compose
        ClearComposeMessageForm();

        // QR Code Scanner
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
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
        if (id == R.id.action_refresh)
        {
            switch(CurrentViewPagerPosition)
            {
                case 0:
                {
                    SyncMessage();
                    UpdateSMSLastSync();
                    LoadConversationList(viewconversationlist.getList_conversation());
                    break;
                }
                case 1:
                {
                    break;
                }
                case 2:
                {
                    LoadContactList(viewcontactslist.getList_contacts());
                    break;
                }
                case 3:
                {
                    LoadSessionList(viewsessionlist.getList_session());
                    break;
                }
                case 4:
                {
                    LoadProfile();
                    break;
                }
                case 5:
                {
                    LoadAbout();
                    break;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check for QR Code Scanner
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "QR Code Not Found", Toast.LENGTH_SHORT).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    byte[] EncryptedQR = Cryptography.Base64StringtoByte(result.getContents());
                    SecretKey AESKey = Cryptography.GenerateAESKey(QRCodeHandler.ProfileQRCodeAESKey, Cryptography.PBKDF2ITERATION, Cryptography.AESKEYSIZE);
                    byte[] DecryptedQR = Cryptography.DecryptMessageAES(AESKey, EncryptedQR);
                    JSONObject obj = new JSONObject(new String(DecryptedQR));

                    String name = obj.getString("name");
                    String phonenum = obj.getString("phone");
                    String pubkey = obj.getString("pubkey");

                    // Check if Number Exist
                    contactRepository repo = new contactRepository(this);
                    if (!repo.isContactExist(phonenum)) {
                        profileRepository repo2 = new profileRepository(this);
                        if (!repo2.isProfileExist(phonenum)) {
                            sessionRepository repo3 = new sessionRepository(this);
                            TypeContact newcontact = new TypeContact(phonenum, name, System.currentTimeMillis(), pubkey);
                            TypeSession newsession = new TypeSession(phonenum);
                            repo.insert(newcontact);
                            repo3.insert(newsession);
                            LoadContactList(viewcontactslist.getList_contacts());
                            LoadSessionList(viewsessionlist.getList_session());
                            Toast.makeText(this, "Success: New Contact has been added to Phone Book", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed: Phone Number is already registered as Your Number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed: Phone Number is already registered in Phone Book", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Cannot Recognize QRCode", Toast.LENGTH_SHORT).show();
                }
            }
        } else { // Normal Intent Processor (Outside the QR Code)
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
                    case IntentString.MainFeedbackCode_RefreshSessionList: {
                        LoadSessionList(viewsessionlist.getList_session());
                        break;
                    }
                    case IntentString.MainFeedbackCode_RefreshBothContactandSessionList: {
                        LoadContactList(viewcontactslist.getList_contacts());
                        LoadSessionList(viewsessionlist.getList_session());
                        break;
                    }
                    case IntentString.MainFeedbackCode_LoadValidSession: {
                        String name = data.getStringExtra(IntentString.ValidSessiontoMain_Name);
                        String phone = data.getStringExtra(IntentString.ValidSessiontoMain_PhoneNum);
                        ClearComposeMessageForm();
                        viewcompose.getText_ComposePartnerName().setText(name);
                        viewcompose.getText_ComposePartnerNumber().setText(phone);
                        viewcompose.getText_ComposePlainText().setEnabled(true);
                        viewcompose.getBtn_ComposeEncryptMessage().setEnabled(true);
                        break;
                    }
                    case IntentString.MainFeedbackCode_DiscardValidSession: {
                        Toast.makeText(this, "No Valid Secure Session Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case IntentString.MainFeedBackCode_RefreshContactListSessionListCompose: {
                        LoadContactList(viewcontactslist.getList_contacts());
                        LoadSessionList(viewsessionlist.getList_session());
                        ClearComposeMessageForm();
                    }
                    case IntentString.MainFeedBackCode_RefreshConversationList: {
                        SyncMessage();
                        LoadConversationList(viewconversationlist.getList_conversation());
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "Operation Canceled", Toast.LENGTH_SHORT).show();
            }
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
        viewabout = new ViewAbout(this, findViewById(R.id.view_about));

        // Instantiate View Pager Adapter for the View Pager
        adapter = new ViewPagerAdapter();
        // Add Child View to the Adapter
        adapter.addView(viewconversationlist);
        adapter.addView(viewcompose);
        adapter.addView(viewcontactslist);
        adapter.addView(viewsessionlist);
        adapter.addView(viewprofile);
        adapter.addView(viewabout);

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
            contactListAdapter adapter = (contactListAdapter) listcontact.getAdapter();
            if (adapter != null) {
                listcontact.setAdapter(null);
            }
            Toast.makeText(this, "No Contact List", Toast.LENGTH_SHORT).show();
        }
        viewcontactslist.getBtn_AddContact().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_AddContact_onClick(v);
            }
        });
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
            sessionListAdapter adapter = (sessionListAdapter) listsession.getAdapter();
            if (adapter != null) {
                listsession.setAdapter(null);
            }
            Toast.makeText(this, "No Session List", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadConversationList(ListView listconversation) {
        messageRepository repo = new messageRepository(this);
        ArrayList<HashMap<String, String>> conversationList = repo.getNewestMessageListinEachAddressSorted();
        if (conversationList.size() != 0) {
            // Set adapter for the listconversation
            listconversation.setAdapter(new conversationListAdapter(ActivityMain.this, conversationList));
            // Set the Event Handler when the item in the List Session gets clicked.
            listconversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listconversation_onItemClick(parent, view, position, id);
                }
            });

        } else {
            conversationListAdapter adapter = (conversationListAdapter) listconversation.getAdapter();
            if (adapter != null) {
                listconversation.setAdapter(null);
            }
            Toast.makeText(this, "No Conversation Available at This Moment!", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadAbout() {
        if (RUNTIME_MODE == DEVELOPMENT_MODE) {
            viewabout.getBtn_load1().setEnabled(true);
            viewabout.getBtn_load1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_load1_onClick(v);
                }
            });
            viewabout.getBtn_load2().setEnabled(true);
            viewabout.getBtn_load2().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_load2_onClick(v);
                }
            });
            viewabout.getBtn_load3().setEnabled(true);
            viewabout.getBtn_load3().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_load3_onClick(v);
                }
            });
        }
    }

    public void LoadProfile() {
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

    public void RefreshProfile() {
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

    public void GenerateProfileQRCode() {
        String name = viewprofile.getText_ProfileName().getText().toString();
        String phonenum = viewprofile.getText_ProfileNumber().getText().toString();
        String rsapubkey = viewprofile.getText_ProfileRSAPubKey().getText().toString();
        int size = viewprofile.getImageView_ProfileQRCode().getLayoutParams().height;
        //String phone_number, String name_self, long generated_date, String rsa_publickey, String rsa_privatekey, long lastsync
        TypeProfile profile = new TypeProfile(phonenum, name, 0, rsapubkey, "", 0);
        Bitmap bitmap = null;
        GenerateProfileQRCodeAsync asynctask = new GenerateProfileQRCodeAsync(bitmap, size);
        asynctask.execute(profile);
    }

    public void ClearComposeMessageForm() {
        viewcompose.getText_ComposePartnerName().setText("");
        viewcompose.getText_ComposePartnerNumber().setText("");

        viewcompose.getText_ComposePlainText().setText("");
        viewcompose.getText_ComposePlainText().setEnabled(false);
        viewcompose.getText_ComposePlainText().addTextChangedListener(ComposePlainTextWatcher);

        viewcompose.getText_ComposeCompressedText().setText("");
        viewcompose.getText_ComposeCompressedText().setTextIsSelectable(true);
        viewcompose.getText_ComposeCompressedText().setKeyListener(null);

        viewcompose.getText_ComposeAESIV().setText("");
        viewcompose.getText_ComposeAESIV().setTextIsSelectable(true);
        viewcompose.getText_ComposeAESIV().setKeyListener(null);

        viewcompose.getText_ComposeAESCT().setText("");
        viewcompose.getText_ComposeAESCT().setTextIsSelectable(true);
        viewcompose.getText_ComposeAESCT().setKeyListener(null);

        viewcompose.getText_ComposeEncryptedMessage().setText("");
        viewcompose.getText_ComposeEncryptedMessage().setTextIsSelectable(true);
        viewcompose.getText_ComposeEncryptedMessage().setKeyListener(null);

        viewcompose.getText_ComposeEncodedMessage().setText("");
        viewcompose.getText_ComposeEncodedMessage().setTextIsSelectable(true);
        viewcompose.getText_ComposeEncodedMessage().setKeyListener(null);

        viewcompose.getText_ComposeFinalMessage().setText("");
        viewcompose.getText_ComposeFinalMessage().setTextIsSelectable(true);
        viewcompose.getText_ComposeFinalMessage().setKeyListener(null);

        viewcompose.getBtn_ComposeSendMessage().setEnabled(false);
        viewcompose.getBtn_ComposeEncryptMessage().setEnabled(false);

        viewcompose.getText_ComposePlainTextSize().setText(String.format(this.getString(R.string.compose_plaintextsize0)));
        viewcompose.getText_ComposeCompressedTextSize().setText(String.format(this.getString(R.string.compose_compressedtextsize0)));
        viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm0)));
        viewcompose.getText_ComposeAESIVSize().setText(String.format(this.getString(R.string.compose_aesivtextsize0)));
        viewcompose.getText_ComposeEncryptedMessageSize().setText(String.format(this.getString(R.string.compose_encryptedtextsize0)));
        viewcompose.getText_ComposeEncodedMessageSize().setText(String.format(this.getString(R.string.compose_encodedtextsize0)));
        viewcompose.getText_ComposeMetadataSize().setText(String.format(this.getString(R.string.compose_metadatasize0)));
        viewcompose.getText_ComposeFinalMessageSize().setText(String.format(this.getString(R.string.compose_completeencryptedsize0)));
        viewcompose.getText_ComposeNumberofMessage().setText(String.format(this.getString(R.string.compose_numberofsms0)));
        viewcompose.getBtn_ComposeEncryptMessage().setEnabled(false);
        viewcompose.getBtn_ComposeEncryptMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ComposeEncryptMessage_onClick(v);
            }
        });

        viewcompose.getBtn_ComposeSendMessage().setEnabled(false);
        viewcompose.getBtn_ComposeSendMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ComposeSendMessage_onClick(v);
            }
        });

        viewcompose.getBtn_ComposeSelectSession().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ComposeSelectSession_onClick(v);
            }
        });
    }

    public void EncryptEncodeComposeMessage() {
        SecretKey AESKey;
        String PhoneNum;
        String AESKeyText;
        String PlainText;
        int CompressionMethod;
        byte[] PlainByte;
        byte[] CompressedByteBLZ4;
        byte[] CompressedByteDeflate;
        byte[] CompressedByte;
        byte[] AESIV;
        byte[] AESCT;
        byte[] EncryptedByte;
        String EncodedText;
        byte[] EncodedByte;
        byte[] FinalByte;

        // Get Phone Number and the Plain Text
        PhoneNum = viewcompose.getText_ComposePartnerNumber().getText().toString();
        PlainText = viewcompose.getText_ComposePlainText().getText().toString();

        // Convert the Plain Text into Byte
        PlainByte = PlainText.getBytes();

        // Get the AES Session Key
        sessionRepository repo = new sessionRepository(this);
        TypeSession session = repo.getSession(PhoneNum);
        AESKeyText = session.getSession_ecdh_aes_key();
        AESKey = Cryptography.BytetoKeyAES(Cryptography.Base64StringtoByte(AESKeyText));

        //DEVELOPMENT ONLY
        // TODO: REMOVE WHEN NOT IN DEVELOPMENT
        //AESKey = Cryptography.GenerateAESKey(QRCodeHandler.ProfileQRCodeAESKey, Cryptography.PBKDF2ITERATION, Cryptography.AESKEYSIZE);

        // Compress the PlainByte
        CompressedByteBLZ4 = CompressionDecompression.BlockLZ4Compress(PlainByte);
        CompressedByteDeflate = CompressionDecompression.DeflateCompress(PlainByte);

        if (CompressedByteBLZ4.length >= CompressedByteDeflate.length) // choose Deflate or Plain
        {
            if (CompressedByteDeflate.length >= PlainByte.length) // choose Plain
            {
                CompressedByte = PlainByte;
                CompressionMethod = 0;
            } else {
                CompressedByte = CompressedByteDeflate;
                CompressionMethod = 1;
            }
        } else // choose BLZ4
        {
            if (CompressedByteBLZ4.length >= PlainByte.length) // choose Plain
            {
                CompressedByte = PlainByte;
                CompressionMethod = 0;
            } else {
                CompressedByte = CompressedByteBLZ4;
                CompressionMethod = 2;
            }
        }

        // Encryption AES
        EncryptedByte = Cryptography.EncryptMessageAES(AESKey, CompressedByte);
        AESIV = Cryptography.GetAESIV(EncryptedByte);
        AESCT = Cryptography.GetAESContent(EncryptedByte);

        // Embedding Meta
        TypeMetaMessage meta = null;
        switch (CompressionMethod) {
            case 0: {
                meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
                break;
            }
            case 1: {
                meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeNormalEncryptedCompressedDeflate, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
                break;
            }
            case 2: {
                meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeNormalEncryptedCompressedBLZ4, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
                break;
            }
            default: {
                meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
                break;
            }
        }

        FinalByte = TypeMetaMessage.EmbedMetaData(meta, EncryptedByte);
        // Encoding
        EncodedText = GSMEncoderDecoder.Encode(FinalByte);
        EncodedByte = EncodedText.getBytes(); //Charset.forName("ISO-8859-1")

//        Log.v("Decoded Byte META : ", Cryptography.BytetoBase64String(FinalByte));
//        Log.v("Message Only Byte : ", Cryptography.BytetoBase64String(EncryptedByte));
//        Log.v("AES IV : ", Cryptography.BytetoBase64String(AESIV));
//        Log.v("AES CT : ", Cryptography.BytetoBase64String(AESCT));
//        Log.v("Generated AES Key: ", Cryptography.BytetoBase64String(AESKey.getEncoded()));

        // Update View
        viewcompose.getText_ComposeCompressedText().setText(Cryptography.BytetoBase64String(CompressedByte));
        viewcompose.getText_ComposeAESIV().setText(Cryptography.BytetoBase64String(AESIV));
        viewcompose.getText_ComposeAESCT().setText(Cryptography.BytetoBase64String(AESCT));
        viewcompose.getText_ComposeEncryptedMessage().setText(Cryptography.BytetoBase64String(EncryptedByte));
        viewcompose.getText_ComposeFinalMessage().setText(Cryptography.BytetoBase64String(FinalByte));
        viewcompose.getText_ComposeEncodedMessage().setText(EncodedText);

        viewcompose.getText_ComposePlainTextSize().setText(String.format(this.getString(R.string.compose_plaintextsize), PlainByte.length));
        viewcompose.getText_ComposeCompressedTextSize().setText(String.format(this.getString(R.string.compose_compressedtextsize), CompressedByte.length));
        viewcompose.getText_ComposeAESIVSize().setText(String.format(this.getString(R.string.compose_aesivtextsize), AESIV.length));
        viewcompose.getText_ComposeAESCTSize().setText(String.format(this.getString(R.string.compose_aesctextsize), AESCT.length));
        viewcompose.getText_ComposeEncryptedMessageSize().setText(String.format(this.getString(R.string.compose_encryptedtextsize), EncryptedByte.length));

        viewcompose.getText_ComposeMetadataSize().setText(String.format(this.getString(R.string.compose_metadatasize), FinalByte.length - EncryptedByte.length));
        viewcompose.getText_ComposeFinalMessageSize().setText(String.format(this.getString(R.string.compose_completeencryptedsize), FinalByte.length));

        viewcompose.getText_ComposeEncodedMessageSize().setText(String.format(this.getString(R.string.compose_encodedtextsize), EncodedByte.length, EncodedText.length()));

        // Count message
        int numofmessage;
        if (EncodedText.length() > TypeMetaMessage.SMSStandardLength) // more than 1
        {
            if (EncodedText.length() > TypeMetaMessage.SMSStandardLength + TypeMetaMessage.SMSExtendedLength) // more than 2
            {
                numofmessage = ((EncodedText.length() - (TypeMetaMessage.SMSStandardLength + TypeMetaMessage.SMSExtendedLength)) / TypeMetaMessage.SMSUltraExtendedLength) + 3;
            } else // 2 message
            {
                numofmessage = 2;
            }
        } else // only one
        {
            numofmessage = 1;
        }

        viewcompose.getText_ComposeNumberofMessage().setText(String.format(this.getString(R.string.compose_numberofsms), numofmessage));
        viewcompose.getBtn_ComposeSendMessage().setEnabled(true);


        switch (CompressionMethod) {
            case 0: {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "UNCOMPRESSED"));
                break;
            }
            case 1: {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "DEFLATE"));
                break;
            }
            case 2: {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "BLOCK-LZ4"));
                break;
            }
            default: {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "UNCOMPRESSED"));
                break;
            }
        }

        // DEBUG TEST
//        String receivedtext = viewcompose.getText_ComposeEncodedMessage().getText().toString();
//        byte[] decodedbyte = GSMEncoderDecoder.Decode(receivedtext);
//        Log.v("encodedbyte:", Cryptography.BytetoBase64String(EncodedByte));
//        Log.v("decodedbyte:", Cryptography.BytetoBase64String(decodedbyte));
//        TypeMetaMessage receivedmeta = TypeMetaMessage.ExtractMetaData(decodedbyte);
//        byte[] receivedbytenonmeta = TypeMetaMessage.ExtractOriginalMessage(decodedbyte);
//        Log.v("NONMETARECEIVED:", Cryptography.BytetoBase64String(receivedbytenonmeta));
//        byte[] decryptedbyte = Cryptography.DecryptMessageAES(AESKey, receivedbytenonmeta);
//        byte[] decompressedbyte;
//        switch (receivedmeta.getMessageType()) {
//            case TypeMetaMessage.MessageTypeNormalEncryptedUncompressed: {
//                decompressedbyte = decryptedbyte;
//                break;
//            }
//            case TypeMetaMessage.MessageTypeNormalEncryptedCompressedDeflate: {
//                decompressedbyte = CompressionDecompression.DeflateDecompress(decryptedbyte);
//                break;
//            }
//            case TypeMetaMessage.MessageTypeNormalEncryptedCompressedBLZ4: {
//                decompressedbyte = CompressionDecompression.BlockLZ4Decompress(decryptedbyte);
//                break;
//            }
//            default: {
//                decompressedbyte = decryptedbyte;
//                break;
//            }
//        }
//        Log.v("PLAINTEXTRECOVERED:", new String(decompressedbyte));
    }

    public void ReceiveNewMessage(TypeMessage message) {
        // Process Received Message
        messageRepository repomessage = new messageRepository(this);
        contactRepository repocontact = new contactRepository(this);
        sessionRepository reposession = new sessionRepository(this);
        MessageSender sender = new MessageSender(this);
        String phonenumber = message.getAddress();
        TypeContact contact = repocontact.getContact(phonenumber);
        TypeSession session = reposession.getSession(phonenumber);
        String name = contact.getContact_name();
        Long TimeStamp = message.getTimestamp();
        // Process automatic message that requires no user intervention
        if (!repomessage.isMessageExist(TimeStamp)) {
            int messagetype = message.getMessagetype();
            switch (messagetype) {
                case TypeMetaMessage.MessageTypeNormalEncryptedUncompressed: {
                    if (session.getSession_validity() == TypeSession.StatusValid) {
                        int nummessageprocessed = session.getSession_num_message() + 1;
                        session.setSession_num_message(nummessageprocessed);
                        Toast.makeText(this, "You have a new secure message from " + name, Toast.LENGTH_SHORT).show();
                    } else // session not active!
                    {
                        sender.SendErrorNoSecureSessionActive(phonenumber);
                        Toast.makeText(this, "You have a new invalid secure message from " + name + ". Error message sent back!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case TypeMetaMessage.MessageTypeNormalEncryptedCompressedBLZ4: {
                    if (session.getSession_validity() == TypeSession.StatusValid) {
                        int nummessageprocessed = session.getSession_num_message() + 1;
                        session.setSession_num_message(nummessageprocessed);
                        Toast.makeText(this, "You have a new secure message from " + name, Toast.LENGTH_SHORT).show();
                    } else // session not active!
                    {
                        sender.SendErrorNoSecureSessionActive(phonenumber);
                        Toast.makeText(this, "You have a new invalid secure message from " + name + ". Error message sent back!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case TypeMetaMessage.MessageTypeNormalEncryptedCompressedDeflate: {
                    if (session.getSession_validity() == TypeSession.StatusValid) {
                        int nummessageprocessed = session.getSession_num_message() + 1;
                        session.setSession_num_message(nummessageprocessed);
                        Toast.makeText(this, "You have a new secure message from " + name, Toast.LENGTH_SHORT).show();
                    } else // session not active!
                    {
                        sender.SendErrorNoSecureSessionActive(phonenumber);
                        Toast.makeText(this, "You have a new invalid secure message from " + name + ". Error message sent back!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case TypeMetaMessage.MessageTypeHandshakeRequestDS: {
                    Toast.makeText(this, "You have a new secure session request from " + name, Toast.LENGTH_SHORT).show();
                    break;
                }
                case TypeMetaMessage.MessageTypeHandshakeReplyDS: {
                    Toast.makeText(this, "You have a new secure session reply from " + name, Toast.LENGTH_SHORT).show();
                    break;
                }
                case TypeMetaMessage.MessageTypeHandshakeSuccessDS: {
                    Toast.makeText(this, "You have a new secure session confirmation from " + name, Toast.LENGTH_SHORT).show();
                    break;
                }
                case TypeMetaMessage.MessageTypeEndSessionRequest: {
                    Toast.makeText(this, "You have reguest to end secure session with " + name, Toast.LENGTH_SHORT).show();
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    Log.v("EndedSecureSession:", "ENDED!");
                    try{
                        Thread.sleep(1000L);
                    }
                    catch(InterruptedException e){

                    }
                    sender.SendSessionEndSuccessMessage(phonenumber);
                    break;
                }
                case TypeMetaMessage.MessageTypeEndSessionSuccess: {
                    Log.v("EndedSecureSession:", "ENDEDENDEDENDED!");
                    // Only informational, no further process required!
                    Toast.makeText(this, "Secure session with " + name + " has been ended.", Toast.LENGTH_SHORT).show();
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    break;
                }
                case TypeMetaMessage.MessageTypeErrorHandshakeRequestDSNotValid: {
                    Toast.makeText(this, "Secure session with " + name + " cannot be started because invalid digital signature.", Toast.LENGTH_SHORT).show();
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    break;
                }
                case TypeMetaMessage.MessageTypeErrorHandshakeReplyDSNotValid: {
                    Toast.makeText(this, "Secure session with " + name + " cannot be started because invalid digital signature.", Toast.LENGTH_SHORT).show();
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    break;
                }
                case TypeMetaMessage.MessageTypeErrorHandshakeSuccessDSNotValid: {
                    Toast.makeText(this, "Secure session with " + name + " cannot be started because invalid digital signature.", Toast.LENGTH_SHORT).show();
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    break;
                }
                case TypeMetaMessage.MessageTypeErrorNoSecureSessionActive: {
                    Toast.makeText(this, "No Secure session active with " + name, Toast.LENGTH_SHORT).show();
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    sender.SendSessionEndRequestMessage(phonenumber);
                    break;
                }
                case TypeMetaMessage.MessageTypeErrorHandshakeDeclined: {
                    Toast.makeText(this, "Secure session handshake with " + name + " is declined.", Toast.LENGTH_SHORT).show();
                    session.setSession_num_message(0);
                    session.setSession_validity(TypeSession.StatusNotValid);
                    session.setSession_ecdh_aes_key("");
                    session.setSession_ecdh_partner_digital_signature("");
                    session.setSession_ecdh_partner_validity(TypeSession.StatusDSNotValid);
                    session.setSession_ecdh_shared_secret("");
                    session.setSession_ecdh_partner_public_key("");
                    Log.v("Harus", "masuksini");
                    break;
                }
                default: {
                    Toast.makeText(this, "Message Unknown", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            repomessage.insert(message);
            reposession.update(session, phonenumber);
            LoadConversationList(viewconversationlist.getList_conversation());
            LoadSessionList(viewsessionlist.getList_session());
        }
        UpdateSMSLastSync();
    }

    public void UpdateSMSLastSync() {
        profileRepository repo = new profileRepository(this);
        TypeProfile profile = repo.getProfile(TypeProfile.DEFAULTID);
        profile.setLastsync(System.currentTimeMillis());
        repo.update(profile);
    }

    public long GetSMSLastSync() {
        profileRepository repo2 = new profileRepository(this);
        TypeProfile profile = repo2.getProfile(TypeProfile.DEFAULTID);
        return profile.getLastsync();
    }

    public void SyncMessage() {
        // To load all message in inbox and sent item (if any)
        profileRepository repo = new profileRepository(this);
        long lastsmssync = GetSMSLastSync();

        Uri uriInbox = Uri.parse(SMS_URI_INBOX);
        Uri uriSent = Uri.parse(SMS_URI_SENT);
        String[] projection = new String[]{"_id", "address", "body", "date"};
        String filter = "date>=" + String.valueOf(lastsmssync);

        messageRepository repo2 = new messageRepository(this);
        int inboxcounter = 0;
        int sentcounter = 0;

        contactRepository repo3 = new contactRepository(this);
        // read from inbox first
        Cursor curInbox = getContentResolver().query(uriInbox, projection, filter, null, null);
        if (curInbox.moveToFirst()) {
            int index_Address = curInbox.getColumnIndex("address");
            int index_Body = curInbox.getColumnIndex("body");
            int index_Date = curInbox.getColumnIndex("date");

            do {
                String strAddress = curInbox.getString(index_Address);
                String strbody = curInbox.getString(index_Body);
                String longDate = curInbox.getString(index_Date);
                Long timestamp = Long.parseLong(longDate);
                // Check if the message address is avaliable
                if (repo3.isContactExist(strAddress)) {
                    if (!repo2.isMessageExist(timestamp)) {
                        byte[] decodedmessage = GSMEncoderDecoder.Decode(strbody);
                        if (decodedmessage.length >= 8) // metadata MAY present
                        {
                            TypeMetaMessage meta = TypeMetaMessage.ExtractMetaData(decodedmessage);
                            // Check whether the message is compatible with apps
                            if (meta.getMessageHeadID() == TypeMetaMessage.MessageHeadIDVersion0 && meta.getMessageTailID() == TypeMetaMessage.MessageTailIDVersion0) {
                                TypeMessage message = new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, meta.getMessageType(), strAddress, timestamp, strbody, "");
                                repo2.insert(message);
                                inboxcounter++;
                            }
                        }
                    }

                }
            } while (curInbox.moveToNext());
            if (!curInbox.isClosed()) {
                curInbox.close();
            }
            Toast.makeText(this, String.valueOf(inboxcounter) + " inbox message(s) have been synced.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No New Inbox Message", Toast.LENGTH_SHORT).show();
        }

        // read from sent then
        Cursor curSent = getContentResolver().query(uriSent, projection, filter, null, null);
        if (curSent.moveToFirst()) {
            int index_Address = curSent.getColumnIndex("address");
            int index_Body = curSent.getColumnIndex("body");
            int index_Date = curSent.getColumnIndex("date");

            do {
                String strAddress = curSent.getString(index_Address);
                String strbody = curSent.getString(index_Body);
                String longDate = curSent.getString(index_Date);
                Long timestamp = Long.parseLong(longDate);
                // Check if the message address is avaliable
                if (repo3.isContactExist(strAddress)) {

                    if (!repo2.isMessageExist(timestamp)) {
                        byte[] decodedmessage = GSMEncoderDecoder.Decode(strbody);
                        if (decodedmessage.length >= 8) // metadata MAY present
                        {
                            TypeMetaMessage meta = TypeMetaMessage.ExtractMetaData(decodedmessage);
                            // Check whether the message is compatible with apps
                            if (meta.getMessageHeadID() == TypeMetaMessage.MessageHeadIDVersion0 && meta.getMessageTailID() == TypeMetaMessage.MessageTailIDVersion0) {
                                TypeMessage message = new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, meta.getMessageType(), strAddress, timestamp, strbody, "");
                                repo2.insert(message);
                                sentcounter++;
                            }
                        }
                    }

                }
            } while (curSent.moveToNext());
            if (!curSent.isClosed()) {
                curSent.close();
            }
            Toast.makeText(this, String.valueOf(sentcounter) + " sent message(s) have been synced.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No New Sent Message", Toast.LENGTH_SHORT).show();
        }
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

    private void listconversation_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Method that handle action when the item in SessionList listview is clicked.

        // First, we need to bind the UX Component into variable to access it easily.
        TextView textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);
        TextView textlist_LastMessageReceived = (TextView) view.findViewById(R.id.textlist_LastMessageReceived);
        ImageView imageView_ColorAccent = (ImageView) view.findViewById(R.id.imageView_ColorAccent);

        // Extract the Phone Number as a unique identity for database query.
        String PartnerNumber = textlist_PartnerNumber.getText().toString();
        String Timestamp = textlist_LastMessageReceived.getText().toString();

        // Preparing intent to be passed to ActivitySessionDetail as child activity.
        Intent objIntent = new Intent(getApplicationContext(), ActivityConversationListDetail.class);
        objIntent.putExtra(IntentString.MainToConversationListDetails_PhoneNum, PartnerNumber);
        objIntent.putExtra(IntentString.MainToConversationListDetails_Timestamp, Timestamp);
        objIntent.putExtra(IntentString.MainToConversationListDetails_ColorTheme, ((ColorDrawable) imageView_ColorAccent.getBackground()).getColor());

        // Start the ActivitySessionDetail by passing the intent and the code for feedback request.
        startActivityForResult(objIntent, IntentString.MainFeedBackCode_RefreshConversationList);
    }

    private void mViewPager_onPageChanged(int position) {
        // Method to be executed each time the ViewPager Tab is Changed
       CurrentViewPagerPosition = position;
        switch (position) {
            case 0: {

                break;
            }
            case 1: {

                //UserInterfaceColor.setStatusBarColor(ContextCompat.getColor(this, R.color.colorDarkCyan), this);
                //UserInterfaceColor.setTitleBackgroundColor(ContextCompat.getColor(this, R.color.colorDarkCyan), this);
                break;
            }
            case 2: {

                LoadContactList(viewcontactslist.getList_contacts());
                break;
            }
            case 3: {

                LoadSessionList(viewsessionlist.getList_session());
                break;
            }
            case 4: {

                //UserInterfaceColor.setStatusBarColor(ContextCompat.getColor(this, R.color.colorMediumSeaGreen), this);
                //UserInterfaceColor.setTitleBackgroundColor(ContextCompat.getColor(this, R.color.colorMediumSeaGreen), this);
                break;
            }
            case 5: {
                //UserInterfaceColor.setStatusBarColor(ContextCompat.getColor(this, R.color.colorTeal), this);
                //UserInterfaceColor.setTitleBackgroundColor(ContextCompat.getColor(this, R.color.colorTeal), this);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void btn_RegenRSAKeyPair_onClick(View v) {

        KeyPair RSAKeyPair = null;
        GenerateRSAKeyAsync asynctask = new GenerateRSAKeyAsync(this);
        asynctask.execute(RSAKeyPair, RSAKeyPair, RSAKeyPair);
    }

    private void btn_UpdateProfile_onClick(View v) {

        profileRepository profileRepository = new profileRepository(this);
        TypeProfile profileold = profileRepository.getProfile(TypeProfile.DEFAULTID);
        String name = viewprofile.getText_ProfileName().getText().toString();
        String phonenum = viewprofile.getText_ProfileNumber().getText().toString();
        long currentdate = System.currentTimeMillis();
        String rsapubkey = viewprofile.getText_ProfileRSAPubKey().getText().toString();
        String rsaprivkey = viewprofile.getText_ProfileRSAPrivKey().getText().toString();
        long lastsync = profileold.getLastsync();
        //String phone_number, String name_self, long generated_date, String rsa_publickey, String rsa_privatekey, long lastsync
        TypeProfile profilenew = new TypeProfile(phonenum, name, currentdate, rsapubkey, rsaprivkey, lastsync);
        profileRepository.update(profilenew);
        RefreshProfile();
    }

    private void btn_AddContact_onClick(View v) {
        qrScan.initiateScan();
    }

    private void btn_ComposeSelectSession_onClick(View v) {
        Intent objIntent = new Intent(getApplicationContext(), ActivityValidSessionList.class);
        startActivityForResult(objIntent, IntentString.MainFeedbackCode_DiscardValidSession);
    }

    private void btn_ComposeEncryptMessage_onClick(View v) {
        if (viewcompose.getText_ComposePlainText().getText().toString().matches("")) {
            Toast.makeText(this, "Nothing to Encrypt!", Toast.LENGTH_SHORT).show();
        } else {
            EncryptEncodeComposeMessage();
        }

    }

    private void btn_ComposeSendMessage_onClick(View v) {
        String phonenum = viewcompose.getText_ComposePartnerNumber().getText().toString();
        String content = viewcompose.getText_ComposeEncodedMessage().getText().toString();
        sessionRepository repo = new sessionRepository(this);
        TypeSession session = repo.getSession(phonenum);
        int currentmessage = session.getSession_num_message() + 1;
        session.setSession_num_message(currentmessage);
        repo.update(session, phonenum);
        MessageSender messagesend = new MessageSender(this);
        messagesend.SendNormalMessage(phonenum, content);
        ClearComposeMessageForm();

    }

    private final TextWatcher ComposePlainTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String currentstring = viewcompose.getText_ComposePlainText().getText().toString();
            if (currentstring.matches("")) {
                viewcompose.getText_ComposePlainTextSize().setText(getString(R.string.compose_plaintextsize0));
            } else {
                viewcompose.getText_ComposePlainTextSize().setText(String.format(getString(R.string.compose_plaintextsize), currentstring.getBytes().length));
            }

        }

        public void afterTextChanged(Editable s) {
        }
    };

    //Context context, String name, String PhoneNum, Long Timestamp, String RSAPublicKey, String RSAPrivateKey
    private void btn_load1_onClick(View v) {
        ProfileDummyData.InsertProfile(this, "Ulfah Nadya", "15555215554",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsmykZcBoklG/VOF6LZ7mswQCX5kQTwJ2MVL/fjw79iZb8HBT69P130HMlcALQUr5oA3GBvaOPBVGvvGvkCq2hxa9exSyTfVeDQhPDMUoZsnShzsX2VZpX0rkhTVlUFR3Rup7HS6zhlCdCCIgsOEvYCDpDt5++xPTplBytAH3TgXtzd56u2nkDy60b7CPCmZuEfJgFcSwzTx7wcm5pAOml1JDQuupPlG70TbdPxAlJmUSXVLt+8R5RTRVzF3e7y1LKXLH+qL0w3rcG5TC0anl314Vo7+yb24ImgdP7BdQrZz8E089OHlC1ry1nUTfngONG2nkdRG5oKhKnV6wva0CVwIDAQAB",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCybKRlwGiSUb9U4XotnuazBAJfmRBPAnYxUv9+PDv2JlvwcFPr0/XfQcyVwAtBSvmgDcYG9o48FUa+8a+QKraHFr17FLJN9V4NCE8MxShmydKHOxfZVmlfSuSFNWVQVHdG6nsdLrOGUJ0IIiCw4S9gIOkO3n77E9OmUHK0AfdOBe3N3nq7aeQPLrRvsI8KZm4R8mAVxLDNPHvBybmkA6aXUkNC66k+UbvRNt0/ECUmZRJdUu37xHlFNFXMXd7vLUspcsf6ovTDetwblMLRqeXfXhWjv7JvbgiaB0/sF1CtnPwTTz04eULWvLWdRN+eA40baeR1EbmgqEqdXrC9rQJXAgMBAAECggEASEtp/0qLdIIIBwIHadXBP1bICWkxKPpj3o91M3Z/izi2twEkl+D99nSoSyMRzGZvJ9F85BRhxGmQjUzLaJxN8gWeR/E2YA0kCx7LPO7j9GYhVib0/lybfH+RgYIFp5tZ6xOWR9hE6I7gWcvOOC0973LWS+6OP/ikIPbmyxYi2OszEFgjUyVB4PbmKuMpgiPMXnHG5EZ6k7ygyfwTg3suBDUSiNH9ZcDUvUvjH7K9jVYco9PgfCwFzo4FJCg745FE2ADpJf41EDkW8Xvm2gwvd1vZdDS3+wM5i/UYHyJ3o+YygG61EAoZ3AWnp4RFtO0mOPxCLIN4Q6pjJp8W2P5kMQKBgQDrkLMFbFOL4xfH/a2wa3TqAr8VnAeRY1MzXV5k9Rgb07xmKvfUvAtv2nfGr4rvZVqLBJ+O6fJOrqtaQcuVdgi8EzL1rW+TMrauDtoEb3mQvBcz4aiBuqEbkMfCJp+Y/t3WDHb3wZTU04q6pAVSa/9LVDY6RVfYJUviYUHpAbqopwKBgQDB5v2tryPesIOoegniVZrT8/5J67Nx7NP1J5fwCQ6vy2EBypY5HEoyR7sMrGUl3Z50GsKNXi3KVMnHl2st/qc8yahRmyBtYDcM3cHjkBUQa2ZNXsqPf+Ln4uWT6QG1rvhQKhHCDABJfi36U3hVDT1D0NjtKB5KDTdZxOKq0kRe0QKBgQCoaUzzxZx9WB/6ZQy2Ijz1yHzgcCqg8Mfc4xXHS8quqlP5HyMLvlzW3PPg4kOMH4P5+5YvUGZhhlMPNhbchgR8oaU/K32nWQxtqESKA0CAD1jvSJd2F/1yfbGyxnY3pY7npwWu34EOHycBhJ4hbQCZ1FzokAbbit6TvECo2wmd5QKBgA+pBUH6zC3XGbsIqDW0bj3CDf6QX7zKwM+i8/157cq65UnMv9c69q8a5ft/DOhC/uInqyDZw178/BL3ortjPsSFEOgOXE8ZHzkVy+wCHgFmeyuyhS+Tx5Ks25fXkMDIsdtfmnGOoJNO8o2uIiDUYedRPcPhopieeqLwaNIdC1KRAoGAc9X3jQxOhow3sMvvQdt9IHV/gOJ0XNMfD8Fz4JLPc3+CzLt+dP3MHjBOAyE/N3F5L97OawPnTTBtARo9zayVMG0jUvlSBf3W+1C7wvcUkpQPXr2unpwiOhTX7bV6FBnakdcHNpAiBfGiyyiTyTW5Nv+iNqLjpA4rbaRN36e010Y=");
        LoadProfile();
        ContactDummyData.LoadSingleDummyData(this, 2);
        SessionDummyData.LoadSingleDummyData(this, 2);
        ContactDummyData.LoadSingleDummyData(this, 3);
        SessionDummyData.LoadSingleDummyData(this, 3);
    }

    private void btn_load2_onClick(View v) {
        ProfileDummyData.InsertProfile(this, "Fariz Azmi Pratama", "15555215556",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwV3G508XV1ngOreOHpmIehz9hHbaijbmXxzD+QjC8jlP4W2P3IJbBTaKRHWkwk3GrqexSpQkmhBa4xaOOxld7vfn/yZNc0liVr4lnlaI9iTkQ9jarUnLlg0DKvmrgSUOslbSAtSZ2tszmIPeVZjIsuq//G+kWPCHXxo+bdEMGqiFraW7621polCwwTWzV11pmg+/fopHT6Jy1FYBH4RFrSNltCPIgAd2RmixwRWVEB9UNvx7f7eSY94bZptJar/khy+JGDHKgO3An2gMoXlUDLZ3RzE0iJ51yo0h8nGi8d2B0e72WWTNrRIb9IPIzgv8UOLtEU44OekLOMvj46TNgQIDAQAB",
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDBXcbnTxdXWeA6t44emYh6HP2EdtqKNuZfHMP5CMLyOU/hbY/cglsFNopEdaTCTcaup7FKlCSaEFrjFo47GV3u9+f/Jk1zSWJWviWeVoj2JORD2NqtScuWDQMq+auBJQ6yVtIC1Jna2zOYg95VmMiy6r/8b6RY8IdfGj5t0QwaqIWtpbvrbWmiULDBNbNXXWmaD79+ikdPonLUVgEfhEWtI2W0I8iAB3ZGaLHBFZUQH1Q2/Ht/t5Jj3htmm0lqv+SHL4kYMcqA7cCfaAyheVQMtndHMTSInnXKjSHycaLx3YHR7vZZZM2tEhv0g8jOC/xQ4u0RTjg56Qs4y+PjpM2BAgMBAAECggEAEYytTLkE6Uyi6TFXmLdeh5ax+5+93eV1qxQ1RRjieJFzCoajE+RQ1nxIuEXlGi1s5tUZZidj2we49/tIFV0MBXBCggm75ca3QiAn0eMQsyZOAUphLnEQJSIxM2lNg38VgmIW1WLuQ8q5OBJfz2z6aiKcIhEP3XKXFq6PE/BxJMoWUvLdvRaR7nDDyX1Oqu/mjpctpU0cSX8X90+CfRTMMRsMsQ+MEIljwZ51SHCBF4ZJ3zcBZWYiIR9+7IA5j5AXZBmAivBc8vu7cZxIhi8fyb1YTlq1xv91pUagaMXNMcrFsjEfoei89j+1YhnAiuJAzsiZBCpdDyDmdoZzbsWbQQKBgQDi+53FnMt93bX3MJOMS2Vx0RF0I9s4ut+u608tapI/lSCt3DlPBfIuanhwlBUOk6OA7Ss63hrPGr58M1qcOp56HeMRn/8Tt1j+TKdq02XTNMPXUt9W5/Neyp4CzAAi5hgKUStedHWD+XLzdpbAxSjrh8Cg/pH6jmYt/hkCivwYbQKBgQDaFgEL1DbhZRzH7eV1tnelNsGDHO6v9CnTTRLM95n+9A/logUcVd4BURVQ48H3P70DbZaxNKf5NAzaxGtIe/s90GIhixi5DboDV2mZm3oGZspsfeq8q/pBUbKfFwatV1m3rTtWV+u4nY55gDOAsMsmFreRP4X1TJFBy3HkZH5E5QKBgQDGh+HleFEcVBHWlWxYp5GhTYYAmWQjaIBBVJu6U557copUx2xwy/iZ1JJnlX4dc9Ds8YSARsgYIYI+zAQS5cq7cOys+851hkaWlqFQdHp5k4tACMJEFzjszjgKpjfwTmT0kS5nvWET/9klTbJqBYjXCbPYnRE9n9OLotZpPPtmuQKBgQC00OOjLBsoe84GEb9a/qNqjuCY1acsqbL354I8ANpkYXTAvrmgCa2cx951h7DtT6JmMjlryS2v17EEvS/6FBl14c1K5GnmHHRqitIaMqdUoWsZ0riKH8jI2XTQpKW7mJ3hRTbaWuEs2y0ineGVxH9aoCEow1NM02Pn+kb+xzdN5QKBgQCOgNeYYbbS0BL8kVfUCkV/oT9MWlQp+u1ketzsrUgQQIMsCXDT9Ph3HTQmIeSWB9FeXKUJwWA8YWHm7+yaUq1KCjn3+v9j1N7NNgn7/1nE/cudzxMhDGqvkdsPDjuBIuBUiX5CFxzXkrZUmV9ucSKQAG6HcIk3vrqxA3EI6wjifQ==");
        LoadProfile();
        ContactDummyData.LoadSingleDummyData(this, 1);
        SessionDummyData.LoadSingleDummyData(this, 1);
        ContactDummyData.LoadSingleDummyData(this, 3);
        SessionDummyData.LoadSingleDummyData(this, 3);
    }

    private void btn_load3_onClick(View v) {
        ProfileDummyData.InsertProfile(this, "Bagus Hanindhito", "15555215558",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8XV9rur7H1ONM1syNWm75aVLWiDUVh/y2XN80IfumhEvjV5x8GlcjNrhdWfL4vii9+4bKaUKNAr50Cw1XN8sGyb8bgVAXLLVIquZO8wB1h81PTiUNtoUA/Keb7jOwJXC2+pSW/+U1FpergC6PndS0JyjlLnxifMhudHwnL6mvGomr9x6lLlqTp1Ce0qctOV5cIHOqcYyQ/xjl8/sdXi1u8q8i01+XAIqg11YRyI1qouBYXkhHyzd4GhINy5p8jzttb12ugD6r0H5nU9ZDG3U/FCz9+/Nr9/wmkiOE30Rzw6+3RvcetFcEi+OesWl3b7QyVx4KrIZ1cxYX3j3O9SEFQIDAQAB",
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDxdX2u6vsfU40zWzI1abvlpUtaINRWH/LZc3zQh+6aES+NXnHwaVyM2uF1Z8vi+KL37hsppQo0CvnQLDVc3ywbJvxuBUBcstUiq5k7zAHWHzU9OJQ22hQD8p5vuM7AlcLb6lJb/5TUWl6uALo+d1LQnKOUufGJ8yG50fCcvqa8aiav3HqUuWpOnUJ7Spy05Xlwgc6pxjJD/GOXz+x1eLW7yryLTX5cAiqDXVhHIjWqi4FheSEfLN3gaEg3LmnyPO21vXa6APqvQfmdT1kMbdT8ULP3782v3/CaSI4TfRHPDr7dG9x60VwSL456xaXdvtDJXHgqshnVzFhfePc71IQVAgMBAAECggEAFskgFageD4w6aGzENa8nKJorHLO5ZfE44RVZY3JYbViO1LfjZ60Bq1cibIphfqc49c2V6Z2l81hPz7nERWHYDy3kJ1u+gaDspQttd+ueBr9GLmNO9UgqGF9GXUOi2yCm1Urs/7qEKG/ovGHcMXXgLkiB1VCgvwRfG+J4YspJAMGSuLrzOSvZ+ntNlFCHwRLYkHM8NcvIHD2D7I+d4qbLMBLBefUktKn5Z5coLbqhpcLEAIO7L0T6WwcP0eR8gh9qUeNfF40ti8G78ldeMdKDo51ofzekR5FDN8X2dqfIdRNR2HMSYlPtM/v7ly/D+cXrliYQa7cARTC9WRNHTMORpwKBgQD/seWhg8GdKbePD+JD8THtU1eUcQ9geFHedA01pUZq/kKv5mcA2NaGzT+86UNcb0n19f30wLKdt/aipnawGOlhj9hUlPe6Ex7HThf5ZgNYIs8DFYgCBpPZlrpMSVhAhIPghPUQW+dNwBL1C5VoTUts/vv2bROSQB/AA6Eejoez5wKBgQDxvz7at43jm2/+uxDBLRCtfFu6UDnlmgJPPeYrjatQOwTkHFugxGZP+BL6bqnenfveMiQdKCQ1Wp3clF5Me2Li4E3cS7pp8b+9ToUxR/ekZWUf5bteqasDyPSNTseyj+gxs0iPliLja3Ut0vljEMFRyAgws2Xx5ZBXoGLUzgxIowKBgQCFNcobVkglN9mgl7bajKrlKHlFJY3MRYLpcO5810kTbrmRaGibQVyqR/3/zkrAul3+3RRcUZP8pR3B6RWGcsQLhuQ+VImnEFUFooLM9L+jSCcvRSVhYMngHy2ZltB6dE88RihiSG79y6ZUsZ86AqLb+w+Ld5ItrGieIYXsJHdAWwKBgFYqIz4T1If+vBFgHZ2s8VfUjmE+/RPgK9iQqHx3l0sWbizcCrCnsAvq0ODgFr3ZM4/D8WlPCHDX5pJbc6zxAZL5/eZ6O2xNlVjlJsYk75hx53RSDtGzydekhb3kCXuUnV8xlrhO/ApxH6gAnZD4xN7gXEL3fmuUtjj1WqSc+HXfAoGBAI7CuyT0rHqA0WcmWDyZupfAOhRUXmWR0Y/7u+mKODXRXW6yB0A+JMKyiKg4ztsZPPB31xAp9Te6fYrTjE1kgbSWm23/bwwN/h3vZFHdIBAFKEB6WYuOTq/JTS+1y9S+4oNm7u8ioG7wwh/Bx3Rv95kacAXJMbdB58osLnwdSYNK");
        LoadProfile();
        ContactDummyData.LoadSingleDummyData(this, 1);
        SessionDummyData.LoadSingleDummyData(this, 1);
        ContactDummyData.LoadSingleDummyData(this, 2);
        SessionDummyData.LoadSingleDummyData(this, 2);
    }

    //endregion

    private class GenerateRSAKeyAsync extends AsyncTask<KeyPair, KeyPair, KeyPair> {
        private Activity outer;

        public GenerateRSAKeyAsync(Activity outer) {
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
            viewprofile.getText_ProfileRSAPubKey().setText(Cryptography.BytetoBase64String(a.getPublic().getEncoded()));
            viewprofile.getText_ProfileRSAPrivKey().setText(Cryptography.BytetoBase64String(a.getPrivate().getEncoded()));
        }
    }

    private class GenerateProfileQRCodeAsync extends AsyncTask<TypeProfile, Bitmap, Bitmap> {
        private Bitmap bitmap;
        private int size;

        public GenerateProfileQRCodeAsync(Bitmap bitmap, int size) {
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
            bitmap = QRCodeHandler.GenerateProfileQRCode(arg[0].getName_self(), arg[0].getPhone_number(), arg[0].getRsa_publickey(), size);
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
