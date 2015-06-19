package br.com.sevenbeats.presentation.album.detail;

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
import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.presentation.player.PlayerActivity;
import br.com.sevenbeats.presentation.player.PlayerConstants;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.annotation.Reflection;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

@MvcPattern("View")
public class AlbumDetailFragment extends Fragment implements OnAdapterItemClickListener {

    //TESTE
    public boolean created;
    public boolean started;
    public boolean viewCreated;
    //ENDTESTE

    public static AlbumDetailFragment newInstance(String idAlbum) {
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle args = new Bundle();
        args.putString(AlbumDetailConstants.EXTRA_ALBUM_ID, idAlbum);
        fragment.setArguments(args);
        return fragment;
    }

    private String mExtras;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExtras = getArguments().getString(AlbumDetailConstants.EXTRA_ALBUM_ID);
        }
        created = true;
    }

    @InjectView(R.id.album_list) RecyclerView recyclerView;
    @InjectView(R.id.album_loading) ProgressBar loading;
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
        AlbumDetailController controller = new AlbumDetailController(getActivity(), this);
        controller.onRequest(AlbumDetailConstants.METHOD_ON_BIND_VIEW, getExtras());
    }

    private void setLoading(boolean isLoading){
        loading.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @SuppressWarnings("unused")
    @Reflection("reflection") public void onBindView(Album album){
        setLoading(false);
        recyclerView.setAdapter(new AlbumDetailAdapter(album, this));
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
            startActivity(new Intent(getActivity(), PlayerActivity.class).putParcelableArrayListExtra(PlayerConstants.EXTRA_PLAYLIST, song));
        }
    }

    public static final int ALBUM_FRAGMENT_ID = 2;
}
