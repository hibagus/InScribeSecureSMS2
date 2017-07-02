package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 * This is an abstract class for the Children Activity to be inflated in ViewPager.
 */

abstract class Presenter {
    //region Global Variable
    protected View view;
    private AppCompatActivity activity;

    //endregion
    //region Constructor
    Presenter(AppCompatActivity activity, View view) {
        this.activity = activity;
        this.view = view;
        onCreateView();
    }

    //endregion
    //region Getter
    public View getView() {
        return view;
    }

    //endregion
    //region Method
    public abstract void onCreateView();
    //endregion
}
