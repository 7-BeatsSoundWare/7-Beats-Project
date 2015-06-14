package br.com.sevenbeats.utils.mvc.interfaces.controller;


public interface Controller {
    void onRequest(String method, String... params);
    void onResult(String method, Object result);
    void onError(String errorMethod);
    void notifyView(String methodName, Object... args);
    void finalizePendingWork();
}
