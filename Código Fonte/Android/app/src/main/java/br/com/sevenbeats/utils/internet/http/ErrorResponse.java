package br.com.sevenbeats.utils.internet.http;

/**
 * Created by diogojayme on 6/5/15.
 */
public class ErrorResponse {
    private int errorId;
    private String errorMessage;

    public ErrorResponse(int errorId, String errorMessage) {
        this.errorId = errorId;
        this.errorMessage = errorMessage;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
