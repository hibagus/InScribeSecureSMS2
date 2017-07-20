package digitalquantuminc.inscribesecuresms.ListViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 02/07/2017.
 * This class basically handle each of the item in Session List.
 */

public class sessionListViewHolder {
    //region Global Variable
    private View view;
    private ImageView imageView_PartnerView;
    private TextView textlist_PartnerName;
    private TextView textlist_PartnerNumber;
    private TextView textlist_SessionHandshakeDate;
    private TextView textlist_SessionDetails;
    private ImageView imageView_SessionStatus;
    private ImageView imageView_SessionDuration;

    //endregion
    //region Constructor
    public sessionListViewHolder(View view) {
        this.view = view;
        this.imageView_PartnerView = (ImageView) view.findViewById(R.id.imageView_PartnerView);
        this.textlist_PartnerName = (TextView) view.findViewById(R.id.textlist_PartnerName);
        this.textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);
        this.textlist_SessionHandshakeDate = (TextView) view.findViewById(R.id.textlist_SessionHandshakeDate);
        this.textlist_SessionDetails = (TextView) view.findViewById(R.id.textlist_SessionDetails);
        this.imageView_SessionStatus = (ImageView) view.findViewById(R.id.imageView_SessionStatus);
        this.imageView_SessionDuration = (ImageView) view.findViewById(R.id.imageView_SessionDuration);
    }

    //endregion
    //region Getter
    public ImageView getImageView_PartnerView() {
        return imageView_PartnerView;
    }

    public ImageView getImageView_SessionDuration() {
        return imageView_SessionDuration;
    }

    public ImageView getImageView_SessionStatus() {
        return imageView_SessionStatus;
    }

    public TextView getTextlist_PartnerName() {
        return textlist_PartnerName;
    }

    public TextView getTextlist_PartnerNumber() {
        return textlist_PartnerNumber;
    }

    public TextView getTextlist_SessionDetails() {
        return textlist_SessionDetails;
    }

    public TextView getTextlist_SessionHandshakeDate() {
        return textlist_SessionHandshakeDate;
    }

    //endregion
}
