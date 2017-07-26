package digitalquantuminc.inscribesecuresms.Development;

import android.content.Context;

import java.security.PublicKey;

import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;
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

    public static void InsertProfile(Context context, String name, String PhoneNum, String RSAPublicKey, String RSAPrivateKey ) {
        //String phone_number, String name_self, long generated_date, String rsa_publickey, String rsa_privatekey, long lastsync
        TypeProfile profile = new TypeProfile(PhoneNum, name, System.currentTimeMillis(), RSAPublicKey, RSAPrivateKey, System.currentTimeMillis());
        profileRepository profileRepo = new profileRepository(context);
        profileRepo.update(profile);

    }
}
