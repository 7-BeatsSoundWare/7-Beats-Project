package br.com.sevenbeats.domain;

/**
 * Created by diogojayme on 5/31/15.
 */
public interface Response {
    void onResponse(Object responseSuccess, boolean success);
}
