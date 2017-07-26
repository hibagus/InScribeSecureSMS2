package digitalquantuminc.inscribesecuresms.Development;

import android.content.Context;

import java.util.ArrayList;

import digitalquantuminc.inscribesecuresms.DataType.TypeSession;
import digitalquantuminc.inscribesecuresms.Repository.sessionRepository;

/**
 * Created by Bagus Hanindhito on 20/07/2017.
 * This class is used for development purpose only
 * It will load dummy data into Session Database
 */

public class SessionDummyData {
    //region Global Variable
    private static final ArrayList<TypeSession> DummySessionList = new ArrayList<TypeSession>() {{
        add(new TypeSession("15555215554", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleUnknown, "", "", "", "", "", TypeSession.StatusDSNotValid, "", "", 0));
        add(new TypeSession("15555215554", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleUnknown, "", "", "", "", "", TypeSession.StatusDSNotValid, "", "", 0));
        add(new TypeSession("15555215556",  TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleUnknown, "", "", "", "", "", TypeSession.StatusDSNotValid, "", "", 0));
        add(new TypeSession("15555215558", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleUnknown, "", "", "", "", "", TypeSession.StatusDSValid, "", "", 0));
//        add(new TypeSession("+6281395141700", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 100));
//        add(new TypeSession("089203040120", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 20));
//        add(new TypeSession("085320456179", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
//        add(new TypeSession("087482813212", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 40));
//        add(new TypeSession("082145875521", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSNotValid, "SHARED000000", "AES0000000", 20));
//        add(new TypeSession("087545221214", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
//        add(new TypeSession("025871422552", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 50));
//        add(new TypeSession("025471421414", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 10));
//        add(new TypeSession("593254411241", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 20));
//        add(new TypeSession("025478525224", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
//        add(new TypeSession("025874568545", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
//        add(new TypeSession("036587855457", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSNotValid, "SHARED000000", "AES0000000", 40));
    }};

    //endregion
    //region Static Methods
    public static void LoadDummyData(Context contex) {

        sessionRepository sessionRepo = new sessionRepository(contex);
        for (int i = 0; i < DummySessionList.size(); i++) {
            sessionRepo.insert(DummySessionList.get(i));
        }
    }

    public static void LoadSingleDummyData(Context contex, int idx) {

        sessionRepository sessionRepo = new sessionRepository(contex);
        sessionRepo.insert(DummySessionList.get(idx));
    }

    public static void ClearDB(Context context) {
        sessionRepository sessionRepo = new sessionRepository(context);
        sessionRepo.DropTable();
    }

    public static void CreateDB(Context context) {
        sessionRepository sessionRepo = new sessionRepository(context);
        sessionRepo.CreateTable();
    }
    //endregion
}
