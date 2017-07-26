package digitalquantuminc.inscribesecuresms.ChildrenActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.SecretKey;

import digitalquantuminc.inscribesecuresms.ActivityMain;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.conversationListDetailAdapter;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.messageRepository;
import digitalquantuminc.inscribesecuresms.Repository.profileRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.Cryptography;
import digitalquantuminc.inscribesecuresms.UserInterface.GSMEncoderDecoder;
import digitalquantuminc.inscribesecuresms.UserInterface.QRCodeHandler;
import digitalquantuminc.inscribesecuresms.UserInterface.UserInterfaceColor;

public class ActivityConversationListDetail extends AppCompatActivity {

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    private static final String SMS_URI_INBOX = "content://sms/inbox";
    private static final String SMS_URI_SENT = "content://sms/sent";
    // UI Components
    private ListView list_conversation_list_detail;
    // Intent Variable
    private String PartnerPhoneNumber = "";
    private String NewestTimeStamp ="";
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Binding UI Component to Code
        UIComponentBinding();

        // Get Intent
        IntentProcessor();
    }
    @Override
    public void onBackPressed() {
        IntentFeedback(RESULT_OK, IntentString.MainFeedBackCode_RefreshConversationList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            IntentFeedback(RESULT_OK, IntentString.MainFeedBackCode_RefreshConversationList);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void UIComponentBinding() {
        list_conversation_list_detail = (ListView) findViewById(R.id.list_conversation_list_detail);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle all feedbackcode request from child activity to be executed in parent activity
        super.onActivityResult(requestCode, resultCode, data);
        // Check request code send by child activity before it closes itself.
        if (resultCode == Activity.RESULT_OK) {
            int code = data.getIntExtra(IntentString.ConversationListDetailsFeedbackCode, IntentString.ConversationListDetailsFeedbackCode_RefreshList);
            switch (code) {
                case IntentString.ConversationListDetailsFeedbackCode_DoNothing: {
                    break;
                }
                case IntentString.ConversationListDetailsFeedbackCode_RefreshList: {
                    SyncMessage();
                    LoadConversationList(list_conversation_list_detail,PartnerPhoneNumber);
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

    protected void IntentProcessor() {
        // Get Intent from Main Activity
        Intent intent = getIntent();
        PartnerPhoneNumber = intent.getStringExtra(IntentString.MainToConversationListDetails_PhoneNum);
        contactRepository repo = new contactRepository(this);
        TypeContact contact = repo.getContact(PartnerPhoneNumber);
        setTitle("Conversation with " + contact.getContact_name());

        color = intent.getIntExtra(IntentString.MainToConversationListDetails_ColorTheme, 0);
        UserInterfaceColor.setTitleBackgroundColor(color, this);
        UserInterfaceColor.setStatusBarColor(UserInterfaceColor.getDarkColor(color), this);
        NewestTimeStamp = intent.getStringExtra(IntentString.MainToConversationListDetails_Timestamp);
        LoadConversationList(list_conversation_list_detail,PartnerPhoneNumber);
    }

    protected void IntentFeedback(int status, int FeedbackType) {
        Intent intent = getIntent();
        intent.putExtra(IntentString.MainFeedBackCode, FeedbackType);
        setResult(status, intent);
        finish();
    }

    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    public long GetSMSLastSync() {
        profileRepository repo2 = new profileRepository(this);
        TypeProfile profile = repo2.getProfile(TypeProfile.DEFAULTID);
        return profile.getLastsync();
    }

    public void SyncMessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
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

            ActivityMain inst = ActivityMain.instance();
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

                    // Check if the message address is avaliable
                    if (repo3.isContactExist(strAddress))
                    {
                        long longDate = curInbox.getLong(index_Date);
                        byte[] decodedmessage = GSMEncoderDecoder.Decode(strbody);
                        if (decodedmessage.length >= 8) // metadata MAY present
                        {
                            TypeMetaMessage meta = TypeMetaMessage.ExtractMetaData(decodedmessage);
                            // Check whether the message is compatible with apps
                            if (meta.getMessageHeadID() == TypeMetaMessage.MessageHeadIDVersion0 && meta.getMessageTailID() == TypeMetaMessage.MessageTailIDVersion0) {
                                TypeMessage message = new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, meta.getMessageType(), strAddress, longDate, strbody, "");
                                repo2.insert(message);
                                inboxcounter++;
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

                    // Check if the message address is avaliable
                    if (repo3.isContactExist(strAddress))
                    {
                        long longDate = curSent.getLong(index_Date);
                        byte[] decodedmessage = GSMEncoderDecoder.Decode(strbody);
                        if (decodedmessage.length >= 8) // metadata MAY present
                        {
                            TypeMetaMessage meta = TypeMetaMessage.ExtractMetaData(decodedmessage);
                            // Check whether the message is compatible with apps
                            if (meta.getMessageHeadID() == TypeMetaMessage.MessageHeadIDVersion0 && meta.getMessageTailID() == TypeMetaMessage.MessageTailIDVersion0) {
                                TypeMessage message = new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, meta.getMessageType(), strAddress, longDate, strbody, "");
                                repo2.insert(message);
                                sentcounter++;
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
            UpdateSMSLastSync();
        }
    }

    public void UpdateSMSLastSync() {
        profileRepository repo = new profileRepository(this);
        TypeProfile profile = repo.getProfile(TypeProfile.DEFAULTID);
        profile.setLastsync(System.currentTimeMillis());
        repo.update(profile);
    }

    public void LoadConversationList(ListView listconversationdetails, String PhoneNum) {
        messageRepository repo = new messageRepository(this);
        ArrayList<HashMap<String, String>> conversationListDetails = repo.getMessageListbyAddressSorted(PhoneNum);
        if (conversationListDetails.size() != 0) {
            // Set adapter for the listconversation
            listconversationdetails.setAdapter(new conversationListDetailAdapter(ActivityConversationListDetail.this, conversationListDetails));
            // Set the Event Handler when the item in the List Session gets clicked.
            listconversationdetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listconversationdetails_onItemClick(parent, view, position, id);
                }
            });

        } else {
            conversationListDetailAdapter adapter = (conversationListDetailAdapter) listconversationdetails.getAdapter();
            if(adapter!=null)
            {
                listconversationdetails.setAdapter(null);
            }
            Toast.makeText(this, "No Conversation Available for Specific People at This Moment!", Toast.LENGTH_SHORT).show();
        }
    }

    private void listconversationdetails_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Method that handle action when the item in SessionList listview is clicked.

        // First, we need to bind the UX Component into variable to access it easily.
        TextView textlist_TimeStamp = (TextView) view.findViewById(R.id.textlist_TimeStamp);
        TextView textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);

        // Extract the Phone Number as a unique identity for database query.
        String PartnerNumber = textlist_PartnerNumber.getText().toString();
        String Timestamp = textlist_TimeStamp.getText().toString();

        // Preparing intent to be passed to ActivitySessionDetail as child activity.
        Intent objIntent = new Intent(getApplicationContext(), ActivityMessageDetail.class);
        objIntent.putExtra(IntentString.ConversationListDetailstoMessageDetails_PhoneNum, PartnerNumber);
        objIntent.putExtra(IntentString.ConversationListDetailstoMessageDetails_Timestamp, Timestamp);

        // Start the ActivitySessionDetail by passing the intent and the code for feedback request.
        startActivityForResult(objIntent, IntentString.ConversationListDetailsFeedbackCode_RefreshList);
    }

}
