package br.com.network.streaming.playlist;

/**
 *
 * @author Augusto Cesar
 */
public class PlayListSQLUtil {
    private static final String NOME_TABELA = " playlists ";
    private static final String NOME_TABELA_JOIN_MUSICAS = " playlist_musica ";
    
    private static final String COLUNA_ID = " id ";
    private static final String COLUNA_NOME = " nome ";
    private static final String COLUNA_CRIADO_EM = " criado_em ";
    private static final String COLUNA_ID_CRIADOR = " id_criador ";
    
    public static String SQL_GET_BY_ID_CRIADOR = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_CRIADO_EM + "," +
                COLUNA_ID_CRIADOR +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ID_CRIADOR + "= ?";
    
    
    public static String SQL_GET_BY_ID = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_CRIADO_EM + "," +
                COLUNA_ID_CRIADOR +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ID + "= ?";
}
