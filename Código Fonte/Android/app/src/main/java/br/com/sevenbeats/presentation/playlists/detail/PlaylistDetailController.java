package br.com.sevenbeats.presentation.playlists.detail;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.service.ServiceManager;
import br.com.sevenbeats.utils.mvc.base.SuperController;

@MvcPattern("Controller") public class PlaylistDetailController extends SuperController {

    private Handler backgroundHandler;

    public PlaylistDetailController(Context context, PlaylistDetailFragment fragment){
        super(context, fragment);
        HandlerThread secondaryThread = new HandlerThread("Background Handler");
        secondaryThread.start();
        this.backgroundHandler =  new Handler(secondaryThread.getLooper());
    }

    @Override
    public void onRequest(final String methodName, final String... params){
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    Object object = null;
                    switch (methodName) {
                        case PlaylistDetailConstants.METHOD_ON_LOAD_PLAYLIST:
                            object = ServiceManager.getInstance().build().getPlaylistService().getPlaylistByIdWMusicas(params[0]);
                            break;
                    }

                    onResult(methodName, object);
                }catch (Exception e) {
                    onError(PlaylistDetailConstants.METHOD_ON_ERROR);
                    e.printStackTrace();
                }
            }
        });
    }
}
