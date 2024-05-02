package com.project.layer.Persistence.Error;


import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    //private HttpStatus status;
    private  String code;
    private String message;

}
