package br.com.sevenbeats.presentation.playlists.list;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.playlist.Playlist;
import br.com.sevenbeats.core.user.MockUser;
import br.com.sevenbeats.presentation.playlists.detail.PlaylistDetailFragment;
import br.com.sevenbeats.utils.annotation.Reflection;
import br.com.sevenbeats.utils.dialogs.DialogRenamePlaylist;
import br.com.sevenbeats.utils.mvc.interfaces.view.ActivityCallBack;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterOptionItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlaylistFragment extends Fragment implements OnAdapterOptionItemClickListener {

    //TESTE
    public boolean created;
    public boolean started;
    public boolean viewCreated;
    //ENDTESTE

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    PlayListAdapter mAdapter;
    Playlist mSelectedPlaylist;
    ActivityCallBack mCallBack;
    PlaylistController mController;
    @InjectView(R.id.playlist_list) RecyclerView mRecyclerView;
    @InjectView(R.id.playlist_loading) ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View playlistView =inflater.inflate(R.layout.fragment_playlist, container, false);
        ButterKnife.inject(this, playlistView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mController = new PlaylistController(getActivity(), this);
        mController.onRequest(PlaylistConstants.ON_LOAD_PLAYLISTS, new MockUser().getId());
        loading.setVisibility(View.VISIBLE);
        registerForContextMenu(mRecyclerView);
        return playlistView;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        created = true;
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

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(Object data, int position) {
        if(data instanceof Playlist){
            Playlist playlist = (Playlist) data;
            mCallBack.notifyFragmentChanged(playlist.getId(), PlaylistDetailFragment.PLAYLIST_DETAIL_ID, playlist.getName());
        }
    }

    @Override
    public void onOptionItemClick(Object data, int position) {
        mSelectedPlaylist = (Playlist) data;
        mRecyclerView.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Apagar playlist");
        menu.add("Renomear playlist");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle() == "Apagar playlist"){
//            mController.onRequest(PlaylistConstants.ON_DELETE_PLAYLIST);
        }else if(item.getTitle() == "Renomear playlist"){
            new DialogRenamePlaylist().show(getActivity().getFragmentManager(), null);
        }

        return super.onContextItemSelected(item);
    }

    @Reflection("onLoadPlaylists")
    public void onLoadPlaylists(ArrayList<Playlist> playlists){
        loading.setVisibility(View.INVISIBLE);
        mAdapter = new PlayListAdapter(this);
        mAdapter.setData(playlists);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Reflection("onAddSongPlaylist")
    public void onAddSongPlaylist(){

    }

    @Reflection("onDeleteSongPlaylist")
    public void onDeleteSongPlaylist(){

    }

    @Reflection("onRenamePlaylist")
    public void onRenamePlaylist(List<Playlist> playlistList){
        mAdapter.setData(playlistList);
    }

    @Reflection("onCreatePlaylist")
    public void onCreatePlaylist(){

    }
    @Reflection("onDeletePlaylist")
    public void onDeletePlaylist(){

    }

    public void onError(){
        System.out.println("Something wrong");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (ActivityCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ActivityCalback");
        }
    }

    public static final int PLAYLIST_FRAGMENT_ID = 1;

}
