package digitalquantuminc.inscribesecuresms.DataType;

/**
 * Created by Bagus Hanindhito on 28/06/2017.
 * Define the TypeContact to handle the Contact Data
 */

public class TypeContact {
    //region SQL Table Key
    public static final String TABLE = "contact";
    public static final String KEY_ID = "id";
    public static final String KEY_phone = "phonenum";
    public static final String KEY_name = "contactname";
    public static final String KEY_date = "acquisitiondate";
    public static final String KEY_rsapub = "rsapubkey";
    //endregion
    //region Global Variable
    private String phone_number;
    private String contact_name;
    private long acquisition_date;
    private String rsa_publickey;

    //endregion
    //region Constructor
    public TypeContact() {

    }

    public TypeContact(String phone_number, String contact_name, long acquisition_date, String rsa_publickey) {
        this.phone_number = phone_number;
        this.contact_name = contact_name;
        this.acquisition_date = acquisition_date;
        this.rsa_publickey = rsa_publickey;
    }

    //endregion
    //region Setter
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public void setAcquisition_date(long acquisition_date) {
        this.acquisition_date = acquisition_date;
    }

    public void setRsa_publickey(String rsa_publickey) {
        this.rsa_publickey = rsa_publickey;
    }

    //endregion
    //region Getter
    public String getPhone_number() {
        return this.phone_number;
    }

    public String getContact_name() {
        return this.contact_name;
    }

    public long getAcquisition_date() {
        return this.acquisition_date;
    }

    public String getRsa_publickey() {
        return this.rsa_publickey;
    }
    //endregion
}
