package myfunnylove.locidnet.lullaby.ui.local.folder;

import java.io.File;
import java.util.List;

import myfunnylove.locidnet.lullaby.data.model.Folder;
import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.ui.base.BasePresenter;
import myfunnylove.locidnet.lullaby.ui.base.BaseView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/10/16
 * Time: 11:34 PM
 * Desc: FolderContract
 */
/* package */ interface FolderContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void handleError(Throwable error);

        void onFoldersLoaded(List<Folder> folders);

        void onFoldersAdded(List<Folder> folders);

        void onFolderUpdated(Folder folder);

        void onFolderDeleted(Folder folder);

        void onPlayListCreated(PlayList playList);
    }

    interface Presenter extends BasePresenter {

        void loadFolders();

        void addFolders(List<File> folders, List<Folder> existedFolders);

        void refreshFolder(Folder folder);

        void deleteFolder(Folder folder);

        void createPlayList(PlayList playList);

        void addFolderToPlayList(Folder folder, PlayList playList);
    }
}
