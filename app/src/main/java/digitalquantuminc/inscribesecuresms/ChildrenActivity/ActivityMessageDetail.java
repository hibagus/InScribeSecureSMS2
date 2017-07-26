package digitalquantuminc.inscribesecuresms.ChildrenActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.SecretKey;

import digitalquantuminc.inscribesecuresms.ActivityMain;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.Message.MessageSender;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.messageRepository;
import digitalquantuminc.inscribesecuresms.Repository.profileRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.CompressionDecompression;
import digitalquantuminc.inscribesecuresms.UserInterface.Cryptography;
import digitalquantuminc.inscribesecuresms.UserInterface.GSMEncoderDecoder;
import digitalquantuminc.inscribesecuresms.UserInterface.UserInterfaceColor;

import static digitalquantuminc.inscribesecuresms.UserInterface.Cryptography.BytetoPrivKeyRSA;
import static digitalquantuminc.inscribesecuresms.UserInterface.Cryptography.CreateDigitalSignatureRSA;
import static digitalquantuminc.inscribesecuresms.UserInterface.Cryptography.EmbedDigitalSignaturewithMessage;
import static digitalquantuminc.inscribesecuresms.UserInterface.Cryptography.GenerateECDHKeyPair;

public class ActivityMessageDetail extends AppCompatActivity {


    // UI Components
    private LinearLayout LinearLayout_General;
    private LinearLayout LinearLayout_Handshake;
    private LinearLayout LinearLayout_ErrorMessage;
    private LinearLayout LinearLayout_NormalMessage;

    private TextView text_MessagePartnerName;
    private TextView text_MessagePartnerNumber;
    private TextView text_MessageType;
    private TextView text_MessageTimeStamp;
    private TextView text_MessageTimeStampLong;
    private EditText text_MessageContent;
    private EditText text_PartnerECDHSessionPublicKey;
    private EditText text_PartnerDigitalSignature;
    private TextView text_PartnerValidity;
    private Button btn_AcceptSecureSession;
    private Button btn_ActivateSecureSession;
    private Button btn_RejectSecureSession;
    private EditText text_DecodedTextMessage;
    private EditText text_AESIVText;
    private EditText text_AESCTText;
    private EditText text_DecryptedTextMessage;
    private EditText text_PlainText;
    private Button btn_DecryptMessage;
    private Button btn_SavePlainMessage;
    private Button btn_CloseMessageView;
    private Button btn_ClearPlainMessage;
    private Button btn_DeleteMessage;

    // Intent Variable
    private String PartnerPhoneNumber = "";
    private String TimeStamp = "";
    private int Direction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get Intent from Main Activity
        Intent intent = getIntent();
        PartnerPhoneNumber = intent.getStringExtra(IntentString.ConversationListDetailstoMessageDetails_PhoneNum);
        TimeStamp = intent.getStringExtra(IntentString.ConversationListDetailstoMessageDetails_Timestamp);
        Direction = intent.getIntExtra(IntentString.ConversationListDetailstoMessageDetails_Direction, 0);
        if(Direction == TypeMessage.MESSAGEDIRECTIONOUTBOX)
        {
            setContentView(R.layout.activity_message_detail2);
        }
        else
        {
            setContentView(R.layout.activity_message_detail);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please select action first!", Toast.LENGTH_SHORT).show();
    }

    protected void UIComponentBinding() {
        LinearLayout_General = (LinearLayout) findViewById(R.id.LinearLayout_General);
        LinearLayout_Handshake = (LinearLayout) findViewById(R.id.LinearLayout_Handshake);
        LinearLayout_ErrorMessage = (LinearLayout) findViewById(R.id.LinearLayout_ErrorMessage);
        LinearLayout_NormalMessage = (LinearLayout) findViewById(R.id.LinearLayout_NormalMessage);
        text_MessagePartnerName = (TextView) findViewById(R.id.text_MessagePartnerName);
        text_MessagePartnerNumber = (TextView) findViewById(R.id.text_MessagePartnerNumber);
        text_MessageType = (TextView) findViewById(R.id.text_MessageType);
        text_MessageTimeStamp = (TextView) findViewById(R.id.text_MessageTimeStamp);
        text_MessageTimeStampLong = (TextView) findViewById(R.id.text_MessageTimeStampLong);

        text_MessageContent = (EditText) findViewById(R.id.text_MessageContent);
        text_MessageContent.setTextIsSelectable(true);
        text_MessageContent.setKeyListener(null);

        text_PartnerECDHSessionPublicKey = (EditText) findViewById(R.id.text_PartnerECDHSessionPublicKey);
        text_PartnerECDHSessionPublicKey.setTextIsSelectable(true);
        text_PartnerECDHSessionPublicKey.setKeyListener(null);

        text_PartnerDigitalSignature = (EditText) findViewById(R.id.text_PartnerDigitalSignature);
        text_PartnerDigitalSignature.setTextIsSelectable(true);
        text_PartnerDigitalSignature.setKeyListener(null);

        text_PartnerValidity = (TextView) findViewById(R.id.text_PartnerValidity);

        btn_AcceptSecureSession = (Button) findViewById(R.id.btn_AcceptSecureSession);
        btn_AcceptSecureSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_AcceptSecureSession_onClick(v);
            }
        });

        btn_ActivateSecureSession = (Button) findViewById(R.id.btn_ActivateSecureSession);
        btn_ActivateSecureSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ActivateSecureSession_onClick(v);
            }
        });

        btn_RejectSecureSession = (Button) findViewById(R.id.btn_RejectSecureSession);
        btn_RejectSecureSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_RejectSecureSession_onClick(v);
            }
        });

        text_DecodedTextMessage = (EditText) findViewById(R.id.text_DecodedTextMessage);
        text_DecodedTextMessage.setTextIsSelectable(true);
        text_DecodedTextMessage.setKeyListener(null);

        text_AESIVText = (EditText) findViewById(R.id.text_AESIVText);
        text_AESIVText.setTextIsSelectable(true);
        text_AESIVText.setKeyListener(null);

        text_AESCTText = (EditText) findViewById(R.id.text_AESCTText);
        text_AESCTText.setTextIsSelectable(true);
        text_AESCTText.setKeyListener(null);

        text_DecryptedTextMessage = (EditText) findViewById(R.id.text_DecryptedTextMessage);
        text_DecryptedTextMessage.setTextIsSelectable(true);
        text_DecryptedTextMessage.setKeyListener(null);

        text_PlainText = (EditText) findViewById(R.id.text_PlainText);
        text_PlainText.setTextIsSelectable(true);
        text_PlainText.setKeyListener(null);

        btn_DecryptMessage = (Button) findViewById(R.id.btn_DecryptMessage);
        btn_DecryptMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_DecryptMessage_onClick(v);
            }
        });

        btn_SavePlainMessage = (Button) findViewById(R.id.btn_SavePlainMessage);
        btn_SavePlainMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_SavePlainMessage_onClick(v);
            }
        });

        btn_CloseMessageView = (Button) findViewById(R.id.btn_CloseMessageView);
        btn_CloseMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_CloseMessageView_onClick(v);
            }
        });

        btn_DeleteMessage = (Button) findViewById(R.id.btn_DeleteMessage);
        btn_DeleteMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_DeleteMessage_onClick(v);
            }
        });

        btn_ClearPlainMessage = (Button) findViewById(R.id.btn_ClearPlainMessage);
        btn_ClearPlainMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ClearPlainMessage_onClick(v);
            }
        });
    }

    protected void IntentProcessor() {
        final int ColorDarkGreen = ContextCompat.getColor(this, R.color.colorDarkGreen);
        final int ColorDarkRed = ContextCompat.getColor(this, R.color.colorDarkRed);
        final int ColorAmber = ContextCompat.getColor(this, R.color.colorAmber);
        final int ColorDarkGrey = ContextCompat.getColor(this, R.color.colorDarkGrey);

        messageRepository repo = new messageRepository(this);
        TypeMessage message = repo.getMessagebyTimeStamp(Long.valueOf(TimeStamp));
        String receivedtext = message.getEncodedcontent();
        byte[] decodedbyte = GSMEncoderDecoder.Decode(receivedtext);

        TypeMetaMessage meta = TypeMetaMessage.ExtractMetaData(decodedbyte);
        byte [] messageonlybyte = TypeMetaMessage.ExtractOriginalMessage(decodedbyte);

        String name = " ";

        if(message.getDirection()==TypeMessage.MESSAGEDIRECTIONINBOX)
        {
            contactRepository repo2 = new contactRepository(this);
            TypeContact contact = repo2.getContact(PartnerPhoneNumber);
            setTitle("Received message from " + contact.getContact_name());
            name = contact.getContact_name();
        }
        else if (message.getDirection()==TypeMessage.MESSAGEDIRECTIONOUTBOX)
        {
            profileRepository repo2 = new profileRepository(this);
            TypeProfile profile = repo2.getProfile(TypeProfile.DEFAULTID);
            setTitle("Your message to " + profile.getName_self());
            name = profile.getName_self();
        }

        // Setting Up General Display
        text_MessagePartnerName.setText(name);
        text_MessagePartnerNumber.setText(PartnerPhoneNumber);
        text_MessageTimeStampLong.setText(TimeStamp);
        text_MessageTimeStamp.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(message.getTimestamp())));;
        text_MessageContent.setText(message.getEncodedcontent());
        text_MessageContent.setTextIsSelectable(true);
        text_MessageContent.setKeyListener(null);
        text_DecodedTextMessage.setText(Cryptography.BytetoBase64String(decodedbyte));
        text_DecodedTextMessage.setTextIsSelectable(true);
        text_DecodedTextMessage.setKeyListener(null);

        int messagetype = meta.getMessageType();
        int messagegroup = 0;
        switch(messagetype)
        {
            case TypeMetaMessage.MessageTypeNormalEncryptedUncompressed : {
                messagegroup=1;
                text_MessageType.setText("Normal Uncompressed Encrypted Message");
                break;
            }
            case TypeMetaMessage.MessageTypeNormalEncryptedCompressedBLZ4 : {
                messagegroup=1;
                text_MessageType.setText("Normal Encrypted Message with Block LZ4 Compression");
                break;
            }
            case TypeMetaMessage.MessageTypeNormalEncryptedCompressedDeflate : {
                messagegroup=1;
                text_MessageType.setText("Normal Encrypted Message with Deflate Compression");
                break;
            }
            case TypeMetaMessage.MessageTypeHandshakeRequestDS : {
                messagegroup=2;
                text_MessageType.setText("Handshake Request Message with Digital Signature");
                break;
            }
            case TypeMetaMessage.MessageTypeHandshakeReplyDS : {
                messagegroup=2;
                text_MessageType.setText("Handshake Reply Message with Digital Signature");
                break;
            }
            case TypeMetaMessage.MessageTypeHandshakeSuccessDS : {
                messagegroup=2;
                text_MessageType.setText("Handshake Success Message with Digital Signature");
                break;
            }
            case TypeMetaMessage.MessageTypeEndSessionRequest : {
                messagegroup=3;
                text_MessageType.setText("End Session Request Message");
                break;
            }
            case TypeMetaMessage.MessageTypeEndSessionSuccess : {
                messagegroup=3;
                text_MessageType.setText("End Session Success Message");
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeRequestDSNotValid : {
                messagegroup=3;
                text_MessageType.setText("Error Message on Handshake Request, Digital Signature Invalid");
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeReplyDSNotValid : {
                messagegroup=3;
                text_MessageType.setText("Error Message on Handshake Reply, Digital Signature Invalid");
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeSuccessDSNotValid : {
                messagegroup=3;
                text_MessageType.setText("Error Message on Handshake Success, Digital Signature Invalid");
                break;
            }
            case TypeMetaMessage.MessageTypeErrorNoSecureSessionActive: {
                messagegroup=3;
                text_MessageType.setText("Error Message on Handshake Reply, No Secure Session Active");
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeDeclined: {
                messagegroup=3;
                text_MessageType.setText("Error Message on Handshake: Handshake Declined");
                break;
            }
            default : {
                messagegroup=4;
                text_MessageType.setText("Unknown");
                break;
            }
        }
        // Decide which one to process
        if(messagegroup==1)
        {
            Log.v("MessageGroup: ", "NORMAL");
            LinearLayout_NormalMessage.setVisibility(View.VISIBLE);
            LinearLayout_ErrorMessage.setVisibility(View.GONE);
            LinearLayout_Handshake.setVisibility(View.GONE);
            UserInterfaceColor.setTitleBackgroundColor(ColorDarkGreen, this);
            UserInterfaceColor.setStatusBarColor(ColorDarkGreen, this);

            // Update UI
            String OldPlainText = message.getPlaincontent();
            if(OldPlainText.matches(""))
            {
                btn_DecryptMessage.setEnabled(true);
                btn_ClearPlainMessage.setEnabled(false);
                btn_SavePlainMessage.setEnabled(false);
            }
            else
            {

                text_PlainText.setText(OldPlainText);
                btn_DecryptMessage.setEnabled(false);
                btn_ClearPlainMessage.setEnabled(true);
                btn_SavePlainMessage.setEnabled(false);
            }
        }
        else if(messagegroup==2)
        {   // Handshake
            Log.v("MessageGroup: ", "HANDSHAKE");
            LinearLayout_NormalMessage.setVisibility(View.GONE);
            LinearLayout_ErrorMessage.setVisibility(View.GONE);
            LinearLayout_Handshake.setVisibility(View.VISIBLE);
            UserInterfaceColor.setTitleBackgroundColor(ColorAmber, this);
            UserInterfaceColor.setStatusBarColor(ColorAmber, this);


            if(message.getDirection()==TypeMessage.MESSAGEDIRECTIONINBOX)
            {
                Log.v("MessageDirection: ", "INBOX");
                contactRepository repo2 = new contactRepository(this);
                TypeContact contact = repo2.getContact(PartnerPhoneNumber);

                byte[] contactRSAPubKeyByte = Cryptography.Base64StringtoByte(contact.getRsa_publickey());
                PublicKey contactRSAPubKey = Cryptography.BytetoPubKeyRSA(contactRSAPubKeyByte);
                // Extract ECDH Public Key and Digital Signature
                byte[] ECDHPublicKey = Cryptography.ExtractContentfromDS(messageonlybyte);
                byte[] ECDHReceivedDS = Cryptography.ExtractDigitalSignature(messageonlybyte);

                boolean DSVerify = Cryptography.VerifyDigitalSignatureRSA(ECDHPublicKey,ECDHReceivedDS,contactRSAPubKey);

                // Update UI
                text_PartnerECDHSessionPublicKey.setText(Cryptography.BytetoBase64String(ECDHPublicKey));
                text_PartnerDigitalSignature.setText(Cryptography.BytetoBase64String(ECDHReceivedDS));

                if (DSVerify) {
                    text_PartnerValidity.setText(R.string.session_ds_valid);
                    text_PartnerValidity.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGreen));
                    if(messagetype==TypeMetaMessage.MessageTypeHandshakeRequestDS)
                    {
                        btn_AcceptSecureSession.setEnabled(true);
                        btn_ActivateSecureSession.setEnabled(false);
                    }
                    else if (messagetype == TypeMetaMessage.MessageTypeHandshakeReplyDS)
                    {
                        btn_AcceptSecureSession.setEnabled(false);
                        btn_ActivateSecureSession.setEnabled(true);
                    }
                    else if(messagetype == TypeMetaMessage.MessageTypeHandshakeSuccessDS)
                    {
                        btn_AcceptSecureSession.setEnabled(false);
                        btn_ActivateSecureSession.setEnabled(true);
                    }
                } else {
                    text_PartnerValidity.setText(R.string.session_ds_notvalid);
                    text_PartnerValidity.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGrey));
                    btn_AcceptSecureSession.setEnabled(false);
                    btn_ActivateSecureSession.setEnabled(false);
                }
            }
            else if (message.getDirection()==TypeMessage.MESSAGEDIRECTIONOUTBOX)
            {
                Log.v("MessageDirection: ", "OUTBOX");
                profileRepository repo2 = new profileRepository(this);
                TypeProfile profile = repo2.getProfile(TypeProfile.DEFAULTID);

                byte[] contactRSAPubKeyByte = Cryptography.Base64StringtoByte(profile.getRsa_publickey());
                PublicKey contactRSAPubKey = Cryptography.BytetoPubKeyRSA(contactRSAPubKeyByte);
                // Extract ECDH Public Key and Digital Signature
                byte[] ECDHPublicKey = Cryptography.ExtractContentfromDS(messageonlybyte);
                byte[] ECDHReceivedDS = Cryptography.ExtractDigitalSignature(messageonlybyte);

                boolean DSVerify = Cryptography.VerifyDigitalSignatureRSA(ECDHPublicKey,ECDHReceivedDS,contactRSAPubKey);

                // Update UI
                text_PartnerECDHSessionPublicKey.setText(Cryptography.BytetoBase64String(ECDHPublicKey));
                text_PartnerDigitalSignature.setText(Cryptography.BytetoBase64String(ECDHReceivedDS));
                btn_AcceptSecureSession.setEnabled(false);
                btn_ActivateSecureSession.setEnabled(false);
                btn_RejectSecureSession.setEnabled(false);

                if (DSVerify) {
                    text_PartnerValidity.setText(getString(R.string.session_ds_valid));
                    text_PartnerValidity.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGreen));
                } else {
                    text_PartnerValidity.setText(getString(R.string.session_ds_notvalid));
                    text_PartnerValidity.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGrey));

                }
            }



        }
        else if(messagegroup==3)
        {

            LinearLayout_NormalMessage.setVisibility(View.GONE);
            LinearLayout_ErrorMessage.setVisibility(View.VISIBLE);
            LinearLayout_Handshake.setVisibility(View.GONE);
            UserInterfaceColor.setTitleBackgroundColor(ColorDarkRed, this);
            UserInterfaceColor.setStatusBarColor(ColorDarkRed, this);
        }
        else
        {
            LinearLayout_NormalMessage.setVisibility(View.GONE);
            LinearLayout_ErrorMessage.setVisibility(View.GONE);
            LinearLayout_Handshake.setVisibility(View.GONE);
            UserInterfaceColor.setTitleBackgroundColor(ColorDarkGrey, this);
            UserInterfaceColor.setStatusBarColor(ColorDarkGrey, this);
        }
    }

    protected void IntentFeedback(int status, int FeedbackType) {
        Intent intent = getIntent();
        intent.putExtra(IntentString.ConversationListDetailsFeedbackCode, FeedbackType);
        setResult(status, intent);
        finish();
    }

    private void btn_DecryptMessage_onClick(View v) {

        String Phonenumber = text_MessagePartnerNumber.getText().toString();
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        messageRepository repoMessage = new messageRepository(this);
        TypeMessage message = repoMessage.getMessagebyTimeStamp(TimeStamp);

        sessionRepository repoSession = new sessionRepository(this);
        TypeSession session = repoSession.getSession(Phonenumber);
        int messagetype = message.getMessagetype();

        // Extract Content and AES IV
        if(session.getSession_ecdh_aes_key().matches(""))
        {
            btn_DecryptMessage.setEnabled(false);
            btn_ClearPlainMessage.setEnabled(false);
            btn_SavePlainMessage.setEnabled(false);
            Toast.makeText(this, "Error! AES Key Not Found! Cannot Decrypt.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String EncodedMessage = message.getEncodedcontent();
            byte[] DecodedByte = GSMEncoderDecoder.Decode(EncodedMessage);
            byte[] MessageByte = TypeMetaMessage.ExtractOriginalMessage(DecodedByte);

            byte[] AESIV = Cryptography.GetAESIV(MessageByte);
            byte[] AESCT = Cryptography.GetAESContent(MessageByte);
            byte[] AESKey = Cryptography.Base64StringtoByte(session.getSession_ecdh_aes_key());
            SecretKey AESSecret = Cryptography.BytetoKeyAES(AESKey);

            byte[] decryptedbyte = Cryptography.DecryptMessageAES(AESSecret, MessageByte);

            // Decompress
            byte[] plaintext = null;
            if(messagetype==TypeMetaMessage.MessageTypeNormalEncryptedUncompressed)
            {
                plaintext = decryptedbyte;
            }
            else if (messagetype==TypeMetaMessage.MessageTypeNormalEncryptedCompressedBLZ4)
            {
                plaintext = CompressionDecompression.BlockLZ4Decompress(decryptedbyte);
            }
            else if (messagetype==TypeMetaMessage.MessageTypeNormalEncryptedCompressedDeflate)
            {
                plaintext = CompressionDecompression.DeflateDecompress(decryptedbyte);
            }
            else
            {
                plaintext = decryptedbyte;
            }

            // Update UI
            btn_DecryptMessage.setEnabled(false);
            btn_ClearPlainMessage.setEnabled(false);
            btn_SavePlainMessage.setEnabled(true);

            if (plaintext != null && !plaintext.equals("")) {
                text_PlainText.setText(new String(plaintext));
            }
            else
            {
                text_PlainText.setText("");
            }

            text_DecodedTextMessage.setText(Cryptography.BytetoBase64String(DecodedByte));
            text_AESCTText.setText(Cryptography.BytetoBase64String(AESCT));
            text_AESIVText.setText(Cryptography.BytetoBase64String(AESIV));
            text_DecryptedTextMessage.setText(Cryptography.BytetoBase64String(decryptedbyte));
        }

    }

    private void btn_SavePlainMessage_onClick(View v)
    {
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        messageRepository repoMessage = new messageRepository(this);

        TypeMessage message = repoMessage.getMessagebyTimeStamp(TimeStamp);

        String PlainText = text_PlainText.getText().toString();
        long Timestamp = message.getTimestamp();
        message.setPlaincontent(PlainText);
        repoMessage.update(message, Timestamp);

        btn_DecryptMessage.setEnabled(false);
        btn_ClearPlainMessage.setEnabled(true);
        btn_SavePlainMessage.setEnabled(false);
    }

    private void btn_ClearPlainMessage_onClick(View v)
    {
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        messageRepository repoMessage = new messageRepository(this);
        TypeMessage message = repoMessage.getMessagebyTimeStamp(TimeStamp);

        String PlainText = text_PlainText.getText().toString();

        message.setPlaincontent(PlainText);
        repoMessage.update(message, TimeStamp);

        btn_DecryptMessage.setEnabled(true);
        btn_ClearPlainMessage.setEnabled(false);
        btn_SavePlainMessage.setEnabled(false);
    }

    private void btn_AcceptSecureSession_onClick(View v)
    {
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        String Phonenumber = text_MessagePartnerNumber.getText().toString();
        messageRepository repoMessage = new messageRepository(this);
        TypeMessage message = repoMessage.getMessagebyTimeStamp(TimeStamp);
        sessionRepository repoSession = new sessionRepository(this);
        TypeSession session = repoSession.getSession(Phonenumber);
        profileRepository repoProfile = new profileRepository(this);
        TypeProfile profile = repoProfile.getProfile(TypeProfile.DEFAULTID);

        // Preparing Own ECDH Key before Sending Message

        // Generate Keypair

        String RSAPrivateKey = profile.getRsa_privatekey();
        PrivateKey rsaprivkey = BytetoPrivKeyRSA(Cryptography.Base64StringtoByte(RSAPrivateKey));

        KeyPair ecdhkeypair = GenerateECDHKeyPair(Cryptography.ELIPTICCURVENAME);
        byte[] ecdhpubkey = ecdhkeypair.getPublic().getEncoded();
        byte[] ecdhprivkey = ecdhkeypair.getPrivate().getEncoded();
        byte[] digitalsignature = CreateDigitalSignatureRSA(ecdhpubkey, rsaprivkey);
        byte[] contentds = EmbedDigitalSignaturewithMessage(ecdhpubkey, digitalsignature);

        //update database
        session.setSession_ecdh_private_key(Cryptography.BytetoBase64String(ecdhprivkey));
        session.setSession_ecdh_public_key(Cryptography.BytetoBase64String(ecdhpubkey));
        session.setSession_num_message(0);
        session.setSession_handshake_date(System.currentTimeMillis());
        session.setSession_role(TypeSession.StatusRoleSlave);
        repoSession.update(session, Phonenumber);

        // Send Message
        MessageSender sender = new MessageSender(this);
        sender.SendSessionHandshakeReplyMessage(Phonenumber,contentds);
        Toast.makeText(this, "Secure Session Reply has been sent", Toast.LENGTH_SHORT).show();
        repoMessage.delete(TimeStamp);
        IntentFeedback(RESULT_OK, IntentString.ConversationListDetailsFeedbackCode_RefreshList);

    }

    private void btn_ActivateSecureSession_onClick(View v) {
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        String Phonenumber = text_MessagePartnerNumber.getText().toString();
        messageRepository repoMessage = new messageRepository(this);
        TypeMessage message = repoMessage.getMessagebyTimeStamp(TimeStamp);
        sessionRepository repoSession = new sessionRepository(this);
        TypeSession session = repoSession.getSession(Phonenumber);

        // Get All Text from View
        String ECDHPartnerDigitalSignature = text_PartnerDigitalSignature.getText().toString();
        String ECDHPartnerPubKeyText = text_PartnerECDHSessionPublicKey.getText().toString();
        String ECDHSelfPrivKeyText = session.getSession_ecdh_private_key();
        byte[] ECDHSelfPrivKey = Cryptography.Base64StringtoByte(ECDHSelfPrivKeyText);
        byte[] ECDHPartnerPubKey = Cryptography.Base64StringtoByte(ECDHPartnerPubKeyText);
        PublicKey ECDHPartnerPublicKey = Cryptography.BytetoPubKeyECDH(ECDHPartnerPubKey);
        PrivateKey ECDHSelfPrivateKey = Cryptography.BytetoPrivKeyECDH(ECDHSelfPrivKey);

        Log.v("ECDHParterPub:", Cryptography.BytetoBase64String(ECDHPartnerPubKey));
        Log.v("ECDHSelfPriv", Cryptography.BytetoBase64String(ECDHSelfPrivKey));

        Log.v("ECDHParterPubfromByte:", Cryptography.BytetoBase64String(ECDHPartnerPublicKey.getEncoded()));
        Log.v("ECDHSelfPrivfromByte", Cryptography.BytetoBase64String(ECDHSelfPrivateKey.getEncoded()));

        SecretKey ECDHSharedSecretKey = Cryptography.GenerateECDHSharedSecret(ECDHPartnerPublicKey, ECDHSelfPrivateKey);
        Log.v("ECDHSharedSecret:", Cryptography.BytetoBase64String(ECDHSharedSecretKey.getEncoded()));
        SecretKey AESSharedKey = Cryptography.GenerateAESKey(ECDHSharedSecretKey, Cryptography.PBKDF2ITERATION, Cryptography.AESKEYSIZE);
        byte[] ECDHSharedSecret = ECDHSharedSecretKey.getEncoded();
        byte[] AESKey = AESSharedKey.getEncoded();

        // Update Database
        session.setSession_num_message(0);
        session.setSession_handshake_date(System.currentTimeMillis());

        session.setSession_validity(TypeSession.StatusValid);
        session.setSession_ecdh_aes_key(Cryptography.BytetoBase64String(AESKey));
        session.setSession_ecdh_partner_digital_signature(ECDHPartnerDigitalSignature);
        session.setSession_ecdh_partner_validity(TypeSession.StatusDSValid);
        session.setSession_ecdh_shared_secret(Cryptography.BytetoBase64String(ECDHSharedSecret));
        session.setSession_ecdh_partner_public_key(ECDHPartnerPubKeyText);



        if (message.getMessagetype() == TypeMetaMessage.MessageTypeHandshakeReplyDS) {
            profileRepository repoProfile = new profileRepository(this);
            TypeProfile profile = repoProfile.getProfile(TypeProfile.DEFAULTID);
            String RSAPrivateKey = profile.getRsa_privatekey();
            PrivateKey rsaprivkey = BytetoPrivKeyRSA(Cryptography.Base64StringtoByte(RSAPrivateKey));

            String ECDHSelfPubKey = session.getSession_ecdh_public_key();
            byte[] ecdhpubkey = Cryptography.Base64StringtoByte(ECDHSelfPubKey);
            byte[] digitalsignature = CreateDigitalSignatureRSA(ecdhpubkey, rsaprivkey);
            byte[] contentds = EmbedDigitalSignaturewithMessage(ecdhpubkey, digitalsignature);
            // Send Success Message
            MessageSender sender = new MessageSender(this);
            sender.SendSessionHandshakeSuccessMessage(Phonenumber, contentds);
            Toast.makeText(this, "Secure Session Success has been sent", Toast.LENGTH_SHORT).show();
            IntentFeedback(RESULT_OK, IntentString.MainFeedbackCode_RefreshSessionList);
            session.setSession_role(TypeSession.StatusRoleMaster);
        } else if (message.getMessagetype() == TypeMetaMessage.MessageTypeHandshakeSuccessDS) {
            // Store only
            session.setSession_role(TypeSession.StatusRoleSlave);
        }
        repoSession.update(session, Phonenumber);
        repoMessage.delete(TimeStamp);
        IntentFeedback(RESULT_OK, IntentString.ConversationListDetailsFeedbackCode_RefreshList);

    }

    private void btn_RejectSecureSession_onClick(View v)
    {
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        String Phonenumber = text_MessagePartnerNumber.getText().toString();
        messageRepository repoMessage = new messageRepository(this);
        TypeMessage message = repoMessage.getMessagebyTimeStamp(TimeStamp);
        sessionRepository repoSession = new sessionRepository(this);
        TypeSession session = repoSession.getSession(Phonenumber);
        MessageSender sender = new MessageSender(this);

        String Validity = text_PartnerValidity.getText().toString();
        if(Validity.matches(getString(R.string.session_ds_valid)))
        {
            session.setSession_validity(TypeSession.StatusNotValid);
            sender.SendErrorHandshakeDeclined(Phonenumber);
        }
        else if (Validity.matches(getString(R.string.session_ds_notvalid)))
        {
            if(message.getMessagetype()==TypeMetaMessage.MessageTypeHandshakeRequestDS)
            {
                session.setSession_validity(TypeSession.StatusNotValid);
                sender.SendErrorHandshakeRequestMessage(Phonenumber);
            }
            else if (message.getMessagetype()==TypeMetaMessage.MessageTypeHandshakeReplyDS)
            {
                session.setSession_validity(TypeSession.StatusNotValid);
                sender.SendErrorHandshakeReplyMessage(Phonenumber);
            }
            else if (message.getMessagetype()==TypeMetaMessage.MessageTypeHandshakeSuccessDS)
            {
                session.setSession_validity(TypeSession.StatusNotValid);
                sender.SendErrorHandshakeSuccessMessage(Phonenumber);
            }
        }
        repoMessage.delete(TimeStamp);
        IntentFeedback(RESULT_OK, IntentString.ConversationListDetailsFeedbackCode_RefreshList);

    }

    private void btn_DeleteMessage_onClick(View v)
    {
        long TimeStamp = Long.valueOf(text_MessageTimeStampLong.getText().toString());
        messageRepository repoMessage = new messageRepository(this);
        repoMessage.delete(TimeStamp);
        IntentFeedback(RESULT_OK, IntentString.ConversationListDetailsFeedbackCode_RefreshList);
    }

    private void btn_CloseMessageView_onClick(View v)
    {
        IntentFeedback(RESULT_OK, IntentString.ConversationListDetailsFeedbackCode_DoNothing);
    }




}
