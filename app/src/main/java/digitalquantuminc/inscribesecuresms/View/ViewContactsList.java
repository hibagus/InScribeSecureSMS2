package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 * This class basically provide access to UX Element in Activity Contact List so that the Parent Activity can have access to them.
 */

public class ViewContactsList extends Presenter {
    //region Global Variable
    private ListView list_contacts;
    private Button btn_AddContact;

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
        btn_AddContact = (Button) view.findViewById(R.id.btn_AddContact);
    }

    //endregion
    //region Getter
    public ListView getList_contacts() {
        return list_contacts;
    }

    public Button getBtn_AddContact() {
        return btn_AddContact;
    }

    //endregion
}
