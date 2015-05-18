package br.com.network.streaming.playlist;

import br.com.network.system.util.DAOManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Augusto Cesar
 */
@Path("/rest/playlists")
public class PlayListServices {
    private PlayListDAO playlistDAO = null;
    
    public PlayListServices() throws ClassNotFoundException{
        this.playlistDAO = DAOManager.getPlayListDAO();
    }
    
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlayList getPlayListById(@PathParam("id") int id){
        PlayList playList = playlistDAO.getPlayListById(id);
        return playList;
    }
}
