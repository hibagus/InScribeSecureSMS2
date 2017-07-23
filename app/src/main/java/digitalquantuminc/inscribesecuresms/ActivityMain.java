package digitalquantuminc.inscribesecuresms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import org.spongycastle.util.encoders.Base64;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivityContactsDetail;
import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivitySessionDetail;
import digitalquantuminc.inscribesecuresms.ChildrenActivity.ActivityValidSessionList;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.Development.ContactDummyData;
import digitalquantuminc.inscribesecuresms.Development.ProfileDummyData;
import digitalquantuminc.inscribesecuresms.Development.SessionDummyData;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.contactListAdapter;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.sessionListAdapter;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.profileRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.CompressionDecompression;
import digitalquantuminc.inscribesecuresms.UserInterface.GSMEncodingDecoding;
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

    // QR Code Scanner
    private IntentIntegrator qrScan;

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

        // Compose
        ClearComposeMessageForm();

        // QR Code Scanner
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
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
                    byte[] DecryptedQR = Cryptography.DecryptMessageAESSpongy(AESKey, EncryptedQR);
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
                            TypeSession newsession = new TypeSession(phonenum, name);
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
            Toast.makeText(this, "No Session List", Toast.LENGTH_SHORT).show();
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
        viewcompose.getBtn_ComposeEncryptMessage().setEnabled(false);
        viewcompose.getBtn_ComposeEncryptMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ComposeEncryptMessage_onClick(v);
            }
        });

        viewcompose.getBtn_ComposeSendMessage().setEnabled(false);
        viewcompose.getBtn_ComposeSelectSession().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ComposeSelectSession_onClick(v);
            }
        });
    }

    public void EncryptEncodeComposeMessage()
    {
        SecretKey AESKey;
        String PhoneNum;
        String AESKeyText;
        String PlainText;
        int CompressionMethod;
        byte[] PlainByte;
        byte[] CompressedByteBLZ4;
        byte[] CompressedByteDeflate;
        byte[] CompressedByte;
        byte[] UncompressedByte;
        byte[] AESIV;
        byte[] AESCT;
        byte[] EncryptedByte;
        String EncodedText;
        byte[] EncodedByte;
        byte[] MetadataByte;
        byte[] FinalByte;

        // Get Phone Number and the Plain Text
        PhoneNum = viewcompose.getText_ComposePartnerNumber().getText().toString();
        PlainText = viewcompose.getText_ComposePlainText().getText().toString();

        // Convert the Plain Text into Byte
        PlainByte = PlainText.getBytes();

        // Get the AES Session Key
        //sessionRepository repo = new sessionRepository(this);
        //TypeSession session = repo.getSession(PhoneNum);
        //AESKeyText = session.getSession_ecdh_aes_key();
        //AESKey = Cryptography.BytetoKeyAES(Cryptography.Base64StringtoByte(AESKeyText));

        //DEVELOPMENT ONLY
        AESKey = Cryptography.GenerateAESKey(QRCodeHandler.ProfileQRCodeAESKey, Cryptography.PBKDF2ITERATION, Cryptography.AESKEYSIZE);

        // Compress the PlainByte
        CompressedByteBLZ4 = CompressionDecompression.BlockLZ4Compress(PlainByte);
        CompressedByteDeflate = CompressionDecompression.DeflateCompress(PlainByte);

        if(CompressedByteBLZ4.length>=CompressedByteDeflate.length) // choose Deflate or Plain
        {
            if(CompressedByteDeflate.length>=PlainByte.length) // choose Plain
            {
                CompressedByte = PlainByte;
                CompressionMethod = 0;
            }
            else
            {
                CompressedByte = CompressedByteDeflate;
                CompressionMethod = 1;
            }
        }
        else // choose BLZ4
        {
            if(CompressedByteBLZ4.length>=PlainByte.length) // choose Plain
            {
                CompressedByte = PlainByte;
                CompressionMethod = 0;
            }
            else
            {
                CompressedByte = CompressedByteBLZ4;
                CompressionMethod = 2;
            }
        }

        // Encryption AES
        EncryptedByte = Cryptography.EncryptMessageAESSpongy(AESKey, CompressedByte);
        AESIV = Cryptography.GetAESIV(EncryptedByte);
        AESCT = Cryptography.GetAESContent(EncryptedByte);

        // Encoding
        EncodedText = GSMEncodingDecoding.encode(new String(EncryptedByte));
        EncodedByte = EncodedText.getBytes();


        // Update View
        viewcompose.getText_ComposeCompressedText().setText(Cryptography.BytetoBase64String(CompressedByte));
        viewcompose.getText_ComposeAESIV().setText(Cryptography.BytetoBase64String(AESIV));
        viewcompose.getText_ComposeAESCT().setText(Cryptography.BytetoBase64String(AESCT));
        viewcompose.getText_ComposeEncryptedMessage().setText(Cryptography.BytetoBase64String(EncryptedByte));
        viewcompose.getText_ComposeEncodedMessage().setText(Cryptography.BytetoBase64String(EncodedByte));

        viewcompose.getText_ComposePlainTextSize().setText(String.format(this.getString(R.string.compose_plaintextsize), PlainByte.length));
        viewcompose.getText_ComposeCompressedTextSize().setText(String.format(this.getString(R.string.compose_compressedtextsize), CompressedByte.length));
        viewcompose.getText_ComposeAESIVSize().setText(String.format(this.getString(R.string.compose_aesivtextsize), AESIV.length));
        viewcompose.getText_ComposeAESCTSize().setText(String.format(this.getString(R.string.compose_aesctextsize), AESCT.length));
        viewcompose.getText_ComposeEncryptedMessageSize().setText(String.format(this.getString(R.string.compose_encryptedtextsize), EncryptedByte.length));
        viewcompose.getText_ComposeEncodedMessageSize().setText(String.format(this.getString(R.string.compose_encodedtextsize), EncodedByte.length));

        switch (CompressionMethod)
        {
            case 0 : {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "UNCOMPRESSED"));
                break;
            }
            case 1 :
            {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "DEFLATE"));
                break;
            }
            case 2 : {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "BLOCK-LZ4"));
                break;
            }
            default: {
                viewcompose.getText_ComposeCompressedTextAlgorithm().setText(String.format(this.getString(R.string.compose_compressedtextalgorithm), "UNCOMPRESSED"));
                break;
            }
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

    private void mViewPager_onPageChanged(int position) {
        // Method to be executed each time the ViewPager Tab is Changed
        switch (position) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {
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
        TypeProfile profilenew = new TypeProfile(phonenum, name, currentdate, rsapubkey, rsaprivkey, lastsync);
        profileRepository.update(profilenew);
        RefreshProfile();
    }

    private void btn_AddContact_onClick(View v) {
        qrScan.initiateScan();
    }

    private void btn_ComposeSelectSession_onClick(View v)
    {
        Intent objIntent = new Intent(getApplicationContext(), ActivityValidSessionList.class);
        startActivityForResult(objIntent, IntentString.MainFeedbackCode_DiscardValidSession);
    }

    private void btn_ComposeEncryptMessage_onClick(View v)
    {
        if(viewcompose.getText_ComposePlainText().getText().toString().matches(""))
        {
            Toast.makeText(this, "Nothing to Encrypt!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            EncryptEncodeComposeMessage();
        }

    }
    private final TextWatcher ComposePlainTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String currentstring = viewcompose.getText_ComposePlainText().getText().toString();
            if(currentstring.matches(""))
            {
                viewcompose.getText_ComposePlainTextSize().setText(getString(R.string.compose_plaintextsize0));
            }
            else
            {
                viewcompose.getText_ComposePlainTextSize().setText(String.format(getString(R.string.compose_plaintextsize), currentstring.getBytes().length));
            }

        }

        public void afterTextChanged(Editable s) {
        }
    };
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
            viewprofile.getText_ProfileRSAPubKey().setText(Base64.toBase64String(a.getPublic().getEncoded()));
            viewprofile.getText_ProfileRSAPrivKey().setText(Base64.toBase64String(a.getPrivate().getEncoded()));
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
