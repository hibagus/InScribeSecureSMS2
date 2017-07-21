package digitalquantuminc.inscribesecuresms.UserInterface;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 20/07/2017.
 */

public class QRCodeHandler {

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
        String TextforQRCode = "{\"name\":\"" + name + "\",\"phone\":\"" + phonenum + "\",\"pubkey\":\"" + RSAPubKey + "\"}";
        Bitmap bitmap = StringtoQRCode(TextforQRCode, width);
        return bitmap;
    }

    public static int dipToPixels(Activity outer, int dipValue) {
        DisplayMetrics metrics = outer.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
