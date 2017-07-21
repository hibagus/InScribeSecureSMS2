package digitalquantuminc.inscribesecuresms;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.RSAKeyGenParameterSpec;

/**
 * Created by Bagus Hanindhito on 21/07/2017.
 */

public class Cryptography {
    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public Cryptography() {

    }

    public static KeyPair GenerateRSAKeyPair(int KeySize) {
        KeyPair keypair = null;
        SecureRandom random = null;
        KeyPairGenerator keyGen = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(KeySize, RSAKeyGenParameterSpec.F4);
            keyGen = KeyPairGenerator.getInstance("RSA", "SC");
            keyGen.initialize(spec, random);
            keypair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {

        }
        return keypair;
    }
}
