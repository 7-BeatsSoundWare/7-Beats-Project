
package br.com.network.social.usuario;

import br.com.network.streaming.playlist.PlayList;
import br.com.network.streaming.playlist.PlayListDAO;
import br.com.network.system.util.DAOManager;
import br.com.network.system.util.Utils;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Augusto César
 */
@Path("/rest/usuarios")
public class UsuarioServices {
    private UsuarioDAO usuarioDAO = null;
    private PlayListDAO playlistDAO = null;
    
    public UsuarioServices() throws ClassNotFoundException{
        this.usuarioDAO = DAOManager.getUsuarioDAO();
        this.playlistDAO = DAOManager.getPlayListDAO();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getListaUsuarios(){
        List<Usuario> listaUsuarios = usuarioDAO.getListaUsuarios();
        return listaUsuarios;
    }
    
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuarioById(@PathParam("id") int id){
        Usuario usuario = usuarioDAO.getUsuarioById(id);
        return usuario;
    }
    
    @GET
    @Path("/id/{id}/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayList> getPlayListsByIdUsuario(@PathParam("id") int idUsuario){
        List<PlayList> playlists = playlistDAO.getPlayListsByIdCriador(idUsuario);
        return playlists;
    }
    
    @POST
    @Path("/auth")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario doLogin(String credentialsJson){
        Map<String, String> credentials = Utils.jsonToMap(credentialsJson);
        Usuario usuario = usuarioDAO.doLogin(credentials.get("username"), credentials.get("senha"));
        return usuario;
    }
}
