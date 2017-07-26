package digitalquantuminc.inscribesecuresms.ListViewAdapter;

import android.app.Activity;
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


        // Decide Message Direction
        if(message.getDirection()==TypeMessage.MESSAGEDIRECTIONINBOX)
        {
            holder.getLinearLayout_Partner().setVisibility(View.VISIBLE);
            holder.getLinearLayout_Your().setVisibility(View.GONE);
            contactRepository repo = new contactRepository(outer);
            TypeContact contact = repo.getContact(message.getAddress());
            int color = mColorGenerator.getColor(contact.getContact_name());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(contact.getContact_name().charAt(0)), color);
            holder.getImageView_PartnerView().setImageDrawable(drawable);
            holder.getTextlist_MessageContentPartner().setText(message.getEncodedcontent());
            holder.getTextlist_MessageTimeStampPartner().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(message.getTimestamp())));
        }
        else if (message.getDirection()==TypeMessage.MESSAGEDIRECTIONOUTBOX)
        {
            holder.getLinearLayout_Partner().setVisibility(View.GONE);
            holder.getLinearLayout_Your().setVisibility(View.VISIBLE);
            profileRepository repo = new profileRepository(outer);
            TypeProfile profile = repo.getProfile(TypeProfile.DEFAULTID);
            int color = mColorGenerator.getColor(profile.getName_self());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(profile.getName_self().charAt(0)), color);
            holder.getImageView_YourView().setImageDrawable(drawable);
            holder.getTextlist_MessageContentYour().setText(message.getEncodedcontent());
            holder.getTextlist_MessageTimeStampYour().setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(message.getTimestamp())));
        }
        // return the view
        return convertView;
    }
    //endregion

}
