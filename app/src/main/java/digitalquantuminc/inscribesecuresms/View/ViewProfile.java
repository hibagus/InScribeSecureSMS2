package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 20/07/2017.
 * This class basically provide access to UX Element in Activity Profile so that the Parent Activity can have access to them.
 */

public class ViewProfile extends Presenter {
    //region Global Variable
    private EditText text_ProfileName;
    private EditText text_ProfileNumber;
    private TextView text_ProfileLastUpdate;
    private EditText text_ProfileRSAPubKey;
    private EditText text_ProfileRSAPrivKey;
    private Button btn_RegenRSAKeyPair;
    private Button btn_UpdateProfile;
    private ImageView imageView_ProfileQRCode;
    private ProgressBar progressBar_Refresh;
    //endregion
    //region Constructor
    public ViewProfile(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreateView() {

        text_ProfileName = (EditText) view.findViewById(R.id.text_ProfileName);
        text_ProfileNumber = (EditText) view.findViewById(R.id.text_ProfileNumber);
        text_ProfileLastUpdate = (TextView) view.findViewById(R.id.text_ProfileLastUpdate);
        text_ProfileRSAPubKey = (EditText) view.findViewById(R.id.text_ProfileRSAPubKey);
        text_ProfileRSAPrivKey = (EditText) view.findViewById(R.id.text_ProfileRSAPrivKey);
        btn_RegenRSAKeyPair = (Button) view.findViewById(R.id.btn_RegenRSAKeyPair);
        btn_UpdateProfile = (Button) view.findViewById(R.id.btn_UpdateProfile);
        imageView_ProfileQRCode = (ImageView) view.findViewById(R.id.imageView_ProfileQRCode);
        progressBar_Refresh = (ProgressBar) view.findViewById(R.id.progressBar_Refresh);
    }


    //endregion
    //region Getter

    public EditText getText_ProfileName() {
        return text_ProfileName;
    }

    public EditText getText_ProfileNumber() {
        return text_ProfileNumber;
    }

    public TextView getText_ProfileLastUpdate() {
        return text_ProfileLastUpdate;
    }

    public EditText getText_ProfileRSAPubKey() {
        return text_ProfileRSAPubKey;
    }

    public EditText getText_ProfileRSAPrivKey() {
        return text_ProfileRSAPrivKey;
    }

    public Button getBtn_UpdateProfile() {
        return btn_UpdateProfile;
    }

    public Button getBtn_RegenRSAKeyPair() {
        return btn_RegenRSAKeyPair;
    }

    public ImageView getImageView_ProfileQRCode() {
        return imageView_ProfileQRCode;
    }

    public ProgressBar getProgressBar_Refresh() {
        return progressBar_Refresh;
    }

    //endregion
}
