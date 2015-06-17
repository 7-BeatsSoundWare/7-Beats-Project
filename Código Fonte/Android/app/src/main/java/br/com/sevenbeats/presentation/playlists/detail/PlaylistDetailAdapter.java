package br.com.sevenbeats.presentation.playlists.detail;

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
import br.com.sevenbeats.core.playlist.Playlist;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by diogojayme on 6/11/15.
 */
public class PlaylistDetailAdapter extends RecyclerView.Adapter {

    private int HEADER = 1;
    private int NORMAL = 2;

    private List<Song> songs;

    private OnAdapterItemClickListener listener;

    public PlaylistDetailAdapter(Playlist playlist, OnAdapterItemClickListener listener){
        this.listener = listener;
        this.songs = playlist.getSongs();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER){
            return new HeaderDetailPlaylistHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_playlist_header, parent, false));
        }else{
            return new PlaylistDetailHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_playlist, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            HeaderDetailPlaylistHolder headerHolder = (HeaderDetailPlaylistHolder) holder;
            Picasso.with(headerHolder.itemView.getContext()).load(songs.get(position).getAlbum().getImageUrl()).into(headerHolder.cover);
        }else{
            PlaylistDetailHolder playlistDetailHolder = (PlaylistDetailHolder) holder;
            int aPosition = hasHeader() ? position - 1 : position;
            playlistDetailHolder.songName.setText(songs.get(aPosition).getName());
            playlistDetailHolder.songBtn.setOnClickListener(new CustomClickListener(listener, songs, aPosition));
            Picasso.with(playlistDetailHolder.itemView.getContext()).load(songs.get(aPosition).getAlbum().getImageUrl()).into(playlistDetailHolder.cover);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : NORMAL;
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : hasHeader() ? (songs.size() + 1) : songs.size();
    }

    private boolean hasHeader(){
        return HEADER == 1;
    }

    public class CustomClickListener implements View.OnClickListener{

        private int position;
        private List<Song> songs;
        private OnAdapterItemClickListener listener;

        public CustomClickListener(OnAdapterItemClickListener listener, List<Song> songs, int position){
            this.songs = songs;
            this.position = position;
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            this.listener.onItemClick(songs, position);
        }
    }

    public class HeaderDetailPlaylistHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.playlist_detail_header_cover) ImageView cover;

        public HeaderDetailPlaylistHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public class PlaylistDetailHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.playlist_detail_image) ImageView cover;
        @InjectView(R.id.playlist_detail_name) TextView songName;
        @InjectView(R.id.playlist_detail_btn) ImageButton songBtn;

        public PlaylistDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
