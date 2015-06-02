package br.com.sevenbeats.mvc.interfaces.controller;


public interface GenericController  extends PreparedController{
    void request(String methodName);
    void onRequestCompleted(Object response);
}
