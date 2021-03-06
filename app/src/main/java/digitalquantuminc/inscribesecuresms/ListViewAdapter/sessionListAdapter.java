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
import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.ListViewHolder.sessionListViewHolder;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;

/**
 * Created by Bagus Hanindhito on 02/07/2017.
 * This class defines list adapter for Session List by extending the BaseAdapter
 * Note: it is an inner class that must be used by outer class (i.e. activity class) where
 * the constructor takes one argument as Activity
 */

public class sessionListAdapter extends BaseAdapter {
    //region Global Variable
    private final Activity outer;
    private ArrayList<HashMap<String, String>> sessionList;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    //endregion

    //region Constructor
    public sessionListAdapter(Activity outer, ArrayList<HashMap<String, String>> sessionList) {
        this.sessionList = sessionList;
        this.mDrawableBuilder = TextDrawable.builder().round();
        this.outer = outer;
    }
    //endregion

    //region Override Method
    @Override
    public int getCount() {
        return sessionList.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return sessionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final sessionListViewHolder holder;

        // Check whether the view is already inflated or not.
        // If not, then inflate it first to avoid null reference.
        if (convertView == null) {
            convertView = View.inflate(outer, R.layout.listitem_session_list, null);
            holder = new sessionListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (sessionListViewHolder) convertView.getTag();
        }

        // Prepare a HashMap to store single item Session from Session List
        HashMap<String, String> item = getItem(position);
        //String phone_number, int session_validity, long session_handshake_date, int session_role, String session_ecdh_private_key, String session_ecdh_public_key, String session_ecdh_partner_public_key, String session_ecdh_partner_digital_signature, String session_ecdh_partner_computed_digital_signature, int session_ecdh_partner_validity, String session_ecdh_shared_secret, String session_ecdh_aes_key, int session_num_message
        TypeSession session = new TypeSession(
                item.get(TypeSession.KEY_phone),
                Integer.valueOf(item.get(TypeSession.KEY_valid)),
                Long.valueOf(item.get(TypeSession.KEY_date)),
                Integer.valueOf(item.get(TypeSession.KEY_role)),
                item.get(TypeSession.KEY_ecdhpriv),
                item.get(TypeSession.KEY_ecdhpub),
                item.get(TypeSession.KEY_ecdhpubpart),
                item.get(TypeSession.KEY_ecdhds),
                item.get(TypeSession.KEY_ecdhcomds),
                Integer.valueOf(item.get(TypeSession.KEY_ecdhvalid)),
                item.get(TypeSession.KEY_ecdhsecret),
                item.get(TypeSession.KEY_aeskey),
                Integer.valueOf(item.get(TypeSession.KEY_nummessage))
        );

        // Set the list item based on the information from item Session
        contactRepository repo = new contactRepository(outer);
        if(repo.isContactExist(session.getPhone_number()))
        {
            TypeContact contact = repo.getContact(session.getPhone_number());
            holder.getTextlist_PartnerName().setText(contact.getContact_name());
            holder.getTextlist_PartnerNumber().setText(session.getPhone_number());
            holder.getTextlist_SessionHandshakeDate().setText(String.format(outer.getString(R.string.session_date), new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(session.getSession_handshake_date()))));
            holder.getTextlist_SessionDetails().setText(String.format(outer.getString(R.string.session_details), session.getSessionElapsedHour(), session.getSessionElapsedMin(), session.getSession_num_message()));
            // Set Thumbnail Color
            int color = mColorGenerator.getColor(contact.getContact_name());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(contact.getContact_name().charAt(0)), color);
            holder.getImageView_PartnerView().setImageDrawable(drawable);

            // Set Color to Indicates Session Status
            if (session.getSession_validity() == TypeSession.StatusValid) {
                holder.getImageView_SessionStatus().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
            } else {
                holder.getImageView_SessionStatus().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
            }

            // Set Color to Indicates Session Freshness

            if (session.computeSessionFreshness() == TypeSession.StatusFresh) {
                holder.getImageView_SessionDuration().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkGreen));
            } else if (session.computeSessionFreshness() == TypeSession.StatusStale) {
                holder.getImageView_SessionDuration().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorAmber));
            } else {
                holder.getImageView_SessionDuration().setBackgroundColor(ContextCompat.getColor(outer, R.color.colorDarkRed));
            }

            // return the view
            return convertView;
        }
        else
        {
            return null;
        }

    }
    //endregion

}
