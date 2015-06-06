package br.com.sevenbeats.utils.mvc.interfaces.controller;


public interface Control {
    void onRequest(String method, String... params);
    void onResult(String method, Object result);
    void onError(String errorMethod, Object result);
    void notifyView(String methodName, Object... args);
}
