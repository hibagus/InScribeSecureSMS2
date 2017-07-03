package digitalquantuminc.inscribesecuresms.ListViewAdapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.ListViewHolder.sessionListViewHolder;
import digitalquantuminc.inscribesecuresms.R;

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

        // Prepare a HashMap to store single item Contact from Contact List
        HashMap<String, String> item = getItem(position);

        // Set the list item based on the information from item Contact
        holder.getTextlist_PartnerName().setText(item.get(TypeSession.KEY_name));
        holder.getTextlist_SessionHandshakeDate().setText(item.get(TypeSession.KEY_date));
        int color = mColorGenerator.getColor(item.get(TypeSession.KEY_name));
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.get(TypeSession.KEY_name).charAt(0)), color);
        holder.getImageView_PartnerView().setImageDrawable(drawable);
        //holder.getImageView_ContactAccent().setBackgroundColor(color);
        // return the view
        return convertView;
    }
    //endregion

}
