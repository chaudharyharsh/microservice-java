package com.microservice.product.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class ApiResponse{
	
public ResponseEntity<Object> commonResponse(boolean success, String message, HttpStatus httpStatus, Object data){
        Map<String, Object> response = new HashMap<>();
        response.put("success",success);
        response.put("message", message);
        response.put("httpStatus",httpStatus);
        response.put("data",data);
        return new ResponseEntity<>(response,httpStatus);
    }
}
