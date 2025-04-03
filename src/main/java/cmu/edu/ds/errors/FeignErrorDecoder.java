package cmu.edu.ds.errors;//package cmu.edu.ds.errors;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return new CustomFeignException(response.status(), response.body());
    }
}

//package cmu.edu.ds.errors;
//
//import feign.Response;
//import feign.codec.ErrorDecoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FeignErrorDecoder implements ErrorDecoder {
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        return new CustomFeignException(
//                response.status(),
//                methodKey,
//                response.request().url(),
//                response.body()
//        );
//    }
//}