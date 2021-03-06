package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 * This class basically provide access to UX Element in Activity Conversation List so that the Parent Activity can have access to them.
 */

public class ViewConversationList extends Presenter {

    //region Global Variable
    private ListView list_conversation;
    //endregion
    //region Constructor
    public ViewConversationList(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreateView() {
        list_conversation = (ListView) view.findViewById(R.id.list_conversation);
    }
    //endregion
    //region Getter
    public ListView getList_conversation() {
        return list_conversation;
    }
    //endregion
}
