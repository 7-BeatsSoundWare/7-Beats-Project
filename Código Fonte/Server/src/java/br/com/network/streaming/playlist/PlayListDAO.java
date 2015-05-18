package br.com.network.streaming.playlist;

import java.util.List;

/**
 *
 * @author Augusto Cesar
 */
public interface PlayListDAO {
    /**
     * Metodo para buscar todas as PlayLists de um determinado Usuario.
     * 
     * @param idCriador Id do Usuario que criou a PlayList.
     * @return Lista de instancias de PlayList.
     */
    public List<PlayList> getPlayListsByIdCriador(int idCriador);
    
    /**
     * Metodo para buscar uma determinada PlayList pelo seu Id
     * 
     * @param id Id da PlayList.
     * @return Instancia da PlayList.
     */
    public PlayList getPlayListById(int id);
}
