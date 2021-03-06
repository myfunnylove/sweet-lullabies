package myfunnylove.locidnet.lullaby.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import myfunnylove.locidnet.lullaby.R;
import myfunnylove.locidnet.lullaby.data.model.Folder;
import myfunnylove.locidnet.lullaby.data.model.PlayList;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/9/16
 * Time: 10:27 PM
 * Desc: DBUtils
 */
public class DBUtils {

    public static PlayList generateFavoritePlayList(Context context) {
        PlayList favorite = new PlayList();
        favorite.setFavorite(true);
        favorite.setName(context.getString(R.string.mp_play_list_favorite));
        return favorite;
    }

    public static List<Folder> generateDefaultFolders() {
        List<Folder> defaultFolders = new ArrayList<>(3);
        // File sdcardDir = Environment.getExternalStorageDirectory();
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
         defaultFolders.add(FileUtils.folderFromDir(downloadDir));

        defaultFolders.add(FileUtils.folderFromDir(musicDir));
        return defaultFolders;
    }
}
