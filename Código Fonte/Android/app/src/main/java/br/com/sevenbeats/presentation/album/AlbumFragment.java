package br.com.sevenbeats.presentation.album;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.presentation.player.PlayerActivity;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.annotation.Reflection;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

@MvcPattern("View")
public class AlbumFragment extends Fragment implements OnAdapterItemClickListener {

    public static AlbumFragment newInstance(String idAlbum) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(AlbumConstants.EXTRA_ALBUM_ID, idAlbum);
        fragment.setArguments(args);
        return fragment;
    }

    private String mExtras;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExtras = getArguments().getString(AlbumConstants.EXTRA_ALBUM_ID);
        }
    }

    @InjectView(R.id.album_list) RecyclerView recyclerView;
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View albumView = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.inject(this, albumView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(hasExtras()){
            initRequests();
        }

        return albumView;
    }


    private boolean hasExtras(){
        return  mExtras != null;
    }

    private String getExtras(){
        return mExtras;
    }

    public void initRequests(){
        AlbumController controller = new AlbumController(getActivity(), this);
        controller.onRequest(AlbumConstants.METHOD_ON_BIND_VIEW, getExtras());
    }

    @SuppressWarnings("unused")
    @Reflection("reflection") public void onBindView(Album album){
        recyclerView.setAdapter(new AlbumAdapter(album, this));
    }

    @SuppressWarnings("unused")
    @Reflection("reflection") public void onError(){
        Toast.makeText(getActivity(), "onBindView error" , Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(Object data, int position) {
        if(data instanceof List){
            ArrayList<Song> song = (ArrayList<Song>) data;
            startActivity(new Intent(getActivity(), PlayerActivity.class).putParcelableArrayListExtra(PlayerActivity.EXTRA_PLAYLIST, song));
        }
    }
}
