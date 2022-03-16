package com.meli.orderbook.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiErrorMessage {

    private HttpStatus status;
    private int error;
    private String message;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", error=" + error +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
