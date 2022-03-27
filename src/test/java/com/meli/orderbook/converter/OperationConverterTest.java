package com.meli.orderbook.converter;

import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.enums.operation.Status;
import com.meli.orderbook.enums.operation.Type;
import com.meli.orderbook.model.Audit;
import com.meli.orderbook.model.Operation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationConverterTest {

    @InjectMocks OperationConverter converter;
    @Mock AuditConverter auditConverter;

    @Nested
    class OperationTest {
        @Test
        @DisplayName("This test should apply conversion considering BUY operation context")
        void shouldApplyConversionConsideringSaleOperationContext() {

            var dto = new OperationDto();
            dto.setOperationType(Type.SALE);
            var operation = new Operation();

            final var audit = new Audit();
            when(auditConverter.apply(operation)).thenReturn(audit);
            Operation apply = converter.apply(dto);
            assertEquals(apply.getOperationStatus(), Status.IN_OFFER);
        }


        @Test
        @DisplayName("This test should apply conversion considering SALE operation context")
        void shouldApplyConversionConsideringBuyOperationContext() {

            var dto = new OperationDto();
            dto.setOperationType(Type.BUY);
            var operation = new Operation();

            final var audit = new Audit();
            when(auditConverter.apply(operation)).thenReturn(audit);
            Operation apply = converter.apply(dto);
            assertEquals(apply.getOperationStatus(), Status.AVAILABLE);
        }
    }

}