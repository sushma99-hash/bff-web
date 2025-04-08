package cmu.edu.ds.errors;

import feign.Response;
import feign.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

//public class CustomFeignException extends RuntimeException {
//    private final int status;
//    private final Response.Body body;
//
//    public CustomFeignException(int status, Response.Body body) {
//        super("Feign client error: " + status);
//        this.status = status;
//        this.body = body;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public Response.Body getBody() {
//        return body;
//    }
//}

public class CustomFeignException extends RuntimeException {
    private final int status;
    private final String responseBody;

    public CustomFeignException(int status, String responseBody) {
        super("Feign client error: " + status);
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
