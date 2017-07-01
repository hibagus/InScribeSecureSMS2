package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 * This class basically provide access to UX Element in Activity Contact List so that the Parent Activity can have access to them.
 */

public class ViewContactsList extends Presenter {
    //region Global Variable
    private ListView list_contacts;
    private TextView text_PartnerNumber;
    private TextView text_PartnerName;
    private TextView text_ContactAcquisitionDate;
    private EditText text_partnerRSAPubKey;
    private Button btn_DeleteContact;

    //endregion
    //region Constructor
    public ViewContactsList(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreateView() {
        list_contacts = (ListView) view.findViewById(R.id.list_contacts);
        text_PartnerNumber = (TextView) view.findViewById(R.id.text_PartnerNumber);
        text_PartnerName = (TextView) view.findViewById(R.id.text_PartnerName);
        text_ContactAcquisitionDate = (TextView) view.findViewById(R.id.text_ContactAcquisitionDate);
        text_partnerRSAPubKey = (EditText) view.findViewById(R.id.text_partnerRSAPubKey);
        btn_DeleteContact = (Button) view.findViewById(R.id.btn_DeleteContact);
    }

    //endregion
    //region Getter
    public ListView getList_contacts() {
        return list_contacts;
    }

    public TextView getText_PartnerNumber() {
        return text_PartnerNumber;
    }

    public TextView getText_PartnerName() {
        return text_PartnerName;
    }

    public TextView getText_ContactAcquisitionDate() {
        return text_ContactAcquisitionDate;
    }

    public EditText getText_partnerRSAPubKey() {
        return text_partnerRSAPubKey;
    }

    public Button getBtn_DeleteContact() {
        return btn_DeleteContact;
    }
    //endregion
}
