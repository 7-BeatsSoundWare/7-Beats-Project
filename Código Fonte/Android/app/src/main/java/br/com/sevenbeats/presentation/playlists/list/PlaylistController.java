package br.com.sevenbeats.presentation.playlists.list;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.service.ServiceManager;
import br.com.sevenbeats.utils.mvc.base.SuperController;

@MvcPattern("Controller") public class PlaylistController extends SuperController {

    private Handler backgroundHandler;

    public PlaylistController(Context context, PlaylistFragment fragment){
        super(context, fragment);
        HandlerThread secondaryThread = new HandlerThread("Background Handler");
        secondaryThread.start();
        this.backgroundHandler =  new Handler(secondaryThread.getLooper());
    }

    @Override
    public void onRequest(final String method, final Object... params) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Object object = null;

                    switch (method) {
                        case PlaylistConstants.ON_LOAD_PLAYLISTS:
                            object = ServiceManager.getInstance().build()
                                    .getPlaylistService().getListPlaylistByIdUser(params[0].toString());
                            break;
                        case PlaylistConstants.ON_RENAME_PLAYLIST:
                            object =  ServiceManager.getInstance().build()
                                    .getPlaylistService().renamePlaylist(params[0].toString(), params[1].toString());
                            break;
                    }

                    onResult(method, object);

                } catch (Exception e) {
                    onError(PlaylistConstants.METHOD_ON_ERROR);
                    e.printStackTrace();
                }
            }
        });
    }


}
