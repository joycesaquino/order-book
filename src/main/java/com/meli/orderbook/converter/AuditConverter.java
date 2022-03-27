package com.meli.orderbook.converter;

import com.meli.orderbook.model.Audit;
import com.meli.orderbook.model.Operation;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import java.util.function.Function;

@Component
public class AuditConverter implements Function<Operation, Audit> {

    String API = "API";

    @Override
    public Audit apply(Operation operation) {

        var calendar = Calendar.getInstance();
        var timestamp = new Timestamp(calendar.getTime().getTime());

        var audit = new Audit();
        audit.setCreatedAt(timestamp);
        audit.setUpdatedAt(timestamp);
        audit.setUpdatedBy(API);

        return audit;
    }
}
