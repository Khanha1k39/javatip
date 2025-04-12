//package com.example.demo.utils;
//
//import com.example.demo.domain.RestResponse;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//@RestControllerAdvice
//public class FormatRestResponse implements ResponseBodyAdvice<Object> {
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        int status = httpResponse.getStatus();
//        RestResponse<Object> responseEntity = new RestResponse<Object>();
//        responseEntity.setStatusCode(status);
//        if (status >= 400) {
//            return body;
//        }else{
//            responseEntity  .setData(body);
//            responseEntity.setMessage("Call api success");
//
//        }
//        return responseEntity;
//
//    }
//}
