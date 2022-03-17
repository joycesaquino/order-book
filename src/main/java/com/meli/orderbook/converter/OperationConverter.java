package com.meli.orderbook.converter;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.model.Id;
import com.meli.orderbook.model.Operation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class OperationConverter implements Function<OperationDto, Operation> {

    @Override
    public Operation apply(OperationDto dto) {

        var operation = new Operation();
        operation.setId(new Id(dto.getStatus(),dto.getUser(), LocalDateTime.now()));
        operation.setValue(dto.getValue());
        operation.setQuantity(dto.getQuantity());
        operation.setStatus(dto.getStatus());
        operation.setType(dto.getOperationType());
        return operation;
    }
}
