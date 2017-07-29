package digitalquantuminc.inscribesecuresms.DataType;

/**
 * Created by Fariz Azmi Pratama on 28/06/2017.
 * Define the TypeSession to handle the Session Data
 */

public class TypeSession {
    //region Int Constant
    public static final int StatusFresh = 0;
    public static final int StatusStale = 1;
    public static final int StatusDecomposed = 2;
    public static final int StatusValid = 4;
    public static final int StatusNotValid = 5;
    public static final int StatusDSValid = 6;
    public static final int StatusDSNotValid = 7;
    public static final int StatusRoleMaster = 8;
    public static final int StatusRoleSlave = 9;
    public static final int StatusRoleUnknown = 10;
    //region SQL Table Key
    public static final String TABLE = "session";
    public static final String KEY_ID = "sessionid";
    public static final String KEY_phone = "sessionphonenum";
    public static final String KEY_valid = "sessionvalid";
    public static final String KEY_date = "sessiondate";
    public static final String KEY_role = "sessionrole";
    public static final String KEY_ecdhpub = "ecdhpubkey";
    public static final String KEY_ecdhpriv = "ecdhprivkey";
    public static final String KEY_ecdhpubpart = "ecdhpubpartkey";
    public static final String KEY_ecdhds = "ecdhds";
    public static final String KEY_ecdhcomds = "ecdhcomds";
    public static final String KEY_ecdhvalid = "ecdhvalid";
    public static final String KEY_ecdhsecret = "ecdhsecret";
    public static final String KEY_aeskey = "aeskey";
    public static final String KEY_nummessage = "nummessage";
    //endregion
    //region Global Variable
    private String phone_number;
    private int session_validity;
    private long session_handshake_date;
    private int session_role;
    private String session_ecdh_private_key;
    private String session_ecdh_public_key;
    private String session_ecdh_partner_public_key;
    private String session_ecdh_partner_digital_signature;
    private String session_ecdh_partner_computed_digital_signature;
    private int session_ecdh_partner_validity;
    private String session_ecdh_shared_secret;
    private String session_ecdh_aes_key;
    private int session_num_message;

    //endregion
    //region Constructor
    public TypeSession() {

    }

    public TypeSession(String phone_number) {
        this.phone_number = phone_number;
        this.session_validity = StatusNotValid;
        this.session_handshake_date = 0;
        this.session_role = StatusRoleUnknown;
        this.session_ecdh_private_key = "";
        this.session_ecdh_public_key = "";
        this.session_ecdh_partner_public_key = "";
        this.session_ecdh_partner_digital_signature = "";
        this.session_ecdh_partner_computed_digital_signature = "";
        this.session_ecdh_partner_validity = StatusDSNotValid;
        this.session_ecdh_shared_secret = "";
        this.session_ecdh_aes_key = "";
        this.session_num_message = 0;
    }

    public TypeSession(String phone_number, int session_validity, long session_handshake_date, int session_role, String session_ecdh_private_key, String session_ecdh_public_key, String session_ecdh_partner_public_key, String session_ecdh_partner_digital_signature, String session_ecdh_partner_computed_digital_signature, int session_ecdh_partner_validity, String session_ecdh_shared_secret, String session_ecdh_aes_key, int session_num_message) {
        this.phone_number = phone_number;
        this.session_validity = session_validity;
        this.session_handshake_date = session_handshake_date;
        this.session_role = session_role;
        this.session_ecdh_private_key = session_ecdh_private_key;
        this.session_ecdh_public_key = session_ecdh_public_key;
        this.session_ecdh_partner_public_key = session_ecdh_partner_public_key;
        this.session_ecdh_partner_digital_signature = session_ecdh_partner_digital_signature;
        this.session_ecdh_partner_computed_digital_signature = session_ecdh_partner_computed_digital_signature;
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

    public void setSession_ecdh_partner_computed_digital_signature(String session_ecdh_partner_computed_digital_signature) {
        this.session_ecdh_partner_computed_digital_signature = session_ecdh_partner_computed_digital_signature;
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

    public String getSession_ecdh_partner_computed_digital_signature() {
        return session_ecdh_partner_computed_digital_signature;
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

    //region Method
    public int computeSessionFreshness() {
        if (session_validity == StatusValid) {

            int session_freshness = getSessionElapsedHour() * 5 + session_num_message;
            if (session_freshness < 25) {
                return StatusFresh;
            } else if (session_freshness >= 25 && session_freshness <= 100) {
                return StatusStale;
            } else {
                return StatusDecomposed;
            }
        } else {
            return StatusDecomposed;
        }
    }

    public int getSessionElapsedHour() {
        long mills = System.currentTimeMillis() - session_handshake_date;
        int hours = (int) mills / (1000 * 60 * 60);
        return hours;
    }

    public int getSessionElapsedMin() {
        long mills = System.currentTimeMillis() - session_handshake_date;
        int hours = (int) mills / (1000 * 60 * 60);
        int mins = (int) (mills - hours * 1000 * 60 * 60) / (1000 * 60);
        return mins;
    }
    //endregion
}
