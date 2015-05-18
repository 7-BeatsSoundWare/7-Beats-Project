package br.com.network.system.analytics.filters;

import br.com.network.system.analytics.acesso.Acesso;
import br.com.network.system.analytics.acesso.AcessoDAO;
import br.com.network.system.util.DAOManager;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Augusto Cesar
 */
@Provider
public class RequestFilter implements ContainerRequestFilter{

    @Context
    private HttpServletRequest servletRequest;
    
    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        try {
            AcessoDAO acessoDAO = DAOManager.getAcessoDAO();
            Acesso acesso = new Acesso();
            
            String userAgent = ctx.getHeaders().get("user-agent").get(0);
            String ip = servletRequest.getRemoteAddr();
            Date dataAcesso = new Date();
            String requestedURI = servletRequest.getRequestURI();
            
            acesso.setUserAgent(userAgent);
            acesso.setIp(ip);
            acesso.setDataAcesso(dataAcesso);
            acesso.setRequestedURL(requestedURI);
            
            acessoDAO.insertAcesso(acesso);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }    
}
