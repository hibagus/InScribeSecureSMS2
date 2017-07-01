package digitalquantuminc.inscribesecuresms.Development;

import android.content.Context;

import java.util.ArrayList;

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.Repository.contactRepository;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 * This class is used for development purpose only
 * It will load dummy data into Contact Database
 */

public class ContactDummyData {

    private static final ArrayList<TypeContact> DummyContactList = new ArrayList<TypeContact>() {{
        add(new TypeContact("081395170700", "Bagus Hanindhito", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("081321132456", "Fulan Bin Abi Fulan", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("087213131321", "Cindy Agustina", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("085320456178", "Chobi Chocobi", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("089203040120", "Oryza Sativa", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("085320456179", "Cannabis Sativa", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("087482813212", "Aurelia Auritania", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("082145875521", "Fitria Ridayanti", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("087545221214", "Wisnu Wijayanto", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("025871422552", "Netizen Budiman", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("025471421414", "Ulfah Nadya", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("593254411241", "Fariz Azmi", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("025478525224", "Mantan Terindah", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("025874568545", "Dia Yang Tersakiti", System.currentTimeMillis(), "00000000"));
        add(new TypeContact("036587855457", "Maung Bandung", System.currentTimeMillis(), "00000000"));
    }};

    public static void LoadDummyData(Context contex) {

        contactRepository contactRepo = new contactRepository(contex);
        for (int i = 0; i < DummyContactList.size(); i++) {
            contactRepo.insert(DummyContactList.get(i));
        }
    }

    public static void ClearDB(Context context) {
        contactRepository contactRepo = new contactRepository(context);
        contactRepo.DropTable();
    }

    public static void CreateDB(Context context) {
        contactRepository contactRepo = new contactRepository(context);
        contactRepo.CreateTable();
    }
}
