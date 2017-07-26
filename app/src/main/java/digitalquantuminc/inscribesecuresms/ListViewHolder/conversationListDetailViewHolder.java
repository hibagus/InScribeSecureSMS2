package digitalquantuminc.inscribesecuresms.ListViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class conversationListDetailViewHolder {
    //region Global Variable
    private View view;
    private LinearLayout LinearLayout_Partner;
    private LinearLayout LinearLayout_Your;
    private ImageView imageView_PartnerView;
    private TextView textlist_MessageContentPartner;
    private TextView textlist_MessageTimeStampPartner;
    private TextView textlist_MessageContentYour;
    private TextView textlist_MessageTimeStampYour;
    private TextView textlist_PartnerNumber;
    private TextView textlist_TimeStamp;
    private TextView textlist_Direction;
    private ImageView imageView_YourView;

    //endregion

    //region Constructor
    public conversationListDetailViewHolder(View view) {
        this.view = view;
        this.LinearLayout_Partner = (LinearLayout) view.findViewById(R.id.LinearLayout_Partner);
        this.LinearLayout_Your = (LinearLayout) view.findViewById(R.id.LinearLayout_Your);
        this.imageView_PartnerView = (ImageView) view.findViewById(R.id.imageView_PartnerView);
        this.textlist_MessageContentPartner = (TextView) view.findViewById(R.id.textlist_MessageContentPartner);
        this.textlist_MessageTimeStampPartner = (TextView) view.findViewById(R.id.textlist_MessageTimeStampPartner);
        this.textlist_MessageContentYour = (TextView) view.findViewById(R.id.textlist_MessageContentYour);
        this.textlist_MessageTimeStampYour = (TextView) view.findViewById(R.id.textlist_MessageTimeStampYour);
        this.imageView_YourView = (ImageView) view.findViewById(R.id.imageView_YourView);
        this.textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);
        this.textlist_TimeStamp = (TextView) view.findViewById(R.id.textlist_TimeStamp);
        this.textlist_Direction = (TextView) view.findViewById(R.id.textlist_Direction);
    }
    //endregion


    public ImageView getImageView_PartnerView() {
        return imageView_PartnerView;
    }

    public ImageView getImageView_YourView() {
        return imageView_YourView;
    }

    public LinearLayout getLinearLayout_Partner() {
        return LinearLayout_Partner;
    }

    public LinearLayout getLinearLayout_Your() {
        return LinearLayout_Your;
    }

    public TextView getTextlist_MessageContentPartner() {
        return textlist_MessageContentPartner;
    }

    public TextView getTextlist_MessageContentYour() {
        return textlist_MessageContentYour;
    }

    public TextView getTextlist_MessageTimeStampPartner() {
        return textlist_MessageTimeStampPartner;
    }

    public TextView getTextlist_MessageTimeStampYour() {
        return textlist_MessageTimeStampYour;
    }

    public TextView getTextlist_PartnerNumber() {
        return textlist_PartnerNumber;
    }

    public TextView getTextlist_TimeStamp() {
        return textlist_TimeStamp;
    }

    public TextView getTextlist_Direction() {
        return textlist_Direction;
    }
}
