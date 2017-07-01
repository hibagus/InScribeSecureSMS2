package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.w3c.dom.Text;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 */

public class ViewContactsList extends Presenter {

    private ListView list_contacts;
    private TextView text_PartnerNumber;
    private TextView text_PartnerName;
    private TextView text_ContactAcquisitionDate;
    private EditText text_partnerRSAPubKey;
    private Button btn_DeleteContact;

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ViewContactsList(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    @Override
    public void onCreateView() {
        list_contacts = view.findViewById(R.id.list_contacts);
        text_PartnerNumber = view.findViewById(R.id.text_PartnerNumber);
        text_PartnerName = view.findViewById(R.id.text_PartnerName);
        text_ContactAcquisitionDate = view.findViewById(R.id.text_ContactAcquisitionDate);
        text_partnerRSAPubKey = view.findViewById(R.id.text_partnerRSAPubKey);
        btn_DeleteContact = view.findViewById(R.id.btn_DeleteContact);
        mDrawableBuilder = TextDrawable.builder().round();
    }

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
}
