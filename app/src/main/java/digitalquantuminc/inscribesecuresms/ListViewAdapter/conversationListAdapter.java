package digitalquantuminc.inscribesecuresms.ListViewAdapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import digitalquantuminc.inscribesecuresms.ListViewHolder.conversationListViewHolder;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.messageRepository;
import digitalquantuminc.inscribesecuresms.UserInterface.UserInterfaceColor;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class conversationListAdapter extends BaseAdapter {
    //region Global Variable
    private final Activity outer;
    private ArrayList<HashMap<String, String>> conversationList;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    //endregion

    //region Constructor
    public conversationListAdapter(Activity outer, ArrayList<HashMap<String, String>> conversationList) {
        this.conversationList = conversationList;
        this.mDrawableBuilder = TextDrawable.builder().round();
        this.outer = outer;
    }
    //endregion

    //region Override Method
    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return conversationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final conversationListViewHolder holder;

        // Check whether the view is already inflated or not.
        // If not, then inflate it first to avoid null reference.
        if (convertView == null) {
            convertView = View.inflate(outer, R.layout.listitem_conversation_list, null);
            holder = new conversationListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (conversationListViewHolder) convertView.getTag();
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
        contactRepository repo = new contactRepository(outer);
        if(repo.isContactExist(message.getAddress()))
        {
            TypeContact contact = repo.getContact(message.getAddress());
            // Set the list item based on the information from item Session
            holder.getTextlist_AddressNamePhone().setText(contact.getContact_name() + " (" +contact.getPhone_number() + ")");
            holder.getTextlist_PartnerNumber().setText(message.getAddress());

            // Get Message Information
            holder.getTextlist_LastMessageReceived().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(message.getTimestamp())));
            holder.getTextlist_NewestMessagePreview().setText(message.getEncodedcontent());

            // Set Thumbnail Color
            int color = mColorGenerator.getColor(contact.getContact_name());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(contact.getContact_name().charAt(0)), color);
            holder.getImageView_PartnerView().setImageDrawable(drawable);
            holder.getImageView_ColorAccent().setBackgroundColor(color);
            holder.getImageView_ColorAccentDarker().setBackgroundColor(UserInterfaceColor.getDarkColor(color));
            return convertView;
        }
        else
        {
            return null;
        }

        // return the view

    }
    //endregion
}
