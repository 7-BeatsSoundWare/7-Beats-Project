package br.com.sevenbeats.mvc.controller;

/**
 * Created by diogojayme on 5/31/15.
 */
public interface PreparedController {

    /**
     * habilita a activity quando a mesma estiver preparada
     *
     * */

    void setViewPrepared(boolean prepared);


    /**
     * O controle retornará quando a view estiver preparada
     *
     * */

    boolean isViewPrepared();


    /**
     * Checa se algum erro ocorreu durante o processamento do Model
     *
     * */

    boolean hasError();


    /**
     * O model enviará uma mensagem no caso de ocorrencia de erros
     *
     * */

    void setError(boolean error);


    /**
     * O controle Notificará a VIEW quando receber uma resposta do Model
     *
     * */

    void notifyPrepared();
}
