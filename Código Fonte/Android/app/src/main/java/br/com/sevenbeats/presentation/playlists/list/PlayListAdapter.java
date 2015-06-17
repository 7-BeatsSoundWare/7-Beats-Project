package br.com.sevenbeats.presentation.playlists.list;

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
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterOptionItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by diogojayme on 6/15/15.
 */
public class PlayListAdapter extends RecyclerView.Adapter {

    final OnAdapterOptionItemClickListener listener;

    public PlayListAdapter(OnAdapterOptionItemClickListener listener){
        this.listener = listener;
    }

    private List<Playlist> playlists;

    public void setData(List<Playlist> playlists){
        this.playlists = playlists;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlaylistHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlaylistHolder playlistHolder = (PlaylistHolder) holder;
        Playlist playlist = playlists.get(position);
        playlistHolder.name.setText(playlist.getName());
        playlistHolder.totalSongs.setText(playlist.getQtdMusicas() + " songs");
        CustomClickListener listener = new CustomClickListener(playlist, position);
        playlistHolder.open.setOnClickListener(listener);
        playlistHolder.options.setOnClickListener(listener);
        Picasso.with(holder.itemView.getContext()).load(playlist.getUrl()).into(playlistHolder.image);
    }

    public class CustomClickListener implements View.OnClickListener{
        int position;
        Playlist playlist;

        public CustomClickListener(Playlist playlist, int position){
            this.playlist = playlist;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.playlist_open_btn){
                listener.onItemClick(playlist, position);
            }else{
                listener.onOptionItemClick(playlist, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class PlaylistHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.playlist_name) TextView name;
        @InjectView(R.id.playlist_image) ImageView image;
        @InjectView(R.id.playlist_open_btn) ImageButton open;
        @InjectView(R.id.playlist_options) ImageButton options;
        @InjectView(R.id.playlist_total_songs) TextView totalSongs;
        public PlaylistHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
