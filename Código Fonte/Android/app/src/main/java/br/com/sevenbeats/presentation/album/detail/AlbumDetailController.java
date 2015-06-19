package br.com.sevenbeats.presentation.album.detail;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.service.ServiceManager;
import br.com.sevenbeats.utils.mvc.base.SuperController;

@MvcPattern("Controller") public class AlbumDetailController extends SuperController {

    private Handler backgroundHandler;

    public AlbumDetailController(Context context, AlbumDetailFragment fragment){
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
                        case AlbumDetailConstants.METHOD_ON_BIND_VIEW:
                            object = ServiceManager.getInstance().build().getAlbumService().album(params[0]);
                            break;
                    }

                    onResult(methodName, object);
                }catch (Exception e) {
                    onError(AlbumDetailConstants.METHOD_ON_ERROR);
                    e.printStackTrace();
                }
            }
        });
    }
}
