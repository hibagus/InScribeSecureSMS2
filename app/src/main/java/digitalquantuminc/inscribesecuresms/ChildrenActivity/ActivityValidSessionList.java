package digitalquantuminc.inscribesecuresms.ChildrenActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.Intent.IntentString;
import digitalquantuminc.inscribesecuresms.ListViewAdapter.sessionListAdapter;
import digitalquantuminc.inscribesecuresms.R;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;

public class ActivityValidSessionList extends AppCompatActivity {

    // UI Components
    private ListView list_valid_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_session_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Select a Valid Secure Session");
        // Binding UI Component to Code
        UIComponentBinding();

        // Get Intent
        IntentProcessor();

        // Load Valid Session List
        LoadValidSessionList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            IntentFeedback(RESULT_OK, IntentString.MainFeedbackCode_DiscardValidSession);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void UIComponentBinding() {
        list_valid_session = (ListView) findViewById(R.id.list_valid_session);

    }

    protected void IntentProcessor() {

    }

    protected void IntentFeedback(int status, int FeedbackType) {
        Intent intent = getIntent();
        intent.putExtra(IntentString.MainFeedBackCode, FeedbackType);
        setResult(status, intent);
        finish();
    }

    protected void IntentFeedback(int status, int FeedbackType, String Name, String PhoneNum) {
        Intent intent = getIntent();
        intent.putExtra(IntentString.MainFeedBackCode, FeedbackType);
        intent.putExtra(IntentString.ValidSessiontoMain_Name, Name);
        intent.putExtra(IntentString.ValidSessiontoMain_PhoneNum, PhoneNum);
        setResult(status, intent);
        finish();
    }

    private void LoadValidSessionList() {
        // Access the database and load all of its content to ArrayList
        sessionRepository repo = new sessionRepository(this);
        ArrayList<HashMap<String, String>> validsessionList = repo.getValidSessionListSorted();
        // If there is at least one element
        if (validsessionList.size() != 0) {
            // Set adapter for the listsession
            list_valid_session.setAdapter(new sessionListAdapter(ActivityValidSessionList.this, validsessionList));
            // Set the Event Handler when the item in the List Session gets clicked.
            list_valid_session.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    list_valid_session_onItemClick(parent, view, position, id);
                }
            });
        } else {
            Toast.makeText(this, "No Valid Session List", Toast.LENGTH_SHORT).show();
        }
    }

    private void list_valid_session_onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Method that handle action when the item in list_valid_session listview is clicked.

        // First, we need to bind the UX Component into variable to access it easily.
        TextView textlist_PartnerNumber = (TextView) view.findViewById(R.id.textlist_PartnerNumber);
        TextView textlist_PartnerName = (TextView) view.findViewById(R.id.textlist_PartnerName);

        // Extract the Phone Number as a unique identity for database query.
        String PartnerNumber = textlist_PartnerNumber.getText().toString();
        String PartnerName = textlist_PartnerName.getText().toString();

        // Return it as Intent
        IntentFeedback(Activity.RESULT_OK, IntentString.MainFeedbackCode_LoadValidSession, PartnerName, PartnerNumber);
    }
}
