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

import digitalquantuminc.inscribesecuresms.R;

/**
 * Created by Bagus Hanindhito on 20/07/2017.
 */

public class QRCodeHandler {


    public QRCodeHandler() {

    }

    public static Bitmap StringtoQRCode(Activity outer, String Value, int QRCodeWidth) {
        // Local Variable
        BitMatrix bitMatrix;
        // Algorithm
        try {

            bitMatrix = new MultiFormatWriter().encode(Value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);

        } catch (IllegalArgumentException e) {
            return null;
        } catch (WriterException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                if (bitMatrix.get(x, y)) {
                    pixels[offset + x] = ContextCompat.getColor(outer, R.color.colorBlack);
                } else {
                    pixels[offset + x] = ContextCompat.getColor(outer, R.color.colorWhite);
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, QRCodeWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public static Bitmap GenerateProfileQRCode(Activity outer, String name, String phonenum, String RSAPubKey, int width)
    {
        String TextforQRCode = name + "<>" + phonenum + "<>" + RSAPubKey;
        Bitmap bitmap = StringtoQRCode(outer, TextforQRCode, width);
        return bitmap;
    }

    public static int dipToPixels(Activity outer, int dipValue) {
        DisplayMetrics metrics = outer.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
