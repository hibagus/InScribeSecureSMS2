package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 */

public class ViewContactsList extends Presenter {

    private ListView list_contacts;

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ViewContactsList(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    @Override
    public void onCreateView() {
        list_contacts = view.findViewById(R.id.list_contacts);
        mDrawableBuilder = TextDrawable.builder().round();
    }

    public ListView getList_contacts() {
        return list_contacts;
    }
}
