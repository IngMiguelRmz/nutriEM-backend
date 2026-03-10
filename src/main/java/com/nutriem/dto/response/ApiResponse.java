package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String  message;
    private T       data;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiResponse() {}

    public static <T> Builder<T> builder() { return new Builder<>(); }

    public static class Builder<T> {
        private boolean success;
        private String  message;
        private T       data;

        public Builder<T> success(boolean v)  { this.success = v; return this; }
        public Builder<T> message(String v)   { this.message = v; return this; }
        public Builder<T> data(T v)           { this.data = v; return this; }

        public ApiResponse<T> build() {
            ApiResponse<T> r = new ApiResponse<>();
            r.success = success; r.message = message; r.data = data;
            return r;
        }
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder().success(true).data(data).build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder().success(false).message(message).build();
    }

    public boolean isSuccess()              { return success; }
    public void setSuccess(boolean v)       { this.success = v; }
    public String getMessage()              { return message; }
    public void setMessage(String v)        { this.message = v; }
    public T getData()                      { return data; }
    public void setData(T v)                { this.data = v; }
    public LocalDateTime getTimestamp()     { return timestamp; }
}
