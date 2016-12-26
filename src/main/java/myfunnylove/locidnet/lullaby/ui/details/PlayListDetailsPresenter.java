package myfunnylove.locidnet.lullaby.ui.details;

import myfunnylove.locidnet.lullaby.RxBus;
import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.data.model.Song;
import myfunnylove.locidnet.lullaby.data.source.AppRepository;
import myfunnylove.locidnet.lullaby.event.PlayListUpdatedEvent;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/14/16
 * Time: 2:35 AM
 * Desc: PlayListDetailsPresenter
 */
public class PlayListDetailsPresenter implements PlayListDetailsContract.Presenter {

    private PlayListDetailsContract.View mView;
    private AppRepository mRepository;
    private CompositeSubscription mSubscriptions;

    public PlayListDetailsPresenter(AppRepository repository, PlayListDetailsContract.View view) {
        mView = view;
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        // Nothing to do
    }

    @Override
    public void unsubscribe() {
        mView = null;
        mSubscriptions.clear();
    }

    @Override
    public void addSongToPlayList(Song song, PlayList playList) {
        if (playList.isFavorite()) {
            song.setFavorite(true);
        }
        playList.addSong(song, 0);
        Subscription subscription = mRepository.update(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PlayList>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(PlayList playList) {
                        RxBus.getInstance().post(new PlayListUpdatedEvent(playList));
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void delete(final Song song, PlayList playList) {
        playList.removeSong(song);
        Subscription subscription = mRepository.update(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PlayList>() {
                    @Override
                    public void onStart() {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(PlayList playList) {
                        mView.onSongDeleted(song);
                        RxBus.getInstance().post(new PlayListUpdatedEvent(playList));
                    }
                });
        mSubscriptions.add(subscription);
    }
}
