package br.com.sevenbeats.mvc.model;

/**
 * Created by diogojayme on 5/31/15.
 */
public interface PreparedModel{
    /**
     * E chamado quando o controle instancia o model
     * server para enviar uma mensagem para a view
     * de que o trabalho irá começar
     *
     * */
    void onPreparing();

    /**
     * Uma resposta inesperada ou erro aconteceu no model
     * @param response envia o objeto de resposta para
     * o listener
     *
     * */
    void onFailed(Object response);

    /**
     * O processamento foi bem sucedido, uma resposta com o
     * @param response para o listener
     *
     * */
    void onSuccess(Object response);
}
