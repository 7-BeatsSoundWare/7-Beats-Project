package br.com.network.system.analytics.acesso;

/**
 *
 * @author Augusto Cesar
 */
public interface AcessoDAO {
    
    /**
     * Metodo para cadastrar um novo Acesso.
     * 
     * @param acesso Instancia de Acesso.
     */
    public void insertAcesso(Acesso acesso);
}
