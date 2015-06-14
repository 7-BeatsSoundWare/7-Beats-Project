package br.com.sevenbeats.presentation.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by diogojayme on 6/8/15.
 */
public class SearchAdapter extends RecyclerView.Adapter {

    List<Album> albums;
    OnAdapterItemClickListener listener;

    public SearchAdapter(OnAdapterItemClickListener listener){
        this.listener = listener;
    }

    public void setData(List<Album> albums){
        this.albums = albums;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchHolder searchHolder = (SearchHolder) holder;
        Album album = getItem(position);
        searchHolder.albumName.setText(album.getName());
        searchHolder.cover.setOnClickListener(new CustomClickListener(listener, album, position));
        Picasso.with(searchHolder.itemView.getContext()).load(album.getImageUrl()).into(searchHolder.cover);
        searchHolder.artist.setText(album.getAutor() == null ? "Artista não encontrado" : getItem(position).getAutor());
    }

    private Album getItem(int position){
        if(albums == null || position > albums.size()){
            throw  new ArrayIndexOutOfBoundsException("você está acessando um indice invalido dessa lista");
        }

        return albums.get(position);
    }

    @Override
    public int getItemCount() {
        return albums == null ? 0 : albums.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class CustomClickListener implements View.OnClickListener{

        private Album album;
        private int position;
        private OnAdapterItemClickListener listener;

        public CustomClickListener(OnAdapterItemClickListener listener, Album album, int position){
            this.album = album;
            this.position = position;
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            this.listener.onItemClick(album, position);
        }
    }

    public class SearchHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.search_artist_name) TextView artist;
        @InjectView(R.id.search_album_cover) ImageView cover;
        @InjectView(R.id.search_album_name) TextView albumName;

        public SearchHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
