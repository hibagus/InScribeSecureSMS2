package digitalquantuminc.inscribesecuresms.DataType;

/**
 * Created by Bagus Hanindhito on 21/07/2017.
 */

public class TypeAESByte {
    private byte[] IV;
    private byte[] CipherText;

    public TypeAESByte()
    {

    }

    public TypeAESByte(byte[] IV, byte[] CipherText)
    {
        this.IV = IV;
        this.CipherText = CipherText;
    }

    public byte[] getCipherText() {
        return CipherText;
    }

    public byte[] getIV() {
        return IV;
    }


}
