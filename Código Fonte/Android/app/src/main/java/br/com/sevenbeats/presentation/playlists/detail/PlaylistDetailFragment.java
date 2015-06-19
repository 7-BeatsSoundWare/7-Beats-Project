package br.com.sevenbeats.presentation.playlists.detail;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.playlist.Playlist;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.presentation.player.PlayerActivity;
import br.com.sevenbeats.presentation.player.PlayerConstants;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.annotation.Reflection;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

@MvcPattern("View")
public class PlaylistDetailFragment extends Fragment implements OnAdapterItemClickListener {

    //TESTE
    public boolean created;
    public boolean started;
    public boolean viewCreated;
    //ENDTESTE

    public static PlaylistDetailFragment newInstance(String idAlbum) {
        PlaylistDetailFragment fragment = new PlaylistDetailFragment();
        Bundle args = new Bundle();
        args.putString(PlaylistDetailConstants.EXTRA_PLAYLIST_ID, idAlbum);
        fragment.setArguments(args);
        return fragment;
    }

    private String mExtras;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExtras = getArguments().getString(PlaylistDetailConstants.EXTRA_PLAYLIST_ID);
        }

        created = true;
    }

    @InjectView(R.id.playlist_detail_list) RecyclerView recyclerView;
    @InjectView(R.id.playlist_detail_loading) ProgressBar loading;
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View albumView = inflater.inflate(R.layout.fragment_detail_playlist, container, false);
        ButterKnife.inject(this, albumView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(hasExtras()){
            initRequests();
        }

        return albumView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewCreated  = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        started = true;
    }


    private boolean hasExtras(){
        return  mExtras != null;
    }

    private String getExtras(){
        return mExtras;
    }

    public void initRequests(){
        setLoading(true);
        PlaylistDetailController controller = new PlaylistDetailController(getActivity(), this);
        controller.onRequest(PlaylistDetailConstants.METHOD_ON_LOAD_PLAYLIST, getExtras());
    }

    private void setLoading(boolean isLoading){
        loading.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @SuppressWarnings("unused")
    @Reflection("onLoadPlaylist") public void onLoadPlaylist(Playlist playlist){
        setLoading(false);
        recyclerView.setAdapter(new PlaylistDetailAdapter(playlist, this));
    }

    @SuppressWarnings("unused")
    @Reflection("onError") public void onError(){
        Toast.makeText(getActivity(), "onBindView error" , Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(Object data, int position) {
        if(data instanceof List){
            ArrayList<Song> songs = (ArrayList<Song>) data;
            startActivity(new Intent(getActivity(), PlayerActivity.class).putParcelableArrayListExtra(PlayerConstants.EXTRA_PLAYLIST, songs));
        }
    }

    public static final int PLAYLIST_DETAIL_ID = 3;

}
