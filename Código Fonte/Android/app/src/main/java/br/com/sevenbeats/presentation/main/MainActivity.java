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
import android.view.MenuItem;
import android.view.View;

import java.util.StringTokenizer;

import br.com.sevenbeats.R;
import br.com.sevenbeats.presentation.album.AlbumFragment;
import br.com.sevenbeats.presentation.player.PlayerActivity;
import br.com.sevenbeats.presentation.search.SearchFragment;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.mvc.interfaces.view.ActivityCallBack;
import butterknife.ButterKnife;
import butterknife.InjectView;

@MvcPattern("View")public class MainActivity extends AppCompatActivity implements ActivityCallBack {
    FragmentTransaction mTransaction;
    ActionBarDrawerToggle mDrawerToggle;
    @InjectView(R.id.main_toolbar) Toolbar mToolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
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
    @Override public void notifyFragmentChanged(Object data, int fragmentId) {
        // passando o ID do album por parametro pra outro fragmento
        mTransaction = getFragmentManager().beginTransaction();

        if(fragmentId == 1){
            AlbumFragment fragment = AlbumFragment.newInstance(data.toString());
            mTransaction.replace(R.id.main_fragment_container, fragment);
        }else if(fragmentId == 0 ){
            SearchFragment fragment = SearchFragment.newInstance();
            mTransaction.replace(R.id.main_fragment_container, fragment);
        }

        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    public static int albumFragmentId = 1;

    @Override public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
