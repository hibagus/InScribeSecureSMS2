package digitalquantuminc.inscribesecuresms.ListViewAdapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
import digitalquantuminc.inscribesecuresms.ListViewHolder.conversationListDetailViewHolder;
import digitalquantuminc.inscribesecuresms.ListViewHolder.conversationListViewHolder;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.profileRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.UserInterfaceColor;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class conversationListDetailAdapter extends BaseAdapter {
    //region Global Variable
    private final Activity outer;
    private ArrayList<HashMap<String, String>> conversationListDetail;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    //endregion

    //region Constructor
    public conversationListDetailAdapter(Activity outer, ArrayList<HashMap<String, String>> conversationListDetail) {
        this.conversationListDetail = conversationListDetail;
        this.mDrawableBuilder = TextDrawable.builder().round();
        this.outer = outer;
    }

    //region Override Method
    @Override
    public int getCount() {
        return conversationListDetail.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return conversationListDetail.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final conversationListDetailViewHolder holder;

        // Check whether the view is already inflated or not.
        // If not, then inflate it first to avoid null reference.
        if (convertView == null) {
            convertView = View.inflate(outer, R.layout.listitem_conversation_list_detail, null);
            holder = new conversationListDetailViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (conversationListDetailViewHolder) convertView.getTag();
        }

        // Prepare a HashMap to store single item Session from Session List
        HashMap<String, String> item = getItem(position);
        //(int direction, int messagetype, String address, long timestamp, String encodedcontent, String plaincontent)
        TypeMessage message = new TypeMessage(
                Integer.valueOf(item.get(TypeMessage.KEY_direction)),
                Integer.valueOf(item.get(TypeMessage.KEY_messagetype)),
                item.get(TypeMessage.KEY_address),
                Long.valueOf(item.get(TypeMessage.KEY_timestamp)),
                item.get(TypeMessage.KEY_encodedcontent),
                item.get(TypeMessage.KEY_plaincontent)
        );

        // Access the name
        holder.getTextlist_TimeStamp().setText(String.valueOf(message.getTimestamp()));
        holder.getTextlist_PartnerNumber().setText(message.getAddress());

        // Decide Message Type
        int messagegroup = 0;
        String messagedesc;
        switch(message.getMessagetype())
        {
            case TypeMetaMessage.MessageTypeNormalEncryptedUncompressed : {
                messagegroup=1;
                messagedesc = outer.getString(R.string.messagetype_1);
                break;
            }
            case TypeMetaMessage.MessageTypeNormalEncryptedCompressedBLZ4 : {
                messagegroup=1;
                messagedesc = outer.getString(R.string.messagetype_2
                );
                break;
            }
            case TypeMetaMessage.MessageTypeNormalEncryptedCompressedDeflate : {
                messagegroup=1;
                messagedesc = outer.getString(R.string.messagetype_3
                );
                break;
            }
            case TypeMetaMessage.MessageTypeHandshakeRequestDS : {
                messagegroup=2;
                messagedesc = outer.getString(R.string.messagetype_4
                );
                break;
            }
            case TypeMetaMessage.MessageTypeHandshakeReplyDS : {
                messagegroup=2;
                messagedesc = outer.getString(R.string.messagetype_5);
                break;
            }
            case TypeMetaMessage.MessageTypeHandshakeSuccessDS : {
                messagegroup=2;
                messagedesc = outer.getString(R.string.messagetype_6);
                break;
            }
            case TypeMetaMessage.MessageTypeEndSessionRequest : {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_7);
                break;
            }
            case TypeMetaMessage.MessageTypeEndSessionSuccess : {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_8);
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeRequestDSNotValid : {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_9);
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeReplyDSNotValid : {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_10);
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeSuccessDSNotValid : {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_11);
                break;
            }
            case TypeMetaMessage.MessageTypeErrorNoSecureSessionActive: {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_12);
                break;
            }
            case TypeMetaMessage.MessageTypeErrorHandshakeDeclined: {
                messagegroup=3;
                messagedesc = outer.getString(R.string.messagetype_13);
                break;
            }
            default : {
                messagegroup=4;
                messagedesc = outer.getString(R.string.messagetype_14);
                break;
            }
        }


        // Decide Message Direction

        if(message.getDirection()==TypeMessage.MESSAGEDIRECTIONINBOX)
        {
            holder.getTextlist_Direction().setText(String.valueOf(TypeMessage.MESSAGEDIRECTIONINBOX));
            holder.getLinearLayout_Partner().setVisibility(View.VISIBLE);
            holder.getLinearLayout_Your().setVisibility(View.GONE);
            contactRepository repo = new contactRepository(outer);
            TypeContact contact = repo.getContact(message.getAddress());
            int color = mColorGenerator.getColor(contact.getContact_name());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(contact.getContact_name().charAt(0)), color);
            holder.getImageView_PartnerView().setImageDrawable(drawable);
            holder.getTextlist_MessageTimeStampPartner().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(message.getTimestamp())));
            holder.getTextlist_MessageContentPartner().setText(message.getEncodedcontent());
            holder.getTextlist_MessageTypePartner().setText(messagedesc);
            if(messagegroup==1)
            {
                holder.getImageView_LeftPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
                holder.getImageView_RightPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
                holder.getTextlist_MessageTypePartner().setTextColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
            }
            else if (messagegroup==2)
            {
                holder.getImageView_LeftPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorAmber));
                holder.getImageView_RightPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorAmber));
                holder.getTextlist_MessageTypePartner().setTextColor(ContextCompat.getColor(outer, R.color.colorAmber));
                holder.getTextlist_MessageContentPartner().setVisibility(View.GONE);
            }
            else if(messagegroup==3)
            {
                holder.getImageView_LeftPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getImageView_RightPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageTypePartner().setTextColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageContentPartner().setVisibility(View.GONE);
            }
            else
            {
                holder.getImageView_LeftPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getImageView_RightPartner().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageTypePartner().setTextColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageContentPartner().setVisibility(View.GONE);
            }


        }
        else if (message.getDirection()==TypeMessage.MESSAGEDIRECTIONOUTBOX)
        {
            holder.getTextlist_Direction().setText(String.valueOf(TypeMessage.MESSAGEDIRECTIONOUTBOX));
            holder.getLinearLayout_Partner().setVisibility(View.GONE);
            holder.getLinearLayout_Your().setVisibility(View.VISIBLE);
            profileRepository repo = new profileRepository(outer);
            TypeProfile profile = repo.getProfile(TypeProfile.DEFAULTID);
            int color = mColorGenerator.getColor(profile.getName_self());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(profile.getName_self().charAt(0)), color);
            holder.getImageView_YourView().setImageDrawable(drawable);
            holder.getTextlist_MessageTimeStampYour().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(message.getTimestamp())));
            holder.getTextlist_MessageContentYour().setText(message.getEncodedcontent());
            holder.getTextlist_MessageTypeYour().setText(messagedesc);
            if(messagegroup==1)
            {
                holder.getImageView_LeftYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
                holder.getImageView_RightYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
                holder.getTextlist_MessageTypeYour().setTextColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
            }
            else if (messagegroup==2)
            {
                holder.getImageView_LeftYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorAmber));
                holder.getImageView_RightYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorAmber));
                holder.getTextlist_MessageTypeYour().setTextColor(ContextCompat.getColor(outer, R.color.colorAmber));
                holder.getTextlist_MessageContentYour().setVisibility(View.GONE);
            }
            else if(messagegroup==3)
            {
                holder.getImageView_LeftYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getImageView_RightYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageTypeYour().setTextColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageContentYour().setVisibility(View.GONE);
            }
            else
            {
                holder.getImageView_LeftYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getImageView_RightYour().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageTypeYour().setTextColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
                holder.getTextlist_MessageContentYour().setVisibility(View.GONE);
            }

        }
        // return the view
        return convertView;
    }
    //endregion

}
