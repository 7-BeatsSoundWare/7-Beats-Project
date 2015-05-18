package br.com.network.streaming.musica;

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
@Path("/rest/musicas")
public class MusicaServices {

    private MusicaDAO musicaDAO = null;

    public MusicaServices() throws ClassNotFoundException {
        this.musicaDAO = DAOManager.getMusicaDAO();
    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Musica> getMusicas(){
        List<Musica> musicas = musicaDAO.getMusicas();
        return musicas;
    }
    
    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Musica getMusicaById(@PathParam("id") int id) {
        Musica musica = musicaDAO.getMusicaById(id);
        return musica;
    }
}
