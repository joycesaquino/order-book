package com.meli.orderbook.converter;

import com.meli.orderbook.model.Audit;
import com.meli.orderbook.model.Operation;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.function.Function;

import static java.time.ZoneOffset.UTC;

@Component
public class AuditConverter implements Function<Operation, Audit> {

    String API = "API";

    @Override
    public Audit apply(Operation operation) {

        var audit = new Audit();
        audit.setCreatedAt(ZonedDateTime.now(UTC));
        audit.setUpdatedAt(ZonedDateTime.now(UTC));
        audit.setUpdatedBy(API);

        return audit;
    }
}
