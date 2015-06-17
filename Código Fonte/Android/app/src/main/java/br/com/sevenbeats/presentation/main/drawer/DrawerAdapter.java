package br.com.sevenbeats.presentation.main.drawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.sevenbeats.R;
import br.com.sevenbeats.utils.mvc.interfaces.view.OnAdapterItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by diogojayme on 6/10/15.
 */
public class DrawerAdapter extends RecyclerView.Adapter {

    OnAdapterItemClickListener listener;

    public DrawerAdapter(OnAdapterItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DrawerHolder drawerHolder = (DrawerHolder) holder;

        switch (position){
            case 0:
                drawerHolder.drawerIcon.setImageResource(R.drawable.ic_action_search);
                drawerHolder.drawerText.setText("Search");
                drawerHolder.drawerBtn.setOnClickListener(new CustomClickListener(listener, position));
                break;
            case 1:
                drawerHolder.drawerIcon.setImageResource(R.drawable.ic_action_av_my_library_music);
                drawerHolder.drawerText.setText("Playlists");
                drawerHolder.drawerBtn.setOnClickListener(new CustomClickListener(listener, position));
                break;
        }
    }

    public class CustomClickListener implements View.OnClickListener{

        private int position;
        private OnAdapterItemClickListener listener;

        public CustomClickListener(OnAdapterItemClickListener listener, int position){
            this.position = position;
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            this.listener.onItemClick(null, position);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class DrawerHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.drawer_text) TextView drawerText;
        @InjectView(R.id.drawer_icon) ImageView drawerIcon;
        @InjectView(R.id.drawer_btn) ImageButton drawerBtn;

        public DrawerHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
