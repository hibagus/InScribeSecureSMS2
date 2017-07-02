package digitalquantuminc.inscribesecuresms.DataType;

/**
 * Created by Bagus Hanindhito on 28/06/2017.
 * Define the TypeProfile to handle the Profile Data
 * Note Profile Data also includes application last state.
 */

public class TypeProfile {
    //region SQL Table Key
    public static final String TABLE = "profile";
    public static final String KEY_ID = "id";
    public static final String KEY_phone = "phonenum";
    public static final String KEY_name = "selfname";
    public static final String KEY_date = "generateddate";
    public static final String KEY_rsapub = "rsapubkey";
    public static final String KEY_rsapriv = "rsaprivkey";
    //endregion
    //region Global Variable
    private String phone_number;
    private String name_self;
    private long generated_date;
    private String rsa_publickey;
    private String rsa_privatekey;

    //endregion
    //region Constructor
    public TypeProfile() {

    }

    public TypeProfile(String phone_number, String name_self, long generated_date, String rsa_publickey, String rsa_privatekey) {
        this.phone_number = phone_number;
        this.name_self = name_self;
        this.generated_date = generated_date;
        this.rsa_publickey = rsa_publickey;
        this.rsa_privatekey = rsa_privatekey;
    }

    //endregion
    //region Setter
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setName_self(String name_self) {
        this.name_self = name_self;
    }

    public void setGenerated_date(long generated_date) {
        this.generated_date = generated_date;
    }

    public void setRsa_publickey(String rsa_publickey) {
        this.rsa_publickey = rsa_publickey;
    }

    public void setRsa_privatekey(String rsa_privatekey) {
        this.rsa_privatekey = rsa_privatekey;
    }

    //endregion
    //region Getter
    public long getGenerated_date() {
        return generated_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getName_self() {
        return name_self;
    }

    public String getRsa_privatekey() {
        return rsa_privatekey;
    }

    public String getRsa_publickey() {
        return rsa_publickey;
    }
    //endregion
}
