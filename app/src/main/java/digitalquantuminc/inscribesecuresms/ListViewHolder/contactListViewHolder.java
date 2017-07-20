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
        this.imageView_ContactView = (ImageView) view.findViewById(R.id.imageView_ContactView);
        this.textlist_ContactName = (TextView) view.findViewById(R.id.textlist_ContactName);
        this.textlist_ContactPhoneNumber = (TextView) view.findViewById(R.id.textlist_ContactPhoneNumber);
        this.imageView_ContactAccent = (ImageView) view.findViewById(R.id.imageView_ContactAccent);
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
