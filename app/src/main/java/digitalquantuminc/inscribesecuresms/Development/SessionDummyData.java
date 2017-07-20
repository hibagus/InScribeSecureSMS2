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
        add(new TypeSession("081395170700", "Bagus Hanindhito", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 50));
        add(new TypeSession("081321132456", "Fulan Bin Abi Fulan", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSNotValid, "SHARED000000", "AES0000000", 20));
        add(new TypeSession("087213131321", "Cindy Agustina", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 10));
        add(new TypeSession("085320456178", "Chobi Chocobi", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 100));
        add(new TypeSession("089203040120", "Oryza Sativa", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 20));
        add(new TypeSession("085320456179", "Cannabis Sativa", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
        add(new TypeSession("087482813212", "Aurelia Auritania", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 40));
        add(new TypeSession("082145875521", "Fitria Ridayanti", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSNotValid, "SHARED000000", "AES0000000", 20));
        add(new TypeSession("087545221214", "Wisnu Wijayanto", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
        add(new TypeSession("025871422552", "Netizen Budiman", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 50));
        add(new TypeSession("025471421414", "Ulfah Nadya", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 10));
        add(new TypeSession("593254411241", "Fariz Azmi", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 20));
        add(new TypeSession("025478525224", "Mantan Terindah", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleMaster, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
        add(new TypeSession("025874568545", "Dia Yang Tersakiti", TypeSession.StatusValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSValid, "SHARED000000", "AES0000000", 30));
        add(new TypeSession("036587855457", "Maung Bandung", TypeSession.StatusNotValid, System.currentTimeMillis(), TypeSession.StatusRoleSlave, "ECDHPRIV00000", "ECDHPUB00000", "ECDHPARTPUB00000", "ECDHDS000000", "ECDHDSCOM001213", TypeSession.StatusDSNotValid, "SHARED000000", "AES0000000", 40));
    }};

    //endregion
    //region Static Methods
    public static void LoadDummyData(Context contex) {

        sessionRepository sessionRepo = new sessionRepository(contex);
        for (int i = 0; i < DummySessionList.size(); i++) {
            sessionRepo.insert(DummySessionList.get(i));
        }
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
