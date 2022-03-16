package com.meli.orderbook.dto;

import com.meli.orderbook.enums.OperationType;
import com.meli.orderbook.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
public class OperationDto {

    @DecimalMin(value = "0.1")
    private BigDecimal value;

    @DecimalMin(value = "1")
    private int quantity;

    private String user;

    @NotEmpty
    private Status status;

    @NotEmpty
    private OperationType operationType;

}
