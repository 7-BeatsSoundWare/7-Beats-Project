package br.com.sevenbeats.presentation.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sevenbeats.R;
import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.utils.annotation.Reflection;
import br.com.sevenbeats.utils.mvc.interfaces.view.ActivityCallBack;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchFragment extends android.app.Fragment implements OnAdapterItemClickListener {

    @InjectView(R.id.search_dosearch) ImageButton doSearch;
    @InjectView(R.id.search_type_text) EditText searchField;
    @InjectView(R.id.search_list) RecyclerView recyclerView;

    SearchController controller;
    ActivityCallBack mCallBack;

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, searchView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SearchConstants.GRID_SIZE));
        controller = new SearchController(getActivity(), this);
        controller.onRequest(SearchConstants.METHOD_ON_START);
        return searchView;
    }

    /**
     * @param data Os dados vindos do adapter
     * @param position posição do item clicado
     * */
    @Override
    public void onItemClick(Object data, int position) {
        if(data instanceof Album){
            mCallBack.notifyFragmentChanged(((Album) data).getId(), 1);
        }
    }

    /**
     * CSU 1 Pesquisar música - requisição
     *
     * */
    @SuppressWarnings("unused")
    @OnClick(R.id.search_dosearch) public void searchClick(){
        String search = searchField.getText().toString();
        if(search.length() != 0) {
            controller.onRequest(SearchConstants.METHOD_ON_SEARCH, search);
        }
    }

    /**
     * CSU 1 Pesquisar música - resposta
     *
     * */
    @Reflection("onSearch")
    @SuppressWarnings("unused")
    public void onSearch(ArrayList<Album> albumList){
        SearchAdapter adapter = new SearchAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(albumList);

    }

    @Reflection("onStart")
    @SuppressWarnings("unused")
    public void onStart(ArrayList<Album> albumList){
        SearchAdapter adapter = new SearchAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(albumList);

    }
    @Reflection("onError")
    public void onError(){
        Toast.makeText(getActivity(), "OnError", Toast.LENGTH_LONG).show();
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
}
