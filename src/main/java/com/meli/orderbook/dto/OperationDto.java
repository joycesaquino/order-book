package com.meli.orderbook.dto;

import com.meli.orderbook.enums.operation.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OperationDto {

    @DecimalMin(value = "0.1")
    private BigDecimal value;

    @DecimalMin(value = "1")
    private int quantity;

    private Long userId;

    @NotNull
    private Type operationType;

}
