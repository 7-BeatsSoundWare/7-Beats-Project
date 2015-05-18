package br.com.network.streaming.album;

/**
 *
 * @author Augusto Cesar
 */
public class AlbumSQLUtil {
    private static final String NOME_TABELA = " albuns ";
    
    private static final String COLUNA_ID = " id ";
    private static final String COLUNA_NOME = " nome ";
    private static final String COLUNA_AUTOR = " autor ";
    private static final String COLUNA_ANO_LANCAMENTO = " ano_lancamento ";
    private static final String COLUNA_URL_IMG_CAPA = " url_img_capa ";
    
    public static String SQL_GET_ALBUNS = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_AUTOR + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL_IMG_CAPA +
            "FROM" +
                NOME_TABELA;
    
    public static String SQL_GET_BY_ID = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_AUTOR + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL_IMG_CAPA +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ID + "= ?";
    
    public static String SQL_GET_BY_LIKE_NOME = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_AUTOR + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL_IMG_CAPA +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_NOME + " LIKE ?";
    
    public static String SQL_GET_BY_LIKE_AUTOR = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_AUTOR + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL_IMG_CAPA +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_AUTOR + " LIKE ?";
    
    public static String SQL_GET_BY_ANO_LANCAMENTO = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_NOME + "," +
                COLUNA_AUTOR + "," +
                COLUNA_ANO_LANCAMENTO + "," +
                COLUNA_URL_IMG_CAPA +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ANO_LANCAMENTO + "= ?";
}
