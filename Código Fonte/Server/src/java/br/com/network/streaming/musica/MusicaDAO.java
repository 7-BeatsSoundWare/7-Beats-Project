package br.com.network.streaming.musica;

import java.util.List;


public interface MusicaDAO {

    /**
     * Metodo para buscar todas as musicas cadastradas no banco de dados.
     * 
     * @return Lista de instancias de Musica.
     */
    public List<Musica> getMusicas();
    
    /**
     * Metodo para buscar uma musica pelo id
     *
     * @param id Id da Musica
     * @return Instancia da Musica
     */
    public Musica getMusicaById(int id);

    /**
     * Metodo para buscar musicas que tenham nome similar ao passado como
     * parametro.
     *
     * @param nome Nome que sera pesquisado.
     * @return Lista de instancias de Musica.
     */
    public List<Musica> getMusicasByLikeNome(String nome);
}
