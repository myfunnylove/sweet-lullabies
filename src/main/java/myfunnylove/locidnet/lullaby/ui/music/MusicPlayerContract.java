package myfunnylove.locidnet.lullaby.ui.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import myfunnylove.locidnet.lullaby.data.model.Song;
import myfunnylove.locidnet.lullaby.player.PlayMode;
import myfunnylove.locidnet.lullaby.player.PlaybackService;
import myfunnylove.locidnet.lullaby.ui.base.BasePresenter;
import myfunnylove.locidnet.lullaby.ui.base.BaseView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/12/16
 * Time: 8:27 AM
 * Desc: MusicPlayerContract
 */
/* package */ interface MusicPlayerContract {

    interface View extends BaseView<Presenter> {

        void handleError(Throwable error);

        void onPlaybackServiceBound(PlaybackService service);

        void onPlaybackServiceUnbound();

        void onSongSetAsFavorite(@NonNull Song song);

        void onSongUpdated(@Nullable Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

        void updateFavoriteToggle(boolean favorite);
    }

    interface Presenter extends BasePresenter {

        void retrieveLastPlayMode();

        void setSongAsFavorite(Song song, boolean favorite);

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}
