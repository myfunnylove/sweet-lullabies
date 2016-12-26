package myfunnylove.locidnet.lullaby.ui.local.all;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfunnylove.locidnet.lullaby.R;
import myfunnylove.locidnet.lullaby.data.model.Song;
import myfunnylove.locidnet.lullaby.ui.base.adapter.IAdapterView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 5:58 PM
 * Desc: LocalMusicItemView
 */
public class LocalMusicItemView extends RelativeLayout implements IAdapterView<Song> {

    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view_artist)
    TextView textViewArtist;
    @BindView(R.id.text_view_duration)
    TextView textViewDuration;

    public LocalMusicItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.item_local_music, this);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(Song song, int position) {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/ComicRelief.ttf");
        textViewName.setText(song.getDisplayName());
        textViewName.setTypeface(typeface);
        textViewArtist.setText(song.getArtist());
        textViewArtist.setTypeface(typeface);

//        Log.d("DURATION@",TimeUtils.formatDuration(song.getDuration())+"");
        textViewDuration.setText("");
//        textViewDuration.setText(TimeUtils.formatDuration(song.getDuration()));
    }
}
