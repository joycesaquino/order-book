package com.meli.orderbook.dto;

import com.meli.orderbook.enums.OperationType;
import com.meli.orderbook.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OperationDto {

    private String immutableId;
    private BigDecimal value;
    private int quantity;
    private String user;
    private Status status;
    private OperationType operationType;

}
