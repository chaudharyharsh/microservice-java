package com.microservice.order.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class ApiResponse{

    ResponseEntity<Object> responseEntity;
	
public ResponseEntity<Object> commonResponse(boolean success, String message, HttpStatus httpStatus, Object data){
        Map<String, Object> response = new HashMap<>();
        response.put("success",success);
        response.put("message", message);
        response.put("httpStatus",httpStatus);
        response.put("data",data);
        responseEntity = new ResponseEntity<>(response,httpStatus);
        return  responseEntity;
    }

    public ResponseEntity<Object> getResponse(){
        return responseEntity;
    }
}
