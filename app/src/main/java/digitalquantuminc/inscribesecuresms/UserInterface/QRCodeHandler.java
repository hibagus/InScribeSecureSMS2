package digitalquantuminc.inscribesecuresms.UserInterface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import javax.crypto.SecretKey;

import digitalquantuminc.inscribesecuresms.Cryptography;
import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 20/07/2017.
 */

public class QRCodeHandler {

    public static final String ProfileQRCodeAESKey = "BagusHanindhitoFarizAzmiPratamaUlfahNadya";

    public QRCodeHandler() {

    }

    public static Bitmap StringtoQRCode(String Value, int QRCodeWidth)
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = null;
        try
        {
            bitMatrix = multiFormatWriter.encode(Value, BarcodeFormat.QR_CODE,QRCodeWidth,QRCodeWidth);
        }
        catch (WriterException e) {}

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }

    public static Bitmap GenerateProfileQRCode(String name, String phonenum, String RSAPubKey, int width)
    {
        SecretKey AESKey = Cryptography.GenerateAESKey(ProfileQRCodeAESKey, Cryptography.PBKDF2ITERATION, Cryptography.AESKEYSIZE);
        String TextforQRCode = "{\"name\":\"" + name + "\",\"phone\":\"" + phonenum + "\",\"pubkey\":\"" + RSAPubKey + "\"}";
        byte[] DecryptedQR = TextforQRCode.getBytes();
        byte[] EncryptedQR = Cryptography.EncryptMessageAESSpongy(AESKey, DecryptedQR);
        Bitmap bitmap = StringtoQRCode(Cryptography.BytetoBase64String(EncryptedQR), width);
        return bitmap;
    }

    public static int dipToPixels(Activity outer, int dipValue) {
        DisplayMetrics metrics = outer.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
