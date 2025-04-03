package cmu.edu.ds.errors;

import feign.Response;
import feign.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomFeignException extends RuntimeException {
    private final int status;
    private final Response.Body body;

    public CustomFeignException(int status, Response.Body body) {
        super("Feign client error: " + status);
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public Response.Body getBody() {
        return body;
    }
}

//package cmu.edu.ds.errors;
//
//import feign.Response;
//import feign.Util;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class CustomFeignException extends RuntimeException {
//    private final int status;
//    private final String responseBody;
//    private final String requestUrl;
//    private final String methodKey;
//
//    public CustomFeignException(int status, String methodKey, String requestUrl,
//                                Response.Body body) {
//        super("Feign client error: " + status);
//        this.status = status;
//        this.methodKey = methodKey;
//        this.requestUrl = requestUrl;
//        this.responseBody = parseBody(body);
//    }
//
//    private String parseBody(Response.Body body) {
//        try {
//            return body == null ? null : Util.toString(body.asReader());
//        } catch (IOException e) {
//            return "Unable to read response body: " + e.getMessage();
//        }
//    }
//
//    // Getters
//    public int getStatus() { return status; }
//    public String getResponseBody() { return responseBody; }
//    public String getRequestUrl() { return requestUrl; }
//    public String getMethodKey() { return methodKey; }
//}