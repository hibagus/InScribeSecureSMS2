package digitalquantuminc.inscribesecuresms.UserInterface;

import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.AESFastEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.jcajce.provider.symmetric.AES;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;

import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import digitalquantuminc.inscribesecuresms.DataType.TypeAESByte;

/**
 * Created by Bagus Hanindhito on 21/07/2017.
 */

public class Cryptography {
    public static final int RSAKEYSIZE = 2048;
    public static final int DIGITALSIGNATURESIZE = 256;
    public static final int AESKEYSIZE = 128; //128?
    public static final int AESIVLENGTH = 16;
    public static final int PBKDF2ITERATION = 16384;
    public static final String ELIPTICCURVENAME = "secp256k1"; //secp256k1 secp224k1
    public static final int AESBLOCKBYTE = 16;

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public Cryptography() {

    }

    public static KeyPair GenerateRSAKeyPair(int KeySize) {
        KeyPair keypair = null;
        SecureRandom random;
        KeyPairGenerator keyGen;
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

    public static KeyPair GenerateECDHKeyPair(String ElipticCurve) {
        KeyPair keypair = null;
        SecureRandom random;
        KeyPairGenerator keyGen;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec spec = new ECGenParameterSpec(ElipticCurve);
            keyGen = KeyPairGenerator.getInstance("ECDH", "SC");
            keyGen.initialize(spec, random);
            keypair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {

        }
        return keypair;
    }

    public static SecretKey GenerateECDHSharedSecret(PublicKey partnerpubkey, PrivateKey selfprivkey) {
        byte[] sharedsecret;
        KeyAgreement keyagreement;
        SecretKey sharedkey = null;
        try {
            keyagreement = KeyAgreement.getInstance("ECDH", "SC");
            keyagreement.init(selfprivkey);
            keyagreement.doPhase(partnerpubkey, true);
            sharedsecret = keyagreement.generateSecret();
            sharedkey = new SecretKeySpec(sharedsecret, 0, sharedsecret.length, "ECDH");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {

        }
        return sharedkey;
    }


    public static SecretKey GenerateAESKey(SecretKey SharedSecret, int iteration, int aeskeysize) {
        MessageDigest md;
        byte[] salt;
        KeySpec spec;
        SecretKeyFactory secretKeyFactory;
        SecretKey PBKDF2Key;
        SecretKey AESKey = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(SharedSecret.getEncoded());
            salt = md.digest();

            spec = new PBEKeySpec(BytetoBase64String(SharedSecret.getEncoded()).toCharArray(), salt, iteration, aeskeysize);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256", "SC");
            PBKDF2Key = secretKeyFactory.generateSecret(spec);
            AESKey = new SecretKeySpec(PBKDF2Key.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        //Log.v("AES Secret Length: ", String.valueOf(AESKey.getEncoded().length));
        return AESKey;
    }

    public static SecretKey GenerateAESKey(String password, int iteration, int aeskeysize) {
        MessageDigest md;
        byte[] salt;
        KeySpec spec;
        SecretKeyFactory secretKeyFactory;
        SecretKey PBKDF2Key;
        SecretKey AESKey = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            salt = md.digest();

            spec = new PBEKeySpec(password.toCharArray(), salt, iteration, aeskeysize);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256", "SC");
            PBKDF2Key = secretKeyFactory.generateSecret(spec);
            AESKey = new SecretKeySpec(PBKDF2Key.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        return AESKey;
    }

//    public static TypeAESByte EncryptMessageAES(SecretKey AESKey, byte[] plaintext) {
//        Cipher cipher;
//        AlgorithmParameters params;
//        TypeAESByte encryptionresult = null;
//        byte[] IV;
//        byte[] CT;
//
//        try {
//            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, AESKey);
//            params = cipher.getParameters();
//            IV = params.getParameterSpec(IvParameterSpec.class).getIV();
//            CT = cipher.doFinal(plaintext);
//            encryptionresult = new TypeAESByte(IV, CT);
//
//        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidParameterSpecException | IllegalBlockSizeException | BadPaddingException e) {
//
//        }
//        return encryptionresult;
//    }

    public static byte[] EncryptMessageAES(SecretKey AESKey, byte[] plaintext) {
        Cipher cipher;
        AlgorithmParameters params;
        SecureRandom random;
        byte[] IV;
        byte[] CT;
        byte[] EncryptedText = null;

        random = new SecureRandom();
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AESKey, random);
            params = cipher.getParameters();
            IV = params.getParameterSpec(IvParameterSpec.class).getIV();
            CT = cipher.doFinal(plaintext);
            EncryptedText = new byte[AESBLOCKBYTE + CT.length];
            System.arraycopy(IV, 0, EncryptedText, 0, AESBLOCKBYTE);    // Add iv
            System.arraycopy(CT, 0, EncryptedText, AESBLOCKBYTE, CT.length);    // Then the encrypted data

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidParameterSpecException | IllegalBlockSizeException | BadPaddingException e) {

        }
        return EncryptedText;
    }

    public static byte[] EncryptMessageAESSpongy(SecretKey AESKey, byte[] plaintext) {
        try {
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
            // Random iv
            SecureRandom rng = new SecureRandom();
            byte[] ivBytes = new byte[AESBLOCKBYTE];
            rng.nextBytes(ivBytes);

            cipher.init(true, new ParametersWithIV(new KeyParameter(AESKey.getEncoded()), ivBytes));
            byte[] outBuf = new byte[cipher.getOutputSize(plaintext.length)];

            int processed = cipher.processBytes(plaintext, 0, plaintext.length, outBuf, 0);
            processed += cipher.doFinal(outBuf, processed);

            byte[] outBuf2 = new byte[processed + AESBLOCKBYTE];        // Make room for iv
            System.arraycopy(ivBytes, 0, outBuf2, 0, AESBLOCKBYTE);    // Add iv
            System.arraycopy(outBuf, 0, outBuf2, AESBLOCKBYTE, processed);    // Then the encrypted data
            return outBuf2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static byte[] DecryptMessageAES(SecretKey AESKey, byte[] CT, byte[] IV) {
//        Cipher cipher;
//        AlgorithmParameters params;
//        byte[] plaintext = null;
//
//        try {
//            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, AESKey, new IvParameterSpec(IV));
//            plaintext = cipher.doFinal(CT);
//        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
//
//        }
//        return plaintext;
//    }

    public static byte[] DecryptMessageAES(SecretKey AESKey, byte[] EncryptedMessage) {
        Cipher cipher;
        AlgorithmParameters params;
        byte[] plaintext = null;
        byte[] IV = new byte[AESBLOCKBYTE];
        byte[] CT = new byte[EncryptedMessage.length - AESBLOCKBYTE];

        System.arraycopy(EncryptedMessage, 0, IV, 0, AESBLOCKBYTE); // Get iv from data
        System.arraycopy(EncryptedMessage, AESBLOCKBYTE, CT, 0, EncryptedMessage.length - AESBLOCKBYTE);

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, AESKey, new IvParameterSpec(IV));
            plaintext = cipher.doFinal(CT);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            plaintext = new byte[EncryptedMessage.length];
            System.arraycopy(EncryptedMessage, 0, plaintext, 0, EncryptedMessage.length);
        }
        return plaintext;
    }

    public static byte[] DecryptMessageAESSpongy(SecretKey AESKey, byte[] ciphertext) {
        try {
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
            byte[] ivBytes = new byte[AESBLOCKBYTE];
            System.arraycopy(ciphertext, 0, ivBytes, 0, AESBLOCKBYTE); // Get iv from data
            byte[] dataonly = new byte[ciphertext.length - AESBLOCKBYTE];
            System.arraycopy(ciphertext, AESBLOCKBYTE, dataonly, 0, ciphertext.length - AESBLOCKBYTE);

            cipher.init(false, new ParametersWithIV(new KeyParameter(AESKey.getEncoded()), ivBytes));
            byte[] plaintext = new byte[cipher.getOutputSize(dataonly.length)];
            int len = cipher.processBytes(dataonly, 0, dataonly.length, plaintext, 0);
            len += cipher.doFinal(plaintext, len);

            return plaintext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] GetAESIV(byte[] cipherText) {
        byte[] AESIV = new byte[AESBLOCKBYTE];
        System.arraycopy(cipherText, 0, AESIV, 0, AESIV.length);
        return AESIV;
    }

    public static byte[] GetAESContent(byte[] cipherText) {
        byte[] AESCT = new byte[cipherText.length - AESBLOCKBYTE];
        System.arraycopy(cipherText, AESBLOCKBYTE, AESCT, 0, cipherText.length - AESBLOCKBYTE);
        return AESCT;
    }

    public static byte[] CreateDigitalSignatureRSA(byte[] data, PrivateKey rsaprivatekey) {
        byte[] calculatedsignature = null;
        SecureRandom random;
        Signature signature;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            signature = Signature.getInstance("SHA256withRSA", "SC");
            signature.initSign(rsaprivatekey, random);
            signature.update(data);
            calculatedsignature = signature.sign();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {

        }

        return calculatedsignature;
    }

    public static byte[] EmbedDigitalSignaturewithMessage(byte[] data, byte[] digitalsignature) {
        byte[] dsembedded = new byte[data.length + digitalsignature.length];
        System.arraycopy(digitalsignature, 0, dsembedded, 0, DIGITALSIGNATURESIZE);
        System.arraycopy(data, 0, dsembedded, DIGITALSIGNATURESIZE, data.length);
        return dsembedded;
    }

    public static byte[] ExtractDigitalSignature(byte[] dsembedded) {
        byte[] digitalsignatureonly = new byte[DIGITALSIGNATURESIZE];
        System.arraycopy(dsembedded, 0, digitalsignatureonly, 0, DIGITALSIGNATURESIZE);
        return digitalsignatureonly;
    }

    public static byte[] ExtractContentfromDS(byte[] dsembedded) {
        byte[] contentonly = new byte[dsembedded.length - DIGITALSIGNATURESIZE];
        System.arraycopy(dsembedded, DIGITALSIGNATURESIZE, contentonly, 0, dsembedded.length - DIGITALSIGNATURESIZE);
        return contentonly;
    }

    public static boolean VerifyDigitalSignatureRSA(byte[] data, byte[] receivedsignature, PublicKey rsapublickey) {
        boolean verify_result = false;
        Signature signature;
        try {
            signature = Signature.getInstance("SHA256withRSA", "SC");
            signature.initVerify(rsapublickey);
            signature.update(data);
            verify_result = signature.verify(receivedsignature);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {

        }

        return verify_result;
    }


    public static KeyPair BytetoKeyPairRSA(byte[] pubkey, byte[] privkey) {
        KeyFactory kf;
        KeyPair kp = null;
        try {
            kf = KeyFactory.getInstance("RSA", "SC");
            PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privkey));
            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(pubkey));
            kp = new KeyPair(publicKey, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        return kp;
    }

    public static PublicKey BytetoPubKeyRSA(byte[] pubkey) {
        KeyFactory kf;
        PublicKey publickey = null;
        try {
            kf = KeyFactory.getInstance("RSA", "SC");
            publickey = kf.generatePublic(new X509EncodedKeySpec(pubkey));
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        return publickey;
    }

    public static PrivateKey BytetoPrivKeyRSA(byte[] privkey) {
        KeyFactory kf;
        PrivateKey privatekey = null;
        try {
            kf = KeyFactory.getInstance("RSA", "SC");
            privatekey = kf.generatePrivate(new PKCS8EncodedKeySpec(privkey));
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        return privatekey;
    }

    public static PublicKey BytetoPubKeyECDH(byte[] pubkey) {
        KeyFactory kf;
        PublicKey publickey = null;
        try {
            kf = KeyFactory.getInstance("ECDH", "SC");
            publickey = kf.generatePublic(new X509EncodedKeySpec(pubkey));
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        return publickey;
    }

    public static PrivateKey BytetoPrivKeyECDH(byte[] privkey) {
        KeyFactory kf;
        PrivateKey privatekey = null;
        try {
            kf = KeyFactory.getInstance("ECDH", "SC");
            privatekey = kf.generatePrivate(new PKCS8EncodedKeySpec(privkey));
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {

        }
        return privatekey;
    }

    public static SecretKey BytetoKeyAES(byte[] KeyAES) {
        return new SecretKeySpec(KeyAES, 0, KeyAES.length, "AES");
    }


    public static byte[] Base64StringtoByte(String Base64String) {
        try {
            return Base64.decode(Base64String, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            return new byte[0];
        }


    }

    public static String BytetoBase64String(byte[] bytearray) {

        try {
            return Base64.encodeToString(bytearray, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            String Ret = "";
            return Ret;
        }

    }


}
