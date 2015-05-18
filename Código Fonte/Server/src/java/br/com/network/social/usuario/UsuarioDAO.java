package br.com.network.social.usuario;

import java.util.List;

/**
 *
 * @author Augusto César
 */
public interface UsuarioDAO {

    /**
     * Metodo para buscar um Usuario pelo seu id.
     *
     * @param id Id do usuario.
     * @return Instancia de Usuario.
     */
    public Usuario getUsuarioById(int id);    
    
    /**
     * Metodo para trazer a lista de Usuarios cadastrados no sistema.
     *
     * @return Lista de Usuarios
     */
    public List<Usuario> getListaUsuarios();

    /**
     * Metodo para buscar usuarios pelo seu Username. (Nao traz a senha_MD5).
     *
     * @param username
     * @return Lista de Usuarios.
     */
    public List<Usuario> getUsuariosByLikeUsername(String username);

    /**
     * Metodo para buscar usuarios pelo seu Nome. (Nao traz a senha_MD5).
     *
     * @param nome Nome para pesquisar
     * @return Lista de Usuarios.
     */
    public List<Usuario> getUsuariosByLikeNome(String nome);

    /**
     * Metodo para efetuar o login e retornar os dados do usuario caso o login
     * seja bem sucedido. (Nao traz a senha_MD5).
     *
     * @param username Username
     * @param senha Senha
     * @return Instancia de usuario.
     */
    public Usuario doLogin(String username, String senha);
}
