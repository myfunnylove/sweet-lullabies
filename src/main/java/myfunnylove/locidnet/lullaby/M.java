package myfunnylove.locidnet.lullaby;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/31/16
 * Time: 9:32 PM
 * Desc: MusicPlayerApplication
 */
public class M extends Application {

    private static M sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        // Custom fonts
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/ComicRelief.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

    }

    public static M getInstance() {
        return sInstance;
    }
}
