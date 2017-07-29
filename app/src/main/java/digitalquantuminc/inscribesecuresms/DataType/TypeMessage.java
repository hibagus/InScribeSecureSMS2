package digitalquantuminc.inscribesecuresms.DataType;

/**
 * Created by Ulfah Nadiya on 24/07/2017.
 * Define the TypeMessage to handle the Message Format
 */

public class TypeMessage {
    //region Placeholder
    public static final int MESSAGEDIRECTIONINBOX = 1;
    public static final int MESSAGEDIRECTIONOUTBOX = 2;
    //endregion
    //region SQL Table Key
    public static final String TABLE = "message";
    public static final String KEY_ID = "messageid";
    public static final String KEY_direction = "direction";
    public static final String KEY_messagetype = "messagetype";
    public static final String KEY_address = "address";
    public static final String KEY_timestamp = "timestamp";
    public static final String KEY_encodedcontent = "encodedcontent";
    public static final String KEY_plaincontent = "plaincontent";
    //endregion
    //region Global Variable
    private int direction;
    private int messagetype;
    private String address;
    private long timestamp;
    private String encodedcontent;
    private String plaincontent;

    //endregion
    //region Constructor
    public TypeMessage() {

    }

    public TypeMessage(int direction, int messagetype, String address, long timestamp, String encodedcontent, String plaincontent) {
        this.direction = direction;
        this.messagetype = messagetype;
        this.address = address;
        this.timestamp = timestamp;
        this.encodedcontent = encodedcontent;
        this.plaincontent = plaincontent;
    }

    //endregion
    //region Setter
    public void setEncodedcontent(String content) {
        this.encodedcontent = content;
    }

    public void setPlaincontent(String plaincontent) {
        this.plaincontent = plaincontent;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    //endregion
    //region Getter

    public int getDirection() {
        return this.direction;
    }

    public int getMessagetype() {
        return this.messagetype;
    }

    public String getEncodedcontent() {
        return this.encodedcontent;
    }

    public String getPlaincontent() {
        return plaincontent;
    }

    public String getAddress() {
        return this.address;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    //endregion
}
