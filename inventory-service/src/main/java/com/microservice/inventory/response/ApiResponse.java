package com.microservice.inventory.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.Data;

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