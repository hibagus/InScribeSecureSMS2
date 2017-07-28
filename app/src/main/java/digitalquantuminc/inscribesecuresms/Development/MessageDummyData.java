package digitalquantuminc.inscribesecuresms.Development;

import android.content.Context;

import java.util.ArrayList;

import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;
import digitalquantuminc.inscribesecuresms.DataType.TypeMetaMessage;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;
import digitalquantuminc.inscribesecuresms.Repository.messageRepository;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class MessageDummyData {

    //region Global Variable
    //int direction, int messagetype, String address, long timestamp, String encodedcontent, String plaincontent
    private static final ArrayList<TypeMessage> DummyMessageList = new ArrayList<TypeMessage>() {{
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "15555215554", System.currentTimeMillis()-50000, "N;Γ£è1wåÄwFö8Äñu2ÉHt7WkÇ5Ω33;èå!£xT$0G¡väiå\\Sè2sΨ\\Å7-li3èn¿*EéU", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "081321132456", System.currentTimeMillis()-30000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "15555215556", System.currentTimeMillis()-100000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "+6281395141700", System.currentTimeMillis()-20000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "089203040120", System.currentTimeMillis()-40000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "085320456179", System.currentTimeMillis()-100000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "087482813212", System.currentTimeMillis()-90000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "082145875521", System.currentTimeMillis()+500000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "087545221214", System.currentTimeMillis()+10000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025871422552", System.currentTimeMillis(), "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025471421414", System.currentTimeMillis(), "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "593254411241", System.currentTimeMillis(), "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025478525224", System.currentTimeMillis(), "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025874568545", System.currentTimeMillis(), "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "036587855457", System.currentTimeMillis(), "00000000ENCODED", "0000000000PLAIN"));

        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "15555215554", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "081321132456", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "15555215556", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "+6281395141700", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "089203040120", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "085320456179", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "087482813212", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "082145875521", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "087545221214", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025871422552", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025471421414", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "593254411241", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025478525224", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025874568545", 0, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONINBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "036587855457", 0, "00000000ENCODED", "0000000000PLAIN"));

        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "15555215554", 50000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "081321132456", 52000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "15555215556", 54000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "+6281395141700", 50000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "089203040120", 67000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "085320456179", 50000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "087482813212", 87000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "082145875521", 50000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "087545221214", 52000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025871422552", 50000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025471421414", 14000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "593254411241", 21000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025478525224", 50000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "025874568545", 55000, "00000000ENCODED", "0000000000PLAIN"));
        add(new TypeMessage(TypeMessage.MESSAGEDIRECTIONOUTBOX, TypeMetaMessage.MessageTypeNormalEncryptedUncompressed, "036587855457", 50000, "00000000ENCODED", "0000000000PLAIN"));
    }};

    //endregion
    //region Static Methods
    public static void LoadDummyData(Context contex) {

        messageRepository messageRepo = new messageRepository(contex);
        for (int i = 0; i < DummyMessageList.size(); i++) {
            messageRepo.insert(DummyMessageList.get(i));
        }
    }

    public static void LoadSingleDummyData(Context contex, int idx) {

        messageRepository messageRepo = new messageRepository(contex);
        messageRepo.insert(DummyMessageList.get(idx));
    }

    public static void ClearDB(Context context) {
        messageRepository messageRepo = new messageRepository(context);
        messageRepo.DropTable();
    }

    public static void CreateDB(Context context) {
        messageRepository messageRepo = new messageRepository(context);
        messageRepo.CreateTable();
    }
    //endregion
}
