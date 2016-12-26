package myfunnylove.locidnet.lullaby.player;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import myfunnylove.locidnet.lullaby.M;
import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.data.model.Song;
import myfunnylove.locidnet.lullaby.ui.base.Const;
import myfunnylove.locidnet.lullaby.ui.base.Preferences;
import myfunnylove.locidnet.lullaby.ui.settings.AlarmReceiver;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/5/16
 * Time: 5:57 PM
 * Desc: Player
 */
public class Player implements IPlayback, MediaPlayer.OnCompletionListener{

    private static final String TAG = "Player";

    private static volatile Player sInstance;

    private MediaPlayer mPlayer;

    private PlayList mPlayList;
    // Default size 2: for service and UI
    private List<Callback> mCallbacks = new ArrayList<>(2);

    // Player status
    public static int isPaused = -1;

    private Player() {
        mPlayer = new MediaPlayer();
        mPlayList = new PlayList();

        mPlayer.setOnCompletionListener(this);
    }

    public static Player getInstance() {
        if (sInstance == null) {
            synchronized (Player.class) {
                if (sInstance == null) {
                    sInstance = new Player();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void setPlayList(PlayList list) {
        if (list == null) {
            list = new PlayList();
        }
        mPlayList = list;
    }

    @Override
    public boolean play() {
        if (isPaused == 1) {
            Log.e(TAG, "stop play boldi");
            isPaused =0;
            mPlayer.start();
            notifyPlayStatusChanged(true);
            return true;
        }
        if (mPlayList.prepare()) {

            try {
                final Song song = mPlayList.getCurrentSong();
                mPlayer.reset();
                AssetFileDescriptor songer = M.getInstance().getApplicationContext().getAssets().openFd(song.getPath());
                mPlayer.setDataSource(songer.getFileDescriptor(),songer.getStartOffset(),songer.getLength());

                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        Log.d("DALBAN",mediaPlayer.getCurrentPosition()+"");
                        setDuration(mediaPlayer.getDuration());
                        mediaPlayer.start();

                        notifyPlayStatusChanged(true);
                    }
                });
//                mPlayer.start();
            } catch (IOException e) {
                Log.e(TAG, "play: ", e);
                notifyPlayStatusChanged(false);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void setAlarm() {
        int timer = Preferences.getInstance().getTimerId();
        Log.d("PlaybackService","timer id : " + timer);
        if (timer != 0) {
            AlarmManager am = (AlarmManager) M.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(M.getInstance().getApplicationContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(M.getInstance().getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            am.cancel(pendingIntent);
            long time = System.currentTimeMillis() + Const.timer.get(timer);

            am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        }
    }

    @Override
    public boolean play(PlayList list) {
        if (list == null) return false;

        isPaused = 0;
        setPlayList(list);
        return play();
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        if (list == null || startIndex < 0 || startIndex >= list.getNumOfSongs()) return false;

        isPaused = 0;
        list.setPlayingIndex(startIndex);
        setPlayList(list);
        return play();
    }

    @Override
    public boolean play(Song song) {
        if (song == null) return false;

        isPaused = 0;
        mPlayList.getSongs().clear();
        mPlayList.getSongs().add(song);
        return play();
    }

    @Override
    public boolean playLast() {
        isPaused = 0;
        boolean hasLast = mPlayList.hasLast();
        if (hasLast) {
            Song last = mPlayList.last();
            play();
            notifyPlayLast(last);
            return true;
        }
        return false;
    }

    @Override
    public boolean playNext() {
        isPaused = 0;
        boolean hasNext = mPlayList.hasNext(false);
        if (hasNext) {
            Song next = mPlayList.next();
            play();
            notifyPlayNext(next);
            return true;
        }
        return false;
    }

    @Override
    public boolean pause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            isPaused = 1;
            notifyPlayStatusChanged(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getCurrentPosition();
    }

    @Nullable
    @Override
    public Song getPlayingSong() {
        return mPlayList.getCurrentSong();
    }

    @Override
    public boolean seekTo(int progress) {
        if (mPlayList.getSongs().isEmpty()) return false;

        Song currentSong = mPlayList.getCurrentSong();
        if (currentSong != null) {
            if (currentSong.getDuration() <= progress) {
                onCompletion(mPlayer);
            } else {
                mPlayer.seekTo(progress);
            }
            return true;
        }
        return false;
    }


    private int duration;
    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        mPlayList.setPlayMode(playMode);
    }

    // Listeners

    @Override
    public void onCompletion(MediaPlayer mp) {

        Song next = null;
        // There is only one limited play mode which is list, player should be stopped when hitting the list end
        if (mPlayList.getPlayMode() == PlayMode.LIST && mPlayList.getPlayingIndex() == mPlayList.getNumOfSongs() - 1) {

            // In the end of the list
            // Do nothing, just deliver the callback
        } else if (mPlayList.getPlayMode() == PlayMode.SINGLE) {

            next = mPlayList.getCurrentSong();
            play();

        } else {

            boolean hasNext = mPlayList.hasNext(true);
            if (hasNext) {


                next = mPlayList.next();

                play();



            }
        }
        notifyComplete(next);
    }

    @Override
    public void releasePlayer() {
        mPlayList = null;
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        sInstance = null;
    }

    // Callbacks

    @Override
    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
        mCallbacks.clear();
    }

    private void notifyPlayStatusChanged(boolean isPlaying) {
        for (Callback callback : mCallbacks) {
            callback.onPlayStatusChanged(isPlaying);
        }
    }

    private void notifyPlayLast(Song song) {
        for (Callback callback : mCallbacks) {
            callback.onSwitchLast(song);
        }
    }

    private void notifyPlayNext(Song song) {
        for (Callback callback : mCallbacks) {
            callback.onSwitchNext(song);
        }
    }

    private void notifyComplete(Song song) {
        for (Callback callback : mCallbacks) {
            callback.onComplete(song);
        }
    }


}
