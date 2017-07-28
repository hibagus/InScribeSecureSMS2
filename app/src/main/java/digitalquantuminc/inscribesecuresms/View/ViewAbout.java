package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 26/07/2017.
 */

public class ViewAbout extends Presenter {

    // Global Variable
    private Button btn_load1;
    private Button btn_load2;
    private Button btn_load3;
    //region Constructor
    public ViewAbout(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreateView() {

        btn_load1 = (Button) view.findViewById(R.id.btn_load1);
        btn_load2 = (Button) view.findViewById(R.id.btn_load2);
        btn_load3 = (Button) view.findViewById(R.id.btn_load3);
    }

    public Button getBtn_load1() {
        return btn_load1;
    }

    public Button getBtn_load2() {
        return btn_load2;
    }

    public Button getBtn_load3() {
        return btn_load3;
    }
}
