package br.com.sevenbeats.mvc.interfaces.model;

/**
 * Construído com o propósito de comunicar com a camada de DOMAIN (package domain)
 * com intuido de permitir qualquer classe implementa Generic Model instanciar a camada de Domínio (package domain)
 * */
public interface GenericModel extends PreparedModel {
    void onModelDestroy();
}
