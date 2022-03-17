package com.meli.orderbook.model;

import com.meli.orderbook.enums.operation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class Id {

    private Status status;
    private String user;
    private LocalDateTime timestamp;

}
