package digitalquantuminc.inscribesecuresms.ListViewAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.ActivityMain;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.ListViewHolder.contactListViewHolder;
import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 */

public class contactListAdapter extends BaseAdapter {

    final ActivityMain outer;
    private ArrayList<HashMap<String, String>> contactList;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public contactListAdapter(ActivityMain outer, ArrayList<HashMap<String, String>> contactList) {
        this.contactList = contactList;
        this.mDrawableBuilder = TextDrawable.builder().round();
        this.outer = outer;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final contactListViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(outer, R.layout.listitem_contacts_list, null);
            holder = new contactListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (contactListViewHolder) convertView.getTag();
        }
        HashMap<String, String> item = getItem(position);
        holder.getTextlist_ContactName().setText(item.get(TypeContact.KEY_name));
        holder.getTextlist_ContactPhoneNumber().setText(item.get(TypeContact.KEY_phone));
        int color = mColorGenerator.getColor(item.get(TypeContact.KEY_name));
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.get(TypeContact.KEY_name).charAt(0)), color);
        holder.getImageView_ContactView().setImageDrawable(drawable);
        holder.getImageView_ContactAccent().setBackgroundColor(color);
        return convertView;
    }
}
