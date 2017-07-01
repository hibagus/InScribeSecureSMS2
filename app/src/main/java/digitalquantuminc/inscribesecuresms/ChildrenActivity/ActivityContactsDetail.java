package digitalquantuminc.inscribesecuresms.ChildrenActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;

public class ActivityContactsDetail extends AppCompatActivity {

    // UI Components
    private TextView text_PartnerName;
    private TextView text_PartnerNumber;
    private TextView text_ContactAcquisitionDate;
    private EditText text_partnerRSAPubKey;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            IntentFeedback(IntentString.MainFeedbackCode_DoNothing);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void UIComponentBinding() {
        text_PartnerName = (TextView) findViewById(R.id.text_PartnerName);
        text_PartnerNumber = (TextView) findViewById(R.id.text_PartnerNumber);
        text_ContactAcquisitionDate = (TextView) findViewById(R.id.text_ContactAcquisitionDate);
        text_partnerRSAPubKey = (EditText) findViewById(R.id.text_partnerRSAPubKey);
        btn_DeleteContact = (Button) findViewById(R.id.btn_DeleteContact);
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
        setStatusBarColor(color);
        setTitleBackgroundColor(color);
        text_PartnerName.setText(contact.getContact_name());
        text_PartnerNumber.setText(contact.getPhone_number());
        text_ContactAcquisitionDate.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(contact.getAcquisition_date())));
        text_partnerRSAPubKey.setText(contact.getRsa_publickey());
        text_partnerRSAPubKey.setTextIsSelectable(true);
        text_partnerRSAPubKey.setKeyListener(null);
    }

    protected void IntentFeedback(int FeedbackType) {
        Intent intent = new Intent();
        setResult(FeedbackType, intent);
        finish();
    }


    public void setStatusBarColor(int color) {

        int darken_color = darkenColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            if (darken_color == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(darken_color);
        }
    }

    public void setTitleBackgroundColor(int color) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }

    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    public void btn_DeleteContact_onClick(View v) {
        String PhoneNumber = text_PartnerNumber.getText().toString();
        contactRepository repo = new contactRepository(this);
        repo.delete(PhoneNumber);
        onBackPressed();
        IntentFeedback(IntentString.MainFeedbackCode_RefreshContactList);
    }

}
