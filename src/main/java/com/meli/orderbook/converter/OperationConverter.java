package com.meli.orderbook.converter;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.model.Operation;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OperationConverter implements Function<OperationDto, Operation> {

    @Override
    public Operation apply(OperationDto dto) {
        var operation = new Operation();
        operation.setValue(dto.getValue());
        operation.setQuantity(dto.getQuantity());
        operation.setStatus(dto.getStatus());
        return operation;
    }
}
