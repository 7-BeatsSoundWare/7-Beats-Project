package br.com.network.system.util;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.mvc.Viewable;

/**
 *
 * @author Augusto Cesar
 */
@Stateless
@Path("/")
public class MainService {    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index(@Context HttpServletRequest req, @Context HttpServletResponse res){
        return new Viewable("/index.jsp");
    }
}
