package digitalquantuminc.inscribesecuresms.ListViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 * This class basically handle each of the item in Contact List.
 */

public class contactListViewHolder {
    //region Global Variable
    private View view;
    private ImageView imageView_ContactView;
    private ImageView imageView_ContactAccent;
    private TextView textlist_ContactName;
    private TextView textlist_ContactPhoneNumber;

    //endregion
    //region Constructor
    public contactListViewHolder(View view) {
        this.view = view;
        imageView_ContactView = view.findViewById(R.id.imageView_ContactView);
        textlist_ContactName = view.findViewById(R.id.textlist_ContactName);
        textlist_ContactPhoneNumber = view.findViewById(R.id.textlist_ContactPhoneNumber);
        imageView_ContactAccent = view.findViewById(R.id.imageView_ContactAccent);
    }

    //endregion
    //region Setter
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

    //endregion
    //region Getter
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
    //endregion
}
