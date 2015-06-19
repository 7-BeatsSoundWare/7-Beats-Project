package br.com.sevenbeats.presentation.search;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.ArrayList;

import br.com.sevenbeats.core.album.Album;
import br.com.sevenbeats.utils.service.ServiceManager;
import br.com.sevenbeats.utils.mvc.base.SuperController;

/**
 * Created by diogojayme on 6/5/15.
 */
public class SearchController extends SuperController {
    private Handler backgroundHandler;

    public SearchController(Context context, SearchFragment fragment){
        super(context,fragment);
        HandlerThread secondaryThread = new HandlerThread("Background Handler");
        secondaryThread.start();
        this.backgroundHandler =  new android.os.Handler(secondaryThread.getLooper());
    }

    @Override
    public void onRequest(final String methodName, final String... params){
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Album> albums = null;

                    switch (methodName) {
                        case SearchConstants.METHOD_ON_SEARCH:
                            albums = ServiceManager.getInstance().build().getAlbumService().listAlbums(params[0]);
                            break;
                        case SearchConstants.METHOD_ON_START:
                            albums = ServiceManager.getInstance().build().getAlbumService().listAlbums();
                            break;
                    }

                    onResult(methodName, albums);
                } catch (Exception e) {
                    onError(SearchConstants.METHOD_ON_ERROR);
                    e.printStackTrace();
                }

                backgroundHandler.removeCallbacks(this);
            }
        });
    }

}
