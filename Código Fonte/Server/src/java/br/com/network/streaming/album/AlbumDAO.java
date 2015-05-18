package br.com.network.streaming.album;

import java.util.List;
import java.util.Set;

/**
 * Interface pra implementacao do DAO de Album.
 *
 * @author Augusto Cesar
 *
 */
public interface AlbumDAO {

    /**
     * Metodo para buscar todos os albuns;
     *
     * @return Lista com todos os albuns;
     */
    public List<Album> getAlbuns();

    /**
     * Metodo para pesquisar um Album a partir do id do Album.
     *
     * @param id Id do Album.
     * @return Instancia de Album.
     */
    public Album getAlbumById(int id);

    /**
     * Metodo para pesquisar Albuns que contenham no nome o valor passado.
     *
     * @param nome Nome ou parte do nome do Album.
     * @return Lista de instancias de Album.
     */
    public List<Album> getAlbunsByLikeNome(String nome);

    /**
     * Metodo para pesquisar albuns que contenham no nome do autor o valor
     * passado.
     *
     * @param autor Nome ou parte do nome do autor do Album.
     * @return Lista de instancias de Album.
     */
    public List<Album> getAlbunsByLikeAutor(String autor);

    /**
     * Metodo para pesquisar albuns que tenham como data de lan�amento a data
     * passada.
     *
     * @param ano Ano que o Album tenha sido lancado.
     * @return Lista de instancia de Album.
     */
    public List<Album> getAlbunsByAnoLancamento(int ano);

}
