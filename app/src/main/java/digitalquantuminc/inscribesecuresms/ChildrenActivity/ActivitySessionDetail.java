package digitalquantuminc.inscribesecuresms.ChildrenActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.R;

public class ActivitySessionDetail extends AppCompatActivity {

    // UI Components
    TextView text_PartnerName;
    TextView text_PartnerNumber;
    TextView text_SecureSessionStatus;
    TextView text_SecureSessionFreshness;
    TextView text_LastSessionHandshake;
    TextView text_SessionHandshakeAge;
    TextView text_SessionNumMessage;
    TextView text_PartnerValidity;
    EditText text_PartnerRSAPubKey;
    EditText text_ECDHSessionPrivateKey;
    EditText text_ECDHSessionPublicKey;
    EditText text_PartnerECDHSessionPublicKey;
    EditText text_PartnerDigitalSignature;
    EditText text_SharedSecret;
    EditText text_SessionAESKey;
    Button btn_InitiateSession;
    Button btn_RestartSession;
    Button btn_EndSession;

    // Intent Variable
    private String ContactPhoneNumber = "";
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);
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
            onBackPressed();
            //IntentFeedback(IntentString.MainFeedbackCode_DoNothing);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void UIComponentBinding() {
        text_PartnerName = (TextView) findViewById(R.id.text_PartnerName);
        text_PartnerNumber = (TextView) findViewById(R.id.text_PartnerNumber);
        text_SecureSessionStatus = (TextView) findViewById(R.id.text_SecureSessionStatus);
        text_SecureSessionFreshness = (TextView) findViewById(R.id.text_SecureSessionFreshness);
        text_LastSessionHandshake = (TextView) findViewById(R.id.text_LastSessionHandshake);
        text_SessionHandshakeAge = (TextView) findViewById(R.id.text_SessionHandshakeAge);
        text_SessionNumMessage = (TextView) findViewById(R.id.text_SessionNumMessage);
        text_PartnerValidity = (TextView) findViewById(R.id.text_PartnerValidity);
        text_PartnerRSAPubKey = (EditText) findViewById(R.id.text_PartnerRSAPubKey);
        text_ECDHSessionPrivateKey = (EditText) findViewById(R.id.text_ECDHSessionPrivateKey);
        text_ECDHSessionPublicKey = (EditText) findViewById(R.id.text_ECDHSessionPublicKey);
        text_PartnerECDHSessionPublicKey = (EditText) findViewById(R.id.text_PartnerECDHSessionPublicKey);
        text_PartnerDigitalSignature = (EditText) findViewById(R.id.text_PartnerDigitalSignature);
        text_SharedSecret = (EditText) findViewById(R.id.text_SharedSecret);
        text_SessionAESKey = (EditText) findViewById(R.id.text_SessionAESKey);
        btn_InitiateSession = (Button) findViewById(R.id.btn_InitiateSession);
        btn_RestartSession = (Button) findViewById(R.id.btn_RestartSession);
        btn_EndSession = (Button) findViewById(R.id.btn_EndSession);
    }

    protected void IntentProcessor() {
        // Get Intent from Main Activity
        Intent intent = getIntent();

    }
}
