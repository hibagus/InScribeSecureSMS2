package digitalquantuminc.inscribesecuresms.Message;

import android.app.Activity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.UserInterface.Cryptography;
import digitalquantuminc.inscribesecuresms.UserInterface.GSMEncoderDecoder;

/**
 * Created by Bagus Hanindhito on 25/07/2017.
 */

public class MessageSender {

    private static final String BODYSESSIONENDREQUEST = "Please end this secure session now!";
    private static final String BODYSESSIONENDSUCCESS = "Secure session has been ended!";

    private static final String BODYERRORHANDSHAKEREQUESTDSNOTVALID = "Handshake request error, Digital Signature Invalid";
    private static final String BODYERRORHANDSHAKEREPLYDSNOTVALID = "Handshake reply error, Digital Signature Invalid";
    private static final String BODYERRORHANDSHAKESUCCESSDSNOTVALID = "Handshake result error, Digital Signature Invalid";
    private static final String BODYERRORNOSECURESESSIONACTIVE = "No secure session active, message ignored";
    private static final String BODYERRORHANDSHAKEDECLINED = "Your handshake is declined!";

    private static SmsManager smsManager = SmsManager.getDefault();
    private Activity outer;

    public MessageSender(Activity outer)
    {
        this.outer = outer;
    }

    public void SendNormalMessage(String phonenum, String content)
    {

        if(content.length()> TypeMetaMessage.SMSStandardLength)
        {
            ArrayList<String> parts = smsManager.divideMessage(content);
            smsManager.sendMultipartTextMessage(phonenum, null, parts, null, null);
            Toast.makeText(outer, "Message sent as multipart message", Toast.LENGTH_SHORT).show();
        }
        else
        {
            smsManager.sendTextMessage(phonenum, null, content, null, null);
            Toast.makeText(outer, "Message sent as single message", Toast.LENGTH_SHORT).show();
        }
    }


    public void SendSessionHandshakeRequestMessage(String phonenum, byte[] Content)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeHandshakeRequestDS, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        Log.v("SENDDECODEDMESSAGE:", Cryptography.BytetoBase64String(finalbyte));
        Log.v("SENDENCODEDMESSAGE:", encodedmessage);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendSessionHandshakeReplyMessage(String phonenum, byte[] Content)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeHandshakeReplyDS, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendSessionHandshakeSuccessMessage(String phonenum, byte[] Content)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeHandshakeSuccessDS, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendSessionEndRequestMessage(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeEndSessionRequest, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYSESSIONENDREQUEST.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendSessionEndSuccessMessage(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeEndSessionSuccess, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYSESSIONENDSUCCESS.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendErrorHandshakeRequestMessage(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeErrorHandshakeRequestDSNotValid, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYERRORHANDSHAKEREQUESTDSNOTVALID.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendErrorHandshakeReplyMessage(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeErrorHandshakeReplyDSNotValid, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYERRORHANDSHAKEREPLYDSNOTVALID.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendErrorHandshakeSuccessMessage(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeErrorHandshakeSuccessDSNotValid, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYERRORHANDSHAKESUCCESSDSNOTVALID.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendErrorNoSecureSessionActive(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeErrorNoSecureSessionActive, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYERRORNOSECURESESSIONACTIVE.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

    public void SendErrorHandshakeDeclined(String phonenum)
    {
        TypeMetaMessage meta = new TypeMetaMessage(TypeMetaMessage.MessageTypeErrorHandshakeDeclined, TypeMetaMessage.MessageHeadIDVersion0, TypeMetaMessage.MessageTailIDVersion0);
        byte[] Content = BODYERRORHANDSHAKEDECLINED.getBytes();
        byte[] finalbyte = TypeMetaMessage.EmbedMetaData(meta, Content);
        String encodedmessage = GSMEncoderDecoder.Encode(finalbyte);
        SendNormalMessage(phonenum,encodedmessage);
    }

}
