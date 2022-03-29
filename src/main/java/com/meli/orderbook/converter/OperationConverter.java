package com.meli.orderbook.converter;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.enums.operation.Status;
import com.meli.orderbook.model.Id;
import com.meli.orderbook.model.Operation;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
public class OperationConverter implements Function<OperationDto, Operation> {

    private final AuditConverter auditConverter;

    public OperationConverter(AuditConverter auditConverter) {
        this.auditConverter = auditConverter;
    }

    @Override
    public Operation apply(OperationDto dto) {

        var operation = new Operation();
        var audit = auditConverter.apply(operation);
        var requestId = UUID.randomUUID();
        operation.setId(new Id(dto.getUserId(), requestId));
        operation.setValue(dto.getValue());
        operation.setQuantity(dto.getQuantity());
        operation.setOperationStatus(Status.IN_TRADE);
        operation.setOperationType(dto.getOperationType());
        operation.setTraceId(requestId);
        operation.setAudit(audit);
        operation.setHash(operation.hash());

        return operation;
    }
}
