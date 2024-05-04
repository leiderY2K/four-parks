package com.project.layer.Persistence.Error;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    //private HttpStatus status;
    private  String code;
    private String message;

}
