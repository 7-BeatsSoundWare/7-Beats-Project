package br.com.sevenbeats.presentation.album;

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
import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by diogojayme on 6/11/15.
 */
public class AlbumAdapter extends RecyclerView.Adapter {

    private int HEADER = 1;
    private int NORMAL = 2;

    private Album album;
    private List<Song> songs;

    private OnAdapterItemClickListener listener;

    public AlbumAdapter(Album album, OnAdapterItemClickListener listener){
        this.album = album;
        this.songs = album.getMusicas();

        for (int i = 0; i < songs.size(); i++) {
            Album tmpAlbum = new Album();
            tmpAlbum.setId(album.getId());
            tmpAlbum.setImageUrl(album.getImageUrl());
            songs.get(i).setAlbum(tmpAlbum);
        }

        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL){
            return new AlbumHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false));
        }else{
            return new HeaderAlbumHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            HeaderAlbumHolder headerHolder = (HeaderAlbumHolder) holder;
            Picasso.with(headerHolder.itemView.getContext()).load(album.getImageUrl()).into(headerHolder.cover);
        }else{
            AlbumHolder albumHolder = (AlbumHolder) holder;
            albumHolder.songName.setText(songs.get(hasHeader() ? position - 1 : position).getName());
            albumHolder.songBtn.setOnClickListener(new CustomClickListener(listener, songs, position));
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

    public class HeaderAlbumHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.album_header_cover) ImageView cover;

        public HeaderAlbumHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public class AlbumHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.album_song_name) TextView songName;
        @InjectView(R.id.album_song_btn) ImageButton songBtn;

        public AlbumHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
