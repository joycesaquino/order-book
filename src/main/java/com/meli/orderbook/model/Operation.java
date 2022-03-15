package com.meli.orderbook.model;

import com.meli.orderbook.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Operation {

    private Long id;
    private UUID requestId;
    private String hash;

    private BigDecimal value;
    private int quantity;
    private String user;
    private Status status;
    private Audit audit;

}
