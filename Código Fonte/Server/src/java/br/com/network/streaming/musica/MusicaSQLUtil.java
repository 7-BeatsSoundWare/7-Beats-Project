package br.com.network.streaming.musica;

/**
 *
 * @author Augusto Cesar
 */
public class MusicaSQLUtil {
    private static final String NOME_TABELA = " musicas ";
    private static final String NOME_TABELA_JOIN_PLAYLIST = " playlist_musica ";
    
    private static final String COLUNA_ID = " id ";
    private static final String COLUNA_NOME = " nome ";
    private static final String COLUNA_ANO_LANCAMENTO = " ano_lancamento ";
    private static final String COLUNA_URL = " url ";
    private static final String COLUNA_ID_ALBUM = " id_album ";
    
    public static String SQL_GET_MUSICAS = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL + "," +
                COLUNA_ID_ALBUM +
            "FROM" +
                NOME_TABELA;
    
    public static String SQL_GET_BY_ID = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL + "," +
                COLUNA_ID_ALBUM +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ID + "= ?";
    
    public static String SQL_GET_BY_ID_PLAYLIST = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL + "," +
                COLUNA_ID_ALBUM +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ID + "IN" +
                    "(" +
                        "SELECT" +
                            " id_musica " +
                        "FROM" +
                            NOME_TABELA_JOIN_PLAYLIST +
                        "WHERE" +
                            " id_playlist = ?" +
                    ")";
}
