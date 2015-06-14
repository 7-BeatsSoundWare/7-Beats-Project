package br.com.sevenbeats.presentation.main.drawer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.sevenbeats.R;
import br.com.sevenbeats.utils.mvc.interfaces.view.ActivityCallBack;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainDrawerFragment extends Fragment implements OnAdapterItemClickListener {
    @InjectView(R.id.drawer_list) RecyclerView recyclerView;

    ActivityCallBack mCallBack;


    public static MainDrawerFragment newInstance(){
        return new MainDrawerFragment();
    }
    public MainDrawerFragment(){}

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View drawer = inflater.inflate(R.layout.fragment_main_drawer, container, false);
        ButterKnife.inject(this, drawer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        staticAdapterSet();
        return drawer;
    }

    private void staticAdapterSet(){
        recyclerView.setAdapter(new DrawerAdapter(this));
    }

    @Override
    public void onItemClick(Object data, int position) {
        mCallBack.notifyFragmentChanged(data, position);
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
