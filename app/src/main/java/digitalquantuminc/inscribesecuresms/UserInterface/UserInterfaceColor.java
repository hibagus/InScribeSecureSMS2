package digitalquantuminc.inscribesecuresms.UserInterface;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Bagus Hanindhito on 19/07/2017.
 */

public class UserInterfaceColor {

    public static int getDarkColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    public static void setTitleBackgroundColor(int color, AppCompatActivity activity) {
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }

    public static void setStatusBarColor(int color, Activity activity) {

        int darken_color = getDarkColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            if (darken_color == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(darken_color);
        }
    }
}
