package digitalquantuminc.inscribesecuresms.ListViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 */

public class contactListViewHolder {

    private View view;

    private ImageView imageView_ContactView;

    private ImageView imageView_ContactAccent;

    private TextView textlist_ContactName;

    private TextView textlist_ContactPhoneNumber;

    public contactListViewHolder(View view) {
        this.view = view;
        imageView_ContactView = (ImageView) view.findViewById(R.id.imageView_ContactView);
        textlist_ContactName = (TextView) view.findViewById(R.id.textlist_ContactName);
        textlist_ContactPhoneNumber = (TextView) view.findViewById(R.id.textlist_ContactPhoneNumber);
        imageView_ContactAccent = (ImageView) view.findViewById(R.id.imageView_ContactAccent);
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setImageView_ContactView(ImageView imageView_ContactView) {
        this.imageView_ContactView = imageView_ContactView;
    }

    public void setTextlist_ContactName(TextView textlist_ContactName) {
        this.textlist_ContactName = textlist_ContactName;
    }

    public void setTextlist_ContactPhoneNumber(TextView textlist_ContactPhoneNumber) {
        this.textlist_ContactPhoneNumber = textlist_ContactPhoneNumber;
    }

    public void setImageView_ContactAccent(ImageView imageView_ContactAccent) {
        this.imageView_ContactAccent = imageView_ContactAccent;
    }

    public ImageView getImageView_ContactView() {
        return imageView_ContactView;
    }

    public TextView getTextlist_ContactPhoneNumber() {
        return textlist_ContactPhoneNumber;
    }

    public TextView getTextlist_ContactName() {
        return textlist_ContactName;
    }

    public ImageView getImageView_ContactAccent() {
        return imageView_ContactAccent;
    }
}
