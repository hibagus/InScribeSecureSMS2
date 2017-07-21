package digitalquantuminc.inscribesecuresms.Development;

import android.content.Context;

import digitalquantuminc.inscribesecuresms.Repository.profileRepository;

/**
 * Created by Bagus Hanindhito on 20/07/2017.
 */

public class ProfileDummyData {

    public static void ClearDB(Context context) {
        profileRepository profileRepo = new profileRepository(context);
        profileRepo.DropTable();
    }

    public static void CreateDB(Context context) {
        profileRepository profileRepo = new profileRepository(context);
        profileRepo.CreateTableandInitialize();
    }
}
