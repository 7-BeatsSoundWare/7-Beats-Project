package br.com.sevenbeats.utils.internet;

import br.com.sevenbeats.core.album.AlbumService;
import br.com.sevenbeats.core.song.SongService;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by diogojayme on 6/9/15.
 */
public class ServiceManager {

    static ServiceManager manager;
    static RestAdapter restAdapter;

    public static ServiceManager getInstance(){
        return manager == null ? new ServiceManager() : manager;
    }

    public ServiceManager build(){
        if(restAdapter == null){
            restAdapter  = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setErrorHandler(new ErrorHandler())
                    .build();
        }

        return this;
    }

    public  SongService getSongService(){
        return restAdapter.create(SongService.class);
    }

    public  AlbumService getAlbumService(){
        return restAdapter.create(AlbumService.class);
    }

    private class ErrorHandler implements retrofit.ErrorHandler{
        @Override public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();

            if (r != null && r.getStatus() == 401) {
                return new Exception(cause);
            }

            return cause;
        }
    }

    public static void destroyInstance(){
        manager = null;
        System.gc();
    }

    private static final String url = "http://69.28.84.155:8080/7beats/";
}
