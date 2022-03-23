package com.meli.orderbook.service;

import com.meli.orderbook.converter.OperationConverter;
import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.model.Audit;
import com.meli.orderbook.model.Operation;
import com.meli.orderbook.repository.OperationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

    @InjectMocks
    OperationService service;

    @Mock
    OperationRepository repository;

    @Mock
    OperationConverter converter;

    @Nested
    class OperationTest {

        @Test
        @DisplayName("Should save operation in Database with success")
        void shouldSaveOperationWithSuccess() {

            final var dto = new OperationDto();
            final var audit = new Audit();
            final var operation = new Operation();
            operation.setAudit(audit);
            when(converter.apply(dto)).thenReturn(operation);
            assertDoesNotThrow(() -> service.save(dto));
            verify(repository).save(operation);

        }
    }

}