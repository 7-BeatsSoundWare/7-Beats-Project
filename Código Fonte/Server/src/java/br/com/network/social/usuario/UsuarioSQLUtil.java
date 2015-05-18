package br.com.network.social.usuario;

/**
 *
 * @author Augusto Cesar
 */
public class UsuarioSQLUtil {
    private static final String NOME_TABELA = " usuarios ";
    
    private static final String COLUNA_ID = " id ";
    private static final String COLUNA_USERNAME = " username ";
    private static final String COLUNA_NOME = " nome ";
    private static final String COLUNA_URL_IMG = " url_img_perfil ";
    private static final String COLUNA_ULTIMO_LOGIN = " ultimo_login ";
    private static final String COLUNA_SENHA_MD5 = " senha_MD5 ";
    
    public static String SQL_GET_BY_ID = 
            "SELECT" + 
                COLUNA_ID + "," +
                COLUNA_USERNAME + "," +
                COLUNA_NOME + "," +
                COLUNA_URL_IMG + "," +
                COLUNA_ULTIMO_LOGIN +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_ID + "= ?";
    
    public static String SQL_GET_FOR_LOGIN = 
            "SELECT" + 
                COLUNA_ID + "," +
                COLUNA_USERNAME + "," +
                COLUNA_NOME + "," +
                COLUNA_URL_IMG + "," +
                COLUNA_ULTIMO_LOGIN + ", " +
                COLUNA_SENHA_MD5 +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_USERNAME + "= ?";
    
    public static String SQL_GET_LISTA =
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_USERNAME + "," +
                COLUNA_NOME + "," +
                COLUNA_URL_IMG + "," +
                COLUNA_ULTIMO_LOGIN +
            "FROM" +
                NOME_TABELA;
    
    public static String SQL_GET_BY_LIKE_USERNAME = 
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_USERNAME + "," +
                COLUNA_NOME + "," +
                COLUNA_URL_IMG + "," +
                COLUNA_ULTIMO_LOGIN +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_USERNAME + "LIKE ?" ;
    
    public static String SQL_GET_BY_LIKE_NOME =
            "SELECT" +
                COLUNA_ID + "," +
                COLUNA_USERNAME + "," +
                COLUNA_NOME + "," +
                COLUNA_URL_IMG + "," +
                COLUNA_ULTIMO_LOGIN +
            "FROM" +
                NOME_TABELA +
            "WHERE" +
                COLUNA_NOME + "LIKE ?" ;
}
