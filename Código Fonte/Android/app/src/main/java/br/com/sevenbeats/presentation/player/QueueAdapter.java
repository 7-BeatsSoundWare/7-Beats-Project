package br.com.sevenbeats.presentation.player;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterOptionItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by diogojayme on 6/11/15.
 */
public class QueueAdapter extends RecyclerView.Adapter {

    private List<Song> songs;
    private OnAdapterOptionItemClickListener listener;
    public void setData(List<Song> data, OnAdapterOptionItemClickListener listener){
        this.songs = data;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QueueHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_queue, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QueueHolder queueHolder = (QueueHolder) holder;
        Song song = songs.get(position);
        queueHolder.artist.setText(song.getArtist());
        queueHolder.songName.setText(song.getName());
        queueHolder.options.setOnClickListener(new CustomClickListener(listener, song, position));
        queueHolder.songBtn.setOnClickListener(new CustomClickListener(listener, song, position));
        Picasso.with(queueHolder.itemView.getContext()).load(song.getAlbum().getImageUrl()).into(queueHolder.cover);
    }

    public class CustomClickListener implements View.OnClickListener{

        private Song song;
        private int position;
        private OnAdapterOptionItemClickListener listener;

        public CustomClickListener(OnAdapterOptionItemClickListener listener, Song song, int position){
            this.song = song;
            this.position = position;
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.queue_play_btn) {
                this.listener.onItemClick(song, position);
            }else {
                this.listener.onOptionItemClick(song, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }

    public class QueueHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.queue_song) TextView songName;
        @InjectView(R.id.queue_artist) TextView artist;
        @InjectView(R.id.queue_play_btn) ImageButton songBtn;
        @InjectView(R.id.queue_album_cover) ImageView cover;
        @InjectView(R.id.queue_options) ImageButton options;

        public QueueHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
