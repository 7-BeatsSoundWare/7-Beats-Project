package br.com.sevenbeats.presentation.player;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.ArrayList;

import br.com.sevenbeats.core.song.Song;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.internet.ServiceManager;
import br.com.sevenbeats.utils.mvc.base.SuperController;

@MvcPattern("Control") public class PlayerController extends SuperController {


    private Handler backgroundHandler;
    public PlayerController(Context context){
        super(context);
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
                        case PlayerConstants.METHOD_ON_SONGS:
                            Song song = ServiceManager.getInstance().build().getSongService().songWAlbum(params[0]);
                            ArrayList<Song> songs = new ArrayList<>();
                            songs.add(song);
                            object = songs;
                            break;
                    }

                    onResult(methodName, object);
                }catch (Exception e) {
                    onError(PlayerConstants.METHOD_ON_ERROR);
                    e.printStackTrace();
                }
            }
        });
    }

}
