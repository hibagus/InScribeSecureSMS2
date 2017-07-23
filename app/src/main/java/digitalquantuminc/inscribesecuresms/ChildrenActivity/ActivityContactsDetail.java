package digitalquantuminc.inscribesecuresms.ChildrenActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.UserInterfaceColor;

public class ActivityContactsDetail extends AppCompatActivity {

    // UI Components
    private TextView text_PartnerName;
    private TextView text_PartnerNumber;
    private TextView text_ContactAcquisitionDate;
    private EditText text_PartnerRSAPubKey;
    private Button btn_DeleteContact;

    // Intent Variable
    private String ContactPhoneNumber = "";
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Binding UI Component to Code
        UIComponentBinding();

        // Get Intent
        IntentProcessor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            IntentFeedback(RESULT_OK, IntentString.MainFeedbackCode_DoNothing);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void UIComponentBinding() {
        text_PartnerName = (TextView) findViewById(R.id.text_PartnerName);
        text_PartnerNumber = (TextView) findViewById(R.id.text_PartnerNumber);
        text_ContactAcquisitionDate = (TextView) findViewById(R.id.text_ContactAcquisitionDate);
        text_PartnerRSAPubKey = (EditText) findViewById(R.id.text_PartnerRSAPubKey);
        btn_DeleteContact = (Button) findViewById(R.id.btn_DeleteContact);
        btn_DeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_DeleteContact_onClick(v);
            }
        });
    }

    protected void IntentProcessor() {
        // Get Intent from Main Activity
        Intent intent = getIntent();
        ContactPhoneNumber = intent.getStringExtra(IntentString.MainToContactsDetails_PhoneNum);
        color = intent.getIntExtra(IntentString.MainToContactsDetails_ColorTheme, 0);

        // Preparing Repository
        contactRepository repo = new contactRepository(this);
        TypeContact contact = repo.getContact(ContactPhoneNumber);

        // Set Appearance based on Contact Name
        setTitle(contact.getContact_name());
        UserInterfaceColor.setStatusBarColor(color, this);
        UserInterfaceColor.setTitleBackgroundColor(color, this);
        text_PartnerName.setText(contact.getContact_name());
        text_PartnerNumber.setText(contact.getPhone_number());
        text_ContactAcquisitionDate.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(contact.getAcquisition_date())));
        text_PartnerRSAPubKey.setText(contact.getRsa_publickey());
        text_PartnerRSAPubKey.setTextIsSelectable(true);
        text_PartnerRSAPubKey.setKeyListener(null);
    }

    protected void IntentFeedback(int status, int FeedbackType) {
        Intent intent = getIntent();
        intent.putExtra(IntentString.MainFeedBackCode, FeedbackType);
        setResult(status, intent);
        finish();
    }

    private void btn_DeleteContact_onClick(View v) {
        String PhoneNumber = text_PartnerNumber.getText().toString();
        contactRepository repo = new contactRepository(this);
        sessionRepository repo2 = new sessionRepository(this);
        repo.delete(PhoneNumber);
        repo2.delete(PhoneNumber);
        IntentFeedback(Activity.RESULT_OK, IntentString.MainFeedBackCode_RefreshContactListSessionListCompose);
    }

}
