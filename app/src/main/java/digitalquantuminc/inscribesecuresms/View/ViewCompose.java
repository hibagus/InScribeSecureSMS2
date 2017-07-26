package digitalquantuminc.inscribesecuresms.View;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 21/07/2017.
 */

public class ViewCompose extends Presenter {

    //region Global Variable
    private TextView text_ComposePartnerName;
    private TextView text_ComposePartnerNumber;
    private Button btn_ComposeSelectSession;
    private EditText text_ComposePlainText;
    private TextView text_ComposePlainTextSize;
    private Button btn_ComposeEncryptMessage;
    private EditText text_ComposeCompressedText;
    private TextView text_ComposeCompressedTextSize;
    private TextView text_ComposeCompressedTextAlgorithm;
    private EditText text_ComposeAESIV;
    private TextView text_ComposeAESIVSize;
    private EditText text_ComposeAESCT;
    private TextView text_ComposeAESCTSize;
    private EditText text_ComposeEncryptedMessage;
    private TextView text_ComposeEncryptedMessageSize;
    private EditText text_ComposeEncodedMessage;
    private TextView text_ComposeEncodedMessageSize;
    private EditText text_ComposeFinalMessage;
    private TextView text_ComposeMetadataSize;
    private TextView text_ComposeFinalMessageSize;
    private TextView text_ComposeNumberofMessage;
    private Button btn_ComposeSendMessage;


    //endregion
    //region Constructor
    public ViewCompose(AppCompatActivity activity, View view) {
        super(activity, view);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreateView() {
        text_ComposePartnerName = (TextView) view.findViewById(R.id.text_ComposePartnerName);
        text_ComposePartnerNumber = (TextView) view.findViewById(R.id.text_ComposePartnerNumber);
        btn_ComposeSelectSession = (Button) view.findViewById(R.id.btn_ComposeSelectSession);
        text_ComposePlainText = (EditText) view.findViewById(R.id.text_ComposePlainText);
        text_ComposePlainTextSize = (TextView) view.findViewById(R.id.text_ComposePlainTextSize);
        btn_ComposeEncryptMessage = (Button) view.findViewById(R.id.btn_ComposeEncryptMessage);
        text_ComposeCompressedText = (EditText) view.findViewById(R.id.text_ComposeCompressedText);
        text_ComposeCompressedTextSize = (TextView) view.findViewById(R.id.text_ComposeCompressedTextSize);
        text_ComposeCompressedTextAlgorithm = (TextView) view.findViewById(R.id.text_ComposeCompressedAlgorithm);
        text_ComposeAESIV = (EditText) view.findViewById(R.id.text_ComposeAESIV);
        text_ComposeAESIVSize = (TextView) view.findViewById(R.id.text_ComposeAESIVSize);
        text_ComposeAESCT = (EditText) view.findViewById(R.id.text_ComposeAESCT);
        text_ComposeAESCTSize = (TextView) view.findViewById(R.id.text_ComposeAESCTSize);
        text_ComposeEncryptedMessage = (EditText) view.findViewById(R.id.text_ComposeEncryptedMessage);
        text_ComposeEncryptedMessageSize = (TextView) view.findViewById(R.id.text_ComposeEncryptedMessageSize);
        text_ComposeEncodedMessage = (EditText) view.findViewById(R.id.text_ComposeEncodedMessage);
        text_ComposeEncodedMessageSize = (TextView) view.findViewById(R.id.text_ComposeEncodedMessageSize);
        text_ComposeFinalMessage = (EditText) view.findViewById(R.id.text_ComposeFinalMessage);
        text_ComposeMetadataSize = (TextView) view.findViewById(R.id.text_ComposeMetadataSize);
        text_ComposeFinalMessageSize = (TextView) view.findViewById(R.id.text_ComposeFinalMessageSize);
        text_ComposeNumberofMessage = (TextView) view.findViewById(R.id.text_ComposeNumberofMessage);
        btn_ComposeSendMessage = (Button) view.findViewById(R.id.btn_ComposeSendMessage);

    }


    //endregion
    //region Getter

    public Button getBtn_ComposeEncryptMessage() {
        return btn_ComposeEncryptMessage;
    }

    public Button getBtn_ComposeSelectSession() {
        return btn_ComposeSelectSession;
    }

    public Button getBtn_ComposeSendMessage() {
        return btn_ComposeSendMessage;
    }

    public EditText getText_ComposeAESIV() {
        return text_ComposeAESIV;
    }

    public EditText getText_ComposeAESCT() {
        return text_ComposeAESCT;
    }

    public EditText getText_ComposeCompressedText() {
        return text_ComposeCompressedText;
    }

    public EditText getText_ComposeEncodedMessage() {
        return text_ComposeEncodedMessage;
    }

    public EditText getText_ComposeEncryptedMessage() {
        return text_ComposeEncryptedMessage;
    }

    public EditText getText_ComposeFinalMessage() {
        return text_ComposeFinalMessage;
    }

    public EditText getText_ComposePlainText() {
        return text_ComposePlainText;
    }

    public TextView getText_ComposeAESIVSize() {
        return text_ComposeAESIVSize;
    }

    public TextView getText_ComposeAESCTSize() {
        return text_ComposeAESCTSize;
    }

    public TextView getText_ComposeCompressedTextSize() {
        return text_ComposeCompressedTextSize;
    }

    public TextView getText_ComposeCompressedTextAlgorithm() {
        return text_ComposeCompressedTextAlgorithm;
    }

    public TextView getText_ComposeEncodedMessageSize() {
        return text_ComposeEncodedMessageSize;
    }

    public TextView getText_ComposeEncryptedMessageSize() {
        return text_ComposeEncryptedMessageSize;
    }

    public TextView getText_ComposeFinalMessageSize() {
        return text_ComposeFinalMessageSize;
    }

    public TextView getText_ComposeMetadataSize() {
        return text_ComposeMetadataSize;
    }

    public TextView getText_ComposePartnerName() {
        return text_ComposePartnerName;
    }

    public TextView getText_ComposePartnerNumber() {
        return text_ComposePartnerNumber;
    }

    public TextView getText_ComposePlainTextSize() {
        return text_ComposePlainTextSize;
    }

    public TextView getText_ComposeNumberofMessage() {
        return text_ComposeNumberofMessage;
    }

    //endregion
}
