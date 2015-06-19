package br.com.sevenbeats.utils.service;

import br.com.sevenbeats.core.album.AlbumService;
import br.com.sevenbeats.core.playlist.PlaylistService;
import br.com.sevenbeats.core.song.SongService;
import br.com.sevenbeats.core.user.UserService;
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
        if(restAdapter == null){
            throw  new NullPointerException("You need call build before get your service.");
        }
        return restAdapter.create(SongService.class);
    }

    public PlaylistService getPlaylistService(){
        if(restAdapter == null){
            throw  new NullPointerException("You need call build before get your service.");
        }
        return restAdapter.create(PlaylistService.class);
    }


    public UserService getUserService(){
        if(restAdapter == null){
            throw  new NullPointerException("You need call build before get your service.");
        }
        return restAdapter.create(UserService.class);
    }

    public  AlbumService getAlbumService(){
        if(restAdapter == null){
            throw  new NullPointerException("You need call build before get your service.");
        }
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
//    private static final String url = "http://10.0.1.34:8080/7beats/";
}
