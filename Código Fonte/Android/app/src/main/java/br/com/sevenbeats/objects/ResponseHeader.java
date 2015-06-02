package br.com.sevenbeats.objects;

public class ResponseHeader{

    private Object data;
    private Boolean error;

    public ResponseHeader(Object data, Boolean error) {
        this.data = data;
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean hasError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}