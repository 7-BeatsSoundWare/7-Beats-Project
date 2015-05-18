package br.com.network.streaming.album;

import br.com.network.system.util.DAOManager;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Augusto César
 */
@Path("/rest/albuns")
public class AlbumServices {

    private AlbumDAO albumDAO = null;

    public AlbumServices() throws ClassNotFoundException {
        this.albumDAO = DAOManager.getAlbumDAO();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Album> getTodosAlbuns() {
        List<Album> albuns = this.albumDAO.getAlbuns();
        return albuns;
    }

    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Album getAlbumById(@PathParam("id") int id) {
        Album album = this.albumDAO.getAlbumById(id);
        return album;
    }
}
