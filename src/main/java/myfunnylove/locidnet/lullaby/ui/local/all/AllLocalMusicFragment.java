package myfunnylove.locidnet.lullaby.ui.local.all;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfunnylove.locidnet.lullaby.R;
import myfunnylove.locidnet.lullaby.RxBus;
import myfunnylove.locidnet.lullaby.data.model.PlayList;
import myfunnylove.locidnet.lullaby.data.model.Song;
import myfunnylove.locidnet.lullaby.data.source.AppRepository;
import myfunnylove.locidnet.lullaby.event.PlayListNowEvent;
import myfunnylove.locidnet.lullaby.event.PlayListUpdatedEvent;
import myfunnylove.locidnet.lullaby.ui.base.BaseFragment;
import myfunnylove.locidnet.lullaby.ui.base.adapter.OnItemClickListener;
import myfunnylove.locidnet.lullaby.ui.common.DefaultDividerDecoration;
import myfunnylove.locidnet.lullaby.ui.music.MusicPlayerPresenter;
import myfunnylove.locidnet.lullaby.ui.widget.RecyclerViewFastScroller;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/1/16
 * Time: 9:58 PM
 * Desc: LocalFilesFragment
 */
public class AllLocalMusicFragment extends BaseFragment implements LocalMusicContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fast_scroller)
    RecyclerViewFastScroller fastScroller;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.text_view_empty)
    View emptyView;

    LocalMusicAdapter mAdapter;
    LocalMusicContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_local_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new LocalMusicAdapter(getActivity(), null);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Song song = mAdapter.getItem(position);
//                RxBus.getInstance().post(new PlaySongEvent(song));

                PlayList playList =PlayList.fromLocalPlaylist(getString(R.string.mp_local_files_segmented_all),mAdapter.getData(),mAdapter.getData().size(),position);


                Log.e("Song in init playlist", playList.getNumOfSongs()+" total");

                RxBus.getInstance().post(new PlayListNowEvent(playList, position));
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DefaultDividerDecoration());

        fastScroller.setRecyclerView(recyclerView);

        new LocalMusicPresenter(AppRepository.getInstance(), this).subscribe();


    }

    // RxBus Events

    @Override
    protected Subscription subscribeEvents() {

        return RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof PlayListUpdatedEvent) {
                            mPresenter.loadLocalMusic();
                        }
                    }
                })
                .subscribe();
    }

    // MVP View

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void emptyView(boolean visible) {
        emptyView.setVisibility(visible ? View.VISIBLE : View.GONE);
        fastScroller.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @Override
    public void handleError(Throwable error) {
       // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocalMusicLoaded(List<Song> songs) {

        mAdapter.setData(songs);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(LocalMusicContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
