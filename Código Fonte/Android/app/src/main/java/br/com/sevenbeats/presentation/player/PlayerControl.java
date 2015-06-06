package br.com.sevenbeats.presentation.player;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import br.com.sevenbeats.core.album.AlbumDAO;
import br.com.sevenbeats.core.album.AlbumDAOImpl;
import br.com.sevenbeats.core.song.SongDAO;
import br.com.sevenbeats.core.song.SongDAOImpl;
import br.com.sevenbeats.utils.mvc.interfaces.controller.Control;
import br.com.sevenbeats.utils.annotation.MvcPattern;
import br.com.sevenbeats.utils.mvc.communication.Invoker;
import br.com.sevenbeats.utils.internet.http.ErrorResponse;
import br.com.sevenbeats.utils.internet.exception.Constants;

@MvcPattern("Control") public class PlayerControl implements Control {

    private SongDAO songDAO;
    private AlbumDAO albumDAO;
    private PlayerActivity activity;
    public static String DEBUG_TAG = "SEVENBEATS";
    private Handler mainHandler, backgroundHandler;
    public PlayerControl(Context context){
        this.songDAO = new SongDAOImpl();
        this.albumDAO = new AlbumDAOImpl();
        this.activity = (PlayerActivity) context;
        this.mainHandler = new Handler(context.getMainLooper());
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
                        case PlayerActivity.onAlbum:
                            object = albumDAO.getAlbumById(params[0]);
                            break;
                        case PlayerActivity.onSongs:
                            object = songDAO.getSongById(params[0]);
                            break;
                        case PlayerActivity.onSongsList:
                            object = songDAO.getSongs();
                            break;
                    }

                    if(object == null) {
                        throw new Exception(Constants.EXCEPTION_4 + methodName);
                    }else if(object instanceof ErrorResponse || methodName.equals(PlayerActivity.onError)){
                        onError(PlayerActivity.onError, object);
                    }else{
                        onResult(methodName, object);
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                    Log.d(DEBUG_TAG, Constants.EXCEPTION_5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResult(final String methodName, final Object result) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyView(methodName, result);
            }
        });
    }

    @Override
    public void onError(final String method, final Object result) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyView(method, result);
            }
        });
    }

    @Override
    public void notifyView(String methodName, Object... params) {
        Invoker.invoke(activity, methodName, params);
    }

}
