package br.com.sevenbeats.presentation.main;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.StringTokenizer;

import br.com.sevenbeats.R;
import br.com.sevenbeats.presentation.album.detail.AlbumDetailFragment;
import br.com.sevenbeats.presentation.player.PlayerActivity;
import br.com.sevenbeats.presentation.playlists.detail.PlaylistDetailFragment;
import br.com.sevenbeats.presentation.playlists.list.PlaylistFragment;
import br.com.sevenbeats.presentation.search.SearchFragment;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.mvc.interfaces.view.ActivityCallBack;
import butterknife.ButterKnife;
import butterknife.InjectView;

@MvcPattern("View")public class MainActivity extends AppCompatActivity implements ActivityCallBack {

    public Menu menu;
    public ActionBar mActionBar;
    public FragmentTransaction mTransaction;
    public ActionBarDrawerToggle mDrawerToggle;
    public @InjectView(R.id.main_toolbar) Toolbar mToolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                syncState();
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        if(mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        initFragment();
        needRedirect();
    }

    private void needRedirect(){
        Intent intent = getIntent();
        String str = intent.getDataString();

        if(str != null) {
            Uri uri = Uri.parse(str);
            String path = uri.getPath();
            StringTokenizer stringTokenizer = new StringTokenizer(path, "/");

            String[] tokens = new String[stringTokenizer.countTokens()];
            int i = 0;

            while (stringTokenizer.hasMoreElements()){
                tokens[i] =  stringTokenizer.nextElement().toString();
                i++;
            }

            String params = null;

            for (int j = 0; j < tokens.length; j++) {
                if(tokens[j].equals(MainConstants.EXTRA_MUSIC)){
                    if(tokens[j+1].equals(MainConstants.EXTRA_ID)){
                        params = tokens[j+2];
                    }
                }
            }

            startActivity(new Intent(this, PlayerActivity.class).putExtra(MainConstants.EXTRA_ID, params));
        }
    }

    @SuppressLint("CommitTransaction")
    private void initFragment(){
        mTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = SearchFragment.newInstance();
        mTransaction.add(R.id.main_fragment_container, fragment);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @SuppressLint("CommitTransaction")
    @Override public void notifyFragmentChanged(Object data, int fragmentId, String activityTitle) {
        setActivityTitle(activityTitle);

        mTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = null;
        boolean show = false;

        if(fragmentId == AlbumDetailFragment.ALBUM_FRAGMENT_ID){
            fragment = AlbumDetailFragment.newInstance(data.toString());
        }else if(fragmentId == SearchFragment.SEARCH_FRAGMENT_ID){
            fragment = SearchFragment.newInstance();
        }else if(fragmentId == PlaylistFragment.PLAYLIST_FRAGMENT_ID){
            fragment = PlaylistFragment.newInstance();
            show = true;
        }else if(fragmentId == PlaylistDetailFragment.PLAYLIST_DETAIL_ID){
            fragment = PlaylistDetailFragment.newInstance(data.toString());
        }

        onMenuChanged(show, R.id.action_playlist);

        mTransaction.replace(R.id.main_fragment_container, fragment);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    public void setActivityTitle(String title){
        if(title != null && mActionBar != null){
            mActionBar.setTitle(title);
        }
    }

    public void onMenuChanged(boolean show, int id){
        MenuItem item = menu.findItem(id);
        item.setVisible(show);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
