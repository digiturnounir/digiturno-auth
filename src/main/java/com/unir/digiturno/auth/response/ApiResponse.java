package com.unir.digiturno.auth.response;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private int code;
    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, int code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}


