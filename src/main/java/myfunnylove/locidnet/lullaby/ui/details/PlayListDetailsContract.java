package myfunnylove.locidnet.lullaby.ui.details;

import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.data.model.Song;
import myfunnylove.locidnet.lullaby.ui.base.BasePresenter;
import myfunnylove.locidnet.lullaby.ui.base.BaseView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/14/16
 * Time: 2:32 AM
 * Desc: PlayListDetailsContract
 */
public interface PlayListDetailsContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void handleError(Throwable e);

        void onSongDeleted(Song song);
    }

    interface Presenter extends BasePresenter {

        void addSongToPlayList(Song song, PlayList playList);

        void delete(Song song, PlayList playList);
    }
}
