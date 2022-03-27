package com.meli.orderbook.service;

import com.meli.orderbook.converter.OperationConverter;
import com.meli.orderbook.dto.OperationDto;
import com.meli.orderbook.repository.OperationRepository;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationConverter operationConverter;

    public OperationService(OperationRepository operationRepository,
                            OperationConverter operationConverter) {
        this.operationRepository = operationRepository;
        this.operationConverter = operationConverter;
    }

    public void save(OperationDto dto) {

        var operation = operationConverter.apply(dto);
        operation.setHash(operation.hash());
        operationRepository.save(operation);

    }
}
