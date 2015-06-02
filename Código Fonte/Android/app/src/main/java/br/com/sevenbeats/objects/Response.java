package br.com.sevenbeats.objects;

/**
 * Created by diogojayme on 6/2/15.
 */
public class Response {

    private int id;
    private String caller;
    private ResponseHeader header;

    public Response(String name, ResponseHeader header) {
        this.id = name.hashCode();
        this.caller = name;
        this.header = header;
    }

    public String getMethodCaller() {
        return caller;
    }

    public void setName(String name) {
        this.caller = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }


}
