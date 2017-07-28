package digitalquantuminc.inscribesecuresms.ListViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class conversationListViewHolder {
    //region Global Variable
    private View view;
    private ImageView imageView_PartnerView;
    private TextView textlist_AddressNamePhone;
    private TextView textlist_PartnerNumber;
    private TextView textlist_LastMessageReceived;
    private TextView textlist_NewestMessagePreview;
    private ImageView imageView_ColorAccentDarker;
    private ImageView imageView_ColorAccent;

    //endregion

    //region Constructor
    public conversationListViewHolder(View view) {
        this.view = view;
        this.imageView_PartnerView = (ImageView) view.findViewById(R.id.imageView_PartnerView);
        this.textlist_AddressNamePhone = (TextView) view.findViewById(R.id.textlist_AddressNamePhone);
        this.textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);
        this.textlist_LastMessageReceived = (TextView) view.findViewById(R.id.textlist_LastMessageReceived);
        this.textlist_NewestMessagePreview = (TextView) view.findViewById(R.id.textlist_NewestMessagePreview);
        this.imageView_ColorAccentDarker = (ImageView) view.findViewById(R.id.imageView_ColorAccentDarker);
        this.imageView_ColorAccent = (ImageView) view.findViewById(R.id.imageView_ColorAccent);
    }
    //endregion

    //region Getter
    public ImageView getImageView_ColorAccentDarker() {
        return imageView_ColorAccentDarker;
    }

    public ImageView getImageView_ColorAccent() {
        return imageView_ColorAccent;
    }

    public ImageView getImageView_PartnerView() {
        return imageView_PartnerView;
    }

    public TextView getTextlist_AddressNamePhone() {
        return textlist_AddressNamePhone;
    }

    public TextView getTextlist_LastMessageReceived() {
        return textlist_LastMessageReceived;
    }

    public TextView getTextlist_NewestMessagePreview() {
        return textlist_NewestMessagePreview;
    }

    public TextView getTextlist_PartnerNumber() {
        return textlist_PartnerNumber;
    }
    //endregion

}
