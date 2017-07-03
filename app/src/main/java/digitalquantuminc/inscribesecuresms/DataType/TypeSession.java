package digitalquantuminc.inscribesecuresms.DataType;

/**
 * Created by Bagus Hanindhito on 28/06/2017.
 * Define the TypeSession to handle the Session Data
 */

public class TypeSession {
    //region SQL Table Key
    public static final String TABLE = "session";
    public static final String KEY_ID = "id";
    public static final String KEY_phone = "phonenum";
    public static final String KEY_name = "name";
    public static final String KEY_valid = "sessionvalid";
    public static final String KEY_date = "sessiondate";
    public static final String KEY_role = "sessionrole";
    public static final String KEY_ecdhpub = "ecdhpubkey";
    public static final String KEY_ecdhpriv = "ecdhprivkey";
    public static final String KEY_ecdhpubpart = "ecdhpubpartkey";
    public static final String KEY_ecdhds = "ecdhds";
    public static final String KEY_ecdhvalid = "ecdhvalid";
    public static final String KEY_ecdhsecret = "ecdhsecret";
    public static final String KEY_aeskey = "aeskey";
    public static final String KEY_nummessage = "nummessage";
    //endregion
    //region Global Variable
    private String phone_number;
    private String name;
    private int session_validity;
    private long session_handshake_date;
    private int session_role;
    private String session_ecdh_private_key;
    private String session_ecdh_public_key;
    private String session_ecdh_partner_public_key;
    private String session_ecdh_partner_digital_signature;
    private int session_ecdh_partner_validity;
    private String session_ecdh_shared_secret;
    private String session_ecdh_aes_key;
    private int session_num_message;

    //endregion
    //region Constructor
    public TypeSession() {

    }

    public TypeSession(String phone_number, String name, int session_validity, long session_handshake_date, int session_role, String session_ecdh_private_key, String session_ecdh_public_key, String session_ecdh_partner_public_key, String session_ecdh_partner_digital_signature, int session_ecdh_partner_validity, String session_ecdh_shared_secret, String session_ecdh_aes_key, int session_num_message) {
        this.phone_number = phone_number;
        this.name = name;
        this.session_validity = session_validity;
        this.session_handshake_date = session_handshake_date;
        this.session_role = session_role;
        this.session_ecdh_private_key = session_ecdh_private_key;
        this.session_ecdh_public_key = session_ecdh_public_key;
        this.session_ecdh_partner_public_key = session_ecdh_partner_public_key;
        this.session_ecdh_partner_digital_signature = session_ecdh_partner_digital_signature;
        this.session_ecdh_partner_validity = session_ecdh_partner_validity;
        this.session_ecdh_shared_secret = session_ecdh_shared_secret;
        this.session_ecdh_aes_key = session_ecdh_aes_key;
        this.session_num_message = session_num_message;
    }

    //endregion
    //region Setter
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSession_validity(int session_validity) {
        this.session_validity = session_validity;
    }

    public void setSession_handshake_date(long session_handshake_date) {
        this.session_handshake_date = session_handshake_date;
    }

    public void setSession_role(int session_role) {
        this.session_role = session_role;
    }

    public void setSession_ecdh_private_key(String session_ecdh_private_key) {
        this.session_ecdh_private_key = session_ecdh_private_key;
    }

    public void setSession_ecdh_public_key(String session_ecdh_public_key) {
        this.session_ecdh_public_key = session_ecdh_public_key;
    }

    public void setSession_ecdh_partner_public_key(String session_ecdh_partner_public_key) {
        this.session_ecdh_partner_public_key = session_ecdh_partner_public_key;
    }

    public void setSession_ecdh_partner_digital_signature(String session_ecdh_partner_digital_signature) {
        this.session_ecdh_partner_digital_signature = session_ecdh_partner_digital_signature;
    }

    public void setSession_ecdh_partner_validity(int session_ecdh_partner_validity) {
        this.session_ecdh_partner_validity = session_ecdh_partner_validity;
    }

    public void setSession_ecdh_shared_secret(String session_ecdh_shared_secret) {
        this.session_ecdh_shared_secret = session_ecdh_shared_secret;
    }

    public void setSession_ecdh_aes_key(String session_ecdh_aes_key) {
        this.session_ecdh_aes_key = session_ecdh_aes_key;
    }

    public void setSession_num_message(int session_num_message) {
        this.session_num_message = session_num_message;
    }

    //endregion
    //region Getter
    public String getPhone_number() {
        return this.phone_number;
    }

    public String getName() {
        return this.name;
    }

    public int getSession_validity() {
        return this.session_validity;
    }

    public long getSession_handshake_date() {
        return this.session_handshake_date;
    }

    public int getSession_role() {
        return session_role;
    }

    public String getSession_ecdh_private_key() {
        return this.session_ecdh_private_key;
    }

    public String getSession_ecdh_public_key() {
        return this.session_ecdh_public_key;
    }

    public String getSession_ecdh_partner_public_key() {
        return this.session_ecdh_partner_public_key;
    }

    public String getSession_ecdh_partner_digital_signature() {
        return this.session_ecdh_partner_digital_signature;
    }

    public int getSession_ecdh_partner_validity() {
        return this.session_ecdh_partner_validity;
    }

    public String getSession_ecdh_shared_secret() {
        return this.session_ecdh_shared_secret;
    }

    public String getSession_ecdh_aes_key() {
        return this.session_ecdh_aes_key;
    }

    public int getSession_num_message() {
        return session_num_message;
    }
    //endregion
}
