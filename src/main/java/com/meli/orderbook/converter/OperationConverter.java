package com.meli.orderbook.converter;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.model.Audit;
import com.meli.orderbook.model.Id;
import com.meli.orderbook.model.Operation;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import java.util.function.Function;

@Component
public class OperationConverter implements Function<OperationDto, Operation> {

    AuditConverter auditConverter;

    @Override
    public Operation apply(OperationDto dto) {

        var operation = new Operation();
        var audit = auditConverter.apply(operation);
        operation.setId(new Id(dto.getStatus(), dto.getUser(), audit.getRequestId()));
        operation.setValue(dto.getValue());
        operation.setQuantity(dto.getQuantity());
        operation.setStatus(dto.getStatus());
        operation.setType(dto.getOperationType());
        operation.setAudit(audit);

        operation.setAudit(audit);

        return operation;
    }
}
