package myfunnylove.locidnet.lullaby.player;

import android.support.annotation.Nullable;

import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.data.model.Song;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/5/16
 * Time: 6:02 PM
 * Desc: IPlayer
 */
public interface IPlayback {

    void setPlayList(PlayList list);

    boolean play();


    void setAlarm();
    boolean play(PlayList list);

    boolean play(PlayList list, int startIndex);

    boolean play(Song song);

    boolean playLast();

    boolean playNext();

    boolean pause();

    boolean isPlaying();

    int getProgress();

    Song getPlayingSong();

    boolean seekTo(int progress);


    void setDuration(int duration);

    int getDuration();
    void setPlayMode(PlayMode playMode);

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();



    interface Callback {

        void onSwitchLast(@Nullable Song last);

        void onSwitchNext(@Nullable Song next);

        void onComplete(@Nullable Song next);

        void onPlayStatusChanged(boolean isPlaying);
    }
}
