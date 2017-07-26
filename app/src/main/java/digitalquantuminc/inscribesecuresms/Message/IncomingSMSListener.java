package digitalquantuminc.inscribesecuresms.Message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import digitalquantuminc.inscribesecuresms.ActivityMain;
import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.messageRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.GSMEncoderDecoder;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class IncomingSMSListener extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            if (!(sms == null))
            {
                final SmsMessage[] smsMessage = new SmsMessage[sms.length];
                for (int i = 0; i < sms.length; i++) {
                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) sms[i]);
                            }

                            StringBuffer content = new StringBuffer();

                            for (int i = 0; i<sms.length; i++)
                            {
                                content.append(smsMessage[i].getMessageBody().toString());
                            }

                            // Check if Contact Exist!
                            // Check if Number Exist
                            ActivityMain inst = ActivityMain.instance();
                            contactRepository repo = new contactRepository(inst);
                            //Log.v("SMSNumber", smsMessage[0].getOriginatingAddress());
                            //Log.v("SMSContent", content.toString());
                            if (repo.isContactExist(smsMessage[0].getOriginatingAddress()))
                            {
                                byte[] decodedmessage = GSMEncoderDecoder.Decode(content.toString());
                                int smspartnumber = smsMessage.length;
                                if(decodedmessage.length>=8) // metadata MAY present
                                {
                                    TypeMetaMessage meta = TypeMetaMessage.ExtractMetaData(decodedmessage);
                                    // Check whether the message is compatible with apps
                                    if(meta.getMessageHeadID()==TypeMetaMessage.MessageHeadIDVersion0 && meta.getMessageTailID()==TypeMetaMessage.MessageTailIDVersion0)
                                    {
                                        TypeMessage message = new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, meta.getMessageType(),smsMessage[0].getOriginatingAddress(),smsMessage[smspartnumber-1].getTimestampMillis(),content.toString(),"");
                                        Log.v("LongDateViaListner: ", String.valueOf(message.getTimestamp()));
                                        if(ActivityMain.active())
                                        {
                                            inst = ActivityMain.instance();
                                            inst.ReceiveNewMessage(message);
                                        }
                                        else
                                        {
                                            Intent i = new Intent(context, ActivityMain.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }
                        }
                        else
                        {
                            // DEBUGGING ONLY
                            //TypeMessage message = new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, 0,smsMessage[0].getOriginatingAddress(),smsMessage[0].getTimestampMillis(),content.toString(),"");
                            //ActivityMain inst = ActivityMain.instance();
                            //inst.ReceiveNewMessage(message);
                            //Log.v("SMSNumber", smsMessage[0].getOriginatingAddress());
                        }
                    }
                }
            }

        }
    }

}
