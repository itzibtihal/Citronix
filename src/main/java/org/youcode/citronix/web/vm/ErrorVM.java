package org.youcode.citronix.web.vm;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorVM {
    private int status;
    private String message;
    private long timestamp;
}
