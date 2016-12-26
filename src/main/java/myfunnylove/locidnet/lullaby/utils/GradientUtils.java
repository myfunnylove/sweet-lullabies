package myfunnylove.locidnet.lullaby.utils;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/1/16
 * Time: 10:13 PM
 * Desc: GradientUtils
 */
public class GradientUtils {

    public static GradientDrawable create(@ColorInt int startColor, @ColorInt int endColor, int radius,
                                          @FloatRange(from = 0f, to = 1f) float centerX,
                                          @FloatRange(from = 0f, to = 1f) float centerY) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            gradientDrawable.setColors(new int[]{
                    startColor,
                    endColor
            });
        }
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(radius);
        gradientDrawable.setGradientCenter(centerX, centerY);
        return gradientDrawable;
    }
}
