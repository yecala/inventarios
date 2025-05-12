package gm.inventarios.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private String error;
    private boolean success;
}