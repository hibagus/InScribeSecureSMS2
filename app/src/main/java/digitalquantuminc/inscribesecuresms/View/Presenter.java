package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 */

public abstract class Presenter {

    protected View view;
    private AppCompatActivity activity;

    public Presenter(AppCompatActivity activity, View view) {
        this.activity = activity;
        this.view = view;
        onCreateView();
    }

    public View getView() {
        return view;
    }

    public abstract void onCreateView();
}
